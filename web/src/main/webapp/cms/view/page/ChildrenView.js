// file ChildrenView.js

define(['text!template/page/children.html',
        'view/page/context', 'view/page/version', 'view/page/breadcrumbs'], 
function(childrenHtml, ctx, version, breadcrumbs) {
	
	console.log('Loading ChildrenView.js');
	
	var parentURL = null;
	var children = null;
	    
	function postRender() {
		ctx.loadData = loadData;
	    //$("#page-dialog").dialog({ width: 400, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    // hover states on the link buttons
	    $('a.button').hover(
	     	function() { $(this).addClass('ui-state-hover'); },
	       	function() { $(this).removeClass('ui-state-hover'); }
	    ); 
	    version.initVersionDialog();
	    $('#addChildButton').click(onAddChild);
	    $('#deleteChildButton').click(onDelete);
	    $('#defaultSettingsButton').click(onDefaultSettings);
		
		$('ul.nav-tabs li:nth-child(3)').addClass('active');
	    
		$('#cancelDlgButton').click(onPageCancel);
	    $('#pageForm').submit(function() {onSave(); return false;});
	    $('#title').change(onTitleChange);

	}

	function loadData() {
		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
			ctx.pageRequest = r;
			ctx.page = ctx.pageRequest.page;
			loadPage();
			breadcrumbs.breadcrumbsShow();
		}, ctx.pageId, ctx.pageParentUrl);
	}

	function callLoadPage() {
		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
			ctx.pageRequest = r;
			ctx.page = ctx.pageRequest.page;
			ctx.editMode = ctx.pageId != null;
			loadPage();
		}, ctx.pageId, ctx.pageParentUrl);
	}

	function loadPage() {
		if (ctx.editMode) {
			pageId = String(ctx.page.id);
			pageParentUrl = ctx.page.parentUrl;
			loadChildren();
			version.loadVersions();
			version.showAuditInfo();
		} else {
			pages['1'] = ctx.page;
		}
		loadPagePermission();
	}

	function callLoadChildren() {
		Heiduc.jsonrpc.pageService.getChildren(function(r) {
			ctx.pageRequest.children = r;
			loadChildren();
		}, ctx.page.friendlyURL);
	}

	function loadChildren() {
		children = ctx.pageRequest.children.list;
	    var html = '<table class="table table-striped"><tr><th></th><th>' + messages('title') 
	    	+ '</th><th>' + messages('page.friendly_url') + '</th><th></th></tr>';
	    $.each(children, function (n, value) {
	        html += '<tr><td><input type="checkbox" value="' + value.id 
	        + '"/></td><td><a href="#page/content/' + value.id 
	        +'">' + value.title + '</a></td><td>' + value.friendlyURL + '</td>\
			<td><a class="pageup" data-n="' + n + '"><img src="/static/images/02_up.png"/></a>\
	        <a class="pagedown" data-n="' + n + '"><img src="/static/images/02_down.png"/></a>\
	        </td></tr>';
	    });
	    $('#children').html(html + '</table>');
	    $('#children tr:even').addClass('even');
	    $('a.pageup').click(function() {
	    	onPageUp(Number($(this).attr('data-n')));
	    });
	    $('a.pagedown').click(function() {
	    	onPageDown(Number($(this).attr('data-n')));
	    });
	}

	function onAddChild() {
		$('#ui-dialog-title-page-dialog').text(messages('pages.new_page'));
		parentURL = ctx.page.friendlyURL == '/' ? '' : ctx.page.friendlyURL;
		//$('#page-dialog').dialog('open');
		$('#page-dialog').modal({show:true});
		$('#parentURL').html(parentURL + '/');
		$('#title').val('');
		$('#url').val('');
		$('#title').focus();
	}

	function onDelete() {
		var ids = [];
		$('#children input:checked').each(function() {
			ids.push(this.value);
		});
		if (ids.length == 0) {
			Heiduc.info(messages('nothing_selected'));
			return;
		}
		if (confirm(messages('are_you_sure'))) {
			Heiduc.jsonrpc.pageService.deletePages(function(r) {
				Heiduc.showServiceMessages(r);
				callLoadChildren();
			}, Heiduc.javaList(ids));
		}
	}

	function loadPagePermission() {
	    var r = ctx.pageRequest.pagePermission;
	   	if (r.changeGranted) {
	   		$('#addChildButton').show();
	   		$('#deleteChildButton').show();
	   	}
	   	else {
	   		$('#addChildButton').hide();
	   		$('#deleteChildButton').hide();
	   	}
	   	if (r.admin && ctx.editMode) {
	   		$('.securityTab').show();
	   	}
	   	else {
	   		$('.securityTab').hide();
	   	}
	}

	function onPageUp(i) {
		if (i - 1 >= 0) {
			Heiduc.jsonrpc.pageService.moveUp(function(r) {}, children[i].id);
	        children[i].sortIndex--;
	        children[i - 1].sortIndex++;
			swapPages(i, i - 1);
			callLoadChildren();
		}
	}

	function onPageDown(i) {
		if (i + 1 < children.length) {
			Heiduc.jsonrpc.pageService.moveDown(function(r) {}, children[i].id);
	        children[i + 1].sortIndex--;
	        children[i].sortIndex++;
			swapPages(i, i + 1);
			callLoadChildren();
		}
	}

	function swapPages(i, j) {
		var tmp = children[j];
		children[j] = children[i];
		children[i] = tmp;
	}

	function onDefaultSettings() {
		Heiduc.jsonrpc.pageService.getPageDefaultSettings(function(r) {
			location.href = "#page/" + r.id;
		}, ctx.page.friendlyURL);
	}

	function onPageCancel() {
		//$('#page-dialog').dialog('close');
	}

	function onTitleChange() {
		var url = $("#url").val();
		var title = $("#title").val();
		if (url == '') {
			$("#url").val(Heiduc.urlFromTitle(title));
		}
	}

	function validate(vo) {
		if (vo.title == '') {
			return messages('title_is_empty');
		}
		else {
			if (vo.title.indexOf(',') != -1) {
				return messages('pages.coma_not_allowed');
			}
		}
		if (vo.url == '') {
			return messages('pages.url_is_empty');
		}
		else {
			if (vo.url.indexOf('/') != -1) {
				return messages('pages.slash_not_allowed');
			}
		}
	}

	function onSave() {
		var vo = {
			id : '',
			title : $('#title').val(),
			url : $('#url').val(),
			friendlyUrl : parentURL + '/' + $('#url').val()
		};
		var error = validate(vo);
		if (!error) {
			Heiduc.jsonrpc.pageService.savePage(function(r) {
				if (r.result == 'success') {
					Heiduc.info(messages('pages.success_created'));
					//$('#page-dialog').dialog('close');
					location.href = '#page/content/' + r.message;
				}
				else {
					showError(r.message);
				}
			}, Heiduc.javaMap(vo));
		}
		else {
			showError(error);
		}
	}

	function showError(msg) {
		Heiduc.errorMessage('#pageMessages', msg);
	}

	
	return Backbone.View.extend({
		
		//css: '/static/css/children.css',
		
		el: $('#tab-1'),

		tmpl: _.template(childrenHtml),
		
		render: function() {
			//Heiduc.addCSSFile(this.css);
			this.el = $('#tab-1');
			this.el.html(this.tmpl({messages:messages}));
			postRender();
		},
		
		remove: function() {
		    //$("#page-dialog").dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFile(this.css);
		}
		
	});
	
});
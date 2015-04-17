// file PagesView.js

define(['text!template/pages.html',
        'jquery.treeview',
        'view/pages/PageSearchComponent'], function(tmpl) {
	
	var parentURL = null;
	var root = null;
	var page = null;
	var showTitle = $.cookie("pages.showTitle") != 'names';
	var invertOrder = $.cookie("pages.invertOrder") == 'true';
	var searchUI = null;
	
	function loadData() {
		loadTree();
		loadUser();
	    searchUI = Heiduc.PageSearchComponent('#pageSearch');
	}

	function isRoot() {
		return page && page.friendlyURL == '/';
	}
	
	function loadTree() {
		Heiduc.jsonrpc.pageService.getTree(function(r) {
			root = r;
			if (invertOrder) {
				invertChildrenOrder(r);
			}
			$('#pages-tree').html(renderPage(r));
			$("#pages-tree").treeview({
				animated: "fast",
				collapsed: true,
				unique: true,
				persist: "cookie",
				cookieId: "pageTree"
			});
			$('.content-link').each(function () {
				$(this).mouseover(function(event) {
					$('.page_edit').hide()
					$(event.target).siblings('.page_edit').show()			
				});
			});
			$('#pages-tree li').each(function () {
				$(this).hover(function(event) {
					$('.page_edit').hide();
					$('> .page_edit', event.target).show();			
				}, function(event) {
					//$('> .page_edit', event.target).hide();			
				});
			});
		});
	}

	function renderPage(vo) {
		var pageUrl = encodeURIComponent(vo.entity.friendlyURL);
		var title = showTitle ? vo.entity.title : vo.entity.pageFriendlyURL;
		if (!title) {
			title = '/';
		}
		var p = vo.entity.hasPublishedVersion ? 'published' : 'unpublished';
		var published_msg = messages(p);
		var published_link = ' <img src="/static/images/'+ p +'.png" title="' 
			+ published_msg + '" width="16px" />';
		if (!vo.entity.hasPublishedVersion) {
			published_link = ' <a onclick="Heiduc.app.pagesView.onPagePublish(' 
				+ vo.entity.id + ')">'
				+ '<img src="/static/images/'+ p +'.png" title="' 
				+ published_msg + '" width="16px" /></a>';
		}
		var html = '<li> ' + published_link
		
				+ ' <a href="#page/content/' + vo.entity.id + '" title="'
				+ messages('page.edit_content') + '" class="content-link">'
				+ title + '</a> '
				
				+ '<span class="page_edit" style="display:none">'
				
				+ '<a title="' + messages('add_child') 
				+ '" onclick="Heiduc.app.pagesView.onPageAdd(\'' + vo.entity.friendlyURL
				+ '\')"><img src="/static/images/add.png"/></a> '
				
				+ '<a title="' + messages('remove') 
				+ '" onclick="Heiduc.app.pagesView.onPageRemove(\'' 
				+ vo.entity.friendlyURL + '\')">'
				+ '<img src="/static/images/02_x.png" /></a> '
				
				+ '<a href="#page/' + vo.entity.id + '" title="'
				+ messages('page.edit_properties') + '">'
				+ '<img src="/static/images/pencil.png" /></a> '
				
				+ '<a onclick="Heiduc.app.pagesView.onChangeTitle(' + vo.entity.id + ')" title="'
				+ messages('page.edit_url_title') + '">'
				+ '<img src="/static/images/globe.png" /></a>'
				
				+ '</span>';
		if (vo.children.list.length > 0) {
			html += '<ul>';
			$.each(vo.children.list, function(n, value) {
				html += renderPage(value);
			});
			html += '</ul>';
		}
		return html + '</li>';
	}

	function loadUser() {
		if (!Heiduc.app.user.admin) {
		    $('#structuresTab').hide();
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
		if (vo.url == '' && !(page && page.friendlyURL == '/')) {
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
			id : page == null ? '' : String(page.id),
			title : $('#title').val(),
			url : $('#url').val(),
			friendlyUrl : (isRoot() ? '/' : parentURL + '/' + $('#url').val())
		};
		var error = validate(vo);
		if (!error) {
			Heiduc.jsonrpc.pageService.savePage(function(r) {
				if (r.result == 'success') {
					Heiduc.info(messages('pages.success_created'));
					//$('#page-dialog').dialog('close');
					$('#page-dialog').modal('hide');
					loadData();
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

	function findPage(id) {
		return findChildPage(root, id);
	}

	function findChildPage(page, id) {
		if (page.entity.id == id) {
			return page;
		}
		var result = null;
		if (page.children.list.length > 0) {
			$.each(page.children.list, function(i,value) {
				var res = findChildPage(value, id);
				if (res != null) {
					result = res;
				}
			});
		}
		return result;
	}

	function renderShowTitle() {
		if (showTitle) {
			$('#showTitle').html('<a href="#" onclick="Heiduc.app.pagesView.onShowTitle(false)">'
					+ messages('show_names') + '</a>');
		}
		else {
			$('#showTitle').html('<a href="#" onclick="Heiduc.app.pagesView.onShowTitle(true)">'
					+ messages('show_titles') + '</a>');
		}
	}

	function renderInvertOrder() {
		if (invertOrder) {
			$('#invertOrder').html('<a href="#" onclick="Heiduc.app.pagesView.onInvertOrder(false)">'
					+ messages('restore_order') + '</a>');
		}
		else {
			$('#invertOrder').html('<a href="#" onclick="Heiduc.app.pagesView.onInvertOrder(true)">'
					+ messages('invert_order') + '</a>');
		}
	}

	function invertChildrenOrder(vo) {
		vo.children.list.reverse();
		if (vo.hasChildren) {
			$.each(vo.children.list, function (n, value) {
				invertChildrenOrder(value);
			});
		}
	}
	
	
	return Backbone.View.extend({
		
		css: ['/static/css/jquery.treeview.css', '/static/css/pages.css'],
		
		el: $('#content'),
		
		events: {},
		
		initialize: function() {},
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(_.template(tmpl, {messages: messages}));
			
			//$("#page-dialog").dialog({ width: 400, autoOpen: false });
			Heiduc.initJSONRpc(loadData);
			//$('#cancelDlgButton').click(function() {
			//	$('#page-dialog').dialog('close');				
			//});
		    $('#pageForm').submit(function() {onSave(); return false;});
		    $('#title').change(this.onTitleChange);
		    renderShowTitle();
		    renderInvertOrder();

		},
		
		remove: function() {
		    //$("#page-dialog").dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		},
		
		onPageRemove: function(url) {
			if (url == '/') return; 
			if (confirm(messages('are_you_sure'))) {
				Heiduc.jsonrpc.pageService.remove(function(r) {
					Heiduc.showServiceMessages(r);
					loadData();
				}, url);
			}
		},

		onPageAdd: function(parent) {
			$('#ui-dialog-title-page-dialog').text(messages('pages.new_page'));
			parentURL = parent == '/' ? '' : parent;
			$('#page-dialog').modal({show:true});
			//$('#page-dialog').dialog('open');
			$('#parentURL').html(parentURL + '/');
			$('#title').val('');
			$('#url').val('');
			$('#url').removeAttr('disabled');
			$('#title').focus();
			page = null;
		},

		onTitleChange: function() {
			var url = $("#url").val();
			var title = $("#title").val();
			if (url == '' && !isRoot()) {
				$("#url").val(Heiduc.urlFromTitle(title));
			}
		},
		
		onInvertOrder: function(flag) {
			invertOrder = flag;
			renderInvertOrder();
			if (invertOrder) {
				$.cookie("pages.invertOrder", 'true', {path:'/', expires: 10});
			}
			else {
				$.cookie("pages.invertOrder", 'false', {path:'/', expires: 10});
			}
			loadData();
		},

		onPagePublish: function(id) {
			if (confirm(messages('are_you_sure'))) {
				Heiduc.jsonrpc.pageService.approve(function(r) {
					Heiduc.showServiceMessages(r);
					loadData();
				}, id);
			}
		},
		
		onShowTitle: function(flag) {
			showTitle = flag;
			renderShowTitle();
			if (showTitle) {
				$.cookie("pages.showTitle", 'titles', {path:'/', expires: 10});
			}
			else {
				$.cookie("pages.showTitle", 'names', {path:'/', expires: 10});
			}
			loadData();
		},
		
		onChangeTitle: function(id) {
			var pageItem = findPage(id);
			page = pageItem.entity;
			$('#ui-dialog-title-page-dialog').text(messages('pages.change_page'));
			parentURL = page.parentUrl == '/' ? '' : page.parentUrl;
			$('#page-dialog').modal({show:true});
			$('#parentURL').html(parentURL + '/');
			$('#title').val(page.title);
			$('#url').val(page.pageFriendlyURL);
			$('#url').attr('disabled', isRoot());
			$('#title').focus();
		}


	});
	
});
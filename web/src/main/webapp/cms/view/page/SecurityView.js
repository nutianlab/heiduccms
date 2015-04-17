// file SecurityView.js

define(['text!template/page/security.html',
        'view/page/context', 'view/page/version', 'view/page/breadcrumbs'], 
function(securityHtml, ctx, version, breadcrumbs) {
	
	console.log('Loading SecurityView.js');
	
	var permission = null;
	var permissions = null;
	var groups = null;
	var languages = {};
	    
	function postRender() {
		ctx.loadData = loadData;
		ctx.editMode = ctx.pageId != '';
	    Heiduc.initJSONRpc(loadData);

	    // hover states on the link buttons
	    $('a.button').hover(
	     	function() { $(this).addClass('ui-state-hover'); },
	       	function() { $(this).removeClass('ui-state-hover'); }
	    ); 

	    version.initVersionDialog();
	    //$("#permission-dialog").dialog({ width: 400, autoOpen: false });
	    
	    $('#addPermissionButton').click(onAddPermission);
	    $('#deletePermissionButton').click(onDeletePermission);
	    $('#permissionForm').submit(function() {onPermissionSave(); return false;});
	    $('#permissionCancelButton').click(onPermissionCancel);
	    $('#allLanguages').change(onAllLanguagesChange);
	    
	    $('ul.nav-tabs li:nth-child(5)').addClass('active');
	    

	}

	function loadData() {
		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
			ctx.pageRequest = r;
			ctx.page = ctx.pageRequest.page;
			loadLanguages();
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
			ctx.pageId = String(ctx.page.id);
			ctx.pageParentUrl = ctx.page.parentUrl;
			version.loadVersions();
			loadPermissions();
			loadGroups();
		} else {
			ctx.pages['1'] = ctx.page;
		}
		loadPagePermission();
	}

	function loadLanguages() {
		var r = ctx.pageRequest.languages;
		languages = {};
		var h = '';
		$.each(r.list, function(i, value) {
			languages[value.code] = value;
		});
		setPermissionLanguages();
	}
	 
	function getPermissionName(perm) {
		if (perm == 'DENIED') {
			return messages('denied');
		}
		if (perm == 'READ') {
			return messages('read');
		}
		if (perm == 'WRITE') {
			return messages('read_write');
		}
		if (perm == 'PUBLISH') {
			return messages('read_write_publish');
		}
		if (perm == 'ADMIN') {
			return messages('read_write_publish_grant');
		}
	}

	function callLoadPermissions() {
		Heiduc.jsonrpc.contentPermissionService.selectByUrl(function (r) {
			ctx.pageRequest.permissions = r;
			loadPermissions();
		}, ctx.page.friendlyURL);
	}

	function loadPermissions() {
		var r = ctx.pageRequest.permissions;
		permissions = Heiduc.idMap(r.list);
		var h = '<table class="table table-striped"><tr><th></th><th>'
			+ messages('group') + '</th><th>' + messages('permission') + '</th><th>'
			+ messages('languages') + '</th></tr>';
		$.each(permissions, function(i,value) {
			var checkbox = '';
			var editLink = value.group.name;
			if (!value.inherited) {
				checkbox = '<input type="checkbox" value="' + value.id + '">';
				editLink = '<a data-id="' + value.id + '"> ' + value.group.name + '</a>';
			}
			var l = value.allLanguages ? messages('page.all_languages') : value.languages;
			h += '<tr><td>' + checkbox + '</td><td>' + editLink + '</td><td>'
				+ getPermissionName(value.permission) + '</td><td>' + l 
				+ '</td></tr>';
		});
		$('#permissions').html(h + '</table>');
	    $('#permissions tr:even').addClass('even');
	    $('#permissions a').click(function() {
	    	var id = $(this).attr('data-id');
	    	if (id) {
	    		onPermissionEdit(id);
	    	}
	    });
	}

	function loadGroups() {
		var r = ctx.pageRequest.groups;
		groups = Heiduc.idMap(r.list);
		var h = '';
		$.each(groups, function(i,value) {
			h += '<option value="' + value.id + '">' + value.name + '</option>';
		});
		$('#groupSelect').html(h);
	}

	function setPermissionLanguages() {
		var h = '<fieldset><legend>' + messages('languages') + '</legend>';
		$.each(languages, function(i,value) {
			h += '<input type="checkbox" value="' + value.code + '" /> ' + value.title 
				+ '<br />';
		});
		$('#permLanguages').html(h + '</fieldset>');
	}

	function onPermissionEdit(id) {
		permission = permissions[id];
		initPermissionForm();
		//$('#permission-dialog').dialog('open');
		$('#permission-dialog').modal({show:true});
	}

	function initPermissionForm() {
		$('#permission-dialog input[type=radio]').removeAttr('checked');
		$('#allLanguages').attr('checked','checked');
		$('#permLanguages').hide();
		if (permission == null) {
			$('#permission-dialog input[value=READ]').attr('checked', 'checked');
			$('#groupSelect').show();
			$('#groupName').hide();
		}
		else {
			$('#permissionList input[value=' + permission.permission 
					+ ']').attr('checked', 'checked');
			$('#groupSelect').hide();
			$('#groupName').show();
			$('#groupName').text(permission.group.name);
			if (!permission.allLanguages) {
				$('#allLanguages').removeAttr('checked');
				$('#permLanguages').show();
				var codes = permission.languages.split(',');
				$('#permLanguages input').removeAttr('checked');
				$.each(codes, function(i,value) {
					$('#permLanguages input[value=' + value + ']').attr('checked',
							'checked');
				});
			}
		}
	}

	function onPermissionSave() {
		var langs = '';
		$('#permLanguages input:checked').each(function() {
			langs += (langs == '' ? '' : ',') + this.value;
		});
		var vo = {
			url: ctx.page.friendlyURL,
			groupId: permission == null ? $('#groupSelect').val() : 
				String(permission.group.id),
			permission: $('#permissionList input:checked')[0].value,
			languages: $('#allLanguages')[0].checked ? '' : langs
		};
		Heiduc.jsonrpc.contentPermissionService.save(function(r) {
			Heiduc.showServiceMessages(r);
			//$('#permission-dialog').dialog('close');
			if (r.result == 'success') {
				callLoadPermissions();
			}
		}, Heiduc.javaMap(vo));
	}

	function onAddPermission() {
		permission = null;
		initPermissionForm();
		//$('#permission-dialog').dialog('open');
		$('#permission-dialog').modal({show:true});
	}

	function onDeletePermission() {
		var ids = [];
		$('#permissions input:checked').each(function() {
			ids.push(this.value);
		});
		if (ids.length == 0) {
			Heiduc.info(messages('nothing_selected'));
			return;
		}
		if (confirm(messages('are_you_sure'))) {
			Heiduc.jsonrpc.contentPermissionService.remove(function(r) {
				Heiduc.showServiceMessages(r);
				callLoadPermissions();
			}, Heiduc.javaList(ids));
		}
	}

	function onPermissionCancel() {
		//$('#permission-dialog').dialog('close');
	}

	function onAllLanguagesChange() {
		if (this.checked) {
			$('#permLanguages').hide();
		}	
		else {
			$('#permLanguages').show();
		}
	}

	function loadPagePermission() {
	    var r = ctx.pageRequest.pagePermission;
	   	if (r.admin && ctx.editMode) {
	   		$('.securityTab').show();
	   	}
	   	else {
	   		$('.securityTab').hide();
	   	}
	}

	
	return Backbone.View.extend({
		
		el: $('#tab-1'),

		tmpl: _.template(securityHtml),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(this.tmpl({messages:messages}));
			postRender();
		},
		
		remove: function() {
		    //$("#permission-dialog").dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
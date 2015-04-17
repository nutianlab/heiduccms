// file GroupsView.js

define(['text!template/config/groups.html'],
function(tmpl) {
	
	console.log('Loading GroupsView.js');

	var group = null;
	var groups = null;
	var users = null;

	function postRender() {
        //$("#group-dialog").dialog({ width: 460, autoOpen: false });
	    //$("#user-group-dialog").dialog({ width: 300, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    $('#addGroupButton').click(onAddGroup);
	    $('#removeGroupButton').click(onRemoveGroup);
	    $('#groupForm').submit(function() {onGroupSave(); return false;});
	    $('#groupCancelDlgButton').click(onGroupCancel);
	    $('#userGroupForm').submit(function() {onUserGroupSave(); return false;});
	    $('#userGroupCancelDlgButton').click(onUserGroupCancel);
	    $('ul.nav-tabs li:nth-child(6)').addClass('active');
	}

	function loadData() {
		loadGroups();
	}

	// Group

	function loadGroups() {
		Heiduc.jsonrpc.groupService.select(function (r) {
	    	groups = Heiduc.idMap(r.list);
	        var h = '<table class="table table-striped"><tr><th></th><th>' + messages('name') 
	        	+ '</th><th>' + messages('users') + '</th></tr>';
	        $.each(r.list, function (i, group) {
	        	if (group.name == 'guests') {
	        		return;
	        	}
	        	var users = messages('add_users');
	        	if (group.users.list.length > 0) {
	        		users = '';
	        		$.each(group.users.list, function (i, user) {
	        			users += (i==0 ? '' : ', ') + user.name;
	        		});
	        	}
	        	var editLink = '<a class="groupEdit" data-id="' + group.id + '">' + group.name + '</a>';
	        	var userGroupLink = '<a class="userGroupEdit" data-id="' + group.id + '">' + users + '</a>';
	            h += '<tr><td><input type="checkbox" value="' + group.id 
	                + '"/></td><td>' + editLink + '</td><td>' + userGroupLink 
	                + '</td></tr>';
	        });
	        $('#groups').html(h + '</table>');
	        $('#groups tr:even').addClass('even');
	        $('#groups .groupEdit').click(function() {
	        	onGroupEdit($(this).attr('data-id'));
	        });
	        $('#groups .userGroupEdit').click(function() {
	        	onEditUserGroup($(this).attr('data-id'));
	        });
	    });
	}

	function onAddGroup() {
	    group = null;
	    initGroupForm();
	    //$('#group-dialog').dialog('open');
	    $('#group-dialog').modal({show:true});
	}

	function onRemoveGroup() {
	    var ids = [];
	    $('#groups input:checked').each(function () {
	        ids.push(String(this.value));
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.groupService.remove(function (r) {
	    		Heiduc.info(r.message);
	            loadGroups();
	        }, Heiduc.javaList(ids));
	    }
	}

	function onGroupEdit(id) {
		Heiduc.jsonrpc.groupService.getById(function (r) {
	        group = r;
	        initGroupForm();
	        //$('#group-dialog').dialog('open');
	        $('#group-dialog').modal({show:true});
	    }, id);
	}

	function initGroupForm() {
		if (group == null) {
	        $('#groupName').val('');
		}
		else {
	        $('#groupName').val(group.name);
		}
	    $('#group-dialog .messages').html('');
	}

	function validateGroup(vo) {
	    var errors = [];
	    if (vo.name == '') {
	        errors.push(messages('name_is_empty'));
	    }
	    return errors;
	}

	function onGroupSave() {
	    var vo = {
	    	id : group != null ? String(group.id) : '',
	        name : $('#groupName').val()
	    };
	    var errors = validateGroup(vo);
	    if (errors.length == 0) {
	    	Heiduc.jsonrpc.groupService.save(function (r) {
	            if (r.result == 'success') {
	                //$('#group-dialog').dialog('close');
	                Heiduc.info(r.message);
	                loadGroups();
	            }
	            else {
	                groupErrors(r.messages.list);
	            }
	        }, Heiduc.javaMap(vo));
	    }
	    else {
	        groupErrors(errors);
	    }
	}

	function onGroupCancel() {
	    //$('#group-dialog').dialog('close');
	}

	function groupError(msg) {
		Heiduc.errorMessage('#group-dialog .messages', msg);
	}

	function groupErrors(errors) {
		Heiduc.errorMessages('#group-dialog .messages', errors);
	}

	function onEditUserGroup(id) {
		group = groups[id];
		Heiduc.jsonrpc.userService.select(function (r) {
			users = Heiduc.idMap(r.list);
			var groupUsers = Heiduc.idMap(group.users.list);
			var h = '';
			$.each(users, function (i, value) {
				var checked = '';
				if (groupUsers[value.id] != undefined) {
					checked = 'checked = "checked"';
				}
				h += '<div class="form-row"><input type="checkbox" ' + checked 
					+ ' value="' + value.id + '"> ' + value.name + '</div>';
			});
			$('#groupUsers').html(h);
			//$('#user-group-dialog').dialog('open');
			$('#group-dialog').modal({show:true});
		});
	}

	function onUserGroupCancel() {
	    //$('#user-group-dialog').dialog('close');
	}

	function onUserGroupSave() {
		var usersId = [];
		$('#user-group-dialog input:checked').each(function () {
			usersId.push(this.value);
		});
		Heiduc.jsonrpc.groupService.setGroupUsers(function (r) {
			Heiduc.showServiceMessages(r);
		    //$('#user-group-dialog').dialog('close');
		    loadGroups();
		}, group.id, Heiduc.javaList(usersId));
	}

	return Backbone.View.extend({
		
		el: $('#tab-1'),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
	        //$("#group-dialog").dialog('destroy').remove();
		    //$("#user-group-dialog").dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
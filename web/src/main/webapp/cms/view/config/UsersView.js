// file UsersView.js

define(['text!template/config/users.html'],
function(tmpl) {
	
	console.log('Loading UsersView.js');

	var user = null;
	var users = null;

	function postRender() {
	    //$("#user-dialog").dialog({ width: 500, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    $('#addUserButton').click(onAddUser);
	    $('#removeUserButton').click(onRemoveUser);
	    $('#userForm').submit(function() {onUserSave(); return false;});
	    $('#userCancelDlgButton').click(onUserCancel);
	    $('#userDisableDlgButton').click(onUserDisable);
	    $('ul.nav-tabs li:nth-child(5)').addClass('active');
	}

	function loadData() {
		Heiduc.jsonrpc.userService.getTimezones(function (r) {
	        timezones = r.list;
	        showTimezones();
	        loadUsers();
	    });
	}

	// User 

	function onAddUser() {
	    user = null;
	    initUserForm();
	    //$('#user-dialog').dialog('open');
	    $('#user-dialog').modal({show:true});
	}

	function onRemoveUser() {
	    var ids = [];
	    $('#users input:checked').each(function () {
	        ids.push(String(this.value));
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.userService.remove(function (r) {
	    		Heiduc.info(r.message);
	            loadUsers();
	        }, Heiduc.javaList(ids));
	    }
	}

	function loadUsers() {
		Heiduc.jsonrpc.userService.select(function (r) {
	        var h = '<table class="table table-striped"><tr><th></th><th>' + messages('name') 
	        	+ '</th><th>' + messages('email') + '</th><th>' + messages('role') 
	        	+ '</th><th>' + messages('access') + '</th></tr>';
	        $.each(r.list, function (i, user) {
	            var disabled = user.disabled ? messages('disabled') : messages('enabled');
	        	h += '<tr><td><input type="checkbox" value="' + user.id 
	                + '"/></td><td>' + user.name + '</td><td>\
	                <a data-id="' + user.id + '">' + user.email + '</a></td><td>'
	                + getRole(user.role) + '</td>'
	                + '<td>' + disabled + '</td></tr>';
	        });
	        $('#users').html(h + '</table>');
	        $('#users tr:even').addClass('even');
	        $('#users a').click(function() {
	        	var id = $(this).attr('data-id');
	        	if (id) {
	        		onUserEdit(id);
	        	}
	        })
	    });
	}

	function getRole(role) {
	    if (role == 'ADMIN') return messages('administrator');
	    if (role == 'USER') return messages('user');
	    if (role == 'SITE_USER') return messages('site_user');
	}

	function onUserEdit(id) {
		Heiduc.jsonrpc.userService.getById(function (r) {
	        user = r;
	        initUserForm();
	        //$('#user-dialog').dialog('open');
	        $('#user-dialog').modal({show:true});
	    }, id);
	}

	function initUserForm() {
		if (user == null) {
	        $('#userName').val('');
	        $('#userEmail').val('');
	        $('#userEmail').removeAttr('disabled');
	        $('#userRole').val('');
	        $('#userDisableDlgButton').hide();
		}
		else {
	        $('#userName').val(user.name);
	        $('#userEmail').val(user.email);
	        $('#userEmail').attr('disabled', true);
	        $('#userRole').val(user.roleString);
	        $('#timezone').val(user.timezone);
	        $('#userDisableDlgButton').val(user.disabled ? messages('enable') : 
	    		messages('disable')).show();
		}
	    $('#userPassword1').val('');
	    $('#userPassword2').val('');
	    $('#user-dialog .messages').html('');
	}

	function validateUser(vo) {
	    var errors = [];
	    if (vo.email == '') {
	        errors.push(messages('email_is_empty'));
	    }
	    if (vo.password1 != vo.password2) {
	        errors.push(messages('config.passwords_dont_match'));
	    }
	    return errors;
	}

	function onUserSave() {
	    var vo = {
	    	id : user != null ? String(user.id) : '',
	        name : $('#userName').val(),
	        email : $('#userEmail').val(),
	        role : $('#userRole').val(),
	        timezone : $('#timezone').val(),
	        password : $('#userPassword1').val(),
	        password1 : $('#userPassword1').val(),
	        password2 : $('#userPassword2').val()
	    };
	    var errors = validateUser(vo);
	    if (errors.length == 0) {
	    	Heiduc.jsonrpc.userService.save(function (r) {
	            if (r.result == 'success') {
	                //$('#user-dialog').dialog('close');
	                Heiduc.info(r.message);
	                loadUsers();
	            }
	            else {
	                userErrors(r.messages.list);
	            }
	        }, Heiduc.javaMap(vo));
	    }
	    else {
	        userErrors(errors);
	    }
	}

	function onUserCancel() {
	    //$('#user-dialog').dialog('close');
	}

	function userError(msg) {
		Heiduc.errorMessage('#user-dialog .messages', msg);
	}

	function userErrors(errors) {
		Heiduc.errorMessages('#user-dialog .messages', errors);
	}

	function onUserDisable() {
		Heiduc.jsonrpc.userService.disable(function(r) {
	        //$('#user-dialog').dialog('close');
	        Heiduc.showServiceMessages(r);
	        loadUsers();
		}, user.id, !user.disabled);
	}

	function showTimezones() {
		var h = '';
		$.each(timezones, function(i, value) {
			h += '<option value="' + value + '">' + value + '</option>';
		});
		$('#timezone').html(h);
	}

	return Backbone.View.extend({
		
		el: $('#tab-1'),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
		    //$("#user-dialog").dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
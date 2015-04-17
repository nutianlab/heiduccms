// file ProfileView.js

define(['text!template/profile.html'], function(tmpl) {
	
	console.log('Loading ProfileView.js');
	
	var user = null;
	var timezones = null;

	function postRender() {
		//$("#tabs").tabs();
		Heiduc.initJSONRpc(loadData);
	    $('#saveButton').click(onSave);
	}

	function loadData() {
		Heiduc.jsonrpc.userService.getTimezones(function (r) {
	        timezones = r.list;
	        showTimezones();
	        loadUser();
	    });
	}

	function loadUser() {
		Heiduc.jsonrpc.userService.getLoggedIn(function (r) {
	        user = r;
	        $('#name').val(user.name);
	        $('#email').val(user.email);
	        $('#password1').val('');
	        $('#password2').val('');
	        $('#timezone').val(user.timezone);
	    });
	}

	function validPasswords() {
	    var pass1 = $('#password1').val();
	    var pass2 = $('#password2').val();
	    if (pass1 || pass2) {
	        if (pass1 == pass2) {
	            return true;
	        }
	        return false;
	    }
	    return true;
	}
	    
	function onSave() {
	    var pass = '';
	    if (validPasswords()) {
	        pass = $('#password1').val();
	    }
	    else {
	    	Heiduc.error(messages('profile.password_dont_match'));
	        return;
	    }
	    var vo = {
	     	id : String(user.id),
	        name : $('#name').val(),
	        timezone : $('#timezone').val(),
	        password : pass   
	    };
	    Heiduc.jsonrpc.userService.save(function (r) {
	    	Heiduc.showServiceMessages(r);
	    }, Heiduc.javaMap(vo));
	}

	function showTimezones() {
		var h = '';
		$.each(timezones, function(i, value) {
			h += '<option value="' + value + '">' + value + '</option>';
		});
		$('#timezone').html(h);
	}

	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
		}
		
	});
	
});
// file LoginView.js


define(['text!template/login.html', 'text!template/locale.html', 'text!template/login-topbar.html'], 
function(tmpl, localeTmpl, loginTopbarTmpl) {

	// Due to JQuery-UI dialog - Backbone problems we are using this handler.
	function forgotForm_submit(e) {
		e.preventDefault();
    	var email = $('#email').val();
    	if (!email) {
    		Heiduc.error(messages('email_is_empty'));
    		return false;
    	}
    	$('#loading').show();
    	Heiduc.jsonrpc.loginFrontService.forgotPassword(function(r) {
    		$('#loading').hide();
    		if (r.result == 'success') {
    	    	Heiduc.info(messages('login.password_letter_success'));
    		}
    		else {
    			Heiduc.error(e.message);
    		}
    		$('#forgot-dialog').dialog('close');
    	}, email.toLowerCase());
    	return false;
	}
	
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		events: {
			"click #forgot": 	"forgot_click",
			"submit form#login":"login_submit"
		},
		
		css: '/static/css/login.css', 

		forgot_click : function() {
			this.$('#email').val('');
			//$('#forgot-dialog').dialog('open');
		},
		
		login_submit: function(e) {
			e.preventDefault();
	    	if (Heiduc.app.loggedIn) {
	    		Heiduc.app.trigger("login");
	    		return false;
	    	}
	    	var email = this.$('#loginEmail').val();
	    	var password = this.$('#loginPassword').val();
	    	if (email == '') {
	    		Heiduc.errorMessage('#login-messages', messages('email_is_empty'));
	    	} else {
	    		Heiduc.jsonrpc.loginFrontService.login(function(r, e) {
	    			if (Heiduc.serviceFailed(e))
	    				return false;
	    			if (r.result == 'success') {
	    				if(r.data == 'siteUser'){
	    					location.href = '/';
	    				}
	    				Heiduc.infoMessage('#login-messages', messages('success_logging_in'));
	    				Heiduc.app.loggedIn = true;
	    	    		Heiduc.app.trigger('login');
	    			} else {
	    				Heiduc.errorMessage('#login-messages', r.message);
	    			}
	    		}, email, password);
	    	}
	    	return false;
		},
		
		render: function() {
			Heiduc.addCSSFile(this.css);
			//this.el.html(_.template(tmpl, {messages: messages}));
			this.el.html($.jqote(tmpl, {messages: messages}));
			
			//$("#forgot-dialog").dialog({ 
			//	width: 400, 
			//	autoOpen: false 
			//});
			
			// defining event handlers here due to JQuery-UI - Backbone issue
			$('#forgotForm').submit(forgotForm_submit);
			$("#forgotCancelButton").click(function() {
				$('#forgot-dialog').dialog('close');
			});
			// filling topbar
			var localeHtml = _.template(localeTmpl, {messages:messages});
			$('#topbar').html(_.template(loginTopbarTmpl, {messages:messages, locale:localeHtml}));
			//$('#languageSelect').click(function() {
			//	$('#languageDiv').show();
			//	setTimeout(function() {
			//        $('#languageDiv').hide();
			//	}, 5000);
			//});
			
			return this;
		},
		
		remove: function() {
    		//$('#forgot-dialog').dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFile(this.css);
		}
	
	});
	
});
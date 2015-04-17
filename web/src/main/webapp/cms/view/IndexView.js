// file IndexView.js

define(['text!template/index.html'], function(tmpl) {
	
	console.log('Loading IndexView.js');
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {user:Heiduc.app.user, messages:messages}));
		},
		
		remove: function() {
			this.el.html('');
		}
		
	});
	
});
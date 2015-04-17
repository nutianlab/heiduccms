// file PluginsView.js

define(['text!template/plugins/index.html'], function(tmpl) {
	
	console.log('Loading PluginsView.js');
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
		},
		
		remove: function() {
			this.el.html('');
		}
		
	});
	
});
// file ConfigView.js

define(['text!template/config/config.html', 'view/config/ConfigRouter', 'jquery.form'], 
function(configHtml, ConfigRouter) {
	
	console.log('Loading ConfigView.js');
	
	return Backbone.View.extend({
		
		css: '/static/css/config.css',
		
		el: $('#content'),

		tmpl: _.template(configHtml),

		viewCmd: null,
		
		router: null,
		
		initialize: function() {
			this.router = new ConfigRouter({view:this});
		},
		
		render: function() {
			Heiduc.addCSSFile(this.css);
			this.el.html(this.tmpl({ messages : messages }));
		    //$("#import-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#export-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#stat-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#afterUpload-dialog").dialog({autoOpen: false });
			this.router.index();
		},
		
		remove: function() {
			if (this.router.currentView) {
				this.router.currentView.remove();
			}
		    //$("#import-dialog").dialog('destroy').remove();
		    //$("#export-dialog").dialog('destroy').remove();
		    //$("#stat-dialog").dialog('destroy').remove();
		    //$("#afterUpload-dialog").dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFile(this.css);
		}
		
	});
	
});
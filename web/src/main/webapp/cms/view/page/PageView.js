// file PageView.js

define(['text!template/page/page.html', 'view/page/PageRouter', 'view/page/context'], 
function(pageHtml, PageRouter, ctx) {
	
	console.log('Loading PageView.js');
	
	return Backbone.View.extend({
		
		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css',
		      '/static/css/page.css'],
		
		el: $('#content'),

		tmpl: _.template(pageHtml),

		viewCmd: null,
		
		router: null,
		
		initialize: function() {
			this.router = new PageRouter({view:this});
		},
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(this.tmpl({
				messages : messages, 
				id : ctx.pageId
			}));
		    //$("#version-dialog").dialog({ width: 400, autoOpen: false });
			this.router.showCmd(this.viewCmd);
		},
		
		remove: function() {
			if (this.router.currentView) {
				this.router.currentView.remove();
			}
			//$('#version-dialog').dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		},
		
		editContent: function(id) {
			ctx.pageId = id;
			this.viewCmd = 'editContent';
		},

		editPage: function(id) {
			ctx.pageId = id;
			this.viewCmd = 'editPage';
		},
		
		setResource: function(path) {
			this.router.contentView.setResource(path);
		}
		
	});
	
});
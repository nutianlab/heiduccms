// file PageRouter.js

define(['view/page/context',
        'view/page/ContentView', 'view/page/IndexView', 'view/page/ChildrenView',
        'view/page/CommentsView', 'view/page/SecurityView', 
        'view/page/AttributesView'], 
function(ctx, ContentView, IndexView, ChildrenView, CommentsView, SecurityView, 
		 AttributesView) {
    
    console.log('Loading PageRouter.js');
    
    return Backbone.Router.extend({
        
        pageView: null,
        
        currentView: null,
        contentView: new ContentView(),
        indexView: new IndexView(),
        childrenView: new ChildrenView(),
        commentsView: new CommentsView(),
        securityView: new SecurityView(),
        attributesView: new AttributesView(),

        initialize: function(options) {
            this.pageView = options.view;
        },

        show: function(view) {
        	if (Heiduc.app.currentView != this.pageView) {
        		Heiduc.app.show(this.pageView);
        	}
            if (this.currentView) {
                this.currentView.remove();
                this.currentView = null;
            }
    	    $('ul.nav-tabs li').removeClass('active');
    	    
            view.render();
            this.currentView = view;
        },
        
        routes: {
            'page/children/:id':	'children',
            'page/comments/:id':	'comments',
            'page/security/:id':	'security',
            'page/attributes/:id':	'attributes',
            'page/resources/:id':	'resources'
        },

        showCmd: function(cmd) {
            if (cmd == 'editContent') this.editContent(ctx.pageId);
            if (cmd == 'editPage') this.editPage(ctx.pageId);
        },
        
        editContent: function(id) {
        	ctx.pageId = id;
            this.show(this.contentView);
        },
        
        editPage: function(id) {
        	ctx.pageId = id;
            this.show(this.indexView);
        },
            
        children: function(id) {
        	ctx.pageId = id;
            this.show(this.childrenView);
        },
        
        comments: function(id) {
        	ctx.pageId = id;
            this.show(this.commentsView);
        },

        security: function(id) {
        	ctx.pageId = id;
            this.show(this.securityView);
        },

        attributes: function(id) {
        	ctx.pageId = id;
            this.show(this.attributesView);
        },
        
        resources: function(id) {
    		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
    			ctx.pageRequest = r;
    			ctx.pageId = id;
    			ctx.page = ctx.pageRequest.page;
    			
            	Heiduc.jsonrpc.folderService.createFolderByPath(function(r) {
            		jQuery.cookie('folderReturnPath', '#page/content/' + ctx.pageId, 
            		    {path:'/', expires: 10});
            		location.href = '#folder/' + r.id;
            	}, '/page' + ctx.page.friendlyURL);

    		}, id, '');
        }
    });
    
});
// file ConfigRouter.js

define(['view/config/IndexView', 'view/config/CommentsView',
        'view/config/LanguagesView', 'view/config/MessagesView',
        'view/config/UsersView', 'view/config/GroupsView',
        'view/config/TagsView', 'view/config/AttributesView'], 
function(IndexView, CommentsView, LanguagesView, MessagesView, UsersView,
		 GroupsView, TagsView, AttributesView) {
    
    console.log('Loading ConfigRouter.js');
    
    return Backbone.Router.extend({
        
        configView: null,
        
        currentView: null,
        indexView: new IndexView(),
        commentsView: new CommentsView(),
        languagesView: new LanguagesView(),
        messagesView: new MessagesView(),
        usersView: new UsersView(),
        groupsView: new GroupsView(),
        tagsView: new TagsView(),
        attributesView: new AttributesView(),

        initialize: function(options) {
            this.configView = options.view;
        },

        show: function(view) {
        	if (Heiduc.app.currentView != this.configView) {
        		Heiduc.app.show(this.configView);
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
        	'config/comments':	'comments',
        	'config/languages':	'languages',
        	'config/messages':	'messages',
        	'config/users':		'users',
        	'config/groups':	'groups',
        	'config/tags':		'tags',
        	'config/attributes':'attributes'
        },

        index: function() {
            this.show(this.indexView);
        },
        
        comments: function() {
        	this.show(this.commentsView);
        },
        
        languages: function() {
        	this.show(this.languagesView);
        },
        
        messages: function() {
        	this.show(this.messagesView);
        },
        
        users: function() {
        	this.show(this.usersView);
        },

        groups: function() {
        	this.show(this.groupsView);
        },
        
        tags: function() {
        	this.show(this.tagsView);
        },
        
        attributes: function() {
        	this.show(this.attributesView);
        }
    });
    
});
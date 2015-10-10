
define(['view/LoginView', 'view/PagesView', 'view/IndexView',
        'view/StructuresView', 'view/StructureView', 'view/StructureTemplateView',
        'view/TemplatesView', 'view/TemplateView',
        'view/page/PageView', 'view/ProfileView', 
        'view/plugins/PluginsView', 'view/plugins/PluginView', 
        'view/plugins/ConfigView', 'view/plugins/FormsView', 
        'view/plugins/FormView', 'view/plugins/SeoUrlsView','view/plugins/AddonsView',
        'view/config/ConfigView', 'view/FoldersView', 'view/FolderView',
        'view/FileView',
        
        'text!template/topbar.html', 'text!template/locale.html','text!template/sidebar.html'], 
function(LoginView, PagesView, IndexView, 
		StructuresView, StructureView, StructureTemplateView,
		TemplatesView, TemplateView,
		PageView, ProfileView, 
		PluginsView, PluginView, 
		PluginsConfigView, PluginsFormsView, PluginsFormView, SeoUrlsView, AddonsView,
		ConfigView, FoldersView, FolderView, FileView, 
		topbarTmpl, localeTmpl,sidebarTmpl){
	
	return Backbone.Router.extend({

		initialize:function() {
			this.bind("login", this.login, this);
			this.bind("renderSide", this.renderSide, this);
			if (Heiduc.loggedIn) {
				this.login();
			}
			else {
				this.currentView = this.loginView.render();
			}
			//$('#loading').html(messages('loading'));
			$('#topbar, #main_wrapper').fadeIn();
			$('#main_wrapper').append('<!-- FOOTER --><footer id="footer" role="contentinfo">Copyright &copy; 2015 Heiduc Inc.</footer><!-- FOOTER -->');
			/*******************************
			CONTENT MIN-HEIGHT
			*******************************/
			    function setHeight() {
			        var windowHeight = $(window).innerHeight();
			        $('.main').css('min-height', windowHeight);
			    };
			    setHeight();
			    
			    $(window).on('resize', function() {
			        setHeight();
			});
		},

		// Views
		
		currentView: null,
		
		indexView: new IndexView(),
		loginView: new LoginView(),
		pagesView: new PagesView(),
		structuresView: new StructuresView(),
		structureView: new StructureView(),
		structureTemplateView: new StructureTemplateView(),
		templatesView: new TemplatesView(),
		templateView: new TemplateView(),
		pageView: new PageView(),
		profileView: new ProfileView(),
		pluginsView: new PluginsView(),
		pluginView: new PluginView(),
		pluginsConfigView: new PluginsConfigView(),
		pluginsFormsView: new PluginsFormsView(),
		pluginsFormView: new PluginsFormView(),
		pluginsSeoUrlsView: new SeoUrlsView(),
		pluginsAddonsView: new AddonsView(),
		configView: new ConfigView(),
		foldersView: new FoldersView(),
		folderView: new FolderView(),
		fileView: new FileView(),

		routes: {
			'index': 			'index',
			'pages': 			'pages',
			
			'page/content/:id': 'editContent',
			'page/:id': 		'editPage',

			'structures':		'structures',
			'structure':		'addStructure',
			'structure/:id':	'editStructure',

			'addStructureTemplate/:id'	: 'addStructureTemplate',
			'structureTemplate/:id' 	: 'structureTemplate',

			'profile':			'profile',
			'logout':			'logout',
			
			'plugins':			'plugins',
			'plugins/config':	'pluginsConfig',
			'plugins/forms':	'pluginsForms',
			'plugins/form':		'pluginsFormNew',
			'plugins/form/:id':	'pluginsFormEdit',
			'plugins/seo-urls':	'pluginsSeoUrls',
			'plugin/:id':		'plugin',
			'addons/*path':       'pluginsAddons',
			
			'templates':		'templates',
			'template':			'createTemplate',
			'template/:id':		'editTemplate',
			
			'config':			'config',
			'folders':			'folders',
			'folder/:id':		'editFolder',
			'addFolder/:id':	'addFolder',
			'file/:id':			'editFile',
			'addFile/:id':		'addFile'
				
		},
		
		// Routes handlers
		
		show: function(view) {
			if (this.currentView) {
				this.currentView.remove();
				this.currentView = null;
			}
			this.currentView = view;
			$('#content').hide();
			view.render();
			$('#content').fadeIn();
		},
		
		pages: function() {
			this.show(this.pagesView);
		},
		
		index: function() {
			this.show(this.indexView);
		},

		structures: function() {
			this.show(this.structuresView);
		},
		
		addStructure: function() {
			this.structureView.setId('');
			this.show(this.structureView);
		},
		
		editStructure: function(id) {
			this.structureView.setId(id);
			this.show(this.structureView);
		},
		
		addStructureTemplate: function(id) {
			this.structureTemplateView.create(id);
			this.show(this.structureTemplateView);
		},
		
		structureTemplate: function(id) {
			this.structureTemplateView.edit(id);
			this.show(this.structureTemplateView);
		},
		
		templates: function() {
			this.show(this.templatesView);
		},
		
		createTemplate: function() {
			this.templateView.create();
			this.show(this.templateView);
		},
		
		editTemplate: function(id) {
			this.templateView.edit(id);
			this.show(this.templateView);
		},

		editContent: function(id) {
			this.pageView.editContent(id);
			this.show(this.pageView);
		},
		
		editPage: function(id) {
			this.pageView.editPage(id);
			this.show(this.pageView);
		},
		
		profile: function(id) {
			this.show(this.profileView);
		},

		plugins: function() {
			this.show(this.pluginsView);
		},
		
		pluginsConfig: function() {
			this.show(this.pluginsConfigView);
		},

		pluginsForms: function() {
			this.show(this.pluginsFormsView);
		},

		pluginsFormNew: function() {
			this.pluginsFormView.setId('');
			this.show(this.pluginsFormView);
		},
		
		pluginsFormEdit: function(id) {
			this.pluginsFormView.setId(id);
			this.show(this.pluginsFormView);
		},

		pluginsSeoUrls: function() {
			this.show(this.pluginsSeoUrlsView);
		},
		
		pluginsAddons: function(id){
			this.pluginsAddonsView.setAddonsId(id);
			this.show(this.pluginsAddonsView);
		},
		
		config: function() {
			this.show(this.configView);
		},

		folders: function() {
			this.show(this.foldersView);
		},
		
		editFolder: function(id) {
			this.folderView.setFolderId(id);
			this.folderView.setFolderParentId('');
			this.show(this.folderView);
		},
		
		addFolder: function(id) {
			this.folderView.setFolderId('');
			this.folderView.setFolderParentId(id);
			this.show(this.folderView);
		},

		editFile: function(id) {
			this.fileView.setFileId(id);
			this.fileView.setFolderId('');
			this.show(this.fileView);
		},
		
		addFile: function(id) {
			this.fileView.setFileId('');
			this.fileView.setFolderId(id);
			this.show(this.fileView);
		},

		logout: function() {
	        Heiduc.jsonrpc.loginFrontService.logout(function (r, e) {
	            if (Heiduc.serviceFailed(e)) return;
	            if (r.result == 'success') {
	                location.href = '/cms';
	            }
	            else {
	                Heiduc.showServiceMessages(r);
	            }
	        });
		},
		
		plugin: function(id) {
			this.pluginView.setPluginId(id);
			this.show(this.pluginView);
		},
		
		// Event handlers
		
		login: function() {
			Heiduc.jsonrpcInitialized = false;
			Heiduc.createJSONRpc();
		    Heiduc.initJSONRpcSystem(Heiduc.initBackServices);
			Heiduc.initJSONRpc(function() {
				try{
				Heiduc.jsonrpc.userService.getLoggedIn(function(user) {
					Heiduc.app.user = user;

					var localeHtml = _.template(localeTmpl, {messages:messages});
					$('#topbar').html(_.template(topbarTmpl, 
						{locale: localeHtml, "Heiduc": Heiduc, "messages": messages}
					));
					
					Heiduc.app.trigger("renderSide");

					if (!Backbone.history.start()) {
						Heiduc.app.navigate('index', true);
					}
				});
			}catch(e){
				alert(e);
			}
			});
		},
		
		renderSide: function(){
			try{
			var localeHtml = _.template(localeTmpl, {messages:messages});
			Heiduc.jsonrpc.pluginService.select(function (r) {
				plugins = r.list;
				var html = '';//<li><a href="#plugins/config"><%= messages("plugins.config") %></a></li>
				$.each(plugins, function(i, plugin) {
					var configURL = '#plugin/' + plugin.id;
			    	if (plugin.configURL) {
			        	//configURL = '/file/plugins/' + plugin.name + '/' + plugin.configURL;
			        	configURL = '#addons/' + plugin.name ;
			        }
			    	var roles = plugin.role == null ? [] : plugin.role.split(",");
			    	if(Heiduc.app.user.user && $.inArray("ALL",roles)<0 ){
			    		if($.inArray("USER",roles)<0 ){
			    			return true;
			    		}
			    	}
			    	html += '<li><a href="'+ configURL + '">'+ Heiduc.message(plugin.title) +'</a></li>\r\n';
			    	
			    	
				});
				
				//渲染侧边栏
				var localeHtml = _.template(localeTmpl, {messages:messages});
				$('#topbar').after(_.template(sidebarTmpl, 
					{locale: localeHtml, "Heiduc": Heiduc, "messages": messages, "plugins" : html }
				));
				$('.sidebar .nicescroll .wrapper').niceScroll({scrollspeed: 26, cursorcolor:"#429eee", cursorborder: 0, horizrailenabled: false, railoffset: {left:-1}});
				// collapse
			    $(".nav-sidebar .submenu > a").on('click', function (evt) {

			        evt.preventDefault();

			        var parent = $(this).closest('.sidebar');
			        var submenuOpen = parent.find('.submenu .in');

			        // Close Parent Open Submenus
			        submenuOpen.collapse('hide');

			        // Show Current Submenu
			        $(this).next('ul').show().collapse('show');


			        // display:none All Previously Opene Submenus
			        submenuOpen.hide();

			        // Toggle Open Classes
			        if ($(this).hasClass("open")) {
			            $(this).removeClass("open");
			        }

			        parent.find('a.open').removeClass('open');
			        $(this).addClass('open');


			    });

			    // nicescroll resize without debounce delay on collapse
			    $('sidebar').find('.collapse').on('shown.bs.collapse', function () {
			        $(".sidebar").getNiceScroll().show().onResize();
			    });
			    
			    
			  //SIDEBAR TOGGLE
			    $('.sidebar-switch').on('click', function () {
			        if (parseInt($(window).width()) < 1169) {
			            $('.wrapper').removeClass('sidebar-toggle');
			            $('.wrapper').toggleClass('sidebar-toggle-sm');
			        }
			        else if (parseInt($(window).width()) > 1170) {
			            $('.wrapper').toggleClass('sidebar-toggle');
			        }
			    });
			    
			    $(window).on('resize', function() {
			        if ($(window).width() > 1169) {
			            $('.wrapper').removeClass('sidebar-toggle-sm');
			        }
			        else if ($(window).width() < 1170) {
			            $('.wrapper').removeClass('sidebar-toggle');
			        }
			    });
				
			});
			
			}catch(e){
				alert(e);
			}
		}
		
		
		
	});

});
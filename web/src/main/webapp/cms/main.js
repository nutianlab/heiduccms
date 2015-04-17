

/*
if ($.browser.msie) {
	$('.splash h3').html('We are sorry but Internet Explorer is not supported.');
	$('.splash img').attr('src', '/static/images/no_ie.jpg');
	throw new Error('IE is not supported.');
}
*/

// Configure shortcut aliases
require.config({

	paths : {
		'jquery': "/static/assets/js/jquery.min",
	    'underscore': 'libs/underscore',
	    'backbone': 'libs/backbone',
		text : 'libs/text',
		//"order" : 'libs/order',
		"jquery.cookie" : '/static/js/jquery.cookie',
		//"jquery-ui" : '/static/js/jquery-ui',//-1.10.3.custom.min
		"jquery.xmldom" : '/static/js/jquery.xmldom',
		"jquery.form" : '/static/js/jquery.form',
		"jquery.treeview" : '/static/js/jquery.treeview',
		"jquery.jquote2" : 'libs/jquery.jquote2.min',
		jsonrpc : '/static/js/jsonrpc',
		heiduc : '/static/js/heiduc',
		cms : '/static/js/cms',
		i18n : '/i18n',
		"back-services" : '/static/js/back-services',
		
	    'cm'				: "/static/js/codemirror/codemirror", 
	    'cm-css'			: "/static/js/codemirror/css", 
	    'cm-html'			: "/static/js/codemirror/htmlmixed",
	    'cm-js'				: "/static/js/codemirror/javascript",
	    'cm-xml'			: "/static/js/codemirror/xml",
	    
	    'moment-js'			: "/static/assets/lib/moment-js/moment.min",
	    'jquery.easing'		: "/static/assets/js/jquery.easing.1.3.min",
	    'bootstrap'			: "/static/assets/bootstrap/js/bootstrap.min",
	    //'tinynav'			: "/static/assets/js/tinynav.min",
	    //'tisa-common'		: "/static/assets/js/tisa_common",
	    'datepicker'        : "/static/assets/lib/bootstrap-datepicker/js/bootstrap-datepicker",
	    'noty'              :  "/static/assets/lib/noty/jquery.noty", 
	    'noty-layout'              :  "/static/assets/lib/noty/layouts/bottomRight", 
	    //'perfect-scrollbar-0.4.8.with-mousewheel': "/static/assets/lib/perfect-scrollbar/min/perfect-scrollbar-0.4.8.with-mousewheel.min"
	    'jquery.nicescroll': "/static/assets/lib/nicescroll/jquery.nicescroll.min"
		
	},
	shim: {
		/*'jquery':{
		    'exports': 'jQuery'
		},*/
        'underscore': {
            'exports': '_'
        },
        'backbone': {
            'deps': ['jquery', 'underscore'],
            'exports': 'Backbone'
        },
        'bootstrap'			: ['jquery'],
        'jquery.cookie': ['jquery'],
        //'jquery-ui': ['jquery'],
        "jquery.xmldom" : ['jquery'],
		"jquery.form" : ['jquery'],
		"jquery.treeview" : ['jquery'],
		"jquery.jquote2" : ['jquery'],
		'jquery.easing' : ['jquery'],
		//'tinynav' : ['jquery'],
		//'tisa-common' : ['tinynav'],
		//'perfect-scrollbar-0.4.8.with-mousewheel' : ['jquery'],
		'jquery.nicescroll' : ['jquery'],
		/**/
		'cm-css' : ['cm'],
		'cm-html' : ['cm'],
		'cm-js' : ['cm'],
		'cm-xml' : ['cm'],
		
		'heiduc' : ['jsonrpc'],
		'cms' : ['heiduc'],
		'back-services' : ['heiduc'],
		'datepicker'    : ['bootstrap'],
		'noty'          : ['jquery'],
		'noty-layout'          : ['jquery','noty'],
		'app' : ['jquery','heiduc','backbone']
		/**/
    }
});

require([

	// Load our app module and pass it to our definition function
	'app',
	'jquery','underscore','backbone',
	//'jquery-ui',
	'jquery.cookie',  'jquery.xmldom', 'jquery.jquote2', 
	'i18n', 'jsonrpc', 'heiduc', 'cms', 'back-services',
	'moment-js','noty','noty-layout',
	//'tinynav','tisa-common',
	'jquery.easing','bootstrap','datepicker','jquery.nicescroll'
	//,'perfect-scrollbar-0.4.8.with-mousewheel'
	
], function(App) {
	
	Heiduc.initJSONRpc(function() {
		Heiduc.jsonrpc.loginFrontService.getSystemProperties(function(p) {
			Heiduc.version = p.map.version;
			Heiduc.fullVersion = p.map.fullVersion;
			Heiduc.loggedIn = p.map.loggedIn === 'true';
			Heiduc.app = new App();
		});
	});
	
});
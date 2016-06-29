
//******************************************************************************
// This is CMS related JS code.
//******************************************************************************

// Heiduc namespace should exists.

if (Heiduc == undefined) {
	alert(messages('heiduc.namespace_error'));
}

// ****************************** Constants ************************************

/**
 * Autosave timeout in seconds.
 */
Heiduc.AUTOSAVE_TIMEOUT = 60;

Heiduc.ENGLISH_CODE = 'en';

//************************** Utility functions *********************************

Heiduc.info = function(msg) {
	//Heiduc.infoMessage('#wrapper .messages', msg);
    //$('#main_wrapper .messages').fadeIn();
    //setTimeout(function() {
    //	$('#main_wrapper .messages').fadeOut();
    //}, 5000);
    noty({	text: msg, 
    		layout: "bottomRight", 
    		type: 'success',
    		theme: 'tisa_theme',
    		timeout: 10000,
    		closeWith: ['button'],
			maxVisible: 10
    	});
};

Heiduc.error = function(msg) {
    //Heiduc.errorMessage('#main_wrapper .messages', msg);	
    //$('#main_wrapper .messages').fadeIn();
    //setTimeout(function() {
    //	$('#main_wrapper .messages').fadeOut();
    //}, 30000);
    noty({	text: msg, 
    		layout: "bottomRight", 
    		type: 'error',
    		theme: 'tisa_theme',
    		timeout: false,
    		closeWith: ['button'],
			maxVisible: 10
    	});
};

Heiduc.infoMessage = function(widget, msg) {
	//$(widget).html('<div class="ui-widget">\
	//	<div class="ui-state-highlight ui-corner-all" style="padding: 0.5em 0.7em;margin: 4px;"><p>\
	//	<span class="ui-icon ui-icon-info" style="float:left;margin-right:0.3em" />\
	//	<strong>' + messages('heiduc.hey') + '</strong> ' + msg + '</p></div></div>');
	noty({	text: msg, 
		layout: "bottomRight", 
		type: 'info',
		theme: 'tisa_theme',
		timeout: 1000,
		dismissQueue:false,
		closeWith: ['click'],
		maxVisible: 10
	});
};

Heiduc.errorMessage = function(widget, msg) {
	//$(widget).html('<div class="ui-widget">\
	//	<div class="ui-state-error ui-corner-all" style="padding: 0.5em 0.7em;margin: 4px;"><p>\
	//	<span class="ui-icon ui-icon-alert" style="float:left;margin-right:0.3em" />\
	//	<strong>' + messages('alert') + '!</strong> ' + msg + '</p></div></div>');
	noty({	text: msg, 
		layout: "bottomRight", 
		type: 'error',
		theme: 'tisa_theme',
		dismissQueue: false,
		timeout: 3000,
		closeWith: ['click'],
		maxVisible: 10
	});
};

Heiduc.errorMessages = function(widget, errors) {
    var msg = '';
    $.each(errors, function (i, m) {
        msg += (i == 0 ? '' : '<br />') + m;
    });
    Heiduc.errorMessage(widget, msg);
};

Heiduc.showServiceMessages = function(r) {
	if (r.result == 'success') {
		//Heiduc.info(r.message);
		if (r.messages.list.length > 0) {
			$.each(r.messages.list, function(n,value) { Heiduc.info(value) });
		}
	}
	else {
		//Heiduc.error(r.message);
		if (r.messages.list.length > 0) {
			$.each(r.messages.list, function(n,value) { Heiduc.error(value) });
		}
	}
};

/**
 * Create map from list of object with id field as key.
 * @param list - list of objects.
 * @return - map of objects.
 */
Heiduc.idMap = function(list) {
	var map = {};
	$.each(list, function (i, value) {
		map[value.id] = value;
	});
	return map;
};

Heiduc.message = function(s) {
	if (s.charAt(0) == '$') {
		return messages(s.substr(1));
	}	
	return s;
};

Heiduc.addCSSFile = function(css) {
	$('head').append('<link rel="stylesheet" href="' + css + '" type="text/css" />');
};

Heiduc.addCSSFiles = function(cssFiles) {
	$.each(cssFiles, function(i, css) {
		Heiduc.addCSSFile(css);
	});
};

Heiduc.removeCSSFile = function(css) {
	$('head link[href="' + css + '"]').remove();
};

Heiduc.removeCSSFiles = function(cssFiles) {
	$.each(cssFiles, function(i, css) {
		Heiduc.removeCSSFile(css);
	});
};

// GAE Channel API channel for current page.
// page should include <script type="text/javascript" src="/_ah/channel/jsapi"></script>
// before heiduc.js

Heiduc.clientId = Heiduc.generateGUID();

Heiduc.channel = null;

Heiduc.socket = null;

/**
 * Page Channel API initialization.
 */
Heiduc.initChannel = function(onOpened, onMessage, onError, onClose) {
	/*
	Heiduc.jsonrpc.channelApiFrontService.createToken(function(r) {
		Heiduc.channel = new goog.appengine.Channel(r);
	    socket = Heiduc.channel.open();
	    socket.onopen = onOpened;
	    socket.onmessage = onMessage;
	    socket.onerror = onError;
	    socket.onclose = onClose;
	    Heiduc.socket = socket;
	}, Heiduc.clientId);
	*/
	var cometd = atmosphere;
	if(Heiduc.socket != null){
		cometd.unsubscribe();
	}
	//var request = new atmosphere.AtmosphereRequest();
	var transport = 'websocket';
	var request = { url: "/_ah/channel/"+Heiduc.clientId,
        contentType : "application/json",
        logLevel : 'error',
        transport : transport ,
        fallbackTransport : "long-polling",
        trackMessageLength : false,
        reconnectInterval : 5000,
        enableXDR: true,
        enableProtocol: true,
        timeout : 60000
        };
	request.onOpen = onOpened;
	request.onMessage = onMessage;
	/*function(message){//onMessage;
		//var data = atmosphere.util.parseJSON(message);

		//onMessage(data);
		};*/
	request.onError = onError;
	request.onClose = onClose;
	var socket = cometd.subscribe(request);
	Heiduc.socket = socket;
};

Heiduc.sendChannelCommand = function(cmd, paramsObj) {
	var params = '&clientId=' + Heiduc.clientId;
	$.each(paramsObj, function(k, v){
		params += '&' + k + '=' + v;
	});
	//Heiduc.socket.push('cmd=' + cmd + params);
	var xhr = new XMLHttpRequest();
	xhr.open('POST', '/_ah/channelCommand?cmd=' + cmd + params, true);
	xhr.send();
};

Heiduc.changeLanguageCall = function(lang) {
	Heiduc.jsonrpc.loginFrontService.setLanguage(function(r) {
		location.reload();
	}, lang);
};

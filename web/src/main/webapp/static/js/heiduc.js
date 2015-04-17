
//******************************************************************************
// Global namespace.
// For frontend and backend use.
//******************************************************************************
var Heiduc = {};

Heiduc.javaList = function(array) {
	return {javaClass: 'java.util.ArrayList', list: array};
};

Heiduc.javaMap = function(aMap) {
	return {javaClass: 'java.util.HashMap', map: aMap};
};

Heiduc.accents = { 'ÀÁÂÃÄÅ':'A',	'Æ':'AE', 'Ç':'C','ÈÉÊË':'E', 'ÌÍÎÏ':'I',
	'Ð':'Th', 'Ñ':'N', 'ÒÓÔÕÖØ':'O', 'ÙÚÛÜ':'U', 'Ý':'Y', 'Þ':'Th',
	'ß':'ss', 'àáâãäå':'a', 'æ':'ae', 'ç':'c', 'èéêë':'e', 'ìíîï':'i',
	'ð':'o', 'ñ':'n', 'òóôõöø':'o', 'ùúûü':'u', 'ýÿ':'y', 'þ':'th'};

Heiduc.replaceAccents = function(s) {
	$.each(Heiduc.accents, function(n, value) {
		for (var i=0; i < n.length; i++) {
			s = s.replace(n.charAt(i), value);
		}
	});
	return s.toLowerCase();
};

Heiduc.urlFromTitle = function(title) {
    return Heiduc.replaceAccents(title.toLowerCase()).replace(/\W/g, '-');
};

Heiduc.nameFromTitle = function(title) {
    return title.toLowerCase().replace(/\W/g, '_');
};

Heiduc.isImage = function(filename) {
    var ext = Heiduc.getFileExt(filename);
	return ext.toLowerCase().match(/gif|jpg|jpeg|png|ico/) != null;
};

Heiduc.getFileExt = function(filename) {
	return filename.substring(filename.lastIndexOf('.') + 1, filename.length);
};

Heiduc.getFileName = function(path) {
	var slash = path.lastIndexOf('\\');
	if (slash == -1) {
		slash = path.lastIndexOf('/');
	}	
	return slash == -1 ? path : path.substring(slash + 1, path.length);
};

Heiduc.formatDate = function(date) {
	return $.datepicker.formatDate('dd.mm.yy', date);
};

Heiduc.formatTime = function(date) {
	return $.datepicker.formatDate('HH:MM', date);
};

Heiduc.identifier_regex = /^[a-zA-Z_][a-zA-Z0-9_]*$/;

Heiduc.isValidIdentifier = function(s) {
	return Heiduc.identifier_regex.test(s);
};

Heiduc.strip = function(s) {
	var i = 0;
	while (i < s.length && s[i] == ' ') i++;
	var s1 = s.substring(i);
	i = s1.length - 1;
	while (i >= 0 && s1[i] == ' ') i--;
	return s1.slice(0, i + 1);
};

Heiduc.getQueryParam = function(param) {
    var result =  window.location.search.match(
        new RegExp("(\\?|&)" + param + "(\\[\\])?=([^&]*)")
    );
    return Heiduc.escapeHtml(result ? result[3] : '');
};

/*
Heiduc.selectTabFromQueryParam = function(tab) {
	if (Heiduc.getQueryParam('tab')) {
		tab.tabs('select', Number(Heiduc.getQueryParam('tab')));
	}
};*/

Heiduc.escapeHtml = function(s) {
	return s.replace(/&/g,'&amp;')                                         
		.replace(/>/g,'&gt;')                                           
		.replace(/</g,'&lt;')                                           
		.replace(/"/g,'&quot;')
		.replace(/`/g,'&lsquo;')
		.replace(/'/g,'&rsquo;');
}

Heiduc.unescapeHtml = function(s) {
	return s.replace(/&amp;/g,'&')                                         
		.replace(/&gt;/g,'>')                                           
		.replace(/&lt;/g,'<')                                           
		.replace(/&quot;/g,'"')
		.replace(/&lsquo;/g,'`')
		.replace(/&rsquo;/g,"'");
}

Heiduc.generateGUID = function() {
	function S4() {
		return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
	}
	return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
}

/**
 * Global JSON-RPC entry point.
 */

Heiduc.jsonrpc = '';
Heiduc.jsonrpcListeners = [];
Heiduc.jsonrpcSystemListeners = [];
Heiduc.jsonrpcInitialized = false;
Heiduc.jsonrpcInitError = null;

/**
 * Don't call this function directly.
 */
Heiduc.createJSONRpc = function() {
	Heiduc.jsonrpc = new JSONRpcClient(function(result, e) {
		if (e) {
			Heiduc.jsonrpcInitError = "Error during JSON-RPC initialization " + e 
				+ ' ' + e.message;
		}
		else {
			while (Heiduc.jsonrpcSystemListeners.length > 0) {
				var func = Heiduc.jsonrpcSystemListeners.pop();
				func();
			}
			while (Heiduc.jsonrpcListeners.length > 0) {
				var func = Heiduc.jsonrpcListeners.pop();
				func();
			}
		}
		Heiduc.jsonrpcInitialized = true;
	}, '/json-rpc/');
};

Heiduc.createJSONRpc();

/**
 * Add application JSON-RPC initialization callback.
 * @param func - callback to run after successful initialization.
 */
Heiduc.initJSONRpc = function(func) {
	if (func == undefined) {
		return;
	}
	if (Heiduc.jsonrpcInitialized) {
        func();
    } else {
    	Heiduc.jsonrpcListeners.push(func);
    }
};

/**
 * Add system (high priority) JSON-RPC initialization callback.
 * @param func - callback to run after successful initialization.
 */
Heiduc.initJSONRpcSystem = function(func) {
	if (func == undefined) {
		return;
	}
	if (Heiduc.jsonrpcInitialized) {
        func();
    } else {
    	Heiduc.jsonrpcSystemListeners.push(func);
    }
};

Heiduc.serviceFailed = function(e) {
	if (e != null) {
		alert("JSON-RPC service fail " + e + ' ' + e.message);
		return true;
	}
	return false;
};

Heiduc.changeLanguage = function(lang) {
    var url = location.href.replace('#', '');
    var langIndex = url.indexOf('language=');
    var sign = location.search.indexOf('?') == -1 ? '?' : '&';
    if (langIndex > 0) {
        var langAfter = url.substr(langIndex+1);
        var langAndIndex = langAfter.indexOf('&');
        url = url.substr(0, langIndex) + 'language=' + lang;
        if(langAndIndex > 0) {
            url = url + langAfter.substr(langAndIndex+1);
        }
        if(url.indexOf('&') < 0) {
            sign = '';
        }
    } else {
        url = url + sign + 'language=' + lang;
    }
    location.href = url;
};

// String enhancements 

String.prototype.trim = function() {
	return this.replace(/^\s+|\s+$/g,"");
}
String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}
String.prototype.rtrim = function() {
	return this.replace(/\s+$/,"");
}
String.prototype.startsWith = function(str) {
	return (this.match("^"+str)==str)
}
String.prototype.endsWith = function(str) { 
	return (this.match(str+"$")==str)
}

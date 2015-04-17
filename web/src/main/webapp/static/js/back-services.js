// Heiduc namespace should exists.

if (Heiduc == undefined) {
	alert(messages('heiduc.namespace_error'));
}

Heiduc.backServiceFailed = function(e) {
	if (e != null) {
		Heiduc.error(messages('heiduc.cant_connect') + ' ' + e + ' ' + e.message 
			+ ' ' + e.code + ' ' + e.msg);
		return true;
	}
	return false;
};

Heiduc.serviceHandler = function(serviceFunc) {
	return function() {
		var callback = arguments[0];
		var serviceFuncArgs = arguments;
		serviceFuncArgs[0] = function (r, e) {
			$('#loading').hide();
			if (Heiduc.backServiceFailed(e)) return;
			callback(r);
		};
		$('#loading').show();
		serviceFunc.apply(null, serviceFuncArgs);
	}
};

Heiduc.setupJSONRpcHooks = function() {
	for (var serviceName in Heiduc.jsonrpc) {
		if (serviceName.indexOf('Service') != -1 
			&& serviceName.indexOf('FrontService') == -1) {
			for (var methodName in Heiduc.jsonrpc[serviceName]) {
				if (typeof Heiduc.jsonrpc[serviceName][methodName] == 'function') {
					var func = Heiduc.jsonrpc[serviceName][methodName];
					Heiduc.jsonrpc[serviceName][methodName] = Heiduc.serviceHandler(func);
				}
			}
		}
	}
};

/**
 * Backend services.
 */
Heiduc.initBackServices = function() {
	Heiduc.setupJSONRpcHooks();
};


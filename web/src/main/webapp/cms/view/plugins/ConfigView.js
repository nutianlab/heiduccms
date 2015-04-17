// file ConfigView.js

define(['text!template/plugins/config.html', 'jquery.form'], 
function(tmpl) {
	
	console.log('Loading ConfigView.js');
	
	var plugins = '';

	function postRender() {
	    //$("#tabs").tabs();
	    //$("#import-dialog").dialog({ width: 400, autoOpen: false });
	    //$("#afterUpload-dialog").dialog({autoOpen: false});
	    Heiduc.initJSONRpc(loadData);
	    $('#upload').ajaxForm(afterUpload);
	    $('#installButton').click(onInstall);
	    $('#importCancelButton').click(onImportCancel);
	    $('#okButton').click(onAfterUploadOk);
	}

	function loadData() {
		loadPlugins();
	}
	    
	function loadPlugins() {
		Heiduc.jsonrpc.pluginService.select(function (r) {
			plugins = r.list;
			showPlugins();
		});
	}

	function showPlugins() {
	    var html = '<table class="table table-striped"><th>' + messages('title') + '</th>\
	       <th>' + messages('name') + '</th><th>' + messages('version') + '</th><th>'
	       + messages('description') + '</th><th>' + messages('website') + '</th><th>'
	       + messages('state') + '</th><th></th></tr>';
	    $.each(plugins, function(i, plugin) {
	        var configURL = '#plugin/' + plugin.id;
	    	if (plugin.configURL) {
	        	//configURL = '/file/plugins/' + plugin.name + '/' + plugin.configURL;
	        	configURL = '#addons/' + plugin.name ;
	        }
	    	var state = plugin.disabled ? messages('disabled') : messages('enabled');
	    	var link = plugin.disabled ? Heiduc.message(plugin.title) :
	    		'<a href="' + configURL + '">' + Heiduc.message(plugin.title) + '</a>';
	    	html += '<tr><td>' + link + '</td>'
	    		+ '<td>' + plugin.name + '</td>'
	    		+ '<td>' + plugin.version + '</td>'
	            + '<td>' + Heiduc.message(plugin.description)  + '</td>'
	            + '<td>' + plugin.website + '</td>'
	            + '<td>' + state + '</td>'
	            + '<td><a title="Uninstall" class="removeLink" data-index="' + i + '">'
	            + '<img src="/static/images/02_x.png"/></a></td></tr>';
	    });
	    $('#plugins').html(html + '</table>');
	    $('#plugins tr:even').addClass('even');
	    $('#plugins .removeLink').click(function() {
	    	onRemove(Number($(this).attr('data-index')));
	    });
	}

	function onInstall() {
	    $("#import-dialog").modal({show:true});
	}

	function onImportCancel() {
	    $("#import-dialog").modal("hide");
	}

	function afterUpload(data) {
	    var s = data.split('::');
	    var result = s[1];
	    var msg = s[2]; 
	    if (result == 'success') {
	        msg = messages('plugins.success_install');
	    }
	    else {
	        msg = messages('error') + ". " + msg;
	    }   
	    $("#import-dialog").modal("hide");
	    $("#afterUpload-dialog .message").text(msg);
	    $("#afterUpload-dialog").modal('show');
	}

	function onAfterUploadOk() {
	    //$("#afterUpload-dialog").dialog('close');
	    location.reload();
	}

	function onRemove(i) {
		if (confirm(messages('are_you_sure'))) {
			Heiduc.jsonrpc.pluginService.remove(function(r) {
				Heiduc.showServiceMessages(r);
				if (r.result == 'success') {
					plugins.splice(i, 1);
					showPlugins();
				}
			}, plugins[i].id);
		}
	}
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			//$('#import-dialog, #afterUpload-dialog').dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
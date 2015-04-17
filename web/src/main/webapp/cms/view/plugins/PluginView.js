// file PluginView.js

define(['text!template/plugins/plugin.html'], 
function(tmpl) {
	
	console.log('Loading PluginView.js');
	
	var plugin = null;
	var properties = null;

	function postRender() {
	    //$("#tabs").tabs();
	    Heiduc.initJSONRpc(loadData);
	    $('#cancelButton').click(onCancel);
	    $('#saveButton').click(onSave);
	}

	function loadData() {
		loadPlugin();
		loadProperties();
	}
	    
	function loadPlugin() {
		Heiduc.jsonrpc.pluginService.getById(function(r) {
			plugin = r;
			$('#plugin-title').text(plugin.name + ' ' + messages('plugin.config'));
			showPlugin();
		}, pluginId);
	}

	function loadProperties() {
		Heiduc.jsonrpc.pluginService.getProperties(function(r) {
			properties = r.list;
			showPlugin();
		}, pluginId);
	}

	function onCancel() {
		location.href = '#plugins';
	}

	function notNull(s) {
		return s == null ? '' : s;
	}

	function showPlugin() {
		if (plugin == null || properties == null) {
			return;
		}
		var h = '';
		var configData = getConfigData();
		$.each(properties, function(i,value) {
			var data = notNull(configData[value.name.toLowerCase()] ? 
					configData[value.name.toLowerCase()] : value.defaultValue);
			var title = Heiduc.message(value.title);
			if (value.type == 'String' || value.type == 'Integer') {
				h += '<div class="form-group"><label>' + title + '</label>'
					+ '<input class="form-control" id="property-' + value.name + '" value="' 
					+ Heiduc.escapeHtml(data) + '"/></div>';
			}
			if (value.type == 'Date') {
				h += '<div class="form-group"><label>' + title + '</label>'
					+ '<input class="form-control" id="property-' + value.name + '"/></div>';
			}
			if (value.type == 'Boolean') {
				h += '<div class="form-group"><label>' + title + '</label>'
					+ '<input class="form-control" type="checkbox" id="property-' + value.name 
					+ '"/></div>';
			}
			if (value.type == 'Text') {
				h += '<div class="form-group"><label>' + title + '</label>'
					+ '<textarea id="property-' + value.name + '"  class="form-control">'
					+ data + '</textarea></div>';
			}
		});
		$('#properties').html(h);
		$.each(properties, function(i,value) {
			var id = "#property-" + value.name;
			var data = configData[value.name.toLowerCase()] ? 
					configData[value.name.toLowerCase()] : '';
			if (value.type == 'Date') {
			    if (data) {
			    	$(id).val(data);
			    }
			    $(id).datepicker({dateFormat:'dd.mm.yy'});
			}
			if (value.type == 'Boolean') {
			    var checked = data == 'true';
				$(id).each(function() {this.checked = checked});
			}
		});
	}

	/**
	 * Extract config data from plugin configData XML.
	 * @return map with param name as a key
	 */
	function getConfigData() {
		var result = {};
		if (plugin.configData != '') {
			var domData = $.xmlDOM(plugin.configData, function(error) {
				Heiduc.error(messages('plugin.parsing_error') + ' ' + error);
			});
			$(domData).find('plugin-config').children().each(function() {
				result[this.tagName.toLowerCase()] = $(this).text();
			});
		}
		return result;
	}

	function validate(vo) {
		$.each(properties, function(i,value) {
			var data = vo[value.name];
			if (value.type == 'Integer' && parseInt(data) == NaN) {
				return messages('plugin.integer_expected') + ' ' + value.name;
			}
		});
	}

	function onSave() {
		var vo = {};
		var xml = '<plugin-config>\n';
		$.each(properties, function(i,value) {
			var id = '#property-' + value.name;
			if (value.type == 'String' || value.type == 'Integer'
				|| value.type == 'Date' || value.type == 'Text') {
				vo[value.name] = $(id).val();
				xml += '<' + value.name + '>' + Heiduc.escapeHtml(vo[value.name]) 
					+ '</' + value.name + '>\n';
			}
			if (value.type == 'Boolean') {
				vo[value.name] = String($(id + ':checked').size() > 0);
				xml += '<' + value.name + '>' + vo[value.name] + '</' + value.name 
					+ '>\n';
			}
		});
		xml += '</plugin-config>\n'
		var error = validate(vo);
		if (error) {
			Heiduc.error(error);
		}
		else {
			Heiduc.jsonrpc.pluginService.savePluginConfig(function(r) {
				Heiduc.showServiceMessages(r);
			}, Number(pluginId), xml);
		}	
	}
	
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
		},
		
		setPluginId: function(id) {
			pluginId = id;
		}
		
	});
	
});
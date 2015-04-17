// file AttributesView.js

define(['text!template/config/attributes.html'],
function(tmpl) {
	
	console.log('Loading AttributesView.js');
	
	var config = '';

	function postRender() {
	    $('ul.nav-tabs li:nth-child(8)').addClass('active');
	    //$("#attribute-dialog").dialog({ width: 400, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    $('#addButton').click(onAdd);
	    $('#cancelButton').click(function() {
	    	//$("#attribute-dialog").dialog('close');
	    });
	    $('#attributeForm').submit(function() {onSave(); return false});
	    $('#removeButton').click(onRemove);
	}

	function loadData() {
	    loadConfig();
	}

	function loadConfig() {
		Heiduc.jsonrpc.configService.getConfig(function (r) {
	        config = r;
	        showAttributes();
	    }); 
	}

	function showAttributes() {
	    var h = '<table class="table table-striped"><tr><th></th><th>' 
	    	+ messages('name') + '</th><th>' + messages('value') + '</th></tr>';
	    $.each(config.attributes.map, function (name, value) {
	        h += '<tr><td><input type="checkbox" value="' + name + '"/></td>'
	            + '<td><a data-name="' + name + '">' + name + '</a></td>'
	            + '<td>' + value + '</td></tr>';
	    });
	    $('#attributes').html(h + '</table>');
	    $('#attributes tr:even').addClass('even');
	    $('#attributes a').click(function() {
	    	onEdit($(this).attr('data-name'));
	    })
	}

	function onAdd() {
		//$("#attribute-dialog").dialog('open');
		$("#attribute-dialog").modal({show:true});
		$('#name').val('');
		$('#value').val('');
	}

	function onEdit(name) {
		//$("#attribute-dialog").dialog('open');
		$("#attribute-dialog").modal({show:true});
		$('#name').val(name);
		$('#value').val(config.attributes.map[name]);
	}

	function onSave() {
	    var name = $('#name').val();
	    var value = $('#value').val();       
	    Heiduc.jsonrpc.configService.saveAttribute(function(r) {
	    	Heiduc.showServiceMessages(r);
	    	//$("#attribute-dialog").dialog('close');
	    	loadData();
	    }, name, value); 
	}

	function onRemove() {
	    var ids = [];
	    $('#attributes input:checked').each(function () {
	    	ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.configService.removeAttributes(function (r) {
	    		Heiduc.info(r.message);
	            loadData();
	        }, Heiduc.javaList(ids));
	    }
	}
	
	return Backbone.View.extend({
		
		el: $('#tab-1'),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
		    //$("#attribute-dialog").dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
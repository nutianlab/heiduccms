

define(['text!template/structures.html'], function(tmpl) {
	
	console.log('Loading StructuresView.js');
	
	function loadStructures() {
		Heiduc.jsonrpc.structureService.select(function (r) {
	        var html = '<table class="table table-striped"><tr><th></th><th>'  
	        	+ messages('title') + '</th></tr>';
	        $.each(r.list, function (n, value) {
	            html += '<tr><td><input type="checkbox" value="' + value.id 
	                + '" /></td><td><a href="#structure/' + value.id
	                +'">' + value.title + '</a></td></tr>';
	        });
	        $('#structures').html(html + '</table>');
	        $('#structures tr:even').addClass('even');
	    });
	}

	function onAdd() {
		location.href = '#structure';
	}

	function onDelete() {
	    var ids = new Array();
	    $('#structures input:checked').each(function () {
	        ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.structureService.remove(function(r) {
	    		Heiduc.showServiceMessages(r);
	            loadStructures();
	        }, Heiduc.javaList(ids));
	    }
	}

	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
			loadStructures();
		    $('#addButton').click(onAdd);
		    $('#deleteButton').click(onDelete);
		},
		
		remove: function() {
			this.el.html('');
		}
		
	});
	
});
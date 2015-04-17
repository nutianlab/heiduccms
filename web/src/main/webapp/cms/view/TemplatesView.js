// file TemplatesView.js

define(['text!template/templates.html', 'jquery.form'], function(tmpl) {
	
	console.log('Loading TemplatesView.js');
	
	var exportTimer = null;
	var clockTimer = null;
	var clockSeconds = 0;
	var exportFilename = null;

	function initData() {
		loadTemplates();
		loadStructures();
	}

	function afterUpload(data) {
	    var s = data.split('::');
	    var result = s[1];
	    var msg = s[2]; 
	    if (result == 'success') {
	        msg = messages('templates.success_import');
	    }
	    else {
	        msg = messages('error') + ". " + msg;
	    }   
	    //$("#import-dialog").dialog("close");
	    $("#afterUpload-dialog .message").text(msg);
	    //$("#afterUpload-dialog").dialog('open');
	    $("#afterUpload-dialog").modal({show:true});
	}

	function onImport() {
	    //$("#import-dialog").dialog("open");
	    $("#import-dialog").modal({show:true});
	}

	function onImportCancel() {
	    //$("#import-dialog").dialog("close");
	}

	function onAfterUploadOk() {
	    //$("#afterUpload-dialog").dialog('close');
	    initData();
	}

	function loadTemplates() {
		Heiduc.jsonrpc.templateService.getTemplates(function (r) {
	        var html = '<table class="table table-striped"><tr><th></th><th>'
	        	+ messages('title') + '</th></tr>';
	        $.each(r.list, function (n, value) {
	            html += '<tr><td><input type="checkbox" value="' + value.id 
	                + '" /></td><td><a href="#template/' + value.id
	                +'">' + value.title + '</a></td></tr>';
	        });
	        $('#templates').html(html + '</table>');
	        $('#templates tr:even').addClass('even');
	    });
	}

	function loadStructures() {
		Heiduc.jsonrpc.structureService.select(function(r) {
	        var html = '<table class="table"><tr><th></th><th>'
	        	+ messages('title') + '</th></tr>';
	        $.each(r.list, function (n, value) {
	            html += '<tr><td><input type="checkbox" value="' + value.id 
	                + '" /></td><td>' + value.title + '</td></tr>';
	        });
	        $('#structures').html(html + '</table>');
	        $('#structures tr:even').addClass('even');
		});
	}

	function onAdd() {
		location.href = '#template';
	}

	function onDelete() {
	    var ids = new Array();
	    $('#templates input:checked').each(function () {
	        ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.templateService.deleteTemplates(function(r) {
	    		Heiduc.showServiceMessages(r);
	            loadTemplates();
	        }, Heiduc.javaList(ids));
	    }
	}

	function onExport() {
	    $('#structures input:checked').each(function() { this.checked = false; });
		//$("#structures-dialog").dialog("open");
		$("#structures-dialog").modal({show:true});
	}

	function onStartExport() {
		//$("#structures-dialog").dialog("close");
		clockSeconds = 0;
		showClock();
		var ids = [];
		var structureIds = [];
	    $('#templates input:checked').each(function () {
	        ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    $('#structures input:checked').each(function () {
	        structureIds.push(this.value);
	    });
	    //$("#export-dialog").dialog("open");
	    $("#export-dialog").modal({show:true});
	    Heiduc.jsonrpc.configService.startExportThemeTask(function(r) {
	    	if (r.result == 'success') {
	    		$('#templates input:checked').each(function () {
	    			this.checked = false;
	    		});
	    		exportFilename = r.message;
	    	    Heiduc.infoMessage('#exportInfo', messages('creating_export_file'));
	            exportTimer = setInterval(checkExport, 10 * 1000);
	            clockTimer = setInterval(showClock, 1000);
	    	}
	    	else {
	    		Heiduc.showServiceMessages(r);
	    	}
	    }, Heiduc.javaList(ids), Heiduc.javaList(structureIds));
	}

	function checkExport() {
		Heiduc.jsonrpc.configService.isExportTaskFinished(function(r) {
			if (r) {
				clearInterval(exportTimer);
				clearInterval(clockTimer);
				$("#export-dialog").dialog("close");
				$('#exportDialogButton').attr('disabled', false);
				location.href = '/file/tmp/' + exportFilename;
			}
		}, 'theme');
	}

	function showClock() {
		$('#timer').html(clockSeconds++ + ' ' + messages('sec') + '.');
	}

	function onExportCancel() {
		//$('#export-dialog').dialog('close');
		clearInterval(exportTimer);
		clearInterval(clockTimer);
	}
	
	
	return Backbone.View.extend({
		
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
		    //$("#import-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#export-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#structures-dialog").dialog({ width: 400, autoOpen: false });
		    //$("#afterUpload-dialog").dialog({autoOpen: false});
		    $('#upload').ajaxForm(afterUpload);
		    initData();
		    //$("#tabs").tabs();
		    $('#addButton').click(onAdd);
		    $('#deleteButton').click(onDelete);
		    $('#exportButton').click(onExport);
		    $('#exportCancelButton').click(onExportCancel);
		    $('#importButton').click(onImport);
		    $('#importCancelButton').click(onImportCancel);
		    $('#okButton').click(onAfterUploadOk);
		    $('#structuresForm').submit(function() {onStartExport(); return false;});
		    $('#structuresCancelButton').click(function() {
		    	//$("#structures-dialog").dialog("close");
		    });
		},
		
		remove: function() {
			//$('#import-dialog, #export-dialog, #structures-dialog, #afterUpload-dialog')
			//		.dialog('destroy').remove();
			
			this.el.html('');
		}
		
	});
	
});
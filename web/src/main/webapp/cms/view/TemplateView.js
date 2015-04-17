// file TemplateView.js

define(['text!template/template.html', 'jquery.treeview',
        'cm', 'cm-css', 'cm-js', 'cm-xml', 'cm-html'], 
function(tmpl) {
	
	console.log('Loading TemplateView.js');
	
	var templateId = '';

	var template = '';
	var editMode = templateId != '';
	var autosaveTimer = '';
	var editor = null;
	    
	function loadData() {
		loadTemplate();
	}

	function onSaveContinue() {
		onUpdate(true);
	}

	function onSave() {
		onUpdate(false);
	}
	    
	function startAutosave() {
	    if (templateId != 'null') {
	        if (autosaveTimer == '') {
	            autosaveTimer = setInterval(saveContent, 
	            		Heiduc.AUTOSAVE_TIMEOUT * 1000);
	        }
	    }
	}
	    
	function stopAutosave() {
	    if (autosaveTimer != '') {
	        clearInterval(autosaveTimer);
	        autosaveTimer = '';
	    }
	}

	function saveContent() {
		if (editor) editor.save();
	    var content = $("#tcontent").val();
	    Heiduc.jsonrpc.templateService.updateContent(function(r) {
	        if (r.result == 'success') {
	            var now = new Date();
	            Heiduc.info(r.message + " " + now);
	        }
	        else {
	        	Heiduc.error(r.message);
	        }            
	    }, templateId, content);        
	}

	function onAutosave() {
	    if ($("#autosave:checked").length > 0) {
	        startAutosave(); 
	    }
	    else {
	        stopAutosave();
	    }
	}

	function loadTemplate() {
		editMode = templateId != '';
		if (!editMode) {
			template = null;
			//edit by hsiaopo  fixed two textarea in page.
	        //initTemplateForm();
		}
		Heiduc.jsonrpc.templateService.getTemplate(function (r) {
			template = r;
			initTemplateForm();
		}, templateId);
	}

	function initTemplateForm() {
		if (template != null) {
			$('#title').val(template.title);
	        $('#url').val(template.url);
	        $('#tcontent').val(template.content);
		}
		else {
	        $('#title').val('');
	        $('#url').val('');
	        $('#tcontent').val('');
		}
		editor = CodeMirror.fromTextArea(document.getElementById('tcontent'), {
			lineNumbers: true,
			theme: 'eclipse',
			mode: 'htmlmixed'
		});
		editor.focus();
		$(editor.getScrollerElement())
			.css('height', (0.6 * $(window).height()) + 'px')
			.css('border', '1px solid silver');
		editor.refresh();
	}

	function onCancel() {
	    location.href = '#templates';
	}

	function onUpdate(cont) {
		if (editor) editor.save();
		var templateVO = Heiduc.javaMap({
		    id : templateId,
		    title : $('#title').val(),
	        url : $('#url').val(),
	        content : $('#tcontent').val()
		});
		Heiduc.jsonrpc.templateService.saveTemplate(function (r) {
			if (r.result == 'success') {
				Heiduc.info(messages('template.success_save'));
				if (!cont) {
					location.href = '#templates';
				}
				else if (!editMode) {
					templateId = r.message;
					loadTemplate();
				}
			}
			else {
				Heiduc.showServiceMessages(r);
			}			
		}, templateVO);
	}

	// Resources

	function onResources() {
		
		$.cookie('folderReturnPath', '#template/' + template.id, 
				{path:'/', expires: 10});
		Heiduc.jsonrpc.folderService.createFolderByPath(function(r) {
			location.href = '#folder/' + r.id;
	    }, '/theme/' + template.url);
	}


	return Backbone.View.extend({
		
		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css',
		      '/static/css/jquery.treeview.css'],
		
		el: $('#content'),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(_.template(tmpl, {messages:messages}));
			loadData();
		    $('#autosave').change(onAutosave);
		    $('#saveContinueButton').click(onSaveContinue);
		    $('#templateForm').submit(function() {onSave(); return false;});
		    $('#cancelButton').click(onCancel);
		    $('#resources').click(onResources);
		},
		
		remove: function() {
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		},
		
		create: function() {
			templateId = '';
			template = '';
			editMode = templateId != '';
		},
		
		edit: function(id) {
			templateId = id;
			template = '';
			editMode = templateId != '';
		}
		
	});
	
});
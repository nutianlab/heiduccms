// file StructureTemplateView.js

define(['text!template/structureTemplate.html',
        'cm', 'cm-css', 'cm-js', 'cm-xml', 'cm-html'], 
function(tmpl) {
	
	console.log('Loading StructureTemplateView.js');
	
	var structureTemplateId = ''; 
	var structureId = ''; 

	var structureTemplate = '',
	    editMode = structureTemplateId != '',
	    autosaveTimer = '',
	    editor = null, 
	    headEditor = null;
	    
	function onTitleChange() {
		if (editMode) {
			return;
		}
		var name = $("#name").val();
		var title = $("#title").val();
		if (name == '') {
			$("#name").val(Heiduc.urlFromTitle(title));
		}
	}

	function onSaveContinue() {
		onUpdate(true);
	}

	function onSave() {
		onUpdate(false);
	}
	    
	function startAutosave() {
	    if (structureTemplateId != 'null') {
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
		onUpdate(true);   
	}

	function onAutosave() {
	    if ($("#autosave:checked").length > 0) {
	        startAutosave(); 
	    }
	    else {
	        stopAutosave();
	    }
	}

	function loadStructureTemplate() {
		editMode = structureTemplateId != '';
		if (!editMode) {
			structureTemplate = null;
	        initStructureTemplateForm();
		}
		else {
			Heiduc.jsonrpc.structureTemplateService.getById(function (r) {
				structureTemplate = r;
				if (editMode) {
					structureId = structureTemplate.structureId;
				}
				initStructureTemplateForm();
			}, structureTemplateId);
		}
	}

	function initStructureTemplateForm() {
		if (structureTemplate != null) {
			$('#name').val(structureTemplate.name);
			$('#title').val(structureTemplate.title);
	        $('#vcontent').val(structureTemplate.content);
	        $('#headContent').val(structureTemplate.headContent);
		}
		else {
	        $('#name').val('');
	        $('#title').val('');
	        $('#vcontent').val('');
	        $('#headContent').val('');
		}
		if (!editor) {
			editor = createEditor('vcontent');
		} else {
			editor.refresh();
		}
		if (!headEditor) {
			headEditor = createEditor('headContent');
		} else {
			headEditor.refresh();
		}
	}

	function createEditor(id) {
		var e = CodeMirror.fromTextArea(document.getElementById(id), {
			lineNumbers: true,
			theme: 'eclipse',
			mode: 'htmlmixed'
		});
		$(e.getScrollerElement())
			.css('height', (0.6 * $(window).height()) + 'px')
			.css('border', '1px solid silver');
		return e;
	}
	
	function onCancel() {
	    location.href = '#structure/' + structureId;
	}

	function onUpdate(cont) {
		if (editor) {
			editor.save();
		}
		if (headEditor) {
			headEditor.save();
		}
		var structureTemplateVO = Heiduc.javaMap({
		    id : structureTemplateId,
		    name : Heiduc.strip($('#name').val()),
		    title : Heiduc.strip($('#title').val()),
		    type: 'VELOCITY', 
		    structureId: String(structureId),
	        content : $('#vcontent').val(),
	        headContent : $('#headContent').val()
		});
		Heiduc.jsonrpc.structureTemplateService.save(function (r) {
			if (r.result == 'success') {
				Heiduc.info(messages('structureTemplate.success_save'));
				if (!cont) {
					onCancel();
				}
				else if (!editMode) {
					structureTemplateId = r.message;
					loadStructureTemplate();
				}
			}
			else {
				Heiduc.showServiceMessages(r);
			}			
		}, structureTemplateVO);
	}

	
	return Backbone.View.extend({
		
		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css'],
		
		el: $('#content'),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			editor = null; 
		    headEditor = null;
			this.el.html(_.template(tmpl, {messages:messages}));
			loadStructureTemplate();
		    //$("#tabs").tabs();
		    $('#autosave').change(onAutosave);
		    $('#saveContinueButton').click(onSaveContinue);
		    $('#saveButton').click(onSave);
		    $('#cancelButton').click(onCancel);
		    $('#title').change(onTitleChange);
		},
		
		remove: function() {
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		},
		
		create: function(id) {
			structureTemplateId = '';
			structureId = id;
			structureTemplate = '';
			editMode = structureTemplateId != '';
		},
		
		edit: function(id) {
			structureTemplateId = id;
			structureId = '';
			structureTemplate = '';
			editMode = structureTemplateId != '';
		}
		
	});
	
});
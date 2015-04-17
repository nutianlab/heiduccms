// file FileView.js

define(['text!template/file.html', 'cm', 'cm-css',
        'cm-js', 'cm-xml', 'cm-html'], 
function(html) {
	
	console.log('Loading FileView.js');
	
	var fileId = null;
	var folderId = null;
	
	var file = '';
	var editMode = fileId != '';
	var autosaveTimer = '';
	var editor = null;

	function postRender() {
		editMode = fileId != '';
		//$("#tabs").tabs({show: tabSelected});
		$('a[data-toggle="tab"]').on( 'shown.bs.tab',tabSelected);
		Heiduc.initJSONRpc(loadData);
		$('#fileForm').submit(function() {onUpdate(); return false});
		$('#cancelButton').click(onCancel);
		$('#autosave').change(onAutosave);
		$('#contentForm').submit(function() {saveContent(); return false;});
		$('#contentCancelButton').click(onCancel);
	}

	function tabSelected(event, ui) {
		if (event.target.id === "_fileContent") {
			startAutosave();
			if (editor) {
				editor.focus();
				
				$(editor.getScrollerElement())
					.css('height', (0.6 * $(window).height()) + 'px')
					.css('border', '1px solid silver');
					
				editor.refresh();
			}
		} else {
			stopAutosave();
		}
	}

	function startAutosave() {
		if (fileId != 'null' && $("#autosave:checked").length > 0) {
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
		var content = $("textarea").val();
		Heiduc.jsonrpc.fileService.updateContent(function(r) {
			if (r.result == 'success') {
				var now = new Date();
				Heiduc.info(r.message + " " + now);
			} else {
				Heiduc.error(r.message);
			}
		}, fileId, content);
	}

	function onAutosave() {
		if ($("#autosave:checked").length > 0) {
			startAutosave();
		} else {
			stopAutosave();
		}
	}

	function loadData() {
		Heiduc.jsonrpc.fileService.getFile(function(r) {
			file = r;
			if (editMode) {
				folderId = file.folderId;
			}
			initFormFields();
		}, fileId);
	}

	function openCM(mimetype) {
		var mode = 'html';
		if (file.mimeType == 'text/css') {
			mode = 'css';
		}
		if (file.mimeType == 'text/xml') {
			mode = 'xml';
		}
		if (file.mimeType == 'text/html') {
			mode = 'htmlmixed';
		}
		if (file.mimeType.indexOf('javascript') != -1) {
			mode = 'javascript';
		}
		editor = CodeMirror.fromTextArea(document.getElementById('fileContent'), {
			lineNumbers: true,
			theme: 'eclipse',
			mode: mode
		});
	}

	function initFormFields() {
		if (editMode) {
			$('#filename').html(file.name);
			$('#title').val(file.title);
			$('#name').val(file.name);
			$('#fileEditDiv').show();
			$('#mimeType').html(file.mimeType);
			$('#size').html(file.size);
			$('#fileLink').html(file.link);
			$('#download').html('<a href="' + file.link + '">' + messages('download') + '</a>');
			if (file.textFile) {
				$('.contentTab').show();
				$('#fileContent').val(file.content);
				openCM(file.mimetype);
			} else {
				$('.contentTab').hide();
			}
			if (file.imageFile) {
				$('#imageContent').html('<img src="' + file.link + '" />');
			} else {
				$('#imageContent').html('');
			}
		} else {
			$('#filename').html('');
			$('#title').val('');
			$('#name').val('');
			$('#fileEditDiv').hide();
			$('.contentTab').hide();
			$('#imageContent').html('');
		}
	}

	function onUpdate() {
		var vo = Heiduc.javaMap( {
			id : fileId,
			folderId : String(folderId),
			title : $('#title').val(),
			name : $('#name').val()
		});
		Heiduc.jsonrpc.fileService.saveFile(function(r) {
			Heiduc.showServiceMessages(r);
			if (r.result == 'success') {
				location.href = '#file/' + r.data;
			}
		}, vo);
	}

	function onCancel() {
		location.href = '#folder/' + folderId;
	}

	
	return Backbone.View.extend({
		
		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css',
		      '/static/css/file.css'],
		
		el: $('#content'),
		
		tmpl: _.template(html),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(this.tmpl({messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		},
		
		setFileId: function(id) {
			fileId = id;
		},
		
		setFolderId: function(id) {
			folderId = id;
		}
		
	});
	
});
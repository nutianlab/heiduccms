// file FormsView.js

define(['text!template/plugins/forms.html',
        'cm-css', 'cm-js', 'cm-xml', 'cm-html'], 
function(tmpl) {
	
	console.log('Loading FormsView.js');
	
	var formConfig = '',
		templateEditor = null,
		letterEditor = null;

	function postRender() {
	    //$("#tabs").tabs({show: tabSelected});
	    $('a[data-toggle="tab"]').on('shown.bs.tab',tabSelected);
	    Heiduc.initJSONRpc(loadData);
	    $('#addButton').click(onAdd);
	    $('#deleteButton').click(onDelete);
	    $('#restoreFormTemplateLink').click(onFormTemplateRestore);
	    $('#restoreFormLetterLink').click(onFormLetterRestore);
	    $('#saveButton').click(onSaveConfig);
	}

	function tabSelected(event) {
		if (event.target.id === "_config") {
			templateEditor.refresh();
			letterEditor.refresh();
		}
	}

	function loadData() {
	    loadForms();
	    loadFormConfig();
	}
	    
	function loadForms() {
		Heiduc.jsonrpc.formService.select(function (r) {
	        var html = '<table class="table table-striped"><tr><th></th><th>' + messages('title')
	        	+ '</th><th>' + messages('name') + '</th><th>' + messages('email') 
	        	+ '</th></tr>';
	        $.each(r.list, function(i, form) {
	            html += '<tr><td><input type="checkbox" value="' + form.id
	                + '"/></td><td><a href="#plugins/form/' + form.id 
	                + '">' + form.title + '</a></td><td>' + form.name 
	                + '</td><td>' + form.email + '</td></tr>';
	        });
	        $('#forms').html(html + '</table>');
	        $('#forms tr:even').addClass('even');
	    });
	}

	function onAdd() {
	    location.href = '#plugins/form';
	}
	    
	function onDelete() {
	    var ids = [];
	    $('#forms input:checked').each(function() {
	        ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.formService.deleteForm(function (r) {
	    		Heiduc.showServiceMessages(r);
	            loadForms();
	        }, Heiduc.javaList(ids));
	    }
	}

	function loadFormConfig() {
		Heiduc.jsonrpc.formService.getFormConfig(function (r) {
	        formConfig = r;
	        $('#formTemplate').val(r.formTemplate);
	        $('#letterTemplate').val(r.letterTemplate);
			templateEditor = CodeMirror.fromTextArea(document.getElementById('formTemplate'), {
				lineNumbers: true,
				theme: 'eclipse',
				mode: "htmlmixed"
			});
			$(templateEditor.getScrollerElement()).css('border', '1px solid silver');
			letterEditor = CodeMirror.fromTextArea(document.getElementById('letterTemplate'), {
				lineNumbers: true,
				theme: 'eclipse',
				mode: "htmlmixed"
			});
			$(letterEditor.getScrollerElement()).css('border', '1px solid silver');
	    });
	}
	    
	function onSaveConfig() {
		templateEditor.save();
		letterEditor.save();
	    var vo = Heiduc.javaMap({
	   	    formTemplate : $('#formTemplate').val(),
	   	    letterTemplate : $('#letterTemplate').val()
	    });
	    Heiduc.jsonrpc.formService.saveFormConfig(function (r) {
	    	Heiduc.showServiceMessages(r);
	    }, vo);
	}

	function onFormTemplateRestore() {
		Heiduc.jsonrpc.formService.restoreFormTemplate(function (r) {
			Heiduc.showServiceMessages(r);
	        $('.CodeMirror').remove();
			loadFormConfig();
	    });
	}

	function onFormLetterRestore() {
		Heiduc.jsonrpc.formService.restoreFormLetter(function (r) {
			Heiduc.showServiceMessages(r);
	        $('.CodeMirror').remove();
	        loadFormConfig();
	    });
	}
	
	
	return Backbone.View.extend({

		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css'],
		
		el: $('#content'),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		}
		
	});
	
});
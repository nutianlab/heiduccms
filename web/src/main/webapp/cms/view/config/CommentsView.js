// file CommentsView.js

define(['text!template/config/comments.html',
        'cm', 'cm-css', 'cm-js', 'cm-xml', 'cm-html'],
function(tmpl) {
	
	console.log('Loading CommentsView.js');
	
	var config = '',
		editor = null;
	
	function postRender() {
	    Heiduc.initJSONRpc(loadData);
	    $('#commentsForm').submit(function() {onSave(); return false;});
	    $('#restoreButton').click(onRestore);
	    $('ul.nav-tabs li:nth-child(2)').addClass('active');
	}

	function loadData() {
	    loadConfig();
	}

	function loadConfig() {
		Heiduc.jsonrpc.configService.getConfig(function (r) {
	        config = r;
	        initFormFields();
	    }); 
	}

	function initFormFields() {
	    $('#commentsEmail').val(config.commentsEmail);
	    $('#commentsTemplate').val(config.commentsTemplate);
		editor = CodeMirror.fromTextArea(document.getElementById('commentsTemplate'), {
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

	function onSave() {
		editor.save();
	    var vo = Heiduc.javaMap({
	        commentsEmail : $('#commentsEmail').val(),
	        commentsTemplate : $('#commentsTemplate').val()       
	    });
	    Heiduc.jsonrpc.configService.saveConfig(function(r) {
	    	Heiduc.showServiceMessages(r);
	    }, vo); 
	}

	function onRestore() {
		Heiduc.jsonrpc.configService.restoreCommentsTemplate(function (r) {
			Heiduc.showServiceMessages(r);
	        loadConfig();
	    });
	}

	return Backbone.View.extend({
		
		css: ['/static/js/codemirror/codemirror.css',
		      '/static/js/codemirror/eclipse.css'],

		el: $('#tab-1'),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el = $('#tab-1');
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		}
		
	});
	
});
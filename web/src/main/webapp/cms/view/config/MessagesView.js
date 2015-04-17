// file MessagesView.js

define(['text!template/config/messages.html'],
function(tmpl) {
	
	console.log('Loading MessagesView.js');
	
	var languages = null;

	function postRender() {
	    //$("#message-dialog").dialog({ width: 400, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    $('#addMessageButton').click(onAddMessage);
	    $('#removeMessageButton').click(onRemoveMessage);
	    $('#messageForm').submit(function() {onMessageSave(); return false;});
	    $('#cancelMessageDlgButton').click(onMessageCancel);
	    $('ul.nav-tabs li:nth-child(4)').addClass('active');
	}

	function loadData() {
		loadLanguages();
	}

	// Language

	function loadLanguages() {
		Heiduc.jsonrpc.languageService.select(function (r) {
	        languages = r.list;
	        loadMessages();
	    });
	}

	// Message 

	function onAddMessage() {
		clearDialogMessages();
	    var h = '';
		$.each(languages, function (i, lang) {
	        h += '<div class="form-row"><label>' + lang.title 
	            + '</label><input type="text" id="message_' + lang.code + '" /></div>';
	    });
	    $('#messageCode').val('');
		$('#messagesInput').html(h);
		//$('#message-dialog').dialog('open');
		$('#message-dialog').modal({show:true});
	}

	function onRemoveMessage() {
	    var codes = [];
	    $('#messageBundle input:checked').each(function () {
	        codes.push(this.value);
	    });
	    if (codes.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.messageService.remove(function (r) {
	    		Heiduc.info(r.message);
	            loadMessages();
	        }, Heiduc.javaList(codes));
	    }
	}

	function loadMessages() {
		Heiduc.jsonrpc.messageService.select(function (r) {
	        var h = '<table class="table table-striped"><tr><th></th><th>' + messages('code') 
	        	+ '</th>';
	        $.each(languages, function (i, lang) {
	            h += '<th>' + lang.title + '</th>';
	        });
	        h += '</tr>';
	        $.each(r.list, function (i, msg) {
	            h += '<tr><td><input type="checkbox" value="' + msg.code + '"/></td>\
	                <td><a data-code="' + msg.code + '">' + msg.code + '</a></td>';
	            $.each(languages, function (i, lang) {
	                h += '<td>' + msg.values.map[lang.code] + '</td>';
	            });
	            h += '</tr>';
	        });
	        $('#messageBundle').html(h + '</table>');
	        $('#messageBundle tr:even').addClass('even');
	        $('#messageBundle td a').click(function() {
	        	var code = $(this).attr('data-code');
	        	if (code) {
	        		onMessageEdit(code);
	        	}
	        });
		});
	}

	function onMessageEdit(code) {
		Heiduc.jsonrpc.messageService.selectByCode(function (r) {
	        onAddMessage();
	        $('#messageCode').val(code);
	        $.each(r.list, function (i, msg) {
	            $('#message_' + msg.languageCode).val(msg.value);
	        });
	    }, code);
	}

	function onMessageSave() {
	    var vo = {code : $('#messageCode').val() };
	    $.each(languages, function (i, lang) {
	        vo[lang.code] = $('#message_' + lang.code).val();
	    });
	    Heiduc.jsonrpc.messageService.save(function (r) {
	        if (r.result == 'success') {
	            loadMessages();
	            //$('#message-dialog').dialog('close');
	        }
	        else {
	            messageErrors(r.messages.list);
	        }
	    }, Heiduc.javaMap(vo));
	    
	}

	function onMessageCancel() {
	    //$('#message-dialog').dialog('close');
	}

	function messageError(msg) {
		Heiduc.errorMessage('#message-dialog .messages', msg);
	}

	function messageErrors(errors) {
		Heiduc.errorMessages('#message-dialog .messages', errors);
	}

	function clearDialogMessages() {
		$('#message-dialog .messages').html();
	}

	return Backbone.View.extend({
		
		el: $('#tab-1'),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
		    //$("#message-dialog").dialog('destroy').remove();
			this.el.html('');
		}
		
	});
	
});
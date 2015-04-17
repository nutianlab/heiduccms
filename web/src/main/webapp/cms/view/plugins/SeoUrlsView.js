// file SeoUrlsView.js

define(['text!template/plugins/seo-urls.html'], function(tmpl) {
	
	console.log('Loading SeoUrlsView.js');
	
	var seoUrl = null;

	function postRender() {
	    //$("#url-dialog").dialog({ width :500, autoOpen :false });
	    Heiduc.initJSONRpc(loadUrls);
	    //$('#tabs').tabs();
	    $('#addButton').click(onAdd);
	    $('#removeButton').click(onRemove);
	    $('#saveAndAddButton').click(onSaveAndAdd);
	    $('#seoForm').submit(function(){onSave(true); return false;});
	    $('#cancelDlgButton').click(onCancel);
	}

	function loadUrls() {
		Heiduc.jsonrpc.seoUrlService.select(function (r) {
	        var html = '<table class="table table-striped"><tr><th></th><th>' 
	        	+ messages('from') + '</th><th>' + messages('to') + '</th></tr>';
	        $.each(r.list, function (i, url) {
	            html += '<tr><td><input type="checkbox" value="' 
	                + url.id + '"/></td><td><a data-id="'
	                + url.id +'">' + url.fromLink + '</a></td><td>' 
	                + url.toLink + '</td></tr>';
	        });
	        $('#urls').html(html + '</table>');
	        $('#urls tr:even').addClass('even');
	        $('#urls a').click(function() {
	        	onEdit($(this).attr('data-id'));
	        });
	    });
	}

	function onEdit(id) {
	    clearMessages();
	    Heiduc.jsonrpc.seoUrlService.getById(function(r) {
	        seoUrl = r;
	        urlDialogInit();
	        $("#url-dialog").modal({show:true});
	    }, id);
	}

	function onAdd() {
	    seoUrl = null;
	    urlDialogInit();
	    $("#url-dialog").modal({show:true});
	}

	function urlDialogInit() {
		clearMessages();
	    if (seoUrl == null) {
	        $('#fromLink').val('');
	        $('#toLink').val('');
	    }
	    else {
	        $('#fromLink').val(seoUrl.fromLink);
	        $('#toLink').val(seoUrl.toLink);
	    }
	}

	function onRemove() {
	    var ids = new Array();
	    $('#urls input:checked').each(function() {
	        ids.push(this.value);
	    });
	    if (ids.length == 0) {
	    	Heiduc.info(messages('nothing_selected'));
	        return;
	    }
	    if (confirm(messages('are_you_sure'))) {
	    	Heiduc.jsonrpc.seoUrlService.remove(function(r) {
	    		Heiduc.showServiceMessages(r);
	            loadUrls();
	        }, Heiduc.javaList(ids));
	    }
	}

	function onSaveAndAdd() {
	    onSave(false, true);
	}

	function validateSeoUrl(vo) {
	    var errors = new Array();
	    if (vo.fromLink == '') {
	        errors.push(messages('seo_urls.from_link_empty'));
	    }
	    if (vo.toLink == '') {
	        errors.push(messages('seo_urls.to_link_empty'));
	    }
	    return errors;
	}

	function onSave(closeFlag, addFlag) {
	    var vo = {
	        id : seoUrl != null ? String(seoUrl.id) : '',
	        fromLink : $('#fromLink').val(),
	        toLink : $('#toLink').val()
	    };
	    var errors = validateSeoUrl(vo);
	    if (errors.length == 0) {
	    	Heiduc.jsonrpc.seoUrlService.save(function(r) {
	            if (r.result == 'success') {
	                if (closeFlag) {
	                    $("#url-dialog").modal("hide");
	                }
	                loadUrls();
	                if (addFlag) {
	                	onAdd();
	                }
	            } else {
	                errorUrlMessages(r.messages.list);
	            }
	        }, Heiduc.javaMap(vo));
	    } else {
	        errorUrlMessages(errors);
	    }
	}

	function onCancel() {
	    $("#url-dialog").modal("hide");
	}

	function clearMessages() {
	    $('#url-messages').html('');
	}

	function errorUrlMessages(messages) {
		Heiduc.errorMessages("#url-messages", messages);
	}

	
	return Backbone.View.extend({
		
		css: '/static/css/form.css',
		
		el: $('#content'),
		
		render: function() {
			Heiduc.addCSSFile(this.css);
			this.el.html(_.template(tmpl, {messages:messages}));
			postRender();
		},
		
		remove: function() {
			//$("#url-dialog").dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFile(this.css);
		}
		
	});
	
});
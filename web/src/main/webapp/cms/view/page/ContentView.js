// file ContentView.js

define(['text!template/page/content.html',
        'view/page/context', 'view/page/version', 'view/page/breadcrumbs',
        'jquery.form',
        'cm', 'cm-css', 'cm-js', 'cm-xml', 'cm-html'], 
function(contentHtml, ctx, version, breadcrumbs) {
	
	console.log('Loading ContentView.js');
	
	var contents = null;
	var titles = null;
	var languages = null;
	var contentEditor = null;
	var etalonContent = '';
	var autosaveTimer = '';
	var contentEditors = null;
	var browseId = '';
	var editTextarea = false;

	function postRender() {
		ctx.loadData = loadData;
		ctx.editMode = ctx.pageId != '';
	    //$("#restore-dialog").dialog({ width: 400, autoOpen: false });
	    Heiduc.initJSONRpc(loadData);
	    // hover states on the link buttons
	    $('a.button').hover(
	     	function() { $(this).addClass('ui-state-hover'); },
	       	function() { $(this).removeClass('ui-state-hover'); }
	    ); 
	    version.initVersionDialog();
	    $('#autosave').change(onAutosave);
	    $('#language').change(onLanguageChange);
	    $('#saveContinueContentButton').click(function(){onPageUpdate(true)});
	    $('#pageForm').submit(function() {onPageUpdate(false); return false;});
	    $('#contentPreviewButton').click(onPagePreview);
	    $('#approveButton').click(onPageApprove);
	    $('#restoreButton').click(onRestore);
	    $('#contentCancelButton').click(onPageCancel);
	    $('ul.nav-tabs li:nth-child(2)').addClass('active');
	    
	    $('#restoreForm').submit(function() {onRestoreSave(); return false;});
	    //$('#restoreCancelButton').click(onRestoreCancel);
	    $('#resetCacheButton').click(onResetCache);
	    
	    //$("#file-upload").dialog({ width: 400, autoOpen: false });
	    $('#upload').ajaxForm({
	    	beforeSubmit: beforeUpload,
	    	success: afterUpload
	    });
	    $('#fileUploadCancelButton').click(onFileUploadCancel);
	}

	function loadData() {
		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
			ctx.pageRequest = r;
			ctx.page = ctx.pageRequest.page;
			editTextarea = !ctx.pageRequest.config.enableCkeditor 
					|| ctx.pageRequest.page.wikiProcessing || !ctx.page.enableCkeditor;
			var wikiHelp = ctx.pageRequest.page.wikiProcessing ? 
					' <a href="http://en.wikipedia.org/wiki/Help:Wiki_markup" '
					+ 'target="blank">Wiki syntax</a>'
					: '';
			if (editTextarea) {
				$('#ckedit').text(messages('page.edit_ckeditor')).click(onEditCKEditor);
			}
			else {
				$('#ckedit').text(messages('page.edit_textarea')).click(onEditTextarea);
			}
			$('#editorButtons').append(wikiHelp);
			loadLanguages();
			loadPage();
			breadcrumbs.breadcrumbsShow();
		}, ctx.pageId, ctx.pageParentUrl);
	}

	function callLoadPage() {
		Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
			ctx.pageRequest = r;
			ctx.page = ctx.pageRequest.page;
			ctx.editMode = ctx.pageId != null;
			loadPage();
		}, ctx.pageId, ctx.pageParentUrl);
	}

	function loadPage() {
		ctx.pageId = ctx.page.id == null ? '' : String(ctx.page.id);
		ctx.pageParentUrl = ctx.page.parentUrl;
		version.loadVersions();
		loadTitles();
		initPageForm();
		loadPagePermission();
		loadContents();
	}

	function loadLanguages() {
		var r = ctx.pageRequest.languages;
		languages = {};
		var h = '';
		$.each(r.list, function(i, value) {
			languages[value.code] = value;
			h += '<option value="' + value.code + '" ' + '>' 
				+ value.title + '</option>';
		});
		$('#language').html(h);
	}

	function initPageForm() {
		var urlEnd = ctx.pageParentUrl == '/' ? '' : '/';
		if (ctx.page.parentUrl == '' || ctx.page.parentUrl == null) {
			$('#friendlyUrl').hide();
			$('#friendlyUrl').val('');
			$('#parentFriendlyUrl').html('/');
		} else {
			$('#friendlyUrl').show();
			$('#friendlyUrl').val(ctx.page.pageFriendlyURL);
			$('#parentFriendlyUrl').html(ctx.page.parentFriendlyURL + urlEnd);
		}
	    if (ctx.pageRequest.children.list.length > 0) {
	     	$('#parentFriendlyUrl').hide();
	       	$('#friendlyUrl').hide();
	       	$('#friendlyUrlSpan').html(ctx.page.friendlyURL);
	    }
		$('#pageState').html(ctx.page.stateString == 'EDIT' ? messages('edit') : 
				messages('approved'));
		$('#pageCreateDate').html(ctx.page.createDateTimeString);
		$('#pageModDate').html(ctx.page.modDateTimeString);
		$('#pageCreateUser').html(ctx.page.createUserEmail);
		$('#pageModUser').html(ctx.page.modUserEmail);
		if (ctx.page.id != null) {
			$('.pageTab').show();
			$('.childrenTab').show();
			$('.commentsTab').show();
			$('.securityTab').show();
			$('#pagePreview').show();
			$('#versions').show();
		} else {
			$('.pageTab').hide();
			$('.childrenTab').hide();
			$('.commentsTab').hide();
			$('.securityTab').hide();
			$('#pagePreview').hide();
			$('#versions').hide();
		}
		showContentEditor();
		checkDefault();
	}

	function onPageUpdate(continueFlag) {
		var pageVO = Heiduc.javaMap( {
			id : ctx.pageId,
			friendlyUrl : $('#parentFriendlyUrl').text() + $('#friendlyUrl').val(),
			titles : getTitles(),
			content : getEditorContent(),
			approve : String($('#approveOnPageSave:checked, #approveOnContentSave:checked').size() > 0),
			languageCode : ctx.currentLanguage
		});
		Heiduc.jsonrpc.pageService.savePage(function(r) {
			if (r.result == 'success') {
				Heiduc.info(messages('page.success_save'));
				if (!continueFlag) {
					location.href = '#pages';
				}
				else {
					callLoadPage();
				}
			} else {
				Heiduc.showServiceMessages(r);
			}
		}, pageVO);
	}
	    
	function startAutosave() {
		if (ctx.editMode) {
			if (autosaveTimer == '') {
				autosaveTimer = setInterval(saveContentByTimer, 
						Heiduc.AUTOSAVE_TIMEOUT * 1000);
			}
		}
	}

	function saveContentByTimer() {
		onPageUpdate(true);
	}

	function stopAutosave() {
		if (autosaveTimer != '') {
			clearInterval(autosaveTimer);
			autosaveTimer = '';
		}
	}

	function getEditorContent() {
		if (!ctx.editMode) return '';
		if (ctx.page.simple) {
			if (editTextarea) {
				contentEditor.save();
				return $('#pcontent').val();
			}
			else {
				return contentEditor.getData();
			}
		}
		if (ctx.page.structured) {
			var xml = '<?xml version="1.0" encoding="utf-8"?>\n<content>\n';
			$.each(ctx.pageRequest.structureFields.list, function(i, field) {
				if (field.type == 'TEXT' || field.type == 'DATE' 
						|| field.type == 'RESOURCE') {
					xml += '<' + field.name + '><![CDATA[' + $('#field' + field.name).val()
						+ ']]></' + field.name + '>\n';
				}
				if (field.type == 'TEXTAREA') {
					var text = '';
					if (editTextarea) {
						contentEditors[field.name].save();
						text = $('#field' + field.name).val();
					}
					else {
						text = contentEditors[field.name].getData();
					}
					xml += '<' + field.name + '><![CDATA['
						+ text.replace(']]>', ']]]')  
						+ ']]></' + field.name + '>\n';
				}
			});
			return xml + '</content>';
		}
	}

	function setEditorContent(data) {
		data = typeof data === "undefined" ? "" : data;
		if (ctx.page.simple) {
		    if (editTextarea) {
				contentEditor.setValue(data);
		    }
		    else {
		    	contentEditor.setData(data);
		    }
		}
		if (ctx.page.structured) {
			var domData = $.xmlDOM(data, function(error) {
				if (data) {
					Heiduc.error(messages('page.parsing_error') + ' ' + error);
				}
			});
			$.each(ctx.pageRequest.structureFields.list, function(i, field) {
				if (field.type == 'TEXT' || field.type == 'DATE' 
					|| field.type == 'RESOURCE') {
					$(domData).find(field.name).each(function() {
						$('#field' + field.name).val($(this).text())					
					});
				}
				if (field.type == 'TEXTAREA') {
					$(domData).find(field.name).each(function() {
						if (editTextarea) 
							contentEditors[field.name].setValue($(this).text());
						else 
							contentEditors[field.name].setData($(this).text());
					});
				}
			});
		}
	}

	function isContentChanged() {
		return contents[ctx.currentLanguage] != getEditorContent();
	}

	function onAutosave() {
		if ($("#autosave:checked").length > 0) {
			startAutosave();
		} else {
			stopAutosave();
		}
	}

	function onPagePreview() {
		var url = ctx.page.friendlyURL + '?language=' + ctx.currentLanguage 
			+ '&version=' + ctx.page.version;
		window.open(url, "preview");
	}

	function onPageCancel() {
		location.href = '#pages';
	}

	function onLanguageChange() {
		if (!isContentChanged()
				|| confirm(messages('are_you_sure_changes_lost'))) {
			ctx.currentLanguage = $('#language').val();
			if (contents[ctx.currentLanguage] == undefined) {
				contents[ctx.currentLanguage] = '';
			}
			setEditorContent(contents[ctx.currentLanguage]);
			$('#titleLocal').val(getTitle());
		} else {
			$('#language').val(ctx.currentLanguage);
		}
	}

	function loadContents() {
		if (ctx.pageRequest.contents != null) {
			var r = ctx.pageRequest.contents;
			contents = {};
			$.each(r.list, function(i, value) {
				contents[value.languageCode] = value.content;
			});
			if (!ctx.currentLanguage) {
				if (languages[Heiduc.ENGLISH_CODE] != undefined) {
					ctx.currentLanguage = Heiduc.ENGLISH_CODE;
				}
				else {
					ctx.currentLanguage = r.list[0].languageCode;
				}
			}
			$('#language').val(ctx.currentLanguage);
			setEditorContent(contents[ctx.currentLanguage]);
			$('#titleLocal').val(Heiduc.unescapeHtml(getTitle()));
		} else {
			setEditorContent('');
		}
	}

	function onPageApprove() {
		Heiduc.jsonrpc.pageService.approve(function(r) {
			Heiduc.showServiceMessages(r);
			loadData();
		}, ctx.pageId);
	}

	function loadPagePermission() {
	    var r = ctx.pageRequest.pagePermission;
	   	if (r.publishGranted) {
	   		$('#approveButton').show();
	   		$('#approveOnPageSaveDiv').show();
	   		$('#approveOnContentSaveDiv').show();
	   	}
	   	else {
	   		$('#approveButton').hide();
	   		$('#approveOnPageSaveDiv').hide();
	   		$('#approveOnContentSaveDiv').hide();
	   	}
	   	if (r.changeGranted) {
	   		$('#pageSaveButton').show();
	   		$('#saveContinueContentButton').show();
	   		$('#saveContentButton').show();
	   	}
	   	else {
	   		$('#pageSaveButton').hide();
	   		$('#saveContinueContentButton').hide();
	   		$('#saveContentButton').hide();
	   	}
	   	if (r.admin && ctx.editMode) {
	   		$('.securityTab').show();
	   	}
	   	else {
	   		$('.securityTab').hide();
	   	}
	   	checkDefault();
	}

	function showContentEditor() {
		$.each(CKEDITOR.instances, function(i,value) {
			CKEDITOR.remove(value);
		});
		if (ctx.page.simple) {
			$('#page-content').html('<textarea id="pcontent" rows="20" cols="80"></textarea>');
			if (editTextarea) {
				contentEditor = CodeMirror.fromTextArea(document.getElementById('pcontent'), {
					lineNumbers: true,
					lineWrapping: true,
					theme: 'eclipse',
					mode: 'htmlmixed'
				});
				contentEditor.focus();
				$(contentEditor.getScrollerElement())
					.css('height', (0.6 * $(window).height()) + 'px')
					.css('border', '1px solid silver');
			}
			else {
		    	contentEditor = CKEDITOR.replace('pcontent', {
		    		height: 350, width: 'auto',
		    		filebrowserUploadUrl : '/cms/upload',
		    		filebrowserBrowseUrl : 'fileBrowser.html',
		    		toolbar : 'Heiduc'
		    	});
		    }
		}
		if (ctx.page.structured) {
			var h = '';
			$.each(ctx.pageRequest.structureFields.list, function(i, field) {
				h += '<div><div class="label">' + field.title + ':</div>';
				if (field.type == 'TEXT') {
					h += '<input id="field' + field.name + '" size="30"/>';
				}
				if (field.type == 'TEXTAREA') {
					h += '<textarea cols="80" rows="20" id="field' + field.name + '"></textarea>';
				}
				if (field.type == 'DATE') {
					h += '<input id="field' + field.name + '" class="datepicker" size="8" />';
				}
				if (field.type == 'RESOURCE') {
					h += '<input id="field' + field.name + '" size="60"/> '
						+ '<a class="browse" data-name="field' + field.name + '">' 
						+ messages('browse') + '</a>'
						+ ' <a class="upload" data-name="field' + field.name + '">' 
						+ messages('upload') + '</a>';
				}
				h += '</div>';
			});
			$('#page-content').html(h);
			$('#page-content .browse').click(function() {
				browseResources($(this).attr('data-name'));
			});
			$('#page-content .upload').click(function() {
				uploadResources($(this).attr('data-name'));
			});
			//$('#page-content').css('float','left');
		    $(".datepicker").datepicker({dateFormat:'dd.mm.yy'});
			contentEditors = {};
 		    $.each(ctx.pageRequest.structureFields.list, function(i, field) {
				if (field.type == 'TEXTAREA') {
					if (!contentEditors[field.name]) {
						if (editTextarea) {
							var el = document.getElementById('field' + field.name);
							contentEditors[field.name] = CodeMirror.fromTextArea(el, {
								lineNumbers: true,
								lineWrapping: true,
								theme: 'eclipse',
								mode: 'htmlmixed'
							});
							$(contentEditors[field.name].getScrollerElement())
								//.css('width', ($('#pageForm .buttons').width() + 20) + 'px')
								.css('border', '1px solid silver');
						}
						else {
							contentEditors[field.name] = CKEDITOR.replace('field' + field.name, {
								height: 150, width: 'auto',
								filebrowserUploadUrl : '/cms/upload',
								filebrowserBrowseUrl : 'fileBrowser.html',
								toolbar : 'Heiduc'
							});
						}
					}
				}
			});
		}
	}

	function browseResources(id) {
		browseId = id;
		$.cookie('fileBrowserPath', '/page' + ctx.page.friendlyURL, 
				{path:'/', expires: 10});
		window.open('fileBrowser.html?mode=page');
	}

	function setResource(path) {
		$('#' + browseId).val(path);
	}

	function loadTitles() {
		titles = ctx.page.titles.map;
	}

	function getTitle() {
		if (titles[ctx.currentLanguage] == undefined) {
			return '';
		}
		return titles[ctx.currentLanguage];
	}

	function getTitles() {
		if (!ctx.editMode) {
			return '{' + Heiduc.ENGLISH_CODE + ':"' + Heiduc.escapeHtml($('#title').val()) +'"}';
		}
		titles[ctx.currentLanguage] = $('#titleLocal').val();
		var result = '{';
		var count = 0;
		$.each(titles, function(lang, value) {
			var coma = count++ == 0 ? '' : ',';
			result += coma + lang + ':"' + Heiduc.escapeHtml(value) + '"';
		});
		return result + '}';
	}

	function onRestore() {
		//$("#restore-dialog").dialog('open');
		$('#restore-dialog').modal({show:true});
	}

	//function onRestoreCancel() {
	//	$("#restore-dialog").dialog('close');
	//}

	function onRestoreSave() {
		var pageType = $('input[name=page]:checked').val();
		Heiduc.jsonrpc.pageService.restore(function(r) {
			Heiduc.showServiceMessages(r);
			if (r.result == 'success') {
				$("#restore-dialog").modal('hide');
				loadData();
			}
		}, ctx.pageId, pageType, ctx.currentLanguage);
	}

	function onEditTextarea() {
		editTextarea = true;
		$('#ckedit').text(messages('page.edit_ckeditor')).click(onEditCKEditor);
		showContentEditor();
		setEditorContent(contents[ctx.currentLanguage]);
	}

	function onEditCKEditor() {
		editTextarea = false;
		$('#ckedit').text(messages('page.edit_textarea')).click(onEditTextarea);
		showContentEditor();
		setEditorContent(contents[ctx.currentLanguage]);
	}

	function onResetCache() {
		if (ctx.editMode) {
			Heiduc.jsonrpc.pageService.resetCache(function(r) {
				Heiduc.showServiceMessages(r);
			}, ctx.page.friendlyURL);
		}
	}

	function checkDefault() {
		if (ctx.page.friendlyURL.endsWith('/_default')) {
			ctx.isDefault = true;
			$('.securityTab, .commentsTab, .childrenTab, #approveOnContentSaveDiv'
				+ ', #contentPreviewButton, #versions, #resetCacheButton, #restoreButton'
				+ ', #approveButton, #friendlyUrlDiv').hide();
		}
	}

	function uploadResources(field) {
		browseId = field;
		$('#file-upload input[name=folderId]').val(ctx.pageRequest.folderId);
	    //$("#file-upload").dialog("open");
	    $("#file-upload").modal({show:true});
	}
	function onFileUploadCancel() {
		//$("#file-upload").dialog("close");
	}

	function afterUpload(data) {
	    var s = data.split('::');
	    var result = s[1];
	    var msg = s[2]; 
	    if (result == 'success') {
	    	Heiduc.info(messages('folder.file_success_upload'));
	    }
	    else {
	    	Heiduc.error(messages('folder.error_during_upload') + ' ' + msg);
	    }
	    //$("#file-upload").dialog("close");
	    $("#file-upload").modal({show:false});
	}

	function beforeUpload(arr, form, options) {
		var fname = Heiduc.getFileName($('#file-upload input[name=uploadFile]').val());
		var path = '/file/page' + ctx.page.friendlyURL + '/' + fname;
		$('#' + browseId).val(path);
	}
	
	
	return Backbone.View.extend({
		
		el: $('#tab-1'),

		tmpl: _.template(contentHtml),
		
		render: function() {
			this.el = $('#tab-1');
			this.el.html(this.tmpl({messages:messages}));
			postRender();
		},
		
		remove: function() {
			this.el.html('');
		    //$("#restore-dialog, #file-upload").dialog('destroy').remove();
		},
		
		setResource: function(path) {
			setResource(path);
		}

		
	});
	
});
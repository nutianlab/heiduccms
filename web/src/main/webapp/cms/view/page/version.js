
define(['view/page/context'], function(ctx) {

var versions = [];

function initVersionDialog() {
    $('#addVersionLink').click(onAddVersion);
    $('#versionCancelButton').click(onVersionTitleCancel);
    $('#versionForm').submit(function() {onVersionTitleSave(); return false;});
}

function callLoadVersions() {
	Heiduc.jsonrpc.pageService.getPageRequest(function(r) {
		ctx.pageRequest = r;
		ctx.page = ctx.pageRequest.page;
		loadVersions();
	}, ctx.pageId, ctx.pageParentUrl);
}

function loadVersions() {
	var r = ctx.pageRequest.versions; 
    versions = [];
    ctx.pages = {};
    $.each(r.list, function (i, value) {
    	ctx.pages[String(value.version)] = value;
        versions.push(String(value.version));
    });
    versions.sort();
    var h = '';
    $.each(versions, function (i, version) {
        var vPage = ctx.pages[version];
        h += '<div>';
        if (ctx.pageId != vPage.id) {
            h += '<a class="select btn btn-default btn-sm"\
               title="' + vPage.versionTitle + '"\
               data-version="' + version + '">Version ' + version +'</a>';
        }
        else {
            h += '<a class="select btn btn-default btn-sm" title="' + vPage.versionTitle 
               + '" data-version="' + version + '" \
               ><span class="ui-icon ui-icon-triangle-1-e"></span> \
               Version ' + version + '</a>';
        }
        if (versions.length > 1) {
        	h += '<img class="delete button" src="/static/images/delete-16.png" data-version="' 
        		+ version + '"/></div>';
        }
    });
    $('#versions .vertical-buttons-panel').html(h);
    $('#versions a.select').click(function() {
   		onVersionSelect($(this).attr('data-version'));
    });
    $('#versions img.delete').click(function() {
   		onVersionDelete($(this).attr('data-version'));
    });

}

function onVersionDelete(version) {
	if (confirm(messages('are_you_sure'))) {
		var delPage = ctx.pages[version];
		Heiduc.jsonrpc.pageService.deletePageVersion(function(r) {
			if (version == String(ctx.page.version)) {
				if (versions.length == 1) {
					location.href = '#pages';
				} else {
					var previousVersion = versions[0];
					if (versions.indexOf(version) == 0) {
						previousVersion = versions[1];
					} else {
						previousVersion = versions[versions
								.indexOf(version) - 1];
					}
					ctx.pageId = ctx.pages[previousVersion].id;
					ctx.loadData();
				}
			} else {
				callLoadVersions();
			}
		}, delPage.id);
	}
}

function onAddVersion() {
	//$('#version-dialog').dialog('open');
	$('#version-dialog').modal({show:true});
	$('#version-title').val('');
}

function onVersionTitleSave() {
	Heiduc.jsonrpc.pageService.addVersion(function(r) {
		if (r.result == 'success') {
			ctx.pageId = r.message;
			ctx.loadData();
			Heiduc.info(messages('page.version_success_add'));
		}
		else {
			Heiduc.showServiceMessages(r);
		}			
		//$('#version-dialog').dialog('close');
		$('#version-dialog').modal({show:false});
	}, ctx.page.friendlyURL, $('#version-title').val());
}

function onVersionTitleCancel() {
	//$('#version-dialog').dialog('close');
}

function onVersionSelect(version) {
	var selPage = ctx.pages[version];
	ctx.pageId = selPage.id;
	ctx.loadData();
}

function showAuditInfo() {
	$('#pageState').html(ctx.page.stateString == 'EDIT' ? 
			messages('edit') : messages('approved'));
	$('#pageCreateDate').html(ctx.page.createDateTimeString);
	$('#pageModDate').html(ctx.page.modDateTimeString);
	$('#pageCreateUser').html(ctx.page.createUserEmail);
	$('#pageModUser').html(ctx.page.modUserEmail);
}

	return {
		initVersionDialog : initVersionDialog,
		loadVersions : loadVersions,
		showAuditInfo: showAuditInfo
	};

});

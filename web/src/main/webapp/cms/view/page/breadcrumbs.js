
define(['view/page/context'], function(ctx) {

	function breadcrumbsShow() {
		var path = ctx.pageParentUrl;
		path = ctx.pageRequest.page.friendlyURL;
		var h = '';
		var pages = path.substr(1).split('/');
		if (pages.length > 0) {
			var currentPath = ''; 
			$.each(pages, function(i,value) {
				currentPath += '/' + value;
				if (pages.length - 1 == i && ctx.editMode) {
					h += ' ' + pages[pages.length - 1];
				}
				else {
					h += ' <a data-url="' + currentPath + '">' + value + '</a> /';
				}
			});
		}
		$('#crumbs').html(h);
		$('#crumbs a').click(function() {
			var url = $(this).attr('data-url');
			if (url) {
				breadcrumbsEdit(url);
			}
		});
		$('#rootPage').click(function () { breadcrumbsEdit('/'); });
	}
		
	function breadcrumbsEdit(path) {
		Heiduc.jsonrpc.pageService.getPageByUrl(function(r) {
			location.href = '#page/content/' + r.id;
		}, path);
	}	

	return {
		breadcrumbsShow : breadcrumbsShow
	};

});

define(['text!template/folders.html', 'jquery.form', 'jquery.treeview'], 
function(html) {
	
	console.log('Loading FoldersView.js');
	
	function postRender() {
		Heiduc.initJSONRpc(loadTree);
	    //$("#tabs").tabs();
		$.cookie("folderReturnPath", null, {path:'/', expires: 10});
	}

	function loadTree() {
		Heiduc.jsonrpc.folderService.getTree(function(r) {
	        $('#folders-tree').html(renderFolder(r));
	        $("#folders-tree").treeview({
				animated: "fast",
				collapsed: true,
				unique: true,
				persist: "cookie",
				cookieId: "folderTree"
			});
	    });
	}

	function renderFolder(vo) {
	    var html = '<li><a href="#folder/' + vo.entity.id + '">' 
	        + vo.entity.title + '</a> <a title="' + messages('add_child') 
	        + '" href="#addFolder/' + vo.entity.id + '">+</a>';
	    
	    if (vo.children.list.length > 0) {
	        html += '<ul>';
	        $.each(vo.children.list, function(n, value) {
	            html += renderFolder(value);
	        });
	        html += '</ul>';
	    }
	    return html + '</li>';
	}

	// Picasa
	
	var config = null;
	var albums = null;
	var photos = null;
	var album = null;

	function postRender2() {
		//$('#album-dialog').dialog({ width: 400, autoOpen: false });
		//$('#upload-dialog').dialog({ width: 400, autoOpen: false });
	    $('#upload').ajaxForm(afterUpload);
		Heiduc.initJSONRpc(loadConfig);
		$('#createAlbumLink').click(onCreateAlbum);
		$('#deleteAlbumLink').click(onDeleteAlbum);
		$('#uploadPhotoLink').click(onUpload);
	    $('#albumForm').submit(function() {onAlbumSave(); return false;});
		$('#cancelAlbumButton').click(function(){ $('#album-dialog').dialog('close') });
		$('#uploadAlbumButton').click(function(){ $('#upload-dialog').dialog('close') });
		$('#uploadCancelButton').click(function(){ $('#upload-dialog').dialog('close') });
	}

	function loadConfig() {
		Heiduc.jsonrpc.configService.getConfig(function(r) {
			config = r;
			if (config.enablePicasa) {
				loadAlbums();
			}
			else {
				$('#albums').html(messages('folders.picasa_not_enabled'));
				$('#createAlbumLink').hide();
			}
		});
	}

	function loadAlbums() {
		Heiduc.jsonrpc.picasaService.selectAlbums(function(r) {
			albums = r.list;
			showAlbums();
		});
	}

	function showAlbums() {
		var h = '';
		$.each(albums, function(i,value) {
			var c = '';
			$.each(value.categories.list, function(j, category) {
				c += category + ' ';
			});
			h += '<a class="album" data-i="' + i + '">'
				+ '<img src="/static/images/Photos.png" /><p>' 
				+ value.title + ' ' + c + '</p></a>';
		});
		$('#albums').html(h);
		$('#albums a.album').click(function() {
			onAlbumSelect($(this).attr('data-i'));
		});
	}

	function onAlbumSelect(i) {
		album = albums[i];
		albumSelect();
	}

	function albumSelect() {
		$('#album-location').text(album.title);
		Heiduc.jsonrpc.picasaService.selectPhotos(function(r) {
			photos = r.list;
			showPhotos();
		}, album.id)
	}

	function showPhotos() {
		$('#albumDetails').show();
		var h = '';
		$.each(photos, function(i,value) {
			var c = '';
			$.each(value.categories.list, function(j, category) {
				c += category + ' ';
			});
			h += '<div class="photo">'
				+ '<img class="remove" src="/static/images/02_x.png" data-i="' + i + '" />'
				+ '<a data-i="' + i + '"><img src="' + value.thumbnailURL + '" />'
				+ '<p>' + value.title + '<br/>' + c + '</p></a></div>';
		});
		$('#photos').html(h);
		$('#photos img.remove').click(function() {
			onPhotoRemove($(this).attr('data-i'));
		});
		$('#photos a').click(function() {
			onPhotoSelect($(this).attr('data-i'));
		});
	}

	function onPhotoSelect(i) {
		window.open(photos[i].URL, "preview");
	}

	function onCreateAlbum() {
		$('#album-dialog').dialog('open');
	}

	function onAlbumSave() {
		var title = $('#title').val();
		if (!title) {
			Heiduc.errorMessage('#albumMessages', messages('title_is_empty'));
			return;
		}
		Heiduc.jsonrpc.picasaService.addAlbum(function(r) {
			if (r.result == 'success') {
				$('#album-dialog').dialog('close');
				loadAlbums();
			}
			else {
				Heiduc.errorMessage('#albumMessages', r.message);
			}
		}, title);
	}

	function onDeleteAlbum() {
		if (confirm(messages('folders.you_delete_album') + ' ' + album.title 
				+ '. ' + messages('are_you_sure'))) {
			Heiduc.jsonrpc.picasaService.removeAlbum(function(r) {
				if (r.result == 'success') {
					loadAlbums();
					$('#albumDetails').hide();
				}
				Heiduc.showServiceMessages(r);
			}, album.id);
		}
	}

	function onPhotoRemove(i) {
		photo = photos[i];
		if (confirm(messages('folders.you_delete_photo') + ' ' + photo.title 
				+ '. ' + messages('are_you_sure'))) {
			Heiduc.jsonrpc.picasaService.removePhoto(function(r) {
				if (r.result == 'success') {
					albumSelect();
				}
				Heiduc.showServiceMessages(r);
			}, album.id, photo.id);
		}
	}

	function onUpload() {
		$('#upload-dialog input[name=albumId]').val(album.id);
		$('#upload-dialog').dialog('open');
	}

	function afterUpload(data) {
	    var s = data.split('::');
	    var result = s[1];
	    var msg = s[2]; 
	    if (result == 'success') {
	        Heiduc.info('Success.');
	        setTimeout(albumSelect, 3000);
	    }
	    else {
	        msg = messages('error') + '. ' + msg;
	        Heiduc.error(msg);
	    }   
	    $("#upload-dialog").dialog("close");
	}

	
	return Backbone.View.extend({
		
		css: ['/static/css/jquery.treeview.css', '/static/css/picasa.css'],
		
		el: $('#content'),
		
		tmpl: _.template(html),
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(this.tmpl({messages:messages}));
			postRender();
			postRender2();
		},
		
		remove: function() {
			//$('#album-dialog').dialog('destroy').remove();
			//$('#upload-dialog').dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
		}
		
	});
	
});
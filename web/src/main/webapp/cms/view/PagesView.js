// file PagesView.js

define(['text!template/pages.html',
        'interact','slick.grid','slick.dataview','slick.rowselectionmodel',
        'slick.checkboxselectcolumn'], function(tmpl,interact) {
	
	var parentURL = null;
	var root = null;
	var page = null;
	var showTitle = $.cookie("pages.showTitle") != 'names';
	var invertOrder = $.cookie("pages.invertOrder") == 'true';
	var searchUI = null;
	var grid, treeData = [];
	
	function reset(){
		treeData = [];
		if(grid != undefined){
			grid.destroy();
		}
	}
	
	function loadData() {
		reset();
		//loadTree();
		loadTreeView();
		//
		loadUser();
		//从内容管理页面移除搜索功能
	    //searchUI = Heiduc.PageSearchComponent('#pageSearch');
	}

	function isRoot() {
		return page && page.friendlyURL == '/';
	}
	
	function handleData(data,parent){
		
		var item = data.entity;
		item.parent = parent;
		if(item.friendlyURL == '/'){
			item.expanded = true;
		}
		treeData.push(item);
		if (item.hasChildren) {
			$.each(data.children.list, function(n, value) {
				handleData(value,item);
			});
		}
	}
	
	function loadTreeView(){
		
		Heiduc.jsonrpc.pageService.getTree(function(response) {
			if(response == null){
				return;
			}
			if (invertOrder) {
				invertChildrenOrder(response);
			}
			//处理数据
			handleData(response);
			renderTreeGrid(treeData);
		});
	}
	
	function renderTreeGrid(data){
		
		
		var calcLevel = function (dataContext) {
			
			var parent = dataContext.parent,lvl = 0;
			while (parent) {
				parent = parent.parent;
				lvl++;
			}
            return lvl;
        };
		
		
		var treeViewFormatter = function  (row, cell, value, columnDef, dataContext) { 
			if (!value) {
				value = '['+messages('undefined')+']';
			}
			  value = value.replace(/&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;");
			  
			  var marginLeftPX = 20 * calcLevel(dataContext);
			  var spacer = '';
			  if (dataContext.hasChildren) {
				  if (!dataContext.expanded) {
					  return spacer + " <span class='toggle icon expand' style='margin-left:"+ marginLeftPX + "px'></span>&nbsp;" + value;
				  }else{
					  return spacer + " <span class='toggle icon collapse' style='margin-left:"+ marginLeftPX + "px'></span>&nbsp;" + value;
				  }
			  } else {
			  	  return spacer + "<span class='toggle' style='margin-left:"+ marginLeftPX + "px'></span>&nbsp;" + value;
			  }
			};
		
			var dataView;

			var columns = [
			  {id: "title", name: "Title", field: "title", width: 200, cssClass: "cell-title", formatter: treeViewFormatter},//
			  {id: "friendlyURL", name: "friendlyURL", field: "friendlyURL", minWidth: 220},
			  {id: "hasPublishedVersion", name: "hasPublishedVersion", field: "hasPublishedVersion", minWidth: 40},
			  {id: "modDate", name: "modDate", field: "modDate", minWidth: 160}
			];

			var checkboxSelector = new Slick.CheckboxSelectColumn({
			      cssClass: "slick-cell-checkboxsel",
				  width:45
			    });

				
			columns.splice(0,0,checkboxSelector.getColumnDefinition());	

			var options = {
				rowHeight:45,
				autoEdit:false,
				autoHeight:true,
				//forceFitColumns:true,
				showHeaderRow:false,
				enableColumnReorder:false,
				enableCellNavigation: true
			};

			  var treeViewFilter = function (item) {
				  if (item.parent != null) {
				    var parent = item.parent;
				    while (parent) {
				      if (!parent.expanded ) {
				        return false;
				      }
				      parent = parent.parent;
				    }
				  }
				  return true;
				};

			  // initialize the model
			  dataView = new Slick.Data.DataView();
			  dataView.beginUpdate();
			  dataView.setItems(data); 
			  dataView.setFilter(treeViewFilter);
			  dataView.endUpdate();

			  // initialize the grid
			  grid = new Slick.Grid("#treegrid", dataView, columns, options);
				grid.setSelectionModel(new Slick.RowSelectionModel({selectActiveRow:true}));
				grid.registerPlugin(checkboxSelector);
				
				
				


			  grid.onClick.subscribe(function (e, args) {
			    if ($(e.target).hasClass("toggle")) {
			    	
			      var item = dataView.getItem(args.row);
			      if (item) {
			        if (!item.expanded) {
			          item.expanded = true;
			        } else {
			        	//移除子项
			          item.expanded = false;
			        }
			        
			        

			        dataView.updateItem(item.id, item);
			      }
			      e.stopImmediatePropagation();
			    }
			    //alert($(e.target).attr("class"));
			  });

			// wire up model events to drive the grid
			  dataView.onRowCountChanged.subscribe(function (e, args) {
			    grid.updateRowCount();
			    grid.render();
			   	
			  });

			  // wire up model events to drive the grid
			  dataView.onRowsChanged.subscribe(function (e, args) {
			    grid.invalidateRows(args.rows);
			    grid.render();
			  });

		
	}
	
	function loadUser() {
		if (!Heiduc.app.user.admin) {
		    $('#structuresTab').hide();
		}
	}

	function validate(vo) {
		if (vo.title == '') {
			return messages('title_is_empty');
		}
		else {
			if (vo.title.indexOf(',') != -1) {
				return messages('pages.coma_not_allowed');
			}
		}
		if (vo.url == '' && !(page && page.friendlyURL == '/')) {
			return messages('pages.url_is_empty');
		}
		else {
			if (vo.url.indexOf('/') != -1) {
				return messages('pages.slash_not_allowed');
			}
		}
	}

	function onSave() {
		var vo = {
			id : page == null ? '' : String(page.id),
			title : $('#title').val(),
			url : $('#url').val(),
			friendlyUrl : (isRoot() ? '/' : parentURL + '/' + $('#url').val())
		};
		var error = validate(vo);
		if (!error) {
			Heiduc.jsonrpc.pageService.savePage(function(r) {
				if (r.result == 'success') {
					Heiduc.info(messages('pages.success_created'));
					//$('#page-dialog').dialog('close');
					$('#page-dialog').modal('hide');
					loadData();
				}
				else {
					showError(r.message);
				}
			}, Heiduc.javaMap(vo));
		}
		else {
			showError(error);
		}
	}

	function showError(msg) {
		Heiduc.errorMessage('#pageMessages', msg);
	}

	function findPage(id) {
		return findChildPage(root, id);
	}

	function findChildPage(page, id) {
		if (page.entity.id == id) {
			return page;
		}
		var result = null;
		if (page.children.list.length > 0) {
			$.each(page.children.list, function(i,value) {
				var res = findChildPage(value, id);
				if (res != null) {
					result = res;
				}
			});
		}
		return result;
	}

	function renderShowTitle() {
		if (showTitle) {
			$('#showTitle').html('<a href="#" onclick="Heiduc.app.pagesView.onShowTitle(false)">'
					+ messages('show_names') + '</a>');
		}
		else {
			$('#showTitle').html('<a href="#" onclick="Heiduc.app.pagesView.onShowTitle(true)">'
					+ messages('show_titles') + '</a>');
		}
	}

	function renderInvertOrder() {
		if (invertOrder) {
			$('#invertOrder').html( messages('restore_order') );
		}
		else {
			$('#invertOrder').html(messages('invert_order') );
		}
	}

	function invertChildrenOrder(vo) {
		vo.children.list.reverse();
		if (vo.hasChildren) {
			$.each(vo.children.list, function (n, value) {
				invertChildrenOrder(value);
			});
		}
	}
	
	
	
	
	
	return Backbone.View.extend({
		
		css: ['/static/css/jquery.treeview.css', '/static/css/pages.css','/static/slickgrid/slick.grid.css','/static/slickgrid/treegrid.css'],
		
		el: $('#content'),
		
		events: {
		},
		
		initialize: function (){
			window.interact = interact;
		},
		
		render: function() {
			Heiduc.addCSSFiles(this.css);
			this.el.html(_.template(tmpl, {messages: messages}));
			//$("#page-dialog").dialog({ width: 400, autoOpen: false });
			Heiduc.initJSONRpc(loadData);
			//$('#cancelDlgButton').click(function() {
			//	$('#page-dialog').dialog('close');				
			//});
		    $('#pageForm').submit(function() {onSave(); return false;});
		    $('#title').change(this.onTitleChange);
		    //renderShowTitle();
		    renderInvertOrder();
		    
		    var _this = this;
		    
		    $("#invertOrder").click(function (){
		    	_this.onInvertOrder(!invertOrder);
		    });
		    
		    $('#pageAdd').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	var friendlyURL = "/";
		    	if(rows.length == 1){
		    		friendlyURL = grid.getDataItem(rows[0]).friendlyURL;
		    	}
		    	_this.onPageAdd(friendlyURL);
		    });
		    $('#pageEdit').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	if(rows.length == 1){
		    		document.location.href = "#page/content/"+grid.getDataItem(rows[0]).id;
		    	}
		    });
		    $('#pageDelete').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	if(rows.length == 1){
		    		_this.onPageRemove(grid.getDataItem(rows[0]).friendlyURL);
		    	}
		    });
		    $('#pageProperty').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	if(rows.length == 1){
		    		document.location.href = "#page/"+grid.getDataItem(rows[0]).id;
		    	}
		    });
		    $('#pageTitle').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	if(rows.length == 1){
		    		_this.onChangeTitle(grid.getDataItem(rows[0]));
		    	}
		    });
		    
		    $('#pagePublish').click(function(){
		    	var rows = grid.getSelectedRows();
		    	if(rows.length > 1){
		    		Heiduc.error(messages('pages.only_single_selection'));
		    		return false;
		    	}
		    	if(rows.length == 1 ){
		    		var entity = grid.getDataItem(rows[0]);
		    		if(!entity.status){
		    		    _this.onPagePublish(entity.id);
		    		}
		    	}
		    });
		    
		},
		
		remove: function() {
		    //$("#page-dialog").dialog('destroy').remove();
			this.el.html('');
			Heiduc.removeCSSFiles(this.css);
			reset();
		},
		
		onPageRemove: function(url) {
			if (url == '/') return; 
			if (confirm(messages('are_you_sure'))) {
				Heiduc.jsonrpc.pageService.remove(function(r) {
					Heiduc.showServiceMessages(r);
					loadData();
				}, url);
			}
		},

		onPageAdd: function(parent) {
			$('#ui-dialog-title-page-dialog').text(messages('pages.new_page'));
			parentURL = parent == '/' ? '' : parent;
			$('#page-dialog').modal({show:true});
			//$('#page-dialog').dialog('open');
			$('#parentURL').html(parentURL + '/');
			$('#title').val('');
			$('#url').val('');
			$('#url').removeAttr('disabled');
			$('#title').focus();
			page = null;
		},

		/**/
		onTitleChange: function() {
			var url = $("#url").val();
			var title = $("#title").val();
			if (url == '' && !isRoot()) {
				$("#url").val(Heiduc.urlFromTitle(title));
			}
		},
		
		onInvertOrder: function(flag) {
			invertOrder = flag;
			renderInvertOrder();
			if (invertOrder) {
				$.cookie("pages.invertOrder", 'true', {path:'/', expires: 10});
			}
			else {
				$.cookie("pages.invertOrder", 'false', {path:'/', expires: 10});
			}
			loadData();
		},

		onPagePublish: function(id) {
			if (confirm(messages('are_you_sure'))) {
				Heiduc.jsonrpc.pageService.approve(function(r) {
					Heiduc.showServiceMessages(r);
					loadData();
				}, id);
			}
		},
		
		onShowTitle: function(flag) {
			showTitle = flag;
			renderShowTitle();
			if (showTitle) {
				$.cookie("pages.showTitle", 'titles', {path:'/', expires: 10});
			}
			else {
				$.cookie("pages.showTitle", 'names', {path:'/', expires: 10});
			}
			loadData();
		},
		
		onChangeTitle: function(entity) {
			//var pageItem = findPage(id);
			//page = pageItem.entity;
			page = entity;
			$('#ui-dialog-title-page-dialog').text(messages('pages.change_page'));
			parentURL = "";
			if(page.parent != null){
				parentURL = page.parent.friendlyURL == '/' ? '' : page.parent.friendlyURL;
				
			}
			$('#page-dialog').modal({show:true});
			$('#parentURL').html(parentURL + '/');
			$('#title').val(page.title);
			$('#url').val(page.pageFriendlyURL);
			$('#url').attr('disabled', isRoot());
			$('#title').focus();
		}


	});
	
});
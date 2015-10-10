
// file AddonsView.js
define(
function(){

	console.log('Loading AddonsView.js');
	
	var addonsId = null;
	var addons = null;
	var tmpl = '<div id="addons" class="row"></div>';
	
	function loadAddons() {
		
	}
	
	function showAddons() {
		Heiduc.jsonrpc.pluginService.getByName(function(r) {
			addons = r;
			var configURL = '/file/plugins/' + addons.name + '/' + addons.configURL;
			//$.get(configURL,function(data){
			//	$('#addons').html(data);
			//},'html');
			$('#addons').load(configURL);
		}, addonsId.split("/")[0]);
	}
	
	return Backbone.View.extend({
	
		el: $('#content'),
		
		render: function() {
			this.el.html(_.template(tmpl, {messages:messages}));
			showAddons();
		},
		
		remove: function() {
			this.el.html('');
		},
		
		setAddonsId: function(id) {
			addonsId = id;
		}
	
	});
	
}
);
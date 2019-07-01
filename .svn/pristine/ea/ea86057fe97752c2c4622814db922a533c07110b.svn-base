var tabobjOver;
Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $tab_over_id = "$main_over_tab";
	var $over_grid = Ext.create('Wdbl.view.FileOverGrid',{ api:{ list:'over_file/listLoad.do' } });
	$over_grid.getStore().loadPage(1);
	var $over_tab = Ext.create('Wdbl.view.FileOverTab',{
		renderTo : $tab_over_id,
		api:{
			load:'over_file/loadBean.do',
			edit:'reg_file/editSave.do',
			del:'over_file/delete.do',
			toSend:'over_file/toSend.do' 
		},
		innerGrid:$over_grid
	});
	tabobjOver = $over_tab;
	$over_tab.setHeight(document.getElementById($tab_over_id).offsetHeight);
	
});
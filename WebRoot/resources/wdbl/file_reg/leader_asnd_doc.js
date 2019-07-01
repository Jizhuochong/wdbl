Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var $grid_id = "$lad-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		modelType:'basic',
		renderTo : $grid_id,
		api:{
			list:'file_reg/leader_asnd_doc/listLoad.do',
			add:'file_reg/leader_asnd_doc/addSave.do',
			load:'file_reg/leader_asnd_doc/loadBean.do',
			edit:'file_reg/leader_asnd_doc/editSave.do',
			del:'file_reg/leader_asnd_doc/delete.do'
		}
	});
	
	$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	$grid.getStore().loadPage(1);
});
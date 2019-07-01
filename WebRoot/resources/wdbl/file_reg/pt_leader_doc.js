Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var $grid_id = "$pld-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		renderTo : $grid_id,
		api:{
			list:'file_reg/pt_leader_doc/listLoad.do',
			add:'file_reg/pt_leader_doc/addSave.do',
			load:'file_reg/pt_leader_doc/loadBean.do',
			edit:'file_reg/pt_leader_doc/editSave.do',
			del:'file_reg/pt_leader_doc/delete.do'
		}
	});
	
	$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	$grid.getStore().loadPage(1);
});

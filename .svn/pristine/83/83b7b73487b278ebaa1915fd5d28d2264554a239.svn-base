Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var $grid_id = "$md-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		renderTo : $grid_id,
		api:{
			list:'file_reg/mps_doc/listLoad.do',
			add:'file_reg/mps_doc/addSave.do',
			load:'file_reg/mps_doc/loadBean.do',
			edit:'file_reg/mps_doc/editSave.do',
			del:'file_reg/mps_doc/delete.do'
		}
	});
	
	$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	$grid.getStore().loadPage(1);
});

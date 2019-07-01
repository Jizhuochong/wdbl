Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var $grid_id = "$bid-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		renderTo : $grid_id,
		api:{
			list:'file_reg/bur_inside_doc/listLoad.do',
			add:'file_reg/bur_inside_doc/addSave.do',
			load:'file_reg/bur_inside_doc/loadBean.do',
			edit:'file_reg/bur_inside_doc/editSave.do',
			del:'file_reg/bur_inside_doc/delete.do'
		}
	});
	
	$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	$grid.getStore().loadPage(1);
});

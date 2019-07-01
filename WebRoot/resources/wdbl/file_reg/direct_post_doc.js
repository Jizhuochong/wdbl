Ext.onReady(function() {
	Ext.QuickTips.init();
	
	var $grid_id = "$dpd-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		modelType:'basic',
		renderTo : $grid_id,
		api:{
			list:'file_reg/direct_post_doc/listLoad.do',
			add:'file_reg/direct_post_doc/addSave.do',
			load:'file_reg/direct_post_doc/loadBean.do',
			edit:'file_reg/direct_post_doc/editSave.do',
			del:'file_reg/direct_post_doc/delete.do'
		}
	});
	
	$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	$grid.getStore().loadPage(1);
});

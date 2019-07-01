Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $grid_id = "$cgd-grid";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		//renderTo : $grid_id,
		api:{
			list:'file_reg/city_gov_doc/listLoad.do',
			add:'file_reg/city_gov_doc/addSave.do',
			load:'file_reg/city_gov_doc/loadBean.do',
			edit:'file_reg/city_gov_doc/editSave.do',
			del:'file_reg/city_gov_doc/delete.do' 
		}
	});
	$grid.getStore().loadPage(1);
	var $tab = Ext.create('Wdbl.view.FileRegTab',{
		renderTo : $grid_id,
		innerGrid:$grid,
		api:{
			list:'file_reg/city_gov_doc/listLoad.do',
			add:'file_reg/city_gov_doc/addSave.do',
			load:'file_reg/city_gov_doc/loadBean.do',
			edit:'file_reg/city_gov_doc/editSave.do',
			del:'file_reg/city_gov_doc/delete.do' 
		}
	});
	$tab.setHeight(document.getElementById($grid_id).offsetHeight);
	//$grid.getStore().loadPage(1);
	//$grid.setHeight(document.getElementById($grid_id).offsetHeight);
	
});
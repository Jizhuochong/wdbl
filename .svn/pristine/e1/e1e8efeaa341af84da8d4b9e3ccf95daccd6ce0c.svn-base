var tabobjTra;
Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $tabTra_id = "$main_tab_tra";
	var $gridTra = Ext.create('Wdbl.view.FileTraGrid',{
		api:{
			list:'tra_file/listLoad.do'
		}
	});
	$gridTra.getStore().loadPage(1);
	var $tabTra = Ext.create('Wdbl.view.FileTraTab',{
		renderTo : $tabTra_id,
		api:{
			add:'tra_file/addSave.do',
			load:'tra_file/loadBean.do',
			edit:'tra_file/editSave.do',
			del:'tra_file/delete.do' 
		},
		innerGrid:$gridTra
	});
	tabobjTra = $tabTra;
	$tabTra.setHeight(document.getElementById($tabTra_id).offsetHeight);
});
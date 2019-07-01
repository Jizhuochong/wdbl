var tabobj;
Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $tab_id = "$main_tab";
	var $grid = Ext.create('Wdbl.view.FileRegGrid',{
		api:{
			list:'reg_file/listLoad.do'
		}
	});
	$grid.getStore().loadPage(1);
	var $tab = Ext.create('Wdbl.view.FileRegTab',{
		renderTo : $tab_id,
		cls:"ui-tab-bar", 
		bodyCls:"ui-tab-body", 
		api:{
			add:'reg_file/addSave.do',
			load:'reg_file/loadBean.do',
			edit:'reg_file/editSave.do',
			del:'reg_file/delete.do',
			toSend:'reg_file/toSend.do' 
		},
		innerGrid:$grid
	});
	tabobj = $tab;
	$tab.setHeight(document.getElementById($tab_id).offsetHeight);
	
});
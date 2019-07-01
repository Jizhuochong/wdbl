var tabobjAny;
Ext.onReady(function() {
  Ext.QuickTips.init();
  var $tab_any_id = "$main_any_tab";
  var $any_grid = Ext.create('Wdbl.view.FileAnyGrid',{ api:{ list:'any_file/listLoad.do' } });
  $any_grid.getStore().loadPage(1);
  var $any_tab = Ext.create('Wdbl.view.FileAnyTab',{
    renderTo : $tab_any_id,
    api:{
      load:'any_file/loadBean.do',
      edit:'any_file/editSave.do',
      del:'any_file/delete.do',
      toSend:'any_file/toSend.do' 
    },
    innerGrid:$any_grid
  });
  tabobjAny = $any_tab;
  $any_tab.setHeight(document.getElementById($tab_any_id).offsetHeight);
  
});
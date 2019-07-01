function u_u(){

	var u_utree = Ext.define('KitchenSink.view.tree.CheckTree', {
	    extend: 'Ext.tree.Panel',
	    requires: [
	        'Ext.data.TreeStore'
	    ],
	    xtype: 'check-tree',
	    rootVisible: false,
	    useArrows: true,
	    frame: true,
	    title: 'Check Tree',
	    width: 250,
	    height: 300,
	    initComponent: function(){
	        Ext.apply(this, {
	            store: new Ext.data.TreeStore({
	                proxy: {
	                    type: 'ajax',
	                    url: 'resources/data/tree/check-nodes.json'
	                },
	                sorters: [{
	                    property: 'leaf',
	                    direction: 'ASC'
	                }, {
	                    property: 'text',
	                    direction: 'ASC'
	                }]
	            }),
	            tbar: [{
	                text: 'Get checked nodes',
	                scope: this,
	                handler: this.onCheckedNodesClick
	            }]
	        });
	        this.callParent();
	    },
	    onCheckedNodesClick: function(){
	        var records = this.getView().getChecked(),
	            names = [];
	        Ext.Array.each(records, function(rec){
	            names.push(rec.get('text'));
	        });
	        Ext.MessageBox.show({
	            title: 'Selected Nodes',
	            msg: names.join('<br />'),
	            icon: Ext.MessageBox.INFO
	        });
	    }
	});

	 var win = Ext.create('Ext.window.Window',{
        title: '增加',
        layout: 'fit',  
        modal: true, 
        plain:true,
        height: 230,  
        width: 400,  
        border: 0,   
        frame: false, 
        items: u_utree
    }).show();


}


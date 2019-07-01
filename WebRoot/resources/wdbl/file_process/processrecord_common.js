//注办公共资源
Ext.define('Wdbl.fileprocess.processrecord_common',{ 
	//列表store
	grid_store: function(url){  
        return Ext.create("Ext.data.JsonStore", {
            	fields: [
            	 	'id','name','duty','agent','handlecontent','approvalResult','handlestatus','sys_date'
                ],
                pageSize: 20,
                remoteSort : false,
                proxy: {
                    type: "ajax",
                    url: url,
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
    }
    
    
    
});
//列表公共资源
Ext.define('Wdbl.fileprocess.grid_common',{ 
	//文件列表store
	grid_store: function(url){  
        return Ext.create("Ext.data.JsonStore", {
            	fields: [
            	 	'id','title','formUnit','receiveTime','docNumber','docType','handlestatus'
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
    },
     //grid-分页信息
    toolbar: function(obj){ 
		return Ext.create('Ext.PagingToolbar', {
                    store: obj,
					beforePageText:"第 ",  
					afterPageText:"页，共 {0} 页",  
					firstText:"首页",  
					prevText:"上一页",  
					lastText:"末页",  
					nextText:"下一页",  
					refreshText:"刷新",  
					emptyMsg:"没有要显示的数据",  
					displayInfo: true,  
					displayMsg:"<span style='font-size:13px;'>第{0}-{1}条,共{2}条</span>",  
                    emptyMsg: "没有数据"
                });
    },
    //grid - 选择
    selModel: function(){  
		return Ext.create('Ext.selection.CheckboxModel');
    },
    //按钮
    bu: function(t,f){ 
		return Ext.create('Ext.Button', {
				pressed : true,
				text: t,
				handler : f
			});
    },
    //查询条件
    s_field: function(l){  
		return Ext.create('Ext.form.TextField', {
		    	fieldLabel: l,
	    		labelAlign: 'right',
         		labelWidth: 60,
				emptyText:'输入要查询的内容',
			 	width:200
			});
    },
    bu_search: function(obj){  //查询条件-按钮
		return Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				handler : function () {
	            	obj.loadPage(1);
	        	}
			});
    }
   
   
    
    
    
});
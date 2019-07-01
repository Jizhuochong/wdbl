Ext.define('Wdbl.form.op.updateTypeForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	
	recordID:null,
	
	store:null,
	
	parent:null,
	
	type:null,
	
	gridpanel:null,
	
    showWin : function(){
    	var me = this;
		me.form_submit.getForm().load({
	    	url: 'filehandle/loadBean.do',
		    params: {id: me.recordID},
		    success : function(form, action) {
				me.show();
			},
		    failure: function(form, action) {
		        Ext.Msg.alert("加载失败", "加载数据失败！");
		    }
		});
		
    },
    initComponent: function () {
    	var me = this;
	 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
	 	
		me.form_submit = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 5
		    },
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [filed_id,{
				xtype : "combo",
				fieldLabel : "文件类型",
				name : 'docType',
				store : getComboDataStore('wjlx'),
				displayField : 'node',
				valueField : 'code',
				queryMode: 'local',
		    typeAhead: true,
				emptyText:'请选择'
			}],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					me.form_submit.getForm().submit({
						waitTitle:"请稍候",
                   		waitMsg:"正在提交表单数据，请稍候",  
                   		url: 'reg_file/updateDocType.do',
						method : "POST", 
				        success: function(form, action) {  
				           	Ext.Msg.alert("提示",action.result.msg);
				           	me.store.reload();
				           	me.close();
				       	},  
				      	failure: function(form, action) {  
				        	Ext.Msg.alert("提示","保存失败！"); 
				        }  
				   });
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                me.close();
	            }
	        }]
        });
		
		Ext.applyIf(me,{
			title: '修改文件类型',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 450,  
		    border: 0,
		    frame: false,
		    items: me.form_submit
		});
		me.callParent(arguments);
	}
   
});
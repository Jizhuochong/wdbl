Ext.define('Wdbl.form.op.FileUploadForm',{extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],

	recordID:null,
	
	fileContainer:null,
	
    initComponent: function () {
    	var me = this;
		me.upload_form_submit = Ext.create('Ext.form.FormPanel',{
			frame: true,
         	defaults:{labelAlign: 'right',labelWidth: 60,allowBlank: false,msgTarget: 'side'},
            items: [{
	        	anchor: '100%',
	            xtype: 'filefield',
	            emptyText: '请选择...',
	            fieldLabel: ' 文 件 ',
	            name: 'file',
	            buttonText: '',
	            buttonConfig: {
	            	iconCls: 'folder'
	        	}
        	}],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					me.upload_form_submit.getForm().submit({
                        url: 'reg_file/upload.do',
                        method : "POST", 
                        params:{sessionID:me.recordID},
                        waitMsg: '正在上传文件...',
                        success: function(form, action) {
                            //Ext.Msg.alert("提示",action.result.msg); 
                            if(action.result.success){
                            	me.fileContainer.addFile(action.result.data);
                            	me.upload_form_submit.getForm().reset();
                            	me.close();
                            }
                        },
                        failure: function(form, action) {  
				        	Ext.Msg.alert("提示","保存失败！"); 
				        }  
                    });
	            }
	        }]
        });
		Ext.applyIf(me,{
			title: '上传文件',
		    layout: 'fit',  
		    modal: true,
		    plain:true,
		    width: 500,  
		    border: 0,
		    closeAction:'hide',
		    frame: false,
		    items: me.upload_form_submit
		});
		me.callParent(arguments);
	}
   
});
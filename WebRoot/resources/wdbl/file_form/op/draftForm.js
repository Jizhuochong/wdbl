Ext.define('Wdbl.form.op.draftForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	
	recordID:null,
	
	store:null,
	
	gridpanel:null,
	
	form_draft:null,
	
    showWin : function(){
    	var me = this;
		me.form_draft.getForm().load({
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
	 	
	    var nullLable = Ext.create('Ext.form.Label', {text : "",width:80});
    	var orgLable = Ext.create('Ext.form.Label', {text : "部门",width:80,padding:20});
 		var nameLable = Ext.create('Ext.form.Label', {text : "人员",width:80,padding:20});
		var jobLable =  Ext.create('Ext.form.Label', {text : "职务",width:80,padding:20});
 		var actionLable = Ext.create('Ext.form.Label', {text : "动作",width:80,padding:20});
	    
	 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common');  
 		var draft_start = combobox_common.draft_start('officeLeader_start','');
	 	var officeLeader_org = combobox_common.department('officeLeader_org','');
 		var officeLeader_name = combobox_common.officeLeader('officeLeader_name','');
		var officeLeader_job = combobox_common.officeLeaderJob('officeLeader_job','');
 		var officeLeader_action = combobox_common.officeLeaderAction('officeLeader_action','');
 		var sendtype = combobox_common.sendtype('sendtype','送阅类型');
	 	
	 	var draft_op = Ext.create('Ext.form.TextArea', {name: "draft_op", fieldLabel: '拟办意见',labelAlign: 'right',allowBlank:false,maxLength:40,colspan: 5});
	 	var draft_remarkop = Ext.create('Ext.form.TextArea', {name: "draft_remarkop", fieldLabel: '备注',labelAlign: 'right',maxLength:40,colspan: 5});
	 	
		me.form_draft = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 5
		    },
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [filed_id,sendtype,draft_op,draft_remarkop],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					me.form_draft.getForm().submit({
						waitTitle:"请稍候",
                   		waitMsg:"正在提交表单数据，请稍候",  
                   		url: 'filehandle/senddraftSave.do',
						method : "POST", 
				        success: function(form, action) {  
				           	Ext.Msg.alert("提示",action.result.msg);
			           		/*win.close();
				           	store.loadPage(1);
				           	gridpanel.getSelectionModel().clearSelections();*/
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
			title: '拟办意见',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 450,  
		    border: 0,
		    closeAction:'hide',
		    frame: false,
		    items: me.form_draft
		});
		me.callParent(arguments);
	}
   
});
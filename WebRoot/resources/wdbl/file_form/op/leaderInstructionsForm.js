Ext.define('Wdbl.form.op.leaderInstructionsForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	
	recordID:null,
	
	btype:null,
	
	store:null,
	
	gridpanel:null,
	
	form_draft:null,

	parent:null,
	
    showWin : function(){
    	var me = this;
		me.form_leaderInstructions.getForm().load({
	    	url: 'processrecord/loadProcess.do',
		    params: {fid: me.recordID},
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

	 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common'); 
	 	var leadertype = combobox_common.leadertype('leaderType','批示方式');
	 	leadertype.on('change',function(combo,record) {
			if(record == "圈阅") {
	 			draft_op.setValue(record);
			} else {
				draft_op.setValue("");
			}
	 	});
	 	
 		var sendLeader = Ext.create('Ext.form.ComboBox', {
 			fieldLabel : "批示领导",
 			store : getComboLeader(),
 			displayField : 'name',
			valueField : 'name',
			multiSelect : false,
			name : "leader",
			mode: 'local',	
			queryMode: 'local',
	    	typeAhead: true,
			labelAlign: 'right',
			colspan: 5,
			editable : true,
			allowBlank:false
 		});
	 	var draft_op = Ext.create('Ext.form.TextArea', {
	 		name: "leaderOpinion", 
	 		fieldLabel: '批示内容',
	 		labelAlign: 'right',
	 		maxLength:1000,
	 		colspan: 5});
	 	var draft_remarkop = Ext.create('Ext.form.TextArea', {name: "leaderRemarop", fieldLabel: '备注',labelAlign: 'right',maxLength:1000,colspan: 5});
	 	
		me.form_leaderInstructions = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 5
		    },
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [filed_id,
                    sendLeader,draft_op
            ],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					me.form_leaderInstructions.getForm().submit({
						waitTitle:"请稍候",
                   		waitMsg:"正在提交表单数据，请稍候",  
                   		url: 'fileLeader/leaderSave.do',
						method : "POST",
				        success: function(form, action) {
//				           	Ext.Msg.alert("提示",action.result.msg);
				           	try {
			            		var processrecordPanel = Ext.getCmp(me.btype+me.recordID);
			            		processrecordPanel.helpStore.reload();
				            } catch (err) {
				                console.error("error info: " + err.message);
				            }
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
			title: '录入领导批示意见',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 450,  
		    border: 0,
		    closeAction:'hide',
		    frame: false,
		    items: me.form_leaderInstructions
		});
		me.callParent(arguments);
	}
   
});
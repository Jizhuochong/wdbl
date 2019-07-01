Ext.define('Wdbl.form.op.transferForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	
	recordID:null,
	
	store:null,
	
	gridpanel:null,
	
	form_draft:null,
	
    showWin : function(){
    	var me = this;
		me.form_transfer.getForm().load({
	    	url: 'fileTransfer/loadBean.do',
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

	 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common'); 
	 	var transfertype = combobox_common.transfertype('handleMode','处理方式');
// 		var sendLeader = Ext.create('Ext.form.ComboBox', {
// 			fieldLabel : "文件去向",
// 			store : getComboDataStore('jndw'),
// 			displayField : 'node',
//			valueField : 'code',
//			queryMode: 'local',
//			multiSelect : true,
//			name : "fileWhereabouts",
//			mode: 'remote',
//			labelAlign: 'right',
//			colspan: 5,
//			editable : true
// 		});
 		
// 		var store = getTransferCheckStore('jndw');
 		var store = getComboLeader();//获取领导信息
 		var store2 = getUnitInfo();//获取单位信息
 		
	 	var draft_remarkop = Ext.create('Ext.form.TextArea', {name: "remarks", fieldLabel: '备注',labelAlign: 'right',maxLength:40,colspan: 5});
	 	var psfk = Ext.create('Ext.form.TextArea',{name:"psfk",fieldLabel:"批示反馈",labelAlign:'right',colspan:5,row:3});
	 	var jishouren = Ext.create('Ext.form.TextArea',{name:"jishouren",fieldLabel:'接收人',labelAlign:'right',colspan: 5,rows:1});
	 	
		me.form_transfer = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'column'
		    },
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [filed_id,transfertype,
            	{xtype:"fieldcontainer" , layout:"hbox" , padding: '10px 0px 10px 0px', items:
            		[
			            {id: 'fileWhereabouts', name: 'fileWhereabouts', xtype:"textfield" , labelAlign: 'right',fieldLabel: "文件去向", flex:1} , 
			            {xtype:"button" , text:"选择", handler : function() {
				            	var sendSetWin = Ext.create('Wdbl.form.op.transferCheckbox',{checkID:'fileWhereabouts', store: store, store2: store2});
				            	sendSetWin.show();
			            	}
			            }
        			]
        		},jishouren
        	],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					me.form_transfer.getForm().submit({
						waitTitle:"请稍候",
                   		waitMsg:"正在提交表单数据，请稍候",  
                   		url: 'fileTransfer/transferSave.do',
						method : "POST", 
				        success: function(form, action) {  
				           	Ext.Msg.alert("提示",action.result.msg);
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
			title: '发送文件',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 450,  
		    border: 0,
		    closeAction:'hide',
		    frame: false,
		    items: me.form_transfer
		});
		me.callParent(arguments);
	}
   
});
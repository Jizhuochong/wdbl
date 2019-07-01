Ext.define('Wdbl.fileprocess.file_cityGovOfficial',{ 
	//发送拟办
    f_senddraft: function (id,store,gridpanel) { 
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
			 	
			 	var draft_person = Ext.create('Ext.form.Text', {name: "draft_person", fieldLabel: '拟办人',labelAlign: 'right',allowBlank:false,maxLength:10,colspan: 5});
			 	var draft_op = Ext.create('Ext.form.TextArea', {name: "draft_op", fieldLabel: '拟办意见',labelAlign: 'right',allowBlank:false,maxLength:40,colspan: 5});
			 	var draft_remarkop = Ext.create('Ext.form.TextArea', {name: "draft_remarkop", fieldLabel: '备注意见',labelAlign: 'right',allowBlank:false,maxLength:40,colspan: 5});
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
					layout: {
				        type: 'table',
				        columns: 5
				    },
	             	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
		            items: [filed_id,
		            		nullLable,orgLable,nameLable,jobLable,actionLable,
		            		draft_start,officeLeader_org,officeLeader_name,officeLeader_job,officeLeader_action,
		            		sendtype,draft_op,draft_remarkop,draft_person
		            ],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: 'filehandle/senddraftSave.do',
								method : "POST", 
						        success: function(form, action) {  
						           	Ext.Msg.alert("提示",action.result.msg);
					           		win.close();
						           	store.loadPage(1);
						           	gridpanel.getSelectionModel().clearSelections();
						       	},  
						      	failure: function(form, action) {  
						        	Ext.Msg.alert("提示","保存失败！"); 
						        }  
						   });
			            }
			        },{
			            text: '关闭',
			            handler: function() {
			                win.close();
			            }
			        }]
	            });
				
			    var win = Ext.create('Ext.window.Window',{
			        title: '发送拟办',
			        layout: 'fit',  
			        modal: true, 
			        plain:true,
			        width: 450,  
			        border: 0,   
			        frame: false, 
			        items: form_1
			    });
			    win.show();
			    
	    		form_1.getForm().load({
			    	url: 'filehandle/loadBean.do',
				    params: {id: id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", "加载数据失败！");
				    }
				});
    	}
   
});
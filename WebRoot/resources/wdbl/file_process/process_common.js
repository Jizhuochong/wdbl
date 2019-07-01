//文件办理公共资源
Ext.define('Wdbl.fileprocess.process_common',{ 
	//发送拟办
    f_senddraft: function (id,store,gridpanel,m) { 
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
		            		draft_op,draft_remarkop,draft_person
		            ],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: m+'/senddraftSave.do',
								method : "POST", 
						        success: function(form, action) { 
						        	win.close(); 
						           	Ext.Msg.alert("提示",action.result.msg);
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
			    	url: m+'/loadBean.do',
				    params: {id: id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", "加载数据失败！");
				    }
				});
    	},
    	//拟办
   		 f_draft: function (id,store,gridpanel,m) { 
			 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
			 	
			 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common');  
		 		var officeLeader_result = combobox_common.sp_result('officeLeader_result','审批结果');
			 	
			 	
			 	var officeLeader_opinion = Ext.create('Ext.form.TextArea', {name: "officeLeader_opinion", fieldLabel: '批示意见',labelAlign: 'right',allowBlank:false,maxLength:40});
			 	var officeLeader_remarop = Ext.create('Ext.form.TextArea', {name: "officeLeader_remarop", fieldLabel: '备注意见',labelAlign: 'right',allowBlank:false,maxLength:40});
		 		var officeLeader_Approvaltime = Ext.create('MyForm.DateTimeField', {name: "officeLeader_Approvaltime", fieldLabel: '批示时间',labelAlign: 'right',allowBlank:false});
			 	var officeLeader_enter = Ext.create('Ext.form.Text', {name: "officeLeader_enter", fieldLabel: '录入人',labelAlign: 'right',allowBlank:false,maxLength:10});
			 
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
	             	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
		            items: [filed_id,
		            		officeLeader_result,officeLeader_opinion,officeLeader_remarop,officeLeader_Approvaltime,officeLeader_enter
		            ],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: m+'/draftSave.do',
								method : "POST", 
						        success: function(form, action) { 
						        	win.close(); 
						           	Ext.Msg.alert("提示",action.result.msg);
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
			        title: '拟办',
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
			    	url: m+'/loadBean.do',
				    params: {id: id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", "加载数据失败！");
				    }
				});
    	},
    	//领导意见
   		 f_leaderop: function (store,gridpanel,fileid,m) { 
			 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
			 	
			 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common');  
		 		var duty = combobox_common.spLeaderJob('duty','职务');
		 		var approvalResult = combobox_common.sp_result('approvalResult','审批结果');
			 	
			 	var name = Ext.create('Ext.form.Text', {name: "name", fieldLabel: '名称',labelAlign: 'right',allowBlank:false,maxLength:10});
			 	var agent = Ext.create('Ext.form.Text', {name: "agent", fieldLabel: '代办人',labelAlign: 'right',maxLength:10});
			 	
			 	var approvalOp = Ext.create('Ext.form.TextArea', {name: "approvalOp", fieldLabel: '审批意见',labelAlign: 'right',maxLength:40});
			 	var remarkOp = Ext.create('Ext.form.TextArea', {name: "remarkOp", fieldLabel: '备注意见',labelAlign: 'right',maxLength:40});
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
	             	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
		            items: [
		            		filed_id,name,duty,approvalResult,approvalOp,remarkOp,agent
		            ],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: m+'/save.do',
								method : "POST", 
							    params: {fileid: fileid},
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
			        title: '领导意见',
			        layout: 'fit',  
			        modal: true, 
			        plain:true,
			        height: 350,  
			        width: 450,  
			        border: 0,   
			        frame: false, 
			        items: form_1
			    });
			    win.show();
			    
			    if(fileid!=null){
				    form_1.getForm().load({
				    	url: m+'/loadBeanByFileid.do',
					    params: {fileid: fileid},
					    failure: function(form, action) {
					        Ext.Msg.alert("加载失败", "加载数据失败！");
					    }
					});
			    }
    	}
   
   
    
    
    
});
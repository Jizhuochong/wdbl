Ext.define('Wdbl.fileprocess.file_parttimeleader',{ 
	  //录入兼职信息
    f_entry: function (fileid,store,gridpanel,m) { 
			 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
			 	
			 	var combobox_common =  Ext.create('Wdbl.fileprocess.combobox_common');  
		 		var leader_job = combobox_common.spLeaderJob('leader_job','职务');
		 		var part_job = combobox_common.sp_result('part_job','兼职职务');
			 	
			 	var leader_name = Ext.create('Ext.form.Text', {name: "leader_name", fieldLabel: '名称',labelAlign: 'right',allowBlank:false,maxLength:10});
			 	var leader_enter = Ext.create('Ext.form.Text', {name: "leader_enter", fieldLabel: '录入人',labelAlign: 'right',maxLength:10});
			 	
			 	var leader_opinion = Ext.create('Ext.form.TextArea', {name: "leader_opinion", fieldLabel: '意见',labelAlign: 'right',maxLength:40});
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
	             	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
		            items: [
		            		filed_id,leader_name,leader_job,part_job,leader_opinion,leader_enter
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
			        title: '兼职领导',
			        layout: 'fit',  
			        modal: true, 
			        plain:true,
			        height: 300,  
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
Ext.define('Wdbl.fileprocess.f_CGO_Approval',{ 
	//增加/编辑
    f_edit: function (id,store,gridpanel,wintitle,fileid) { 
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
	                       		url: 'fcgoapproval/save.do',
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
			        title: wintitle,
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
			    
			    if(id!=null){
				    form_1.getForm().load({
				    	url: 'fcgoapproval/loadBean.do',
					    params: {id: id},
					    failure: function(form, action) {
					        Ext.Msg.alert("加载失败", "加载数据失败！");
					    }
					});
			    }
    	},
    	// 删除
		f_del: function (store,gridpanel) {
			var selModel = gridpanel.getSelectionModel();
			if (!selModel.hasSelection()){
				Ext.Msg.alert("提示", "未选中！");
				return;
			}
			
			Ext.Msg.confirm("提示", "确定要删除吗？", function(button) {
				if (button == "yes") {
					var selected = selModel.getSelection();
					var ids = [];
					Ext.each(selected, function(item) {
						ids.push(item.data.id);
					});
					Ext.Ajax.request({
						url : 'fcgoapproval/delete.do',
						method : "post",
						params : {ids : ids},
						success : function(response, opts) {
							Ext.MessageBox.hide();
			                var json = Ext.JSON.decode(response.responseText); 
			                if(json.success){
			                	store.reload();
			                	selModel.clearSelections();
			                }
						},
						failure : function() {
							Ext.Msg.alert('系统提示', '删除失败,刷新后重新操作!');
						}
					});
				}
			});
		
		}
   
});
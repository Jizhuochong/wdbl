<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户管理</title>
		
		<script type="text/javascript">
		
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_sys_user = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "userId" },
                    { name: "username" },
                    { name: "state" }
                ],
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "sys_user/listLoad.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			this.s_sys_user_username = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '用户名',
	    		labelAlign: 'right',
         		labelWidth: 60,
				emptyText:'输入要查询的内容',
			 	width:200
			}); 
			
			var addbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'添加',
				handler : add
			});
			var editbu= Ext.create('Ext.Button', {
				pressed : true,
				text:'编辑',
				handler : edit
			});
			var delbu = Ext.create('Ext.Button', {
				pressed : true,
				text:'删除',
				handler : del
			});
			var uu_bu = Ext.create('Ext.Button', {
				pressed : true,
				text:'授权',
				handler : u_u
			});
			
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				handler : function () {
	            	store_sys_user.loadPage(1);
	        	}
			});

			store_sys_user.on('beforeload', function (store1, options) {
        		var new_params = {username:s_sys_user_username.getValue(),property:'userId',direction:'asc'};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			store_sys_user.loadPage(1);
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store_sys_user,
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
			
			var selModel = Ext.create('Ext.selection.CheckboxModel'); 
            this.gridpanel_sys_user = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "字典管理",
                renderTo: "grid_sys_user",
                frame: true,
                store: store_sys_user,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),  
                    { header: "用户名",  dataIndex: "username", sortable: true,flex : 6 },
                    { header: "状态", dataIndex: "state", sortable: true,flex : 6}
                ],
                tbar: [addbu,editbu,delbu,uu_bu,{xtype : "tbfill"},s_sys_user_username,searchebu] ,
                bbar: toolbar
            });
           gridpanel_sys_user.setHeight(document.getElementById("grid_sys_user").offsetHeight); 
     
	
		 function sys_user_createForm(purl,load,id){
		 	var filed_id = Ext.create('Ext.form.Hidden', {name: "userId", fieldLabel: 'ID'});
		 	var filed_enabled = Ext.create('Ext.form.Hidden', {name: "enabled", value:'1'});
		 	var filed_username = Ext.create('Ext.form.Text', {name: "username", fieldLabel: '用户名',labelAlign: 'right',allowBlank:false});
		 	var filed_password = Ext.create('Ext.form.Text', {name: "password", fieldLabel: '密码',labelAlign: 'right',allowBlank:false,inputType: 'password'});
		 	
		 	
			var form_1 = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [filed_id,filed_enabled,filed_username,filed_password],
		        buttons: [{
		            text: '保存',
		            formBind:true,
		            monitorValid:true,
		            handler: function() {
						form_1.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,
							method : "POST", 
					        success: function(form, action) { 
								s_sys_user_username.setValue(filed_username.getValue());
					           	store_sys_user.loadPage(1);
					           	win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
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
		        title: '增加',
		        layout: 'fit',  
		        modal: true, 
		        plain:true,
		        height: 230,  
		        width: 400,  
		        border: 0,   
		        frame: false, 
		        items: form_1
		    }).show();
		    
		    if(load ==true,id!=null){
	    		form_1.getForm().load({
			    	url: 'sys_user/loadBean.do',
				    params: {
				        id: id
				    },
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		}
		//增加
		function add(){
			sys_user_createForm('sys_user/addSave.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel_sys_user.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.userId!=null){
                  		sys_user_createForm('sys_user/editSave.do',true,selected[0].data.userId);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del() {
		 	var selModel = gridpanel_sys_user.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.userId);
                        })
				        Ext.Ajax.request({
				            url: 'sys_user/delete.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                if(json.success){
				                	store_sys_user.reload();
				                }
				            },
				            failure: function () {
				                Ext.Msg.alert('系统提示', '删除失败,刷新后重新操作!');
				            },
				            params: {ids:ids}
				        });
                    }
                });
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
            
		}
           
        function u_u(){
        	var userid = null;
        	var selModel = gridpanel_sys_user.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.userId!=null){
                  		userid = selected[0].data.userId;
                  }else{
                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
                  		return;
                  }    
            }else {
                Ext.Msg.alert("提醒", "未选择！");
                return;
            }

			var u_utree =  Ext.create('Ext.tree.Panel', {
			    title: '菜单列表',
			    store: new Ext.data.TreeStore({
	                proxy: {
	                    type: 'ajax',
	                    url: 'sys_user/loadAuthorityTree.do',
	                    actionMethods: { read: 'POST'},
	                    extraParams: { userid: userid }
	                }
	            }),
			    rootVisible: false,
			    buttons: [{
		            text: '保存',
		            handler: function() {
						var nodes = u_utree.getChecked();
						var ids = []; 
                        Ext.each(nodes, function (item) {
                        	if(item.data.id!='root'){
                        		ids.push(item.data.id);
                        	}
                        })
						Ext.Ajax.request({
				            url: 'sys_user/authoritySave.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.Msg.alert('成功', '保存成功!');
				               	win.close();
				            },
				            failure: function () {
				                Ext.Msg.alert('系统提示', '操作失败,刷新后重新操作!');
				            },
				            params: {userid:userid,ids:ids}
				        });
						
						
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                win.close();
		            }
		        }]
			});
			//选择事件
			var tree_c_f = function(node, checked) {   
	           if (checked) {
	            	node.bubble(function(parentNode) {
	             		parentNode.set('checked', true);
	             		parentNode.expand(false, true);
	           		});
	            	node.cascadeBy(function(node1) {
	             		node1.set('checked', true);
	             		node1.expand(false, true);
	            	});
	           } else {
	            	node.cascadeBy(function(node1) {
	             		node1.set('checked', false);
	            	});
	           }
			}
			u_utree.on('checkchange',tree_c_f,u_utree); 
			
			
		 	var win = Ext.create('Ext.window.Window',{
		        title: '用户授权',
		        layout: 'fit',  
		        modal: true, 
		        plain:true,
		        height: 400,  
		        width: 360,  
		        border: 0,   
		        frame: false, 
		        items: u_utree
		    }).show();
		}

           
       });
		 
	</script>  

 
	</head>
	<body>  
		<div id="grid_sys_user" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
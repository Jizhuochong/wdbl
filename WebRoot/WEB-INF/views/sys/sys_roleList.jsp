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
            
            this.store_sys_role = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "roleId" },
                    { name: "roleName" },
                    { name: "roleDesc" }
                ],
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "sys_role/listLoad.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			this.s_sys_role_rolename = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '角色名',
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
	            	store_sys_role.loadPage(1);
	        	}
			});

			store_sys_role.on('beforeload', function (store1, options) {
        		var new_params = {roleName:s_sys_role_rolename.getValue(),property:'id',direction:'asc'};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			store_sys_role.loadPage(1);
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store_sys_role,
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
            this.gridpanel_sys_role = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "字典管理",
                renderTo: "grid_sys_role",
                frame: true,
                store: store_sys_role,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),  
                    { header: "角色名",  dataIndex: "roleName", sortable: true,flex : 6 },
                    { header: "描述", dataIndex: "roleDesc", sortable: true,flex : 6}
                ],
                tbar: [addbu,editbu,delbu,uu_bu,{xtype : "tbfill"},s_sys_role_rolename,searchebu] ,
                bbar: toolbar
            });
           gridpanel_sys_role.setHeight(document.getElementById("grid_sys_role").offsetHeight); 
     
	
		 function sys_role_createForm(purl,load,id){
		 	var filed_id = Ext.create('Ext.form.Hidden', {name: "roleId", fieldLabel: 'ID'});
		 	var filed_enabled = Ext.create('Ext.form.Hidden', {name: "enabled", value:'1'});
		 	var filed_roleName = Ext.create('Ext.form.Text', {name: "roleName", fieldLabel: '角色名',labelAlign: 'right',allowBlank:false});
		 	var filed_roleDesc = Ext.create('Ext.form.Text', {name: "roleDesc", fieldLabel: '描述',labelAlign: 'right',allowBlank:false});
		 	
		 	
			var form_1 = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [filed_id,filed_enabled,filed_roleName,filed_roleDesc],
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
								s_sys_role_rolename.setValue(filed_roleName.getValue());
					           	store_sys_role.loadPage(1);
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
			    	url: 'sys_role/loadBean.do',
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
			sys_role_createForm('sys_role/addSave.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel_sys_role.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.roleId!=null){
                  		sys_role_createForm('sys_role/editSave.do',true,selected[0].data.roleId);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del() {
		 	var selModel = gridpanel_sys_role.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.roleId);
                        })
				        Ext.Ajax.request({
				            url: 'sys_role/delete.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                if(json.success){
				                	store_sys_role.reload();
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
        	var roleid = null;
        	var selModel = gridpanel_sys_role.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.roleId!=null){
                  		roleid = selected[0].data.roleId;
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
	                    url: 'sys_role/loadAuthorityTree.do',
	                    actionMethods: { read: 'POST'},
	                    extraParams: { roleid: roleid }
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
				            url: 'sys_role/authoritySave.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.Msg.alert('成功', '保存成功!');
				               	win.close();
				            },
				            failure: function () {
				                Ext.Msg.alert('系统提示', '操作失败,刷新后重新操作!');
				            },
				            params: {roleid:roleid,ids:ids}
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
		<div id="grid_sys_role" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
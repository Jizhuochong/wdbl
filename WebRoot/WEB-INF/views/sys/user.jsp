<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用户列表</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_user = Ext.create("Ext.data.JsonStore", {
            	fields: [
            	    { name: "name" },
            	    { name: "unit" },
            	    { name: "position" },
            	    { name: "department" },
                    { name: "userId" },
                    { name: "username" },
                    { name: "bindedIp" },
                    { name: "createTime",  type: 'date', dateFormat:'time'},
                    { name: "updateTime", type: 'date', dateFormat:'time'},
                    { name: "enabled",type:'boolean' }
                ],
                pageSize:20, remoteSort : true,
                sortInfo: { field: 'userId', direction: 'desc' }, 
                proxy: {
                    type: "ajax",
                    url: "access/user/json",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json", root: "rows", totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            this.user_grid = Ext.create("Ext.grid.Panel", {
            	selModel: selModel, layout : 'fit', /* title: "用户列表", */ renderTo: "user-grid",
                autoHeight:true, border:false, frame: true, loadMask:true, columnLines:true,
                store: store_user,
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',flex : 3}),  
                	{ header: "姓名",  dataIndex: "name", sortable: true,flex : 20},
                    { header: "用户名",  dataIndex: "username", sortable: true,flex : 20},
                    { header: "单位",  dataIndex: "unit", sortable: true,flex : 20},
                    { header: "职务",  dataIndex: "position", sortable: true,flex : 20},
                    { header: "部门",  dataIndex: "department", sortable: true,flex : 20},
                    { header: "绑定IP",  dataIndex: "bindedIp", sortable: true,flex : 32},
                    { header: "创建时间", dataIndex: "createTime",renderer: Ext.util.Format.dateRenderer('Y-m-d h:i:s'), sortable: true,flex : 25 },
                    { header: "更新时间", dataIndex: "updateTime",renderer: Ext.util.Format.dateRenderer('Y-m-d h:i:s'), sortable: true,flex : 25},
                    { header: "启用状态", dataIndex: "enabled",renderer:enableRenderer, sortable: true ,flex : 12}
                ],
                tbar: [{xtype:'button',pressed : true,text:'添加',handler : add_user},
                       {xtype:'button',pressed : true,text:'编辑',handler : edit_user},
                       {xtype:'button',pressed : true,text:'删除',handler : del_user},
                       {xtype:'button',pressed : true,text:'角色设置',handler : set_user_role},
                       {xtype:'button',pressed : true,text:'特权设置',handler : set_user_auth},
                       {xtype : "tbfill"},
                       {id:'s_user_name',xtype:'textfield',name:'username',margins:5,fieldLabel: '用户名',labelAlign: 'right',
                    	   emptyText:'输入要查询的名称'},
                       {xtype:'button',pressed:true,text:'搜索',handler : function(){store_user.loadPage(1);}  }
					  ],
                bbar: {
                	xtype:'pagingtoolbar',
                    store: store_user,
					beforePageText:"第 ",afterPageText:"页，共 {0} 页",  
					firstText:"首页",prevText:"上一页", lastText:"末页",nextText:"下一页",  
                    items: [ '-',
                    	 {xtype:'label', text: '每页'},
		               	 {xtype:'numberfield',width:60,value:20,minValue:1, 
		                   allowBlank: false, 
		                   listeners:{ 
		                       change:function(Field, newValue, oldValue){ 
		                           var num = parseInt(newValue); 
		                           if (isNaN(num) || !num || num<1){ num = 10;Field.setValue(num); } 
		                           store_user.pageSize = num;
		                       } 
		                   } 
		               },
		               {xtype:'label', text: '条'}
	             	],
	             	refreshText:"刷新",
	             	emptyMsg:"没有要显示的数据",  
					displayInfo: true, 
					displayMsg:"<span style='font-size:13px;'>第{0}-{1}条,共{2}条</span>",  
                    emptyMsg: "没有数据" 
                }
            });
           
        });
		 user_grid.setHeight(document.getElementById("user-grid").offsetHeight);
		 store_user.on('beforeload', function (store, options) {
    		 	Ext.apply(store.proxy.extraParams,
    		 			{username: Ext.getCmp('s_user_name').getValue()}
    		 	);
 		});
 		store_user.loadPage(1);
     
	
		 function createUserForm(purl,load,id){
		 
			var user_form = Ext.create('Ext.form.FormPanel',{
				frame: true,
				layout:"form",
				margins: '6 6 6 6',
	            items: [
	                    {xtype:"fieldset",
	                     items:[
	                         {xtype:"hidden",fieldLabel:"ID",name:"userId",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"姓名",name:"name",allowBlank:false,anchor: '100%'},
	                          {xtype:"combo",fieldLabel:"单位",name:"unit",id:'unit_type_combo',allowBlank:false,anchor: '100%',store : getComboDataStore('dw'),displayField : 'node',queryMode: 'local',valueField : 'code',},
	                         {xtype:"combo",fieldLabel:"部门",name:"position",id:'position_type_combo',allowBlank:false,anchor: '100%',store : getComboDataStore('bm'),displayField : 'node',queryMode: 'local',valueField : 'code',},
	                         {xtype:"combo",fieldLabel:"职务",name:"department",id:'department_type_combo',allowBlank:false,anchor: '100%',store : getComboDataStore('zw'),displayField : 'node',queryMode: 'local',valueField : 'code',}, 
	                         {xtype:"textfield",fieldLabel:"用户名",name:"username",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"密码",name:"password",inputType:"password",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"确认密码",name:"repassword",inputType:"password",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"绑定IP",name:"bindedIp",allowBlank:false,anchor: '100%'},
	                         {xtype:"combo",fieldLabel:"启用状态",id:'user_enabled_combo',name:"enabled",store:[[true,'启用'],[false,'禁用']],
	                         	forceSelection:true,editable : false,allowBlank:false,anchor: '100%' }	                        
	                    	]
	                    }
	            ],
		        buttons: [{
		            text: '保存', formBind:true, monitorValid:true,
		            handler: function() {
						user_form.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,method : "POST", 
					        success: function(form, action) {  
					           	Ext.Msg.alert("提示",action.result.msg);
								store_user.reload();
					           	user_win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
					        }  
					   });
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                user_win.close();
		            }
		        }]
            });
			var title ='添加';
		    if(load ==true,id!=null){
		    	title ='编辑';
	    		user_form.getForm().load({
			    	url: 'access/user/'+id, method : "POST",
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }/* ,
				    success: function(form,action){
				    	Ext.getCmp('user_enabled_combo').setValue(action.result.data.enabled);
				    } */
				});
		    }
		    
		    var user_win = Ext.create('Ext.window.Window',{
		        title: title, layout: 'fit', modal: true, plain:true,
		        height: 400, width: 400, border: 0, frame: false, 
		        items: user_form
		    });
		    user_win.show();
		    
		}
		//启用状态Renderer
		function enableRenderer(enabled){
			if(enabled){return "启用";}else{return "禁用";}
		}
		//增加
		function add_user(){
			createUserForm('access/user/add',false,null);
		}
		//编辑
		function edit_user(){
			var selModel = user_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  var id = selected[0].data.userId;
                  if(selected.length==1&& id!=null){
                  		createUserForm('access/user/edit/'+ id,true,id);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del_user() {
		 	var selModel = user_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.userId);
                        });
				        Ext.Ajax.request({
				            url: 'access/user/delete',
				            method: "post",
				            params: {ids:ids},
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_user.reload();
				                }
				            },
				            failure: function () {
				                Ext.Msg.alert('系统提示', '删除失败,刷新后重新操作!');
				            }
				        });
                    }
                });
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
            
		}
		//角色设置
		function set_user_role(){
		    var userid = null;
        	var selModel = user_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.userId!=null){
                  		userid = selected[0].data.userId;
                  }else{
                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
                  		return;
                  }    
            }else {
                Ext.Msg.alert("提醒", "请选择要进行角色设置的用户！");
                return;
            }
			
		 	Ext.create('Ext.window.Window',{
		 		id:'user_role_win', title: '用户角色设置', layout: 'fit',  
		        modal: true, plain:true, frame: false, 
		        height: 400, width: 360, border: 0,   
		        items: {
					id:'user_roles_tree',
	            	xtype:'treepanel',
				    title: '角色列表',
				    rootVisible: false,
				    checked:true,
				    store: new Ext.data.TreeStore({
		                proxy: {
		                    type: 'ajax',
		                    url: 'access/user/'+userid+'/role/json',
		                    actionMethods: { read: 'POST'}
		                }
		            }),
				    buttons: [{
			            text: '保存',
			            handler: function() {
							var nodes = Ext.getCmp('user_roles_tree').getChecked();
							var ids = []; 
	                        Ext.each(nodes, function (item) {
	                        	if(item.data.id!='root'){ ids.push(item.data.id); }
	                        });
							Ext.Ajax.request({
					            url: 'access/user/role/mapping/'+userid,
					            method: "post",
					            success: function (response, opts) {
					            	var json = Ext.JSON.decode(response.responseText); 
					                Ext.Msg.alert('系统提示',json.msg);
					                if(json.success){
					                	Ext.getCmp('user_role_win').close();
					                }
					            },
					            failure: function () {
					                Ext.Msg.alert('系统提示', '操作失败,刷新后重新操作!');
					            },
					            params: {ids:ids}
					        });
							
							
			            }
			        },{
			            text: '关闭',
			            handler: function() {
			            	Ext.getCmp('user_role_win').close();
			            }
			        }],
			        listeners:{
				        checkchange:function(node, checked){
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
			        }
		        }
		    }).show();
            
		}
		//设置特权
		function set_user_auth(){
			 var userid = null;
	        	var selModel = user_grid.getSelectionModel();
	            if (selModel.hasSelection()) {
	                  var selected = selModel.getSelection();
	                  if(selected.length==1&&selected[0].data.userId!=null){
	                  		userid = selected[0].data.userId;
	                  }else{
	                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
	                  		return;
	                  }    
	            }else {
	                Ext.Msg.alert("提醒", "请选择要进行特权设置的用户！");
	                return;
	            }
				
			 	Ext.create('Ext.window.Window',{
			 		id:'user_auth_win', title: '用户特权设置', layout: 'fit',  
			        modal: true, plain:true, frame: false, 
			        height: 400, width: 360, border: 0,   
			        items: {
						id:'user_auths_tree',
		            	xtype:'treepanel',
					    title: '权限列表',
					    rootVisible: false,
					    checked:true,
					    store: new Ext.data.TreeStore({
			                proxy: {
			                    type: 'ajax',
			                    url: 'access/user/'+userid+'/authority/json',
			                    actionMethods: { read: 'POST'}
			                }
			            }),
					    buttons: [{
				            text: '保存',
				            handler: function() {
								var nodes = Ext.getCmp('user_auths_tree').getChecked();
								var ids = []; 
		                        Ext.each(nodes, function (item) {
		                        	if(item.data.id!='root'){ ids.push(item.data.id); }
		                        });
								Ext.Ajax.request({
						            url: 'access/user/authority/mapping/'+userid,
						            method: "post",
						            success: function (response, opts) {
						            	var json = Ext.JSON.decode(response.responseText); 
						                Ext.Msg.alert('系统提示',json.msg);
						                if(json.success){
						                	Ext.getCmp('user_auth_win').close();
						                }
						            },
						            failure: function () {
						                Ext.Msg.alert('系统提示', '操作失败,刷新后重新操作!');
						            },
						            params: {ids:ids}
						        });
								
								
				            }
				        },{
				            text: '关闭',
				            handler: function() {
				            	Ext.getCmp('user_auth_win').close();
				            }
				        }],
				        listeners:{
					        checkchange:function(node, checked){
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
				        }
			        }
			    }).show();
		}
		
	</script>  

 
	</head>
	<body>  
		<div id="user-grid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
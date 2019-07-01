<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>角色列表</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_role = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "roleId" },
                    { name: "roleName" },
                    { name: "roleDesc" },
                    { name: "createTime",  type: 'date', dateFormat:'time'},
                    { name: "updateTime", type: 'date', dateFormat:'time'},
                    { name: "enabled",type:'boolean' }
                ],
                pageSize:20, remoteSort : true,
                sortInfo: { field: 'roleId', direction: 'desc' }, 
                proxy: {
                    type: "ajax",
                    url: "access/role/json",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json", root: "rows", totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            this.role_grid = Ext.create("Ext.grid.Panel", {
            	selModel: selModel, layout : 'fit', /* title: "角色列表", */ renderTo: "role-grid",
                autoHeight:true, border:false, frame: true,loadMask:true,columnLines:true,
                store: store_role,
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',flex : 3}),  
                    { header: "角色名称 ",  dataIndex: "roleName", sortable: true,flex : 32},
                    { header: "角色描述 ",  dataIndex: "roleDesc", sortable: true,flex : 32},
                    { header: "创建时间", dataIndex: "createTime",renderer: Ext.util.Format.dateRenderer('Y-m-d h:i:s'), sortable: true,flex : 25 },
                    { header: "启用状态", dataIndex: "enabled",renderer:enableRenderer, sortable: true ,flex : 12}
                ],
                tbar: [{xtype:'button',pressed : true,text:'添加',handler : add_role},
                       {xtype:'button',pressed : true,text:'编辑',handler : edit_role},
                       {xtype:'button',pressed : true,text:'删除',handler : del_role},
                       {xtype:'button',pressed : true,text:'权限设置',handler : set_role_auth},
                       {xtype : "tbfill"},
                       {id:'s_role_name',xtype:'textfield',name:'roleName',margins:5,fieldLabel: '角色名称',labelAlign: 'right',
                    	   emptyText:'输入要查询的名称'},
                       {xtype:'button',pressed:true,text:'搜索',handler : function(){store_role.loadPage(1);}  }
					  ],
                bbar:  {
                	xtype:'pagingtoolbar',
                    store: store_role,
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
		                           store_role.pageSize = num;
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
		role_grid.setHeight(document.getElementById("role-grid").offsetHeight);
			store_role.on('beforeload', function (store, options) {
   		 	Ext.apply(store.proxy.extraParams, 
   		 			{ roleName: Ext.getCmp('s_role_name').getValue() }
   		 	);
 		});
 		store_role.loadPage(1);
	
		 function createRoleForm(purl,load,id){
		 
			var role_form = Ext.create('Ext.form.FormPanel',{
				frame: true,
				layout:"form",
				margins: '6 6 6 6',
	            items: [
	                    {xtype:"fieldset",
	                     items:[
	                         {xtype:"hidden",fieldLabel:"ID",name:"roleId",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"角色名称",name:"roleName",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"角色描述",name:"roleDesc",allowBlank:false,anchor: '100%'},
	                         {xtype:"combo",fieldLabel:"启用状态",name:"enabled",id:'role_enabled_combo',store:[[true,'启用'],[false,'禁用']],
	                         	forceSelection:true,editable : false,allowBlank:false,anchor: '100%'}
	                         ]
	                    }
	            ],
		        buttons: [{
		            text: '保存',
		            formBind:true, monitorValid:true,
		            handler: function() {
						role_form.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,
							method : "POST", 
					        success: function(form, action) {  
					           	Ext.Msg.alert("提示",action.result.msg);
								store_role.reload();
					           	role_win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
					        }  
					   });
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                role_win.close();
		            }
		        }]
            });
		    var title = '添加';
		    if(load ==true,id!=null){
		    	title = '编辑';
	    		role_form.getForm().load({
			    	url: 'access/role/'+id,
				    method : "POST",
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		    var role_win = Ext.create('Ext.window.Window',{
		        title: title, layout: 'fit',  modal: true, plain:true,
		        height: 230,  width: 400, border: 0, frame: false, 
		        items: role_form
		    });
		    role_win.show();
		    
		}
		//启用状态Renderer
		function enableRenderer(enabled){
			if(enabled){return "启用";}else{return "禁用";}
		}
		//增加
		function add_role(){
			createRoleForm('access/role/add',false,null);
		}
		//编辑
		function edit_role(){
			var selModel = role_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  var id = selected[0].data.roleId;
                  if(selected.length==1&& id!=null){
                  		createRoleForm('access/role/edit/'+ id,true,id);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del_role() {
		 	var selModel = role_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.roleId);
                        });
				        Ext.Ajax.request({
				            url: 'access/role/delete',
				            method: "post",
				            params: {ids:ids},
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_role.reload();
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
		//设置角色权限
		function set_role_auth(){
			 var roleid = null;
	        	var selModel = role_grid.getSelectionModel();
	            if (selModel.hasSelection()) {
	                  var selected = selModel.getSelection();
	                  if(selected.length==1&&selected[0].data.roleId!=null){
	                  		roleid = selected[0].data.roleId;
	                  }else{
	                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
	                  		return;
	                  }    
	            }else {
	                Ext.Msg.alert("提醒", "请选择要进行特权设置的用户！");
	                return;
	            }
				
			 	Ext.create('Ext.window.Window',{
			 		id:'role_auth_win', title: '角色权限设置', layout: 'fit',  
			        modal: true, plain:true, frame: false, 
			        height: 400, width: 360, border: 0,   
			        items: {
						id:'role_auths_tree',
		            	xtype:'treepanel',
					    title: '权限列表',
					    rootVisible: false,
					    checked:true,
					    store: new Ext.data.TreeStore({
			                proxy: {
			                    type: 'ajax',
			                    url: 'access/role/'+roleid+'/authority/json',
			                    actionMethods: { read: 'POST'}
			                }
			            }),
					    buttons: [{
				            text: '保存',
				            handler: function() {
								var nodes = Ext.getCmp('role_auths_tree').getChecked();
								var ids = []; 
		                        Ext.each(nodes, function (item) {
		                        	if(item.data.id!='root'){ ids.push(item.data.id); }
		                        });
								Ext.Ajax.request({
						            url: 'access/role/authority/mapping/'+roleid,
						            method: "post",
						            success: function (response, opts) {
						            	var json = Ext.JSON.decode(response.responseText); 
						                Ext.Msg.alert('系统提示',json.msg);
						                if(json.success){
						                	Ext.getCmp('role_auth_win').close();
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
				            	Ext.getCmp('role_auth_win').close();
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
		<div id="role-grid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>权限列表</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_authority = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "authorityId" },
                    { name: "authorityName" },
                    { name: "authorityDesc"},
                    { name: "enabled",type:'boolean' }
                ],
                pageSize:20, remoteSort : true,
                sortInfo: { field: 'authorityId', direction: 'desc' }, 
                proxy: {
                    type: "ajax",
                    url: "access/authority/json",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json", root: "rows", totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            this.auth_grid = Ext.create("Ext.grid.Panel", {
            	selModel: selModel, layout : 'fit', /* title: "权限列表", */ renderTo: "auth-grid",
                autoHeight:true, border:false, frame: true, loadMask:true, columnLines:true,
                store: store_authority,
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',flex : 3}),  
                    { header: "权限名称",  dataIndex: "authorityName", sortable: true,flex : 32},
                    { header: "权限描述", dataIndex: "authorityDesc", sortable: true,flex : 25 },
                    { header: "启用状态", dataIndex: "enabled",renderer:enableRenderer, sortable: true ,flex : 12}
                ],
                tbar: [{xtype:'button',pressed : true,text:'添加',handler : add_auth},
                       {xtype:'button',pressed : true,text:'编辑',handler : edit_auth},
                       {xtype:'button',pressed : true,text:'删除',handler : del_auth},
                       {xtype:'button',pressed : true,text:'资源设置',handler : set_auth_resc},
                       {xtype:'button',pressed : true,text:'菜单设置',handler : set_auth_menu},
                       {xtype : "tbfill"},
                       {id:'s_auth_name',xtype:'textfield',name:'authorityName',margins:5,fieldLabel: '权限名称',labelAlign: 'right',
                    	   emptyText:'输入要查询的名称'},
                       {xtype:'button',pressed:true,text:'搜索',handler : function(){store_authority.loadPage(1);}  }
					  ],
                bbar: {
                	xtype:'pagingtoolbar',
                    store: store_authority,
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
		                           store_authority.pageSize = num;
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
		 auth_grid.setHeight(document.getElementById("auth-grid").offsetHeight);
		 store_authority.on('beforeload', function (store, options) {
    		 	Ext.apply(store.proxy.extraParams,
    		 			{authorityName: Ext.getCmp('s_auth_name').getValue()}
    		 	);
 		});
 		store_authority.loadPage(1);
     
	
		 function createAuthorityForm(purl,load,id){
		 
			var auth_form = Ext.create('Ext.form.FormPanel',{
				frame: true,
				layout:"form",
				margins: '6 6 6 6',
	            items: [
	                    {xtype:"fieldset",
	                     items:[
	                         {xtype:"hidden",fieldLabel:"ID",name:"authorityId",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"权限名称",name:"authorityName",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"权限描述",name:"authorityDesc",allowBlank:false,anchor: '100%'},
	                         {xtype:"combo",fieldLabel:"启用状态",id:'auth_enabled_combo',name:"enabled",store:[[true,'启用'],[false,'禁用']],
	                         	forceSelection:true,editable : false,allowBlank:false,anchor: '100%' }
	                    	]
	                    }
	            ],
		        buttons: [{
		            text: '保存', formBind:true, monitorValid:true,
		            handler: function() {
						auth_form.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,method : "POST", 
					        success: function(form, action) {  
					           	Ext.Msg.alert("提示",action.result.msg);
								store_authority.reload();
					           	auth_win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
					        }  
					   });
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                auth_win.close();
		            }
		        }]
            });
			var title ='添加';
		    if(load ==true,id!=null){
		    	title ='编辑';
	    		auth_form.getForm().load({
			    	url: 'access/authority/'+id, method : "POST",
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		    var auth_win = Ext.create('Ext.window.Window',{
		        title: title, layout: 'fit', modal: true, plain:true,
		        height: 230, width: 400, border: 0, frame: false, 
		        items: auth_form
		    });
		    auth_win.show();
		    
		}
		//启用状态Renderer
		function enableRenderer(enabled){
			if(enabled){return "启用";}else{return "禁用";}
		}
		//增加
		function add_auth(){
			createAuthorityForm('access/authority/add',false,null);
		}
		//编辑
		function edit_auth(){
			var selModel = auth_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  var id = selected[0].data.authorityId;
                  if(selected.length==1&& id!=null){
                  		createAuthorityForm('access/authority/edit/'+ id,true,id);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del_auth() {
		 	var selModel = auth_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.authorityId);
                        });
				        Ext.Ajax.request({
				            url: 'access/authority/delete',
				            method: "post",
				            params: {ids:ids},
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_authority.reload();
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
		//资源设置
		function set_auth_resc(){
		    var authid = null;
        	var selModel = auth_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.authorityId!=null){
                  		authid = selected[0].data.authorityId;
                  }else{
                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
                  		return;
                  }    
            }else {
                Ext.Msg.alert("提醒", "请选择要进行资源设置的权限！");
                return;
            }
			
		 	Ext.create('Ext.window.Window',{
		 		id:'auth_resc_win', title: '权限资源设置', layout: 'fit',  
		        modal: true, plain:true, frame: false, 
		        height: 400, width: 360, border: 0,   
		        items: {
					id:'auth_rescs_tree',
	            	xtype:'treepanel',
				    title: '资源列表',
				    rootVisible: false,
				    checked:true,
				    store: new Ext.data.TreeStore({
		                proxy: {
		                    type: 'ajax',
		                    url: 'access/authority/'+authid+'/resource/json',
		                    actionMethods: { read: 'POST'}
		                }
		            }),
				    buttons: [{
			            text: '保存',
			            handler: function() {
							var nodes = Ext.getCmp('auth_rescs_tree').getChecked();
							var ids = []; 
	                        Ext.each(nodes, function (item) {
	                        	if(item.data.id!='root'){ ids.push(item.data.id); }
	                        });
							Ext.Ajax.request({
					            url: 'access/authority/resource/mapping/'+authid,
					            method: "post",
					            success: function (response, opts) {
					            	var json = Ext.JSON.decode(response.responseText); 
					                Ext.Msg.alert('系统提示',json.msg);
					                if(json.success){
					                	Ext.getCmp('auth_resc_win').close();
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
			            	Ext.getCmp('auth_resc_win').close();
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
		//设置菜单
		function set_auth_menu(){
			 var authid = null;
	        	var selModel = auth_grid.getSelectionModel();
	            if (selModel.hasSelection()) {
	                  var selected = selModel.getSelection();
	                  if(selected.length==1&&selected[0].data.authorityId!=null){
	                  		authid = selected[0].data.authorityId;
	                  }else{
	                  		Ext.Msg.alert("提醒", "只能选择一条记录！");
	                  		return;
	                  }    
	            }else {
	                Ext.Msg.alert("提醒", "请选择要进行菜单设置的权限！");
	                return;
	            }
				
			 	Ext.create('Ext.window.Window',{
			 		id:'auth_menu_win', title: '权限菜单设置', layout: 'fit',  
			        modal: true, plain:true, frame: false, 
			        height: 400, width: 360, border: 0,   
			        items: {
						id:'auth_menus_tree',
		            	xtype:'treepanel',
					    title: '菜单列表',
					    rootVisible: false,
					    checked:true,
					    store: new Ext.data.TreeStore({
			                proxy: {
			                    type: 'ajax',
			                    url: 'access/authority/'+authid+'/menu/json',
			                    actionMethods: { read: 'POST'}
			                }
			            }),
					    buttons: [{
				            text: '保存',
				            handler: function() {
								var nodes = Ext.getCmp('auth_menus_tree').getChecked();
								var ids = []; 
		                        Ext.each(nodes, function (item) {
		                        	if(item.data.id!='root'){ ids.push(item.data.id); }
		                        });
								Ext.Ajax.request({
						            url: 'access/authority/menu/mapping/'+authid,
						            method: "post",
						            success: function (response, opts) {
						            	var json = Ext.JSON.decode(response.responseText); 
						                Ext.Msg.alert('系统提示',json.msg);
						                if(json.success){
						                	Ext.getCmp('auth_menu_win').close();
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
				            	Ext.getCmp('auth_menu_win').close();
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
		<div id="auth-grid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
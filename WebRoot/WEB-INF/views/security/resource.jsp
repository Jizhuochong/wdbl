<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>资源列表</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_resource = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "resourceId" },
                    { name: "resourceName" },
                    { name: "resourceType"},
                    { name: "resourceString"},
                    { name: "resourceDesc" },
                    { name: "enabled",type:'boolean' }
                ],
                pageSize:20, remoteSort : true,
                sortInfo: { field: 'resourceId', direction: 'desc' }, 
                proxy: {
                    type: "ajax",
                    url: "access/resource/json",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json", root: "rows", totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            this.resource_grid = Ext.create("Ext.grid.Panel", {
            	selModel: selModel, layout : 'fit', /* title: "资源列表", */ renderTo: "resource-grid",
                autoHeight:true, border:false, frame: true,loadMask:true,columnLines:true,
                store: store_resource,
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',flex : 3}),  
                    { header: "资源名称 ",  dataIndex: "resourceName", sortable: true,flex : 20},
                    { header: "资源描述 ",  dataIndex: "resourceDesc", sortable: true,flex : 33},
                    { header: "资源类型", dataIndex: "resourceType",renderer:rescourceTypeRenderer, sortable: true,flex : 15 },
                    { header: "资源URL", dataIndex: "resourceString", sortable: true,flex : 25 },
                    { header: "启用状态", dataIndex: "enabled",renderer:enableRenderer, sortable: true ,flex : 12}
                ],
                tbar: [{xtype:'button',pressed : true,text:'添加',handler : add_resource},
                       {xtype:'button',pressed : true,text:'编辑',handler : edit_resource},
                       {xtype:'button',pressed : true,text:'删除',handler : del_resource},
                       {xtype : "tbfill"},
                       {id:'s_resource_name',xtype:'textfield',name:'resourceName',margins:5,fieldLabel: '资源名称',labelAlign: 'right',
                    	   emptyText:'输入要查询的名称'},
                       {xtype:'button',pressed:true,text:'搜索',handler : function(){store_resource.loadPage(1);}  }
					  ],
                bbar:  {
                	xtype:'pagingtoolbar',
                    store: store_resource,
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
		                           store_resource.pageSize = num;
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
		resource_grid.setHeight(document.getElementById("resource-grid").offsetHeight);
			store_resource.on('beforeload', function (store, options) {
   		 	Ext.apply(store.proxy.extraParams, 
   		 			{ resourceName: Ext.getCmp('s_resource_name').getValue() }
   		 	);
 		});
 		store_resource.loadPage(1);
	
		 function createRoleForm(purl,load,id){
		 
			var resource_form = Ext.create('Ext.form.FormPanel',{
				frame: true,
				layout:"form",
				margins: '6 6 6 6',
	            items: [
	                    {xtype:"fieldset",
	                     items:[
	                         {xtype:"hidden",fieldLabel:"ID",name:"resourceId",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"资源名称",name:"resourceName",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"资源描述",name:"resourceDesc",allowBlank:false,anchor: '100%'},
	                         {xtype:"combo",fieldLabel:"资源类型",name:"resourceType",id:'resource_type_combo',
	                        	 store:[['1','PATH'],['2','ACTION'],['3','JSP'],['4','OTHER']],
	                         	forceSelection:true,editable : false,allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"资源URL",name:"resourceString",allowBlank:false,anchor: '100%'},
	                         {xtype:"combo",fieldLabel:"启用状态",name:"enabled",id:'resource_enabled_combo',store:[[true,'启用'],[false,'禁用']],
	                         	forceSelection:true,editable : false,allowBlank:false,anchor: '100%'}
	                         ]
	                    }
	            ],
		        buttons: [{
		            text: '保存',
		            formBind:true, monitorValid:true,
		            handler: function() {
						resource_form.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,
							method : "POST", 
					        success: function(form, action) {  
					           	Ext.Msg.alert("提示",action.result.msg);
								store_resource.reload();
					           	resource_win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
					        }  
					   });
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                resource_win.close();
		            }
		        }]
            });
		    var title = '添加';
		    if(load ==true,id!=null){
		    	title = '编辑';
	    		resource_form.getForm().load({
			    	url: 'access/resource/'+id,
				    method : "POST",
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		    var resource_win = Ext.create('Ext.window.Window',{
		        title: title, layout: 'fit',  modal: true, plain:true,
		        height: 230,  width: 400, border: 0, frame: false, 
		        items: resource_form
		    });
		    resource_win.show();
		    
		}
		//启用状态Renderer
		function enableRenderer(enabled){
			if(enabled){return "启用";}else{return "禁用";}
		}
		//资源类型Renderer
		function rescourceTypeRenderer(resourceType){
			if(resourceType){
				switch(resourceType){
				 case '1':return "PATH"; case '2':return "ACTION";
				 case '3':return "JSP"; default:return "OTHER";
				}
			}
		}
		//增加
		function add_resource(){
			createRoleForm('access/resource/add',false,null);
		}
		//编辑
		function edit_resource(){
			var selModel = resource_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  var id = selected[0].data.resourceId;
                  if(selected.length==1&& id!=null){
                  		createRoleForm('access/resource/edit/'+ id,true,id);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del_resource() {
		 	var selModel = resource_grid.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.resourceId);
                        });
				        Ext.Ajax.request({
				            url: 'access/resource/delete',
				            method: "post",
				            params: {ids:ids},
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_resource.reload();
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
		
	</script>  

 
	</head>
	<body>  
		<div id="resource-grid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
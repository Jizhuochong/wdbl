<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>菜单列表</title>
		<script type="text/javascript">
		Ext.require([  
		    'Ext.data.*',  
		    'Ext.grid.*',  
		    'Ext.tree.*'  
		]);  
  
		Ext.onReady(function() {  
		    //we want to setup a model and store instead of using dataUrl  
		    Ext.define('Menu', {  
		        extend: 'Ext.data.Model',  
		        fields: [
					{ name: "menuId" },
		            {name: 'menuName',     type: 'string'},  
		            {name: 'url',     type: 'string'},  
		            {name: 'menuIcon', type: 'string'}  
		        ]  
		    });  
		    this.store_menu = Ext.create('Ext.data.TreeStore', {  
		        model: 'Menu',  
		        proxy: {  
		            type: 'ajax',  
		            url: 'access/menu/json',
	            	actionMethods: { read: 'POST'}
		        },  
		        folderSort: true  
		    });
		    var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
		    //Ext.ux.tree.TreeGrid is no longer a Ux. You can simply use a tree.TreePanel  
		    this.menu_treegrid = Ext.create('Ext.tree.Panel', {  
		        /* title: '菜单列表', */  
		        renderTo: 'menu-treegrid',
		        border:false,
		        autoHeight:true,
		        useArrows: true,  
		        rootVisible: false,  
		        store: store_menu,
		        selModel: selModel,
		        singleExpand: true,
                tbar: [{xtype:'button',pressed : true,text:'添加',handler : add_menu},
                       {xtype:'button',pressed : true,text:'编辑',handler : edit_menu},
                       {xtype:'button',pressed : true,text:'删除',handler : del_menu},
                       {xtype : "tbfill"}
					  ],
		        //the 'columns' property is now 'headers'  
		        columns: [{  
		            xtype: 'treecolumn', //this is so we know which column will show the tree  
		            text: '菜单名称',  
		            flex: 2,  
		            sortable: true,  
		            dataIndex: 'menuName'  
		        },{  
		            text: '菜单URL',  
		            flex: 1,  
		            dataIndex: 'url',  
		            sortable: true  
		        },{  text: '菜单图标',  
		            flex: 1,  
		            dataIndex: 'menuIcon',  
		            sortable: false
		        }]  
		    });  
		}); 
		
		function createMenuForm(purl,load,id,pid){
			/* var filed_disnum = Ext.create('Ext.form.Number', {name: "disnum",fieldLabel: '显示序号',labelAlign: 'right',minValue:1,allowBlank:false}); */
			var menu_form = Ext.create('Ext.form.FormPanel',{
				frame: true,
				layout:"form",
				margins: '6 6 6 6',
	            items: [
	                    {xtype:"fieldset",
	                     items:[
	                         {xtype:"hidden",fieldLabel:"ID",name:"menuId",allowBlank:false,anchor: '100%'},
	                         {xtype:"hidden",fieldLabel:"PID",name:"parent.menuId",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"菜单名称",name:"menuName",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"菜单URL",name:"url",allowBlank:false,anchor: '100%'},
	                         {xtype:"Number",fieldLabel:"显示序号",name:"squence",allowBlank:false,anchor: '100%'},
	                         {xtype:"textfield",fieldLabel:"菜单图标",name:"menuIcon",allowBlank:false,anchor: '100%'}
	                         
	                    	]
	                    }
	            ],
		        buttons: [{
		            text: '保存', formBind:true, monitorValid:true,
		            handler: function() {
						menu_form.getForm().submit({
							waitTitle:"请稍候",
                       		waitMsg:"正在提交表单数据，请稍候",  
                       		url: purl,method : "POST", 
					        success: function(form, action) {  
					           	Ext.Msg.alert("提示",action.result.msg);
								store_menu.reload();
					           	menu_win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert("提示",action.result.msg); 
					        }  
					   });
		            }
		        },{
		            text: '关闭',
		            handler: function() {
		                menu_win.close();
		            }
		        }]
            });
			var title ='添加';
			if(pid){
				menu_form.getForm().findField('parent.menuId').setValue(pid);
			}
		    if(load ==true,id!=null){
		    	title ='编辑';
	    		menu_form.getForm().load({
			    	url: 'access/menu/'+id, method : "POST",
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		    var menu_win = Ext.create('Ext.window.Window',{
		        title: title, layout: 'fit', modal: true, plain:true,
		        height: 230, width: 400, border: 0, frame: false, 
		        items: menu_form
		    });
		    menu_win.show();
		    
		}
		//添加
		function add_menu(){
			var pid = null;
			var selModel = menu_treegrid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  pid = selected[0].data.menuId;
            }
			createMenuForm('access/menu/add',false,null,pid);
		}
		//修改
		function edit_menu(){
			var selModel = menu_treegrid.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  var id = selected[0].data.menuId;
                  if(selected.length==1&& id!=null){
                  		createMenuForm('access/menu/edit/'+ id,true,id,null);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		//删除
		function del_menu(){
			var selModel = menu_treegrid.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var id = selected[0].data.menuId;
				        Ext.Ajax.request({
				            url: 'access/menu/delete/'+id,
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_menu.reload();
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
		<div id="menu-treegrid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
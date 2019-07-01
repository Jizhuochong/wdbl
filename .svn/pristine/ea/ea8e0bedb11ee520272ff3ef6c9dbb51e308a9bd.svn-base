<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>用权限管理</title>
		
		<script type="text/javascript">
		
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            this.store_sys_authority = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "authorityId" },
                    { name: "authorityName" },
                    { name: "urlString" }
                ],
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "sys_authority/listLoad.do",
                   	actionMethods: { read: 'POST'},
                   	extraParams:{resourceid:${resourceid}},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			store_sys_authority.loadPage(1);
			
			
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

			
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store_sys_authority,
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
            this.gridpanel_sys_authority = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "权限管理",
                renderTo: "grid_sys_authority",
                frame: true,
                store: store_sys_authority,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),  
                    { header: "权限名称",  dataIndex: "authorityName", sortable: true,flex : 6 },
                    { header: "链接地址", dataIndex: "urlString", sortable: true,flex : 6}
                ],
                tbar: [addbu,editbu,delbu,{xtype : "tbfill"}] ,
                bbar: toolbar
            });
           gridpanel_sys_authority.setHeight(document.getElementById("grid_sys_authority").offsetHeight); 
     
	
		 function sys_authority_createForm(purl,load,id){
		 	var filed_id = Ext.create('Ext.form.Hidden', {name: "authorityId"});
		 	var filed_resourceid = Ext.create('Ext.form.Hidden', {name: "resourceid", value:${resourceid}});
		 	var filed_enabled = Ext.create('Ext.form.Hidden', {name: "enabled", value:'1'});
		 	var filed_authorityname = Ext.create('Ext.form.Text', {name: "authorityName", fieldLabel: '名称',labelAlign: 'right',allowBlank:false});
	 	 	var filed_urlString = Ext.create('Ext.form.Text', {name: "urlString", fieldLabel: '链接',labelAlign: 'right',allowBlank:false});
	 	 	var filed_authorityDesc = Ext.create('Ext.form.Text', {name: "authorityDesc", fieldLabel: '描述',labelAlign: 'right'});
	 	 	var filed_disnum = Ext.create('Ext.form.Number', {name: "disnum",fieldLabel: '显示序号',labelAlign: 'right',minValue:1,allowBlank:false});
		 	
			var form_1 = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [filed_id,filed_resourceid,filed_enabled,filed_authorityname,filed_urlString,filed_authorityDesc,filed_disnum],
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
					           	store_sys_authority.loadPage(1);
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
			    	url: 'sys_authority/loadBean.do',
				    params: {
				        id: id
				    },
				    failure: function(form, action) {
				        Ext.Msg.alert("提示", "加载失败");
				    }
				});
		    }
		    
		}
		//增加
		function add(){
			sys_authority_createForm('sys_authority/addSave.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel_sys_authority.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.authorityId!=null){
                  		sys_authority_createForm('sys_authority/editSave.do',true,selected[0].data.authorityId);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
            }
		}
		
		//删除
		function del() {
		 	var selModel = gridpanel_sys_authority.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.authorityId);
                        })
				        Ext.Ajax.request({
				            url: 'sys_authority/delete.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                if(json.success){
				                	store_sys_authority.reload();
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
           

           
       });
		 
	</script>  

 
	</head>
	<body>  
		<div id="grid_sys_authority" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
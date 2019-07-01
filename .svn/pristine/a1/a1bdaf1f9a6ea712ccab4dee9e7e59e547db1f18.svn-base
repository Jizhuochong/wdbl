<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>资源管理</title>
		
		<script type="text/javascript">
		Ext.require(['*']);
		
         Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            Ext.define("resouces", {
                extend: "Ext.data.Model",
                fields: [
                    { name: "id" },
                    { name: "text" },
                    { name: "url" },
                    { name: "ifMenu" }
                ]
            });
            var sys_resouces_store = Ext.create('Ext.data.TreeStore', {  
		        model: 'resouces',  
		        proxy: {  
		            type: 'ajax',  
		            url: 'sys_resources/listLoad.do',
		            actionMethods: { read: 'POST' }
		        }
		    });  

			var addbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'添加',
				handler : add
			});
			var delbu = Ext.create('Ext.Button', {
				pressed : true,
				text:'删除',
				handler : del
			});
			var authoritybu = Ext.create('Ext.Button', {
				pressed : true,
				text:'权限管理',
				handler : authority
			});
		
            var sys_resouces_gridpanel = Ext.create("Ext.tree.Panel", {
                layout : 'fit',
                autoHeight:true,
                title: "资源管理",
                renderTo: "sys_resouces_grid",
                frame: true,
                store: sys_resouces_store,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                rootVisible: false,
                columns: [
                    { header: "名称", dataIndex: "text", xtype: 'treecolumn', sortable: false,flex : 10 },
                    { header: "是否菜单", dataIndex: "ifMenu",	sortable: false,flex : 3 ,
                    	renderer: function (val) {
                    		if(val!=null&&val=='1'){
                    			return '是';
                    		}else if(val=='0'){
                    			return '否';
                    		}
                    	}
                    },
                    { header: "链接地址", dataIndex: "url", sortable: false,flex : 5 }
                ],
                tbar: [addbu,delbu,authoritybu,{xtype : "tbfill"}] 
            });
           sys_resouces_gridpanel.setHeight(document.getElementById("sys_resouces_grid").offsetHeight);
		   
             
           function sys_resouces_createForm(pid,ptext){
			 	var filed_pid = Ext.create('Ext.form.Hidden', {name: "pid",value:pid});
			 	var filed_leaf = Ext.create('Ext.form.Hidden', {name: "leaf",value:'1'});
			 	var filed_ptext = Ext.create('Ext.form.Text', {name: "ptext", fieldLabel: '上级菜单',labelAlign: 'right',value:ptext,readOnly:true});
			 	var filed_text = Ext.create('Ext.form.Text', {name: "text", fieldLabel: '名称',labelAlign: 'right',allowBlank:false});
 	 			var filed_url = Ext.create('Ext.form.Text', {name: "url", fieldLabel: '链接',labelAlign: 'right',allowBlank:false});
	 	 		var filed_disnum = Ext.create('Ext.form.Number', {name: "disnum",fieldLabel: '显示序号',labelAlign: 'right',minValue:1,allowBlank:false});
		 		//创建数据源[数组数据源]
				var combostore = Ext.create('Ext.data.ArrayStore',{
		          	fields: ['id', 'name'],
		          	data: [[1, '是'], [0, '否']]
		      	});
		      	//创建Combobox
		      	var combobox = Ext.create('Ext.form.ComboBox',{
		      		name: "ifMenu",
		      		labelAlign: 'right',
		          	fieldLabel: '是否菜单',
		          	store: combostore,
		          	displayField: 'name',
		          	valueField: 'id',
		          	value:1,
		          	mode: 'local'
		      	});
			 	
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
					margins: '6 6 6 6',
	             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
		            items: [filed_pid,filed_leaf,filed_ptext,filed_text,filed_url,filed_disnum,combobox],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: 'sys_resources/addSave.do',
								method : "POST", 
						        success: function(form, action) { 
						        	Ext.Msg.alert("提示",action.result.msg);
									sys_resouces_store.reload();
						          	win.close();
						       	},  
						      	failure: function(form, action) {  
						        	Ext.Msg.alert("提示",'保存失败'); 
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
			}
			//增加
			function add(){
				var p_id,p_text; 
				var selModel = sys_resouces_gridpanel.getSelectionModel();
	            if (selModel.hasSelection()) {
	                var selected = selModel.getSelection();
                    Ext.each(selected, function (item) {
                        p_id = item.data.id;
                        p_text = item.data.text;
                    });
	            }
	            sys_resouces_createForm(p_id,p_text);
			}
			
			//删除
			function del() {
			 	var selModel = sys_resouces_gridpanel.getSelectionModel();
	            if (selModel.hasSelection()) {
	                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
	                    if (button == "yes") {
	                        var selected = selModel.getSelection();
	                        var ids = []; 
	                        Ext.each(selected, function (item) {
	                            ids.push(item.data.id);
	                        });
					        Ext.Ajax.request({
					            url: 'sys_resources/delete.do',
					            method: "post",
					            success: function (response, opts) {
					                Ext.MessageBox.hide();
					                var json = Ext.JSON.decode(response.responseText); 
					                Ext.Msg.alert('系统提示', json.msg);
					                if(json.reload){
					                	sys_resouces_store.reload();
					                }
					            },
					            failure: function (response) {
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
			
			//权限管理
			function authority(){
				var id; 
				var selModel = sys_resouces_gridpanel.getSelectionModel();
	            if (selModel.hasSelection()) {
	                var selected = selModel.getSelection();
                    Ext.each(selected, function (item) {
                        id = item.data.id;
                    });
                    
                    var purl = "sys_authority/toList.do?resourceid="+id;
					var tab = rightPanel.getComponent('tabauthority123');
	            	if(tab!=null){
	            		rightPanel.remove(tab);
	            	}
	            	rightPanel.add({
            			loader: {url:purl,autoLoad:true,contentType:'html',loadMask:true,scripts:true},
			        	id:'tabauthority123',
			            closable: true,
			            title: '权限管理'
			        }).show();
	            }else {
	                Ext.Msg.alert("提示", "未选择！");
	            }
			
				
			
			}
			
			
			
			
        });
		

	</script>  

 
	</head>
	<body>  
		<div id="sys_resouces_grid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
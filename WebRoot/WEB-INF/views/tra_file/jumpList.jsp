<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>文电办想系统</title>
		<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
		<meta name="renderer" content="webkit"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<style>
			.x-grid-record-red{
    			color: #FF0000;
 			}
		</style>
<script type="text/javascript" >
var id = <% out.print(request.getParameter("id"));%>;
var type = <% out.print(request.getParameter("type"));%>;
var barNo = '<% out.print(request.getParameter("barNo"));%>';
function parentAddPhoto(imgPath){
	alert("ok");
}
Ext.onReady(function() {
	var a = Ext.getCmp(id);
   	Ext.QuickTips.init(); 
   	var $tid = "tid"+id;
   	Ext.define('Wdbl.view.Jump', {
   		extend : 'Ext.panel.Panel',
   		p_id : null,
   	 	type : null,
   	   	loadUrl : 'tra_file/loadBean.do',
   	   	url : 'tra_file/editSave.do',
   	 	initComponent : function() {
   	   	 	var me = this;
	   	 	var helpStore = Ext.create("Ext.data.JsonStore", {
	         	fields: [
	                 { name: "id" },
	                 { name: "sys_date"},
	                 { name: "agent" },
	                 { name: "handlestatus" },
	                 { name: "handlecontent"},
	                 { name: "name" },
	                 { name: "approvalOp"},
	                 { name: "jishouren"}
	             ],
	             pageSize:10,
	             sortInfo: { field: 'id', direction: 'desc' }, 
	             proxy: {
	             	type: "ajax",
	             	url: "processrecord/loadBeanByFileid.do?id="+id,
	             	actionMethods: { read: 'POST'},
	             	reader: {
	                    type: "json",
	                    root: "rows",
	                    totalProperty: 'totalrows'
	             	},
	             	simpleSortMode: true
	             }
	         });

		   	//可编辑的列表
	        var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
	            clicksToEdit: 2
	        });
	   	   	var helpGrid = Ext.create('Ext.grid.Panel',{
		   	   	minHeight : 100,
	   	   		maxHeight : 200,
	   	   	 	padding:'0 0 5 0',
	   	   		anchor:'100% 100%',
	   	   		border:false,
	   	   		store: helpStore,
	   	   		plugins: [cellEditing],
                split: true,
	   	        columns: [
						Ext.create('Ext.grid.RowNumberer',{text : ''}),  
	   				  { header: "办理时间 ",  dataIndex: "sys_date", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	              { header: "办理人 ",  dataIndex: "agent", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	        	  { header: "办理方式 ",  dataIndex: "handlestatus", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	     		  { header: "办理内容 ",  dataIndex: "handlecontent", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	           	  { header: "姓名 ",  dataIndex: "name", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	  			  { header: "批示意见 ",  dataIndex: "approvalOp", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	           	  { header: "接收人 ",  dataIndex: "jishouren", sortable: true,flex : 50,field: {xtype: 'textfield'}}
	   	        ],
	   	     	listeners : {'beforerender' : function(p) { 
	   	   				//加载列表
	            		helpStore.loadPage(1);
			    	}
				}
	   		});
	   	 	helpGrid.on('edit', function(editor, e) {
	         	var data = helpGrid.getStore().data.items[e.rowIdx].data;
	         	//console.log(data);
	         	//return;
	         	Ext.Ajax.request({
					url : "processrecord/update.do",
					params : data,
					scope : this,
					success : function(response, opts) {
						Ext.Msg.alert('系统提示','更新成功!');
					},
					failure : function() {
						Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
					}
				});
	        });
	   		var typeName = 'Wdbl.form.'+ WDBL$Globals.DocTypeEditForms[me.type];
	   		var centerForm = Ext.create(typeName, {
	   			region: 'center',
	   			load:true,
	   			barNo : me.barNo,
	   	        loadUrl : me.loadUrl,
	   	        loadParam : {'id' : me.p_id},
	   	        url : me.url,
	   	     	addType:'process',
	   	        activeTab: 0
	   		});
	   		var gridPanel = Ext.create('Ext.panel.Panel', {
	   			title:'办理信息',
	   	        region: 'north',
	   	        layout:'anchor',
	   	     	padding:'5 5 0 0',
	   	     	collapsible : true,
	   	        width:300,
	   	        tools: [{type:'refresh',handler:function(){helpStore.reload();}}],
	   	        items: [helpGrid]
	   		});
	   		
	   		Ext.applyIf(me, {
	   			layout: 'border',
	   			items: [centerForm,gridPanel]
	   		});
	   		me.callParent(arguments);
   		}
   	});
   	var $create = Ext.create('Wdbl.view.Jump',{
	   		region: 'center',
	   		p_id : id,
			type : type,
			barNo : barNo,
			renderTo : $tid,		
			listeners : {'beforerender' : function(p) { 
		        p.setHeight(document.getElementById($tid).offsetHeight);
	    	},  
	    	scope : this  
		}
	});
   	
});
</script>
</head>
<body>
	<div id="tid<%out.print(request.getParameter("id"));%>" style="width:100%; height:100%;"></div>
</body>
</html>


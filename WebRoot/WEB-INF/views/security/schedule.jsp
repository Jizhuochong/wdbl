<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>自动办结配置</title>
		<script type="text/javascript">
		var purl;
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            var store_schedule = Ext.create("Ext.data.JsonStore", {
           		fields: [
                    { name: "executeDate" },
                    { name: "autoFinishDays" },
                    { name: "isActive" }
                ],  
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "schedule/listLoad.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			var editbu= Ext.create('Ext.Button', {
				pressed : true,
				text:'编辑',
				handler : edit
			});

			store_schedule.on('beforeload', function (store, options) {
        		var new_params = {property:'id',direction:'asc'};
       		 	Ext.apply(store.proxy.extraParams, new_params);
    		});
			store_schedule.loadPage(1);
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
            	store: store_schedule,
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
            var gridpanel_schedule = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "自动办结配置",
                renderTo: "grid_schedule",
                frame: true,
                store: store_schedule,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),  
                    { header: "执行时间",  dataIndex: "executeDate", sortable: true,flex : 6 },
                    { header: "自动办结期限", dataIndex: "autoFinishDays", sortable: true,flex : 6},
                    { header: "是否启用", dataIndex: "isActive", sortable: true,flex : 6,renderer: function (value, metaData, record) {if(value == '1') {return '是';} else {return '否';}}}
                ],
                tbar: [editbu],
                bbar: toolbar
            });
            
           gridpanel_schedule.setHeight(document.getElementById("grid_schedule").offsetHeight); 
           
			var editForm = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
            	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 110,width:300},
	            items: [{xtype:"hiddenfield",name: "id"},
	                    {
	                    	name: "executeDate", 
	                    	fieldLabel: '执行时间',
	                    	labelAlign: 'right',
	                    	allowBlank:false, 
	                    	readonly: true,
	                    	xtype : "timefield",
	                    	format : 'H:i:s',
	                    	editable : true
	                    },
	                    {
	                    	name: "autoFinishDays", 
	                    	fieldLabel: '自动办结期限',
	                    	labelAlign: 'right',
	                    	allowBlank:false, 
	                    	minValue:1,
	                    	xtype : "numberfield",
	                    	editable : false
	                    },
	                    {
	                    	xtype: 'fieldcontainer',
	                    	allowBlank: false, 
	                    	fieldLabel: '是否启用',
	                    	labelAlign: 'right',
	                    	defaultType: 'radiofield',
	                    	defaults: {
	                    	    flex: 1
	                    	},
	                    	layout: 'hbox',
	                    	items: [
	                    		{
	                    			boxLabel  : '是',
	                    			name      : 'isActive',
	                            	inputValue: '1',
	                    	    	id        : 'radio1'
	                    	    }, {
	                    	        boxLabel  : '否',
	                    	        name      : 'isActive',
	                    	        inputValue: '0',
	                    	        id        : 'radio2'
	                    	    }
	                    	]
	                    }
	                   ],
		        buttons: [{
		            text: '保存',
		            formBind:true,
		            monitorValid:true,
		            handler: function() {
		            	editForm.getForm().submit({
							waitTitle:"请稍候",
                      		waitMsg:"正在提交表单数据，请稍候",  
                      		url: purl,
							method : "POST", 
					        success: function(form, action) { 
					           	store_schedule.loadPage(1);
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
	        title: '编辑',
	        layout: 'fit',  
	        modal: true, 
	        plain:true,
	        height: 230,  
	        width: 400,
	        closeAction:'hide',
	        border: 0,   
	        frame: false, 
	        items: editForm
	    });
	 	function schedule_createForm(path,load,id){
	 		purl = path;
			editForm.getForm().reset();
		    if(load ==true,id!=null){
		    	editForm.getForm().load({
			    	url: 'schedule/loadBean.do',
				    params: {id: id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    win.show();
		}
		//编辑
		function edit(){
			var selModel = gridpanel_schedule.getSelectionModel();
            if (!selModel.hasSelection()) {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                return;
            }
           	var selected = selModel.getSelection();
           	if(selected.length!=1 || selected[0].data.id==null){
              	Ext.Msg.alert("提醒", "只能编辑一条记录！");
              	return;
           	}
           	schedule_createForm('schedule/editSave.do',true,selected[0].data.id);
		}
    });
		 
	</script>  

 
	</head>
	<body>  
		<div id="grid_schedule" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
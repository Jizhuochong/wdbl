<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>列表</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            store = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "id" },
                    { name: "barNo" },
//                    { name: "docNumber" },
                    { name: "title" },
                    { name: "unit" },
                    { name: "copyNum" },
                    { name: "numbers" },
                    { name: "sys_date" },
                    { name: "isRecovery" }
                ],
                pageSize:20,
                proxy: {
                    type: "ajax",
                    url: "filedispense/wideList.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
            var searchDate = Ext.create('Ext.form.ComboBox', {
					fieldLabel : "分发时间",
	    			labelAlign: 'left',
         			labelWidth: 60,
         			store:[['2014','2014'],['2015','2015']],
					value:new Date().getFullYear(),
					emptyText:'输入要查询的时间',
					format: 'Y',
				})
			 var searchFile = Ext.create('Ext.form.field.Text', {
		    	fieldLabel: '名称',
	    		labelAlign: 'left',
         		labelWidth: 40,
				emptyText:'输入要查询的名称',
			 	width:160
			}); 
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				iconCls : 'find',
				handler : function () {
	            	store.loadPage(1);
	        	}
			});
			var hs = Ext.create('Ext.Button',{
				pressed : true,
				disabled : false,
				text : '回收',
				handler :function() {
						var selModel = gridpanel.getSelectionModel();
					    if (selModel.hasSelection()) {
			                var selected = selModel.getSelection();
			                var ids = []; 
			                Ext.each(selected, function (item) {
			                    ids.push(item.data.id);
			                });
							Ext.Ajax.request({
								url : "filedispense/hsUnit.do",
								method : "post",
								params: {ids:ids},
								scope : this,
								success : function(response, opts) {
									var json = Ext.JSON.decode(response.responseText);
									//Ext.Msg.alert('系统提示', json.msg);
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
								}
							});
					    }
					    else{
					    	Ext.Msg.alert("错误", "没有任何行被选中，无法进行回收操作！");
					    }
				}
			});
			var qxhs =  Ext.create('Ext.Button',{
					pressed : true,
					disabled : false,
					text : '取消回收',
					handler : function() {
						var selModel = gridpanel.getSelectionModel();
					    if (selModel.hasSelection()) {
			                var selected = selModel.getSelection();
			                var ids = []; 
			                Ext.each(selected, function (item) {
			                    ids.push(item.data.id);
			                });
							Ext.Ajax.request({
								url : "filedispense/unHsUnit.do",
								method : "post",
								params: {ids:ids},
								scope : this,
								success : function(response, opts) {
									var json = Ext.JSON.decode(response.responseText);
									//Ext.Msg.alert('系统提示', json.msg);
									store.reload();
								},
								failure : function() {
									Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
								}
							});
					    }
					    else{
					    	Ext.Msg.alert("错误", "没有任何行被选中，无法进行回收操作！");
					    }
				    }
				});
			store.pageSize = 20,
			store.on('beforeload', function (data, options) {
        		var new_params = {sys_date : searchDate.getValue(),title: searchFile.getValue(),property:'id',direction:'asc'};//name与PubJleaderinfo实体类中属性对应
       		 	Ext.apply(data.proxy.extraParams, new_params);
       		 	searchFile.setValue("");
    		});
    		store.loadPage(1);
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store,
					beforePageText:"第 ",  
					afterPageText:"页，共 {0} 页",  
					firstText:"首页",  
					prevText:"上一页",  
					lastText:"末页",  
					nextText:"下一页",  
                    items: [ '-',
                    	 {xtype:'label', text: '每页'},
		               	 {xtype:'numberfield',width:60,value:20,minValue:1, 
		                   allowBlank: false, 
		                   listeners:{ 
		                       change:function(Field, newValue, oldValue){ 
		                           var num = parseInt(newValue); 
		                           if (isNaN(num) || !num || num<1){ 
		                               num = 10; 
		                               Field.setValue(num); 
		                           } 
		                           store.pageSize = num;
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
                });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel'); 
            gridpanel = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "菜单列表",
                renderTo: "pubFileContactsInfoGrid",
                frame: true,
                store: store,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:27}), 
                	{ header: "ID",  dataIndex: "id", sortable: true,flex : 3 ,hidden:true}, 
                    { header: "条码编号", dataIndex: "barNo", sortable: true,flex : 5 },
//                    { header: "文号", dataIndex: "docNumber", sortable: true ,flex : 5},
                    { header: "文件标题", dataIndex: "title", sortable: true ,flex : 5},
                    { header: "分发单位", dataIndex: "unit", sortable: true ,flex : 5},
                    { header: "分发份数", dataIndex: "copyNum", sortable: true ,flex : 5},
                    { header: "号码", dataIndex: "numbers", sortable: true ,flex : 5},
                    { header: "分发时间", dataIndex: "sys_date", sortable: true ,flex : 5},
                    { header: "是否回收", dataIndex: "isRecovery", sortable: true ,flex : 5}
                ],
                tbar: [searchDate,searchFile,searchebu,hs,qxhs] ,
                bbar: toolbar
                
            });
           
        });
		 gridpanel.setHeight(document.getElementById("pubFileContactsInfoGrid").offsetHeight);
	</script>  

 
	</head>
	<body>  
		<div id="pubFileContactsInfoGrid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
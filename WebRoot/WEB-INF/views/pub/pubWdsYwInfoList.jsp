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
            
            this.store_1 = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "id" },
                    { name: "fileName" },
                    { name: "fileTitle" },
                    { name: "fileContent" },
                    { name: "fileUrl" },
                    { name: "uploadPeople" },
                    { name: "uploadPeopleunit" },
                    { name: "uploadTime" }
                  
                ],
                pageSize:10,
                sortInfo: { field: 'id', direction: 'asc' }, 
                proxy: {
                    type: "ajax",
                    url: "pubWdsYwInfo/list.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });

			this.s_filed_1 = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '名称',
	    		labelAlign: 'right',
         		labelWidth: 40,
				emptyText:'输入要查询的名称',
			 	width:160
			}); 
			
			var addbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'添加',
				iconCls : 'add',
				handler : add
			});
			var editbu= Ext.create('Ext.Button', {
				pressed : true,
				text:'编辑',
				iconCls : 'table_edit',
				handler : edit
			});
			var delbu = Ext.create('Ext.Button', {
				pressed : true,
				text:'删除',
				iconCls : 'delete',
				handler : del
			});
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				iconCls : 'find',
				handler : function () {
	            	store_1.loadPage(1);
	        	}
			});
			store_1.pageSize = 20;
			store_1.on('beforeload', function (store1, options) {
        		var new_params = { fileName: s_filed_1.getValue(),property:'id',direction:'asc'};//name与PubJleaderinfo实体类中属性对应
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
    		store_1.loadPage(1);
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store_1,
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
		                           store_1.pageSize = num;
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
            this.gridpanel = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "菜单列表",
                renderTo: "pubWdsYwInfoGrid",
                frame: true,
                store: store_1,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:27}),  
                    { header: "ID",  dataIndex: "id", sortable: true,flex : 3 },
                    { header: "文件名称", dataIndex: "fileName", sortable: true,flex : 5 },
                    { header: "文件标题", dataIndex: "caseDiscription", sortable: true ,flex : 5},
                    { header: "文件内容", dataIndex: "fileContent", sortable: true ,flex : 5},
                    { header: "文件存储url", dataIndex: "fileUrl", sortable: true ,flex : 5},
                    { header: "上传人", dataIndex: "uploadPeople", sortable: true ,flex : 5},
                    { header: "上传人单位", dataIndex: "uploadPeopleunit", sortable: true ,flex : 5},
                    { header: "上传时间", dataIndex: "uploadTime", sortable: true ,flex : 5}
                ],
                tbar: [addbu,editbu,delbu,{xtype : "tbfill"},s_filed_1,searchebu] ,
                bbar: toolbar
            });
           
        });
		 gridpanel.setHeight(document.getElementById("pubWdsYwInfoGrid").offsetHeight);
     
	
		 function createForm(purl,load,id){
		 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
		 	var filed_fileName = Ext.create('Ext.form.Text', {name: "fileName", fieldLabel: '文件名称',labelAlign: 'right',allowBlank:false});
		 	var filed_fileTitle = Ext.create('Ext.form.Text', {name: "fileTitle", fieldLabel: '文件标题',labelAlign: 'right',allowBlank:false});
		 	var filed_fileContent = Ext.create('Ext.form.Text', {name: "fileContent", fieldLabel: '文件内容',labelAlign: 'right',allowBlank:false});
		 	var filed_fileUrl = Ext.create('Ext.form.Text', {name: "fileUrl", fieldLabel: '文件存储url',labelAlign: 'right',allowBlank:false});
		 	var filed_uploadPeople = Ext.create('Ext.form.Text', {name: "uploadPeople", fieldLabel: '上传人',labelAlign: 'right',allowBlank:false});
		 	var filed_uploadPeopleunit = Ext.create('Ext.form.Text', {name: "uploadPeopleunit", fieldLabel: '上传人单位',labelAlign: 'right',allowBlank:false});
		 	var filed_uploadTime = Ext.create('Ext.form.Date', {name: "uploadTime", fieldLabel: '上传时间',labelAlign: 'right',allowBlank:false});
		 	
	 	 	//创建数据源[数组数据源]
			var combostore = Ext.create('Ext.data.ArrayStore',{
	          	fields: ['id', 'name'],
	          	data: [[true, 'true'], [false, 'false']]
	      	});
	      	//创建Combobox
	      	var combobox = Ext.create('Ext.form.ComboBox',{
	      		labelAlign: 'right',
	          	fieldLabel: '叶子节点',
	          	store: combostore,
	          	displayField: 'name',
	          	valueField: 'id',
	          	mode: 'local'
	      	});

			var form_1 = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [filed_id,filed_fileName,filed_fileTitle,filed_fileContent,filed_fileUrl,filed_uploadPeople,filed_uploadPeopleunit,filed_uploadTime],
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
					           	Ext.Msg.alert("提示",action.result.msg);
								s_filed_1.setValue(filed_fileName.getValue());
					           	store_1.loadPage(1);
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
		        height: 290,  
		        width: 400,  
		        border: 0,   
		        frame: false, 
		        items: form_1
		    });
		    win.show();
		    
		    if(load ==true,id!=null){
	    		form_1.getForm().load({
			    	url: 'pubWdsYwInfo/loadBean.do',
				    params: {
				        id: id
				    },
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    
		}
		//增加
		function add(){
			createForm('pubWdsYwInfo/add.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.id!=null){
                  		createForm('pubWdsYwInfo/update.do',true,selected[0].data.id);
                  }else{
                  		Ext.Msg.alert("提醒", "只能编辑一条记录！");
                  }    
            }else {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
            }
		}
		
		//删除
		function del() {
		 	var selModel = gridpanel.getSelectionModel();
            if (selModel.hasSelection()) {
                Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                    if (button == "yes") {
                        var selected = selModel.getSelection();
                        var ids = []; 
                        Ext.each(selected, function (item) {
                            ids.push(item.data.id);
                        })
				        Ext.Ajax.request({
				            url: 'pubWdsYwInfo/delete.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                Ext.Msg.alert('系统提示',json.msg);
				                if(json.success){
				                	store_1.reload();
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
		
		
	</script>  

 
	</head>
	<body>  
		<div id="pubWdsYwInfoGrid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
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
                    { name: "name" },
                    { name: "job" },
                    { name: "cityPhone" },
                    { name: "phone" },
                    { name: "address" },
                    { name: "dis_num"}
                ],
                pageSize:10,
                sortInfo: { field: 'dis_num', direction: 'asc' }, 
                proxy: {
                    type: "ajax",
                    url: "pubJleaderInfo/list.do",
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
				iconCls:'delete',
				handler : del
			});
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				iconCls:'find',
				handler : function () {
	            	store_1.loadPage(1);
	        	}
			});
			store_1.pageSize = 20;
			store_1.on('beforeload', function (store1, options) {
        		var new_params = { name: s_filed_1.getValue(),property:'dis_num',direction:'asc'};//name与PubJleaderinfo实体类中属性对应
        		s_filed_1.setValue();
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
                renderTo: "pubJleaderInfoGrid",
                frame: true,
                store: store_1,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:27}),  
                    { header: "ID",  dataIndex: "id", sortable: true,flex : 3 },
                    { header: "姓名", dataIndex: "name", sortable: true,flex : 5 },
                    { header: "职务", dataIndex: "job", sortable: true ,flex : 5},
                    { header: "市电", dataIndex: "cityPhone", sortable: true ,flex : 5},
                    { header: "手机", dataIndex: "phone", sortable: true ,flex : 5},
                    { header: "住址", dataIndex: "address", sortable: true ,flex : 5},
                    { header: "排序", dataIndex: "dis_num", sortable: true ,flex : 5}
                ],
                tbar: [addbu,editbu,delbu,{xtype : "tbfill"},s_filed_1,searchebu] ,
                bbar: toolbar
            });
           
        });
		 gridpanel.setHeight(document.getElementById("pubJleaderInfoGrid").offsetHeight);
     
	
		 function createForm(purl,load,id){
		 	var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
		 	var filed_name = Ext.create('Ext.form.Text', {name: "name", fieldLabel: '姓名',labelAlign: 'right',allowBlank:false});
		 	var filed_job = Ext.create('Ext.form.Text', {name: "job", fieldLabel: '职务',labelAlign: 'right',allowBlank:false});
		 	var filed_cityPhone = Ext.create('Ext.form.Text', {name: "cityPhone", fieldLabel: '市电',labelAlign: 'right',allowBlank:false});
		 	var filed_specialPhone = Ext.create('Ext.form.Text', {name: "specialPhone", fieldLabel: '专电',labelAlign: 'right',allowBlank:false});
		 	var filed_redPhone = Ext.create('Ext.form.Text', {name: "redPhone", fieldLabel: '红机',labelAlign: 'right',allowBlank:false});
		 	var filed_phone = Ext.create('Ext.form.Text', {name: "phone", fieldLabel: '手机',labelAlign: 'right',allowBlank:false});
		 	var filed_address = Ext.create('Ext.form.Text', {name: "address", fieldLabel: '住址',labelAlign: 'right',allowBlank:false});
		 	var filed_carNum = Ext.create('Ext.form.Text', {name: "carNum", fieldLabel: '车号',labelAlign: 'right',allowBlank:false});
		 	var filed_driverPhone = Ext.create('Ext.form.Text', {name: "driverPhone", fieldLabel: '司机电话',labelAlign: 'right',allowBlank:false});
		 	var filed_driverUnit = Ext.create('Ext.form.Text', {name: "driverUnit", fieldLabel: '司机单位',labelAlign: 'right',allowBlank:false});
		 	var dis_unitnumber = Ext.create('Ext.form.Text', {name: "dis_num", fieldLabel: '排序',labelAlign: 'right',allowBlank:true});
		 	
		 	
	 	 	//创建数据源[数组数据源]
			var combostore = Ext.create('Ext.data.ArrayStore',{
	          	fields: ['id', 'name'],
	          	data: [[true, 'true'], [false, 'false']]
	      	});
	      	//创建Combobox
	      	var job_store = Ext.create("Ext.data.Store", {
            	fields: [{ name: "jobName" },{ name: "id" }],
            	autoLoad: true,
                proxy: {
                    type: "ajax",
                    url: "pubJleaderInfo/getJobList.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows"
                    }
                }
            });
	 		//创建Combobox
	      	var combobox = Ext.create('Ext.form.ComboBox',{
	      		name:'job',
	      		labelAlign: 'right',
	          	fieldLabel: '职务',
	          	store: job_store,
	          	displayField: 'jobName',
	          	valueField: 'jobName',
	          	mode: 'local'
	      	});
			var form_1 = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [filed_id,filed_name,combobox,filed_cityPhone,filed_specialPhone,filed_redPhone,filed_phone,filed_address,filed_carNum,filed_driverPhone,filed_driverUnit,dis_unitnumber],
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
//								s_filed_1.setValue(filed_name.getValue());
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
		        height: 380,  
		        width: 400,  
		        border: 0,   
		        frame: false, 
		        items: form_1
		    });
		    win.show();
		    
		    if(load ==true,id!=null){
	    		form_1.getForm().load({
			    	url: 'pubJleaderInfo/loadBean.do',
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
			createForm('pubJleaderInfo/add.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel.getSelectionModel();
            if (selModel.hasSelection()) {
                  var selected = selModel.getSelection();
                  if(selected.length==1&&selected[0].data.id!=null){
                  		createForm('pubJleaderInfo/update.do',true,selected[0].data.id);
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
				            url: 'pubJleaderInfo/delete.do',
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
		<div id="pubJleaderInfoGrid" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
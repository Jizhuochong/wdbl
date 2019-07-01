<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>字典管理</title>
		<script type="text/javascript">
		var purl;
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            var store_code = Ext.create("Ext.data.JsonStore", {
           		fields: [
                    { name: "code" },
                    { name: "node" },
                    { name: "dis_num" }
                ],  
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "code/listLoad.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
			var s_code_parent = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '上级代码',
	    		labelAlign: 'right',
         		labelWidth: 60,
				emptyText:'输入要查询的内容',
				value:'DL',
			 	width:200
			}); 
			var s_code_code = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '代码',
	    		labelAlign: 'right',
         		labelWidth: 40,
				emptyText:'输入要查询的内容',
			 	width:200
			}); 
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
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				handler : function () {
	            	store_code.loadPage(1);
	        	}
			});

			store_code.on('beforeload', function (store, options) {
        		var new_params = {code: s_code_code.getValue(),parent_code:s_code_parent.getValue(),property:'id',direction:'asc'};
       		 	Ext.apply(store.proxy.extraParams, new_params);
    		});
			store_code.loadPage(1);
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
            	store: store_code,
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
            var gridpanel_code = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "字典管理",
                renderTo: "grid_code",
                frame: true,
                store: store_code,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),  
                    { header: "代码",  dataIndex: "code", sortable: true,flex : 6 },
                    { header: "名称", dataIndex: "node", sortable: true,flex : 6},
                    { header: "显示顺序", dataIndex: "dis_num", sortable: true ,flex : 5}
                ],
                tbar: [addbu,editbu,delbu,{xtype : "tbfill"},s_code_parent,s_code_code,searchebu],
                bbar: toolbar
            });
            
           gridpanel_code.setHeight(document.getElementById("grid_code").offsetHeight); 
           
			var parent_store = Ext.create("Ext.data.Store", {
				fields: [{ name: "code" },{ name: "node" }],
				autoLoad: true,
		    	proxy: {
					type: "ajax",
		        	url: "code/loadParentList.do",
			        actionMethods: { read: 'POST'},
			        extraParams: {parent:'DL'},
			        reader: {
			        	type: "json",
			            	root: "rows"
			        }
			    }
			});
	 		//
	      	var combobox = Ext.create('Ext.form.ComboBox',{
	      		labelWidth: 80,
	      		width:300,
	      		name:'parent_code',
	      		labelAlign: 'right',
	          	fieldLabel: '上级标题',
	          	store: parent_store,
	          	displayField: 'node',
	          	valueField: 'code',
	          	mode: 'local'
	      	});
		 	
			var editForm = Ext.create('Ext.form.FormPanel',{
				frame: true,
				margins: '6 6 6 6',
            	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [{xtype:"hiddenfield",name: "id"},
	                    {name: "code", fieldLabel: '代码',labelAlign: 'right',allowBlank:false},
	                    {name: "node", fieldLabel: '名称',labelAlign: 'right',allowBlank:false},
	                    {name: "dis_num", fieldLabel: '显示顺序',labelAlign: 'right',allowBlank:false,minValue:1},
	                    combobox],
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
					           	store_code.loadPage(1);
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
	        closeAction:'hide',
	        border: 0,   
	        frame: false, 
	        items: editForm
	    });
	 	function code_createForm(path,load,id){
	 		purl = path;
			editForm.getForm().reset();
		    if(load ==true,id!=null){
		    	editForm.getForm().load({
			    	url: 'code/loadBean.do',
				    params: {id: id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
		    }
		    win.show();
		}
		//增加
		function add(){
			code_createForm('code/addSave.do',false,null);
		}
		//编辑
		function edit(){
			var selModel = gridpanel_code.getSelectionModel();
            if (!selModel.hasSelection()) {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                return;
            }
           	var selected = selModel.getSelection();
           	if(selected.length!=1 || selected[0].data.id==null){
              	Ext.Msg.alert("提醒", "只能编辑一条记录！");
              	return;
           	}
           	code_createForm('code/editSave.do',true,selected[0].data.id);
		}
		
		//删除
		function del() {
		 	var selModel = gridpanel_code.getSelectionModel();
            if (!selModel.hasSelection()) {
                Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                return;
            }
            Ext.Msg.confirm("警告", "确定要删除吗？", function (button) {
                if (button == "yes") {
                    var selected = selModel.getSelection();
                    var ids = []; 
                    Ext.each(selected, function (item) {ids.push(item.data.id);});
			        Ext.Ajax.request({
			            url: 'code/delete.do',
			            method: "post",
			            success: function (response, opts) {
			                Ext.MessageBox.hide();
			                var json = Ext.JSON.decode(response.responseText); 
			                if(json.success){
			                	store_code.reload();
			                }
			            },
			            failure: function () {
			                Ext.Msg.alert('系统提示', '删除失败,刷新后重新操作!');
			            },
			            params: {ids:ids}
			       });
                }
            });
		}
    });
		 
	</script>  

 
	</head>
	<body>  
		<div id="grid_code" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
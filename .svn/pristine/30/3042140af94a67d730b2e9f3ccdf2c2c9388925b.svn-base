/**
 * 
 */
 Ext.define('Wdbl.view.FileOverDetail', {extend : 'Ext.panel.Panel', requires : ['Ext.*', 'Wdbl.utils.*'],
   		p_id : null,
   	 	type : null,
   	   	loadUrl : 'tra_file/loadBean.do',
   	   	url : 'reg_file/editSave.do',
   	   	parent: null,
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
	                 { name: "jishouren"},
	                 { name: "psfk"}
	             ],
	             pageSize:10,
	             sortInfo: { field: 'id', direction: 'desc' }, 
	             proxy: {
	             	type: "ajax",
	             	url: "processrecord/loadBeanByFileid.do?id="+me.p_id,
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
	   	   		region: 'north',
	   	   	 	padding:'0 0 5 0',
	   	   		border:false,
	   	   		store: helpStore,
	   	   		tools: [{type:'refresh',handler:function(){helpStore.reload();}}],
                split: true,
	   	        columns: [
					Ext.create('Ext.grid.RowNumberer',{text : ''}),  
					 { header: "办理时间 ",  dataIndex: "sys_date", sortable: true,flex :50,field: {xtype: 'textfield'}},
	   	              { header: "办理人 ",  dataIndex: "agent", sortable: true,flex : 30,field: {xtype: 'textfield'}},
	   	        	  { header: "办理方式 ",  dataIndex: "handlestatus", sortable: true,flex : 40,field: {xtype: 'textfield'}},
	   	     		  { header: "办理内容 ",  dataIndex: "handlecontent", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	           	  { header: "批示领导 ",  dataIndex: "name", sortable: true,flex : 30,field: {xtype: 'textfield'}},
	   	  			  { header: "批示内容 ",  dataIndex: "approvalOp", sortable: true,flex : 150,field: {xtype: 'textfield'}},
	   	           	  { header: "接收人 ",  dataIndex: "jishouren", sortable: true,flex : 50,field: {xtype: 'textfield'}},
	   	           	  { header: "批示反馈",  dataIndex: "psfk", sortable: true,flex : 50,field: {xtype: 'textfield'}}
	   	        ],
	   	     	listeners : {
	   	     		'beforerender' : function(p) { 
	   	   				//加载列表
	            		helpStore.loadPage(1);
			    	},
			    	celldblclick : editStore
				}
	   		});
	   	 	helpGrid.on('edit', function(editor, e) {
	         	var data = helpGrid.getStore().data.items[e.rowIdx].data;
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
	        function editStore() {
				var record = helpGrid.getSelectionModel().getSelection();
				editForm.getForm().reset();
				editForm.getForm().load({
			    	url: 'processrecord/loadBean.do',
				    params: {id: record[0].data.id},
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", action.result.errorMessage);
				    }
				});
				win.show();
			}
	         var editForm = Ext.create('Ext.form.FormPanel',{
		    	frame: true,
            	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
	            items: [{xtype:"hiddenfield",name: "id"},
	                    Ext.create('Ext.ux.datetime.DateTimeField', {
							labelAlign: 'right',
							labelWidth: 80,
							width:300,
							fieldLabel : "办理时间",
							format: 'Y-m-d H:i:s',
							name : "sys_date"
						}),
	                    {name: "agent", fieldLabel: '办理人', labelAlign: 'right', allowBlank:true, xtype : "textfield"},
	                    {name: "handlestatus", fieldLabel: '办理方式', labelAlign: 'right', allowBlank:false, xtype : "textfield"},
	                    {name: "handlecontent", fieldLabel: '办理内容', labelAlign: 'right', allowBlank:true, xtype : "textfield"},
	                    {name: "name", fieldLabel: '姓名', labelAlign: 'right', allowBlank:true, xtype : "textfield"},
	                    {name: "approvalOp", fieldLabel: '批示内容', labelAlign: 'right', allowBlank:true, xtype : "textarea",row:5},
	                    {name: "jishouren", fieldLabel: '接收人', labelAlign: 'right', allowBlank:true, xtype : "textfield"},
	                    {name: "psfk", fieldLabel: '批示反馈', labelAlign: 'right', allowBlank:true, xtype : "textfield"}
	                   ],
		        buttons: [{
		            text: '保存',
		            formBind:true,
		            monitorValid:true,
		            handler: function() {
		            	editForm.getForm().submit({
							waitTitle:"请稍候",
                      		waitMsg:"正在提交表单数据，请稍候",  
                      		url: "processrecord/update.do",
							method : "POST", 
					        success: function(form, action) { 
					        	helpStore.loadPage(1);
					        	editForm.getForm().reset();
					           	win.close();
					       	},  
					      	failure: function(form, action) {  
					        	Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
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
		        height: 400,  
		        width: 400,
		        closeAction:'hide',
		        border: 0,   
		        frame: false, 
		        items: editForm
		    });
	   		var typeName = 'Wdbl.form.'+ WDBL$Globals.DocTypeEditForms[me.type];
	   		var centerForm = Ext.create(typeName, {
	   			region: 'center',
	   			load:true,
	   	        loadUrl : me.loadUrl,
	   	        loadParam : {'id' : me.p_id},
	   	        url : me.url,
	   	     	addType:'newEdit',
	   	     	barNo:me.barNo,
	   	     	parent:me.parent,
	   	        activeTab: 0
	   		});
	   		Ext.applyIf(me, {
	   			layout: 'border',
	   			items: [centerForm,helpGrid]
	   		});
	   		me.callParent(arguments);
   		}
   	});
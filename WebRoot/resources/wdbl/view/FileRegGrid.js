/**
 * 文件登记列表组件(控件)
 */


Ext.define('Wdbl.view.FileRegGrid',{
	extend:'Ext.grid.Panel',
	alias:'widget.filereglist',
	requires : ['Ext.*',  'Wdbl.utils.*','Wdbl.view.FileRegEditForm'],
	autoHeight : true,
	border : false,
	frame : true,
	loadMask : true,
	columnLines : true,
	modelType : 'advance',
	searchCombo:null,
	searchText:null,
	api : {
		list : null,// 列表
		add : null,// 添加
		load : null,// 加载
		edit : null,// 修改
		del : null
		// 删除
	},
	/**
	 * 双击打开页面
	 */
	$detail : function() {
		var me = this;
		if (this.selModel.hasSelection()) {
			var selected = this.selModel.getSelection();
			var id = selected[0].data.id;
			var title = selected[0].data.title;
			var docType = selected[0].data.docType;
			var barNo = selected[0].data.barNo;
			var formTypeName = 'Wdbl.form.' + WDBL$Globals.DocTypeEditForms[docType];
			if (selected.length == 1 && id != null) {
				var currTab = Ext.getCmp("reg_" + id);
				if(currTab){
					me.ownerCt.setActiveTab(currTab);
					return;
				}
				var ffsz = "0";
				if(formTypeName == "Wdbl.form.WideEditForm") {
					ffsz = "1";
				}
				me.ownerCt.add(Ext.create('Ext.panel.Panel',{
					id: "reg_" + id,
					tooltip : '编辑:'+title,
					closable: true,
		        	title:'编辑'+title,
					layout: 'border',
				    innerType: '2',
		    		ffszType: ffsz,
				    loadParam : {'id' : id},
			   		items: [Ext.create(formTypeName,{
			   			region:'center',
			   			parent:me.ownerCt,
				        grid:me,
				        load:true,
				        barNo:barNo,
				        loadUrl : me.ownerCt.api.load,
				        loadParam : {'id' : id},
				        addType:'newEdit',
				        autoDestroy:true,
				        url : me.ownerCt.api.edit
			   		})]
				})).show();
			} else {
				Ext.Msg.alert("提醒", "只能查看一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行查看详情操作！");
		}
	},
	$search : function(btn) {
		var me = this;
		this.store.proxy.extraParams={};
		Ext.apply(this.store.proxy.extraParams, {
			docType : me.searchCombo.getValue(),
			title : me.searchText.getValue()
		});
		this.store.loadPage(1);
		me.searchCombo.setValue("");
		me.searchText.setValue("");
	},
	//导出
	$printExcept : function() {
		var jsonData = Ext.encode(Ext.pluck(this.getStore().data.items, 'data'));
		var ids = [];
		for(var i =0;i<this.getStore().data.items.length;i++){
			ids.push(this.getStore().data.items[i].data.id);
		}
		window.open('count/printExcel.controller?ids='+ids);
	},
	
	$getSelectedData : function() {
		if (this.selModel.hasSelection()) {
			var selected = this.selModel.getSelection();
			var id = selected[0].data.id;
			if (selected.length == 1 && id != null) {
				return id;
			} else {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
		}
	},
	initComponent : function() {
		var gridme = this;
		Ext.create('Wdbl.model.FileReg');
		var selModel = Ext.create('Ext.selection.CheckboxModel', {
			singleSelect : false,
			onStoreLoad:function(store, records, successful, eOpts){  
		        var me = this,  
	            length = me.selected.getCount( );  
		          
		        //如果没有选中的记录，则不需要进行任何的操作  
		        if(length===0)return;  
		          
		        //遍历selected并更新其中的记录  
		        me.selected.eachKey(function(key,item){  
		            var model = store.getById(key);  
		              
		            //如果获取到了model就更新，否则从selected中移除  
		            if(model){  
		                me.selected.add(model);//add时会覆盖掉原来的值  
		            }else{  
		                me.selected.removeAtKey(key);  
		            }  
		        })  
		          
		    }  
		});
		var store = Ext.create("Ext.data.Store", {
			model : 'Wdbl.model.FileReg',
			pageSize : 20,
			remoteSort : true,  
			sortInfo : {
				field : 'receiveTime',
				direction : 'desc'
			},
			proxy : {
				type : "ajax",
				url : gridme.api.list,
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : "json",
					root : "rows",
					totalProperty : 'totalrows'
				},
				simpleSortMode : true
			}
		});
		gridme.searchCombo = Ext.create('Ext.form.ComboBox',{
			fieldLabel : "文件类型",
			store : getComboDataStore('wjlx'),
			displayField : 'node',
 			labelWidth: 100,
			valueField : 'code',
		    queryMode: 'local',
		    typeAhead: true,
			emptyText:'请选择',
			width:250,
			listConfig : {
            	maxHeight : 350,
            }
		});
		gridme.searchText = Ext.create('Ext.form.field.Text',{
			labelAlign : 'right',
			emptyText : '输入要查询的名称'
		});
		Ext.applyIf(gridme, {
			title:'已登记列表',
			store : store,
			selModel : selModel,
			columns : [
			{
				header : "条码编号",
				dataIndex : "barNo",
				sortable : true,
				flex : 1
			}, {
				header : "来文单位",
				dataIndex : "formUnit",
				sortable : true,
				flex : 1
			}, {
				header : "文号",
				dataIndex : "docNumber",
				sortable : true,
				flex : 1
			},  {
				header : "文件标题",
				dataIndex : "title",
				sortable : true,
				flex : 3
			}, {
				header : "文件类型",
				dataIndex : "docType",
				renderer : docTypeRenderer,
				sortable : true,
				flex : 1
			},  {
				header : "来文时间",
				dataIndex : "receiveTime",
				//renderer : Ext.util.Format.dateRenderer('Y-m-d,H:i'),
				sortable : true,
				flex : 1
			}, {
				header : "状态",
				dataIndex : "handlestatus",
				sortable : true,
				flex : 1
			}],
			dockedItems: [{
				xtype:'toolbar',
				dock:'top',
				items:[gridme.searchCombo,
				{text:'关键字查询'},
				gridme.searchText,{
					xtype : 'button',
					pressed : true,
					text : '查找',
					iconCls: 'find',
					scope : this,
					handler : gridme.$search
				},    
				{
					xtype : 'button',
					pressed : true,
					text : '导出',
					iconCls: 'chart_bar',
					scope : this,
					handler :gridme.$printExcept
				}]      
			}],
			bbar : Ext.create('Wdbl.utils.PagingToolBar',{store:store}),
			viewConfig : {
	              getRowClass : function(record,rowIndex,rowParams,store){
						if(record.data.sameNumTit == "1"){
					    	return 'x-grid-record-yellow';
						}
	             }
			},
			listeners : {
				celldblclick : gridme.$detail
			}
		});
		gridme.callParent(arguments);
	}
});


/**
 * 文件办结列表
 */
Ext.define('Wdbl.view.FileOverGrid',{ extend:'Ext.grid.Panel', requires : ['Ext.*', 'Wdbl.utils.*', 'Wdbl.view.FileRegEditForm'],
	autoHeight : true,
	border : false,
	frame : true,
	loadMask : true,
	columnLines : true,
	modelType : 'advance',
	searchCombo:null,
	searchText:null,
	api : null,
	/**
	 * 双击打开页面
	 */
	$detail : function() {
		var me = this;
		if (!this.selModel.hasSelection()) {
			Ext.Msg.alert("提示", "没有任何行被选中，无法进行查看详情操作！");
			return;
		}
		var selected = this.selModel.getSelection();
		var id = selected[0].data.id;
		
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提示", "只能查看一条记录！");
			return;
		}
		var title = selected[0].data.title;
		var docType = selected[0].data.docType;
		var barNo = selected[0].data.barNo;
		var formTypeName = 'Wdbl.form.' + WDBL$Globals.DocTypeEditForms[docType];
		var currTab = Ext.getCmp('over_'+id);
		if(currTab){
			me.ownerCt.setActiveTab(currTab);
			return;
		}
		
        me.ownerCt.add(Ext.create('Wdbl.view.FileOverDetail',{
        	tooltip : '编辑:'+title,
        	title:'编辑'+title,
        	id:'over_'+id,
        	closable: true,
	        barNo:barNo,
	        parent:me.ownerCt,
	        grid:me,
	        load:true,
	        p_id : id,
   	 		type : docType
        })).show();
	},
	$search : function(btn) {
		var me = this;
		this.store.proxy.extraParams={};
		Ext.apply(this.store.proxy.extraParams, { docType : me.searchCombo.getValue(), title : me.searchText.getValue() });
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
	initComponent : function() {
		var gridme = this;
		Ext.create('Wdbl.model.FileReg');
		var store = Ext.create("Ext.data.Store", {
			model : 'Wdbl.model.FileReg',
			pageSize : 20,
			remoteSort : true,  
			sortInfo : {field : 'receiveTime',direction : 'desc'},
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
			title:'已办结列表',
			store : store,
			selModel : Ext.create('Ext.selection.CheckboxModel', { 
					singleSelect : false ,
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
			}),
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
			listeners : {
				celldblclick : gridme.$detail
			}
		});
		gridme.callParent(arguments);
	}
});

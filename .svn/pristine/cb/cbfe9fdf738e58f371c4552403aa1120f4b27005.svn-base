Ext.define('Meeting.views.InfoTime', {
	extend : 'Ext.grid.Panel',
	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Time'],
	xtype : 'cell-editing',

	// /构造器传参
	meid : null,

	// title : '录入参会领导信息',
	frame : false,

	initComponent : function() {
		this.cellEditing = new Ext.grid.plugin.CellEditing({clicksToEdit : 1});
		var meStartTime,meEndTime;
				
		this.storeTime = new Ext.data.JsonStore({
					// autoDestroy : true,
					autoLoad : true,
					model : 'Meeting.model.Time',
					proxy : {
						type : 'ajax',
						url : 'meeting/timeList.do',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : "json",
							root : "rows",
							totalProperty : 'totalrows'
						},
						// //传参
						extraParams : {
							meid : this.meid
						}
					},
					sorters : [{
								property : 'common',
								direction : 'ASC'
							}]
				});

		Ext.apply(this, {
					minHeight : 73,
					maxHeight : 165,
					margin : '10 0 10 75',
					frame : false,
					columnLines : true,
					plugins : [this.cellEditing],
					store : this.storeTime,
					listeners:{
						cellclick:function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts ){
							if(record.get('meStartTime')!=null&&record.get('meEndTime')>0)
							meStartTime=new Date(record.get('meStartTime'));
							if(record.get('meEndTime')!=null&&record.get('meEndTime').lenght>0){
								meEndTime=new Date(record.get('meEndTime'));
							}
						}
					},
					columns : [{
								dataIndex : 'id',
								xtype : 'hiddenfield'
							},
							{
								header : '会议开始时间',
								dataIndex : 'meStartTime',
								flex : 2,
								renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
								editor : Ext.create('Ext.ux.datetime.DateTimeField', {
									format : 'Y-m-d H:i',
									allowBlank : false,
									listeners : {
										select : function(field, value, eOpts) {
										},
										expand : function(field, eOpts) {
										},
										focus : function(field, click, eOpts) {
											field.setMinValue(new Date());
											field.expand();
											if(meStartTime!=null){
												field.setValue(meStartTime);
											}
										}
									}
								})
							}, {
								header : '会议结束时间',
								dataIndex : 'meEndTime',
								flex : 2,
								renderer : Ext.util.Format.dateRenderer('Y-m-d H:i'),
								editor : Ext.create('Ext.ux.datetime.DateTimeField', {
									xtype : 'datefield',
									format : 'Y-m-d H:i',
									allowBlank : false,
									listeners : {
										select : function(field, value, eOpts) {
										},
										focus : function(field, click, eOpts) {
											field.setMinValue(meStartTime);
											field.expand();
											if(meEndTime instanceof Date){
												field.setValue(meEndTime);
											}else{
												field.setMinValue(new Date());
											}
											
										}
									}
								})
							}, {
								header : '开会地址',
								dataIndex : 'meAddress',
								flex : 3,
								editor : {
									allowBlank : true
								}
							}, {
								xtype : 'actioncolumn',
								width : 30,
								sortable : false,
								menuDisabled : true,
								items : [{
											iconCls : 'delete',
											tooltip : '删除该人员信息',
											scope : this,
											handler : this.onRemoveClick
										}]
							}],
					selModel : {
						selType : 'cellmodel'
					},
					tbar : [{
								text : '添加会议时间地址',
								xtype : 'button',
								pressed : true,
								scope : this,
								handler : this.onAddClick
							}]
				});

		this.callParent();

	},

	onAddClick : function() {
		// Create a model instance
		var rec = Ext.create('Meeting.model.Time', {
					id : null
				});
		this.getStore().insert(0, rec);
		this.cellEditing.startEditByPosition({
					row : 0,
					column : 0
				});
	},

	onRemoveClick : function(grid, rowIndex) {
		this.getStore().removeAt(rowIndex);
	}
})
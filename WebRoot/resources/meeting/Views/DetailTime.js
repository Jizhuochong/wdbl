Ext.define('Meeting.views.DetailTime', {
	extend : 'Ext.grid.Panel',

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Time'],
	// /构造器传参
	meid:null,

	frame : false,

	initComponent : function() {
		this.storeTime = new Ext.data.JsonStore({
					autoLoad : false,
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
					autoHeight:true,
					frame:false,
					border:0,
					columnLines : true,
					margin:'0 20 10 20',
					store : this.storeTime,
					columns : [{
								dataIndex : 'id',
								xtype : 'hiddenfield'
							}, {
								header : '会议开始时间',
								dataIndex : 'meStartTime',
								flex : 1,
								renderer: Ext.util.Format.dateRenderer('m/d/Y'),
								editor : {
									xtype : 'datefield',
									format:'m/d/Y',
									allowBlank : true
								}
							}, {
								header : '会议结束时间',
								dataIndex : 'meEndTime',
								flex : 1,
								renderer: Ext.util.Format.dateRenderer('m/d/Y'),
								editor : {
									xtype : 'datefield',
									allowBlank : true
								}
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
											iconCls : 'eye',
											tooltip : '详情',
											scope : this
										}]
							}]
				});

		this.callParent();
	}
})
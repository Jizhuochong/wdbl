Ext.define('Meeting.views.ScheduleDay', {
	extend : 'Ext.grid.Panel',
	requires : ['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.form.*',
			'Meeting.model.Time'],
	day : null,

	initComponent : function() {
		this.storeTime = new Ext.data.JsonStore({
					autoLoad : true,
					model : 'Meeting.model.Time',
					proxy : {
						type : 'ajax',
						url : 'meSchedule/timeList.do',
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
							day : this.day
						}
					},
					sorters : [{
								property : 'common',
								direction : 'ASC'
							}]
				});

		Ext.apply(this, {
					autoHeight : true,
					frame : false,
					columnLines : true,
					store : this.storeTime,
					border:0,
					columns : [{
								dataIndex : 'id',
								xtype : 'hiddenfield'
							}, {
								header : '会议开始时间',
								dataIndex : 'meStartTime',
								flex : 2,
								renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
								editor : {
									xtype : 'datefield',
									format : 'm/d/Y',
									allowBlank : true
								}
							}, {
								header : '会议结束时间',
								dataIndex : 'meEndTime',
								flex : 2,
								renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
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
								flex:1,
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
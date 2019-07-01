Ext.define('Meeting.views.DetailLeader', {
	extend : 'Ext.grid.Panel',

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Leader'],
	meid : null,

	frame : false,

	initComponent : function() {
		var me = this;
		var cid = 'editLeader' + me.meid;
		var tid='tabpaneledit'+me.meid;
		
		me.storeLeader = new Ext.data.JsonStore({
					// autoDestroy : true,
					autoLoad : false,
					model : 'Meeting.model.Leader',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/leaderList.do',
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
							id : this.meid
						}
					},
					sorters : [{
								property : 'common',
								direction : 'ASC'
							}]
				});

		Ext.apply(me, {
					autoHeight : true,
					columnLines : true,
					margin : '0 20 10 20',
					border : 0,
					store : this.storeLeader,
					listeners : {
						itemclick : function(me, record, item, index, e, eOpts) {
							var eid=record.get('id');
							var comp = Ext.getCmp(cid);
							if (comp != null) {
								Ext.getCmp(tid).setActiveTab('tabLeader'+me.meid);
								
								comp.getForm().findField("id")
										.setValue(record.get('id'));

								comp.getForm().load({
									url : 'meetingDetail/loadLeader.do',
									params : {
										id : eid
									},
									success:function(form,action){
											var joinTime=action.result.data.jionTime.split(" ");
											comp.getForm().findField("jionTime").setValue(joinTime[0]);
											comp.getForm().findField("jionTimeHour").setValue(joinTime[1]);
									},
									failure : function(form, action) {
										Ext.Msg.alert("加载失败",
												action.result.msg);
									}
								});
							}
						}
					},
					columns : [{
								dataIndex : 'id',
								xtype : 'hiddenfield'
							}, {
								header : '参会领导',
								dataIndex : 'leaderName',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}, {
								header : '参会时间',
								dataIndex : 'jionTime',
								flex : 2,
								renderer : Ext.util.Format
										.dateRenderer('m/d/Y H:i'),
								editor : {
									xtype : 'datefield'
								}
							}, {
								header : '会议内容',
								dataIndex : 'contents',
								flex : 2,
								editor : {
									allowBlank : false
								}
							}, {
								header : '随行人员',
								dataIndex : 'followPerson',
								flex : 2,
								editor : {
									allowBlank : true
								}
							}, {
								header : '乘车路线',
								dataIndex : 'roadMap',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '车证',
								dataIndex : 'carNum',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '着装',
								dataIndex : 'clothes',
								flex : 1,
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

		me.callParent();

	}
})
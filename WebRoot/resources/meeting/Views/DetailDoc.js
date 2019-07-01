Ext.define('Meeting.views.DetailDoc', {
	extend : 'Ext.grid.Panel',

	requires : ['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.form.*',
			'Meeting.model.Doc'],
	meid : null,

	frame : false,

	initComponent : function() {
		var me = this;
		var cid = 'editDoc' + me.meid;
		var tid = 'tabpaneledit' + me.meid;

		this.storeDoc = new Ext.data.JsonStore({
					autoLoad : false,
					model : 'Meeting.model.Doc',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/docList.do',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : "json",
							root : "rows",
							totalProperty : 'totalrows'
						},
						extraParams : {
							id : this.meid
						}
					},
					sorters : [{
								property : 'common',
								direction : 'ASC'
							}]
				});
				
		Ext.apply(this, {
					autoHeight : true,
					columnLines : true,
					margin : '0 20 10 20',
					border : 0,
					store : this.storeDoc,
					listeners : {
						itemclick : function(me, record, item, index, e, eOpts) {
							var eid = record.get('id');
							var comp = Ext.getCmp(cid);
							if (comp != null) {
								Ext.getCmp(tid)
										.setActiveTab('tabDoc' + me.meid);

								comp.getForm().findField("id").setValue(record
										.get('id'));

								comp.getForm().load({
									url : 'meetingDetail/loadDoc.do',
									params : {
										id : eid
									},
									success : function(form, action) {
									},
									failure : function(form, action) {
										Ext.Msg
												.alert("加载失败",
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
								header : '文档名称',
								dataIndex : 'docName',
								flex : 1
							}, {
								header : 'Url',
								dataIndex : 'docUrl',
								flex : 2
							}, {
								header : '数据中心',
								dataIndex : 'dateCenterId',
								flex : 2
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
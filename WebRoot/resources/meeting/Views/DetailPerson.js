Ext.define('Meeting.views.DetailPerson', {
	extend : 'Ext.grid.Panel',
	
	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Person'],
	///构造器传参
	meid:null,

	frame : false,

	initComponent : function() {
		var me=this;
		var cid = 'editPerson' + me.meid;
		var tid='tabpaneledit'+me.meid;
		
		this.storePerson = new Ext.data.JsonStore({
					// autoDestroy : true,
					autoLoad : false,
					model : 'Meeting.model.Person',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/personList.do',
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

		Ext.apply(this, {
					autoHeight:true,
					border:0,
					columnLines : true,
					margin:'0 20 10 20',
					store : this.storePerson,
					listeners : {
						itemclick : function(me, record, item, index, e, eOpts) {
							var eid=record.get('id');
							var editLeader = Ext.getCmp(cid);
							if (editLeader != null) {
								Ext.getCmp(tid).setActiveTab('tabPerson'+me.meid);
								
								editLeader.getForm().findField("id")
										.setValue(record.get('id'));

								editLeader.getForm().load({
									url : 'meetingDetail/loadPerson.do',
									params : {
										id : eid
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
								header : '姓名',
								dataIndex : 'name',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}, {
								header : '单位',
								dataIndex : 'unit',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}, {
								header : '职务',
								dataIndex : 'job',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '联系电话',
								dataIndex : 'phone',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '家庭住址',
								dataIndex : 'address',
								flex : 2,
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
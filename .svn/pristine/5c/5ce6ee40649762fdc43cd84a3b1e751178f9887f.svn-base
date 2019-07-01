Ext.define('Meeting.views.InfoDetail', {
	extend : 'Ext.panel.Panel',

	// /构造器传参
	detailStore : null,
	divrenderer : null,

	frame : false,

	initComponent : function() {
		var me=this;
		var meid=me.detailStore.id;

		var detailTime = Ext.create('Meeting.views.DetailTime', {
					meid : meid
				});

		var detailLeader = Ext.create('Meeting.views.DetailLeader', {
					meid : meid
				});

		var detailPerson = Ext.create('Meeting.views.DetailPerson', {
					meid : meid
				});

		var detailDoc = Ext.create('Meeting.views.DetailDoc', {
					meid : meid
				});
		var editLeader = Ext.create('Meeting.views.EditLeader', {
					id : null,
					meid : meid,
					comStore : detailLeader.getStore()
				});
		var editPerson = Ext.create('Meeting.views.EditPerson', {
					id : null,
					meid : meid,
					comStore : detailPerson.getStore()
				});
		var editDoc = Ext.create('Meeting.views.EditDoc', {
					id : null,
					meid : meid,
					comStore : detailDoc.getStore()
				});


		Ext.apply(this, {
			frame : false,
			autoHeight : true,
			// autoScroll : true,
			store : this.detailStore,
			renderTo : this.divrenderer,
			layout : 'border',
			items : [{
				region : 'west',
				title : '会议详情',
				split : true,
				flex : 2,
				margins : '0 0 5 5',
				layout : 'accordion',
				layoutConfig : {
					animate : true
				},
				items : [{
							title : '会议基本信息',
							autoScroll : true,
							border : false,
							minHeight : 200,
							iconCls : 'nav',  //TODO:???
							defaults : {
								layout : 'column',
								margin : '10 20 10 20',
								style : {
									fontSize : '12px',
									lineHeight : '20px',
									fontFamily : 'Microsoft Yahei'
								}
							},
							items : [{
										xtype : 'panel',
										border : false,
										html : this.detailStore.title,
										style : {
											fontSize : '16px',
											lineHeight : '18px',
											fontWeight : 'bold',
											textAlign : 'center',
											fontFamily : 'Microsoft Yahei'
										}
									}, {
										xtype : 'label',
										text : this.detailStore.oprUser
									}, {
										xtype : 'label',
										text : this.detailStore.updateTime
									}, {
										xtype : 'label',
										text : this.detailStore.typeJuge
									}, {
										xtype : 'label',
										text : this.detailStore.meetStatu
									}, {
										xtype : 'panel',
										border : false,
										html : this.detailStore.content,
										style : {
											textIndent : '2em',
											fontFamily : 'Microsoft Yahei',
											lineHeight : '20px'
										}
									}, {
										xtype : 'label',
										text : '联系人：'
												+ this.detailStore.contacter
									}, {
										xtype : 'label',
										text : '电话：'
												+ this.detailStore.phonenum
									}, {
										xtype : 'label',
										text : '承办单位：'
												+ this.detailStore.undertakeUnit
									}, {
										xtype : 'panel',
										border : false,
										html : '<b>备注：</b>'
												+ this.detailStore.remark,
										style : {
											fontFamily : 'Microsoft Yahei'
										}
									}

							]
						}, {
							title : '会议时间信息',
							border : false,
							autoScroll : true,
							iconCls : 'settings',
							items : [detailTime],
							listeners : {
								'expand' : function(p, eOpts) {
									detailTime.storeTime.load();
								}
							}
						}, {
							title : '与会领导信息',
							border : false,
							autoScroll : true,
							iconCls : 'settings',
							items : [detailLeader],
							listeners : {
								'expand' : function(p, eOpts) {
									Ext.getCmp('tabpaneledit'+meid).setActiveTab('tabLeader'+meid);
									detailLeader.getStore().load();
								}
							}
						}, {
							title : '与会人员信息',
							border : false,
							autoScroll : true,
							iconCls : 'settings', //TODO:需确认
							items : [detailPerson],
							listeners : {
								'expand' : function(p, eOpts) {
									Ext.getCmp('tabpaneledit'+meid).setActiveTab('tabPerson'+meid);
									detailPerson.getStore().load();
								}
							}
						}, {
							title : '会议文档信息',
							border : false,
							autoScroll : true,
							iconCls : 'settings',
							items : [detailDoc],
							listeners : {
								'expand' : function(p, eOpts) {
									Ext.getCmp('tabpaneledit'+meid).setActiveTab('tabDoc'+meid);
									detailDoc.getStore().load();
								}
							}
						}]
			}, {
				xtype : 'tabpanel',
				region : 'center',
				title : '信息录入/编辑',
				id:'tabpaneledit'+meid,
				flex : 1,
				autoHeight : true,
				split : true,
				margins : '0 5 0 0',
				activeTab : 0,
				tabPosition : 'bottom',
				items : [{
							title : '与会领导',
							id:'tabLeader'+meid,
							autoScroll : true,
							items : [editLeader]
						}, {
							title : '与会人员',
							id:'tabPerson'+meid,
							autoScroll : true,
							items : [editPerson]
						}, {
							title : '会议文档',
							id:'tabDoc'+meid,
							autoScroll : true,
							items : [editDoc]
						}]
			}]
		});
		this.callParent();
	}
})
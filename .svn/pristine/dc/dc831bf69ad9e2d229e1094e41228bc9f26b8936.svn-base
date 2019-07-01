Ext.define('Meeting.views.EditLeader', {
	extend : 'Ext.form.FormPanel',

	requires : ['Ext.data.*', 'Ext.util.*', 'Ext.form.*',
			'Meeting.model.Leader'],

	// /构造器传参
	id : null,
	meid : null,
	comStore : null,

	frame : false,

	initComponent : function() {
		var me = this;
		var meetingid = me.meid;
		var cid = 'editLeader' + me.meid;

		Ext.apply(this, {
			// autoHeight : true,
			id : cid,
			frame : false,
			border : false,
			title : '新增/编辑与会领导',
			columnLines : true,
			defaults : {
				xtype : "textfield",
				labelWidth : 70,
				anchor : '100%',
				fontSize : '12px',
				lineHeight : '20px',
				margin : '10 10 10 10',
				allowBlank : false
			},
			margin : '10 10 10 10',
			items : [{
						name : 'id',
						xtype : 'hiddenfield'
					}, {
						name : 'meid',
						xtype : 'hiddenfield'
					}, {
						name : 'leaderName',
						fieldLabel : '姓名',
						marginTop : 30
					}, {
						xtype : 'container',
						layout : {
							type : 'hbox'
						},
						fontFamily : 'Microsoft Yahei',
						items : [{
									xtype : 'datefield',
									name : 'jionTime',
									fieldLabel : '参会时间',
									labelWidth : 70,
									flex : 2,
									format : 'm/d/Y',
									allowBlank : false
								}, {
									xtype : 'timefield',
									name : 'jionTimeHour',
									minValue : '6:00 上午',
									maxValue : '11:00 下午',
									format : 'H:i',
									flex : 1,
									allowBlank : false
								}]
					}, {
						name : 'contents',
						fieldLabel : '会议内容',
						xtype : 'textareafield',
						allowBlank : true
					}, {
						name : 'followPerson',
						fieldLabel : '随行人员',
						xtype : 'textareafield',
						rows : 2,
						allowBlank : true
					}, {
						name : 'roadMap',
						fieldLabel : '乘车路线',
						xtype : 'textareafield',
						rows : 2,
						allowBlank : true
					}, {
						name : 'carNum',
						fieldLabel : '车证'
					}, {
						fieldLabel : '着装',
						name : 'clothes',
						allowBlank : true
					}],
			buttons : [{
						text : '清空',
						iconCls : 'add',
						tooltip : '清空以添加与会领导',
						handler : function() {
							this.up('form').getForm().reset();
						}
					}, {
						text : '重置',
						iconCls : 'refresh',
						handler : function() {
							var comp=this.up('form').getForm();
							var id = comp.findField('id')
									.getValue();
							if (id != null && id > 0) {
								comp.load({
									url : 'meetingDetail/loadLeader.do',
									params : {
										id : id
									},
									success : function(form, action) {
										var joinTime = action.result.data.jionTime.split(" ");
										comp.findField("jionTime").setValue(joinTime[0]);
										comp.findField("jionTimeHour").setValue(joinTime[1]);
									},
									failure : function(form, action) {
										Ext.Msg
												.alert("加载失败",
														action.result.msg);
									}
								});
							} else {
								comp.reset();
							}
						}
					}, {
						text : '保存',
						iconCls : 'disk',
						formBind : true,
						monitorValid : true,
						handler : function() {
							this.up('form').getForm().submit({
										waitTitle : "请稍候",
										waitMsg : "正在提交表单数据，请稍候",
										url : 'meetingDetail/leaderSave.do',
										method : "POST",
										success : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
											me.comStore.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
										},
										params : {
											meetingid : meetingid
										}
									});
						}
					}]
		});
		this.callParent();

		if (this.id != null && this.id > 0) {
			this.getForm().load({
						url : 'meetingDetail/loadLeader.do',
						params : {
							id : this.id
						},
						failure : function(form, action) {
							Ext.Msg.alert("加载失败", action.result.errorMessage);
						}
					});
		}
	}
})
Ext.define('Meeting.views.EditTime', {
	extend : 'Ext.form.FormPanel',

	requires : ['Ext.data.*', 'Ext.util.*', 'Ext.form.*', 'Meeting.model.Time'],

	// /构造器传参
	tid : null,
	meid:null,

	frame : false,

	initComponent : function() {
		var meetingid=this.meid;
		Ext.apply(this, {
//					autoHeight : true,
					frame : false,
					border : false,
					title : '新增/编辑会议时间',
					columnLines : true,
					defaults : {
						xtype : "textfield",
						labelWidth : 70,
						anchor : '100%',
						fontSize : '12px',
						lineHeight : '20px',
						margin : '10 10 10 10'
					},
					margin : '10 10 10 10',
					items : [{
								name : 'id',
								xtype : 'hiddenfield'
							}, {
								xtype : 'container',
								layout : {
									type : 'hbox'
								},
								fontFamily : 'Microsoft Yahei',
								items : [{
											xtype : 'datefield',
											name : 'meStartTime',
											fieldLabel : '会议开始时间',
											flex : 2,
											allowBlank : true
										}, {
											xtype : 'timefield',
											name : 'meStartTime',
											flex : 1,
											minValue : '6:00 AM',
											maxValue : '11:00 PM',
											allowBlank : true
										}]
							}, {
								xtype : 'container',
								layout : {
									type : 'hbox'
								},
								fontFamily : 'Microsoft Yahei',
								items : [{
											xtype : 'datefield',
											name : 'meEndTime',
											fieldLabel : '会议结束时间',
											flex : 2,
											labelWidth : 70,
											allowBlank : true
										}, {
											xtype : 'timefield',
											name : 'meEndTime',
											minValue : '6:00 上午',
											maxValue : '11:00 下午',
											labelWidth : 70,
											flex : 1,
											allowBlank : true
										}]
							}, {
								fieldLabel : '开会地址',
								name : 'meAddress',
								xtype : "textareafield",
								fontFamily : 'Microsoft Yahei',
								allowBlank : false
							}],
					buttons : [{
						text : '保存',
						iconCls : 'disk',
						formBind : true,
						monitorValid : true,
						handler : function() {
							this.up('form').getForm().submit({
										waitTitle : "请稍候",
										waitMsg : "正在提交表单数据，请稍候",
										url : purl,
										method : "POST",
										success : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
											// store_meeting.loadPage(1);
										},
										failure : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
										},
										extraParams:{
											meid:meetingid
										}
									});
						}
					}, {
						text : '重置',
						iconCls : 'refresh',
						handler : function() {
						}
					}]
				});
		this.callParent();

		if (this.tid != null) {
			this.getForm().load({
						url : '',
						params : {
							id : this.tid
						},
						failure : function(form, action) {
							Ext.Msg.alert("加载失败", action.result.errorMessage);
						}
					});
		}
	}
})
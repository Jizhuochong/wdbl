Ext.define('Meeting.views.EditPerson', {
	extend : 'Ext.form.FormPanel',

	requires : ['Ext.data.*', 'Ext.util.*', 'Ext.form.*', 'Meeting.model.Person'],

	// /构造器传参
	id : null,
	meid:null,
	comStore:null,
	
	frame : false,

	initComponent : function() {
		var me=this;
		var cid='editPerson'+me.meid;
		Ext.apply(me, {
					frame : false,
					id:cid,
					border : false,
					title : '新增/编辑与会人员',
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
								xtype : 'hiddenfield',
								fontFamily : 'Microsoft Yahei'
							}, {
								fieldLabel : '姓名',
								name : 'name',
								allowBlank : false
							},{
								fieldLabel : '单位',
								name : 'unit'
							},{
								fieldLabel : '职务',
								name : 'job'
							},{
								fieldLabel : '联系电话',
								name : 'phone'
							},{
								fieldLabel : '家庭住址',
								name : 'address',
								xtype:'textareafield'
							}],
					buttons : [{
						text : '清空',
						iconCls : 'add',
						tooltip : '清空以添加新人员',
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
									url : 'meetingDetail/loadPerson.do',
									params : {
										id : id
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
										url : 'meetingDetail/personSave.do',
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
										params:{
											meid:me.meid
										}
									});
						}
					}]
				});
		me.callParent();

		if (this.id != null&&this.id>0) {
			this.getForm().load({
						url : 'meetingDetail/loadPerson.do',
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
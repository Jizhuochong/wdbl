Ext.define('Meeting.views.EditDoc', {
			extend : 'Ext.form.FormPanel',

			requires : ['Ext.data.*', 'Ext.util.*', 'Ext.form.*',
					'Meeting.model.Doc'],

			// /构造器传参
			id : null,
			meid:null,
			comStore:null,

			frame : false,

			initComponent : function() {
				var me=this;
				var meetingid=me.meid;
				var cid='editDoc'+me.meid;
				
				Ext.apply(this, {
							id:cid,
							frame : false,
							border : false,
							title : '新增/编辑与会文档',
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
										name : 'docName',
										fieldLabel : '文档名称',
										marginTop : 30
									}, {
										name : 'docUrl',
										fieldLabel : '文档路径',
										allowBlank : false
									}, {
										name : 'dateCenterId',
										fieldLabel : '数据中心',
										allowBlank : true
									}],
							buttons : [{
						text : '清空',
						tooltip : '清空以添加新文档',
						iconCls : 'add',
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
									url : 'meetingDetail/loadDoc.do',
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
										url : 'meetingDetail/medocSave.do',
										method : "POST",
										success : function(form, action) {
											Ext.Msg.alert("提示",action.result.msg);
											me.comStore.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
										},
										params:{
											meetingid:meetingid
										}
									});
								}
							}]
						});
				this.callParent();
				
			}
		})
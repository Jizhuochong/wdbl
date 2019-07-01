var isSubmit = true;
var modifyWin = Ext.extend(Ext.Window, {
	id:'modify_pwd_win',
	title : '修改密码',
	width : 300,
	autoHeight : true,
	frame : true,
	resizable : false,
	modal : true,
	closeAction : 'hide',
	buttonAlign : 'center',
	createModifyPanel : function() {
		return new Ext.form.FormPanel({
			id:'modify_pwd_fm',
			frame : true,
			defaultType : 'textfield',
			items : [{
				id:'oldPwd',
				fieldLabel : '旧密码',
				name : 'oldPwd',
				inputType : 'password',
				allowBlank : false,
				blankText : '旧密码不能为空',
				listeners : {
					'blur' : function() {// 检查旧密码输入是否正确
						Ext.Ajax.request({
							url : 'access/user/password/check',method : 'POST',
							params: {oldPwd:Ext.getCmp('oldPwd').getValue()},
							success : function(request) {
								var json = Ext.JSON.decode(request.responseText); 
				                if(json.success){isSubmit = true;}
				                else{
				                	Ext.getCmp('oldPwd').markInvalid('输入的旧密码不正确');isSubmit = false;
				                }
							}
						});
					}
				}
			}, {
				fieldLabel : '新密码',
				name : 'newPwd',
				inputType : 'password',
				allowBlank : false,
				blankText : '请输入新密码'
			}, {
				fieldLabel : '确认新密码',
				name : 'reNewPwd',
				allowBlank : false,
				inputType : 'password',
				blankText : '请确认新密码'
			}],
			fbar:{
				xtype:'toolbar'
			}
		});
	},
	modify : function() {
		var form = Ext.getCmp('modify_pwd_fm').form;
		if (isSubmit == false) {return;}
		if (form.isValid()) {
			if (form.findField("newPwd").getValue() != form
					.findField("reNewPwd").getValue()) {
				Ext.Msg.alert('警告', '两次密码输入不一致');
				return;
			}
			form.submit({
				waitTitle : '请稍候',
				waitMsg : '正在保存......',
				url : 'access/user/password/edit',
				method : 'POST',
				success : function(form, action) {
					Ext.Msg.alert('系统消息', action.result.msg, function() {
						Ext.getCmp('modify_pwd_win').close();
					});
				},
				failure : function(form, action) {
					Ext.Msg.alert('错误', action.result.msg);
				}
			});
		}
	},
	initComponent : function() {
		modifyWin.superclass.initComponent.call(this);
		this.fp = this.createModifyPanel();
		this.add(this.fp);
		this.down('toolbar').add({xtype:'button',text:'确定',handler:this.modify});
		this.down('toolbar').add({xtype:'button',text:'重置',handler: function() {
			Ext.getCmp('modify_pwd_fm').form.reset();
		}
		});
	}
});
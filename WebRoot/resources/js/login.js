//定义验证码控件 
Ext.define('CheckCode', {
	extend : 'Ext.form.field.Text',
	alias : 'widget.checkcode',
	inputTyle : 'codefield',
	codeUrl : Ext.BLANK_IMAGE_URL,
	isLoader : true,
	onRender : function(ct, position) {
		this.callParent(arguments);
		this.codeEl = ct.createChild({
			tag : 'img',
			src : Ext.BLANK_IMAGE_URL
		});
		this.codeEl.addCls('x-form-code');
		this.codeEl.on('click', this.loadCodeImg, this);

		if (this.isLoader)
			this.loadCodeImg();
	},
	alignErrorIcon : function() {
		this.errorIcon.alignTo(this.codeEl, 'tl-tr', [ 2, 0 ]);
	},
	// 如果浏览器发现url不变，就认为图片没有改变，就会使用缓存中的图片，而不是重新向服务器请求，所以需要加一个参数，改变url
	loadCodeImg : function() {
		this.codeEl.set({
			src : this.codeUrl + '?id=' + Math.random()
		});
	}
});

var Login = {

	loginFormPanel : null,

	initialize : function() {
		var checkcode = null;
		
		if(allowEmptyValidateCode=='false'){
			checkcode = Ext.create('CheckCode', {
						cls : 'key',
						fieldLabel : '验证码',
						name : 'captcha',
						id : 'CheckCode',
						allowBlank : false,
						isLoader : true,
						style : 'margin-right:5px;float:left;',
						blankText : '验证码不能为空',
						codeUrl : webrootWithoutBackslash
								+ '/security/captcha.jpg',
						height : 25,
						width : 205
					});
		}
		this.states = Ext.create('Ext.data.Store', {
			fields : [{name : "id"}, {name : "username"}],
			autoLoad : true,
        	 proxy : {
				type : "ajax",
				url : 'access/user/alluser',
				actionMethods : {
					read : 'POST'
				},
				reader : {
					type : "json",
					root : "rows"
				}
			}
		});

		this.loginFormPanel = new Ext.FormPanel(
				{
					id : 'loginFormPanel',
					frame : true,
					titleAlign: 'center',
					labelAlign : 'left',
					labelWidth : 64,
					width : 360,
					waitMsgTarget : true,
					layout:'fit',
					title : '欢迎使用文电办理系统',
					renderTo : Ext.get('loginFormPanelContainer'),
					fieldDefaults : {
						// 居左
						labelAlign : 'center',
						// 宽度
						labelWidth : 80,
						// anchor: '90%',
						// 错误提示显示在一边(side)，还可以配置为under、title、none
						msgTarget : 'side'
					},
					// 增加按键事件，回车键提交
					listeners : {
						afterRender : function(thisForm, options) {
							this.keyNav = Ext.create('Ext.util.KeyNav',
									this.el, {
										enter : function() {
											Login.submitAction();
										},
										scope : this
									});
						}
					},
					buttons : [
							{
								text : '登录',
								handler : function() {
									Login.submitAction();
								},
								scope : this
							},
							{
								text : '重置',
								handler : function() {
									this.loginFormPanel.getForm().reset();
								},
								scope : this
							}
						],
					items : [ new Ext.form.FieldSet({
						title : '请登录',
						autoHeight : true,
						defaultType : 'textfield',
						items : [{
							 xtype:"combo",
							 store: this.states,
							 displayField: 'username',
							 valueField: 'username',
							id : 'j_username',
							name : 'j_username',
							fieldLabel : '登录名称',
							emptyText : '请输入您的登录帐号',
							allowBlank : false,
							width : 300
						},{
							inputType : 'password',
							id : 'j_password',
							name : 'j_password',
							emptyText : '请输入您的密码',
							fieldLabel : '登录密码',
							allowBlank : false,
							width : 300
						},
						checkcode
						]
					}) ]
				});
	},

	// 提交表单
	submitAction : function() {
		try {
			this.loginFormPanel.getForm().submit(
					{
						url : webrootWithoutBackslash + '/login_check',
						method : 'POST',
						// timeout set to 1 minute
						params : {
							timestamp : new Date().getTime(),
							j_username : this.loginFormPanel.getForm()
									.findField('j_username').getValue(),
							j_password : this.loginFormPanel.getForm()
									.findField('j_password').getValue()
						},
						// 成功后的响应
						success : function(form, action) {
							window.location.href = webrootWithoutBackslash
									+ '/';
						},
						// 失败后的响应
						failure : function(form, action) {
							// 提示用户操作失败
							Ext.Msg.alert("提示", action.result.error.reason);
						},
						// scope
						scope : this
					});
		} catch (e) {
			alert( //
			String.format( //
			'提交异常:{0}', //
			e.message ? e.message : e //
			)//
			);
		}
	}

};
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';
	Login.initialize();
});

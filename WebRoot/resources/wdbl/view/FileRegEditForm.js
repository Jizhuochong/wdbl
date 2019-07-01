Ext.define('Wdbl.view.FileRegEditForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.fileregedit',

	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label'],
	margin : '6 6 6 6',
	bodyPadding : 5,
	/**
	 * 加载数据
	 * 
	 * @type Boolean
	 */
	load : false,
	/**
	 * Form提交路径
	 * 
	 * @type String
	 */
	url : null,
	/**
	 * 加载数据路径
	 * 
	 * @type String
	 */
	loadUrl : null,
	/**
	 * 加载数据请求参数
	 * 
	 * @type Object
	 */
	loadParam : null,
	/**
	 * 列表
	 * 
	 * @type Ext.grid.Panel
	 */
	grid : null,
	/**
	 * 表单模式:advance：高级,basic:基础模式
	 * @type String
	 */
	modelType : 'advance',
	initComponent : function() {
		var me = this;
		me.fileContainer = Ext.create('Ext.form.FieldContainer', {layout : 'form'});
		Ext.applyIf(me, {
			items : [{
				xtype : "hidden",
				name : "id"
			}, {
				xtype : "fieldset",
				items : [{
					xtype : "fieldcontainer",
					layout : 'column',
					padding : '10 0 0 0',
					items : [{
								columnWidth : 0.5,
								xtype : "textfield",
								fieldLabel : "文号",
								name : "docNumber",
								maxLength : 30
							}, {
								columnWidth : 0.36,
								padding : '0 0 0 5',
								xtype : 'datefield',
								fieldLabel : "来文时间",
								name : "receiveTime",
								format : 'Y-m-d',
								allowBlank : false,
								listeners : {
									render : function(datefield) {
										datefield.setValue(new Date());
									}
								}
							}, {
								columnWidth : 0.14,
								padding : '0 0 0 5',
								xtype : 'timefield',
								name : "receiveTime",
								format : 'H:i',
								increment : 30,
								allowBlank : false,
								listeners : {
									render : function(timefield) {
										timefield.setValue(new Date());
									}
								}
							}]
						}, {
							xtype : "fieldcontainer",
							layout : 'column',
							items : [{
								columnWidth : 0.5,
								xtype : "combo",
								fieldLabel : "密级",
								name : "securityGrade",
								store : getComboDataStore('mj'),
								displayField : 'node',
    							queryMode: 'local',
		    typeAhead: true,
								valueField : 'code',
								mode : 'remote',
								forceSelection : true,
								editable : true,
								allowBlank : false
							}, {
								columnWidth : 0.5,
								padding : '0 0 0 5',
								xtype : "combo",
								fieldLabel : "紧急程度",
								name : "urgencyDegree",
								store : getComboDataStore('jjcd'),
								displayField : 'node',
    							queryMode: 'local',
		    typeAhead: true,
								valueField : 'code',
								mode : 'remote',
								forceSelection : true,
								editable : true,
								allowBlank : false
							}]
						}, {
							xtype : "combo",
							fieldLabel : "来文单位",
							name : "formUnit",
							store : getComboDataStore('lwdw'),
							displayField : 'node',
							queryMode: 'local',
		    typeAhead: true,
							valueField : 'code',
							mode : 'remote',
							forceSelection : true,
							editable : true,
							allowBlank : false,
							anchor : '50%'
						}, {
							xtype : "textarea",
							fieldLabel : "文件标题",
							name : "title",
							maxLength : 100,
							rows : 2,
							allowBlank : false,
							anchor : '100%'
						},{
							xtype : "fieldcontainer",
							layout : 'column',
							items : [{
								columnWidth : 0.5,
								xtype : "numberfield",
								fieldLabel : "份数",
								minValue:1,
								maxValue:999999,
								allowDecimals:false,
								name : "copies"
							},{
								columnWidth : 0.3,
								padding : '0 0 0 5',
								xtype : "numberfield",
								fieldLabel : "序号范围",
								minValue:1,
								maxValue:999999999,
								allowDecimals:false,
								index:0,
								name : "numberRange",
								listeners:{
									change: function(){
										var _minValue= this.minValue;
										var _allowBlank = true;
										if(this.getValue()) {
											_allowBlank = false;
											_minValue= this.getValue();
										}
										Ext.apply(this.nextNode(),{allowBlank:_allowBlank,minValue : _minValue});
										this.validate(); 
									}
								}
							},{
								columnWidth : 0.2,
								padding : '0 0 0 15',
								xtype : "numberfield",
								fieldLabel : "至",
								labelWidth:40,
								minValue:1,
								maxValue:999999999,
								allowDecimals:false,
								index:1,
								name : "numberRange",
								listeners:{
									change: function(){
										var _maxValue=this.maxValue;
										var _allowBlank = true;
										if(this.getValue()){
											_allowBlank = false;
											_maxValue= this.getValue();
										}
										Ext.apply(this.previousNode(),{allowBlank:_allowBlank,maxValue : _maxValue});
										this.validate(); 
									}
								}
							}]
						}]
					}, {
						title : "详细登记信息",
						xtype : "fieldset",
						items : [{
									xtype : "fieldcontainer",
									layout : "column",
									advance:true,
									items : []
								}, {
									xtype : "textarea",
									fieldLabel : "关键字",
									name : "keyword",
									advance:true,
									rows : 2,
									allowBlank : false,
									maxLength:100,
									anchor : '100%'
								}, this.fileContainer, {
									xtype : 'fieldcontainer',
									anchor : '100%',
									layout : {
										type : 'vbox',
										align : 'center'
									},
									items : [{
												xtype : 'button',
												text : '添加更多原文件',
												listeners : {
													click : function() {
														me.addFile();
													}
												}
											}]
								},{
									
									xtype : "fieldcontainer",
									layout : 'column',
									advance:true,
									items : [{
												columnWidth : 0.36,
												xtype : 'datefield',
												fieldLabel : "完成期限",
												name : "deadline",
												format : 'Y-m-d',
												minValue: new Date(),
												listeners:{
													change: function(){
														var _allowBlank;
														if(this.getValue()) _allowBlank= false;
														else _allowBlank= true;
														Ext.apply(this.nextNode(),{allowBlank : _allowBlank});
														this.nextNode().validate(); 
													}
												}
											}, {
												columnWidth : 0.14,
												padding : '0 0 0 5',
												xtype : 'timefield',
												name : "deadline",
												format : 'H:i',
												increment : 30
											}]
								}, {
									xtype : "textarea",
									fieldLabel : "备注",
									name : "remark",
									rows : 2,
									allowBlank : false,
									maxLength: 100,
									anchor : '100%'
								}]
					}],
			buttons : [{
				text : '保存',
				formBind : true,
				monitorValid : true,
				handler : function() {
					me.getForm().submit({
						waitTitle : "请稍候",
						waitMsg : "正在提交表单数据，请稍候",
						url : me.url,
						method : "POST",
						success : function(form, action) {
							Ext.Msg.alert("提示", action.result.msg);
							me.findParentByType('window').close();
							me.grid.getStore().reload();
							me.grid.getSelectionModel()
									.clearSelections();
						},
						failure : function(form, action) {
							Ext.Msg.alert("提示", action.result.msg);
						}
					});
				}
			}, {
				text : '关闭',
				handler : function() {
					this.findParentByType('window').close();
				}
			}]
		});

		me.callParent(arguments);
		if(me.modelType=='basic'){
			Ext.each(Ext.ComponentQuery.query('component[advance="true"]'),function(n){
				n.hide().setDisabled(true);
			});
		}
		if (me.load) {// 修改
			me.getForm().load({
				url : me.loadUrl,
				params : me.loadParam,
				method : "POST",
				failure : function(form, action) {
					Ext.Msg.alert("加载失败", action.result.errorMessage);
				},
				success : function(form, action) {
					var receiveTime = action.result.data.receiveTime;
					var numberRange = action.result.data.numberRange;
					var deadline = action.result.data.deadline;
					me.getForm().getFields().each(function(f) {
								if (f.getName() == 'receiveTime') {
									if (f.xtype == 'datefield') {
										f.setValue(receiveTime.split(',')[0]);
									}
									if (f.xtype == 'timefield') {
										f.setValue(receiveTime.split(',')[1]);
									}
								}
								if (deadline &&f.getName() == 'deadline') {
									if (f.xtype == 'datefield') {
										f.setMinValue(deadline.split(',')[0]);
										f.setValue(deadline.split(',')[0]);
									}
									if (f.xtype == 'timefield') {
										f.setValue(deadline.split(',')[1]);
									}
								}
								if (numberRange && f.getName() == 'numberRange' && f.xtype == 'numberfield') {
									if (numberRange.indexOf(',')!=-1) {
										f.setValue(numberRange.split(',')[f.index]);
									}else{
										f.setValue(numberRange);
									}
								}
							});
					Ext.each(action.result.data.originalFiles, function(n, i) {
								me.fileContainer.add(Ext.create(
										'Ext.form.FieldContainer', {
											layout : "column",
											items : [{
														xtype : "hidden",
														name : "fileIds",
														value : n.id
													}, {
														columnWidth : 0.94,
														xtype : 'textfield',
														padding : '0 5 0 0',
														fieldLabel : '原文件',
														labelWidth: 60,
														readOnly : true,
														name : 'prevFileNames',
														value : n.fileName
													}, {
														columnWidth : 0.06,
														xtype : 'button',
														text : '移除',
														scope : me,
														handler : me.removeFile
													}]
										}));
							});
				}

			});
		} else {// 添加
			me.addFile();
		}
	},
	/**
	 * 添加原文件
	 */
	addFile : function() {
		var currentSize = Ext.query('input[name="files"]').length + Ext.query('input[name="prevFileNames"]').length;
//		if (WDBL$Globals.MAX_ALLOWED_UPLOAD_FILE_SIZE > currentSize) {
//			this.fileContainer.add(Ext.create('Ext.form.FieldContainer', {
//						layout : "column",
//						items : [{
//									columnWidth : 0.94,
//									xtype : 'filefield',
//									name : "files",
//									padding : '0 5 0 0',
//									fieldLabel : '原文件',
//									buttonText : '浏览'
//								}, {
//									columnWidth : 0.06,
//									xtype : 'button',
//									text : '移除',
//									scope : this,
//									handler : this.removeFile
//								}]
//					}));
//		} else {
//			Ext.Msg.alert("操作提示", '最多允许上传'
//							+ WDBL$Globals.MAX_ALLOWED_UPLOAD_FILE_SIZE
//							+ '个原文件');
//		}
	},
	/**
	 * 移除原文件
	 * @param {} btn
	 */
	removeFile : function(btn) {
		this.fileContainer.remove(btn.findParentByType('fieldcontainer'));
	}

});
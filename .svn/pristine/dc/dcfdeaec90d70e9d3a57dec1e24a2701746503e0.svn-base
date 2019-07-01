Ext.define('Wdbl.view.FileRegDetailForm', {
			extend : 'Ext.form.Panel',

			requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer',
					'Ext.form.field.Display', 'Ext.form.Label'],
			margin : '6 6 6 6',
			width : '',
			bodyPadding : 10,
			title : '',
			record : '',
			/**
			 * 表单模式:advance：高级,basic:基础模式
			 * @type String
			 */
			modelType : 'advance',
			initComponent : function() {
				var me = this;
				me.fileContainer = Ext.create('Ext.form.FieldContainer', {
							layout : 'column',
							fieldLabel : ''
						});

				Ext.applyIf(me, {
							items : [{
								xtype : 'fieldset',
								collapsible : true,
								title : '基本信息',
								items : [{
											xtype : 'fieldcontainer',
											layout : 'column',
											fieldLabel : '',
											items : [{
														xtype : 'displayfield',
														columnWidth : 0.5,
														fieldLabel : '流水号',
														name : "sn"
													}, {
														xtype : 'displayfield',
														columnWidth : 0.5,
														padding : '0 0 0 5',
														fieldLabel : '文件号',
														name : "docNumber"
													}]
										}, {
											xtype : 'fieldcontainer',
											layout : 'column',
											fieldLabel : '',
											items : [{
												xtype : 'displayfield',
												columnWidth : 0.5,
												fieldLabel : '来文时间',
												renderer : Ext.util.Format
														.dateRenderer('Y-m-d H:i'),
												name : "receiveTime"
											}, {
												xtype : 'displayfield',
												columnWidth : 0.5,
												padding : '0 0 0 5',
												fieldLabel : '来文单位',
												name : "formUnit"
											}]
										}, {
											xtype : 'fieldcontainer',
											layout : 'column',
											fieldLabel : '',
											items : [{
														xtype : 'displayfield',
														columnWidth : 0.5,
														fieldLabel : '密级',
														name : "securityGrade"
													}, {
														xtype : 'displayfield',
														columnWidth : 0.5,
														padding : '0 0 0 5',
														fieldLabel : '紧急程度',
														name : "urgencyDegree"
													}]
										}, {
											xtype : 'displayfield',
											anchor : '100%',
											fieldLabel : '文件标题',
											name : "title"
										}, {
											xtype : "fieldcontainer",
											layout : 'column',
											items : [{
														columnWidth : 0.5,
														xtype : "displayfield",
														fieldLabel : "份数",
														name : "copies",
														renderer : emptyRenderer
													}, {
														columnWidth : 0.5,
														padding : '0 0 0 5',
														xtype : "displayfield",
														fieldLabel : "序号范围",
														name : "numberRange",
														renderer:numberRangeRenderer
													}]
										}]
							}, {
								xtype : 'fieldset',
								anchor : '100%',
								collapsible : true,
								title : '详细登记信息',
								items : [{
											xtype : 'fieldcontainer',
											layout : 'column',
											advance:true,
											items : []
										}, {
											xtype : 'displayfield',
											anchor : '100%',
											advance:true,
											fieldLabel : '关键字',
											name : "keyword"
										}, this.fileContainer, {
											xtype : 'fieldcontainer',
											layout : 'column',
											items : [{
												xtype : 'displayfield',
												columnWidth : 0.5,
												fieldLabel : '登记时间',
												renderer : Ext.util.Format
														.dateRenderer('Y-m-d H:i:s'),
												name : 'registerTime'
											}, {
												xtype : 'displayfield',
												columnWidth : 0.5,
												padding : '0 0 0 5',
												fieldLabel : '登记人',
												name : 'registrar'
											}]
										},{
											xtype : 'displayfield',
											anchor : '50%',
											advance:true,
											fieldLabel : '完成期限',
											renderer : Ext.util.Format
													.dateRenderer('Y-m-d H:i'),
											name : "deadline"
										}, {
											xtype : 'displayfield',
											anchor : '100%',
											fieldLabel : '备注',
											name : "remark"
										}]
							}]
						});

				me.callParent(arguments);
				if(me.modelType=='basic'){
					Ext.each(Ext.ComponentQuery.query('component[advance="true"]'),function(n){
						n.hide();
					});
				}
				if (me.record) {
					me.loadRecord(me.record);
					Ext.each(me.record.raw.originalFiles, function(n, i) {
								me.fileContainer.add(Ext.create(
										'Ext.form.field.Display', {
											anchor : '100%',
											fieldLabel : '原文件',
											name : 'filePath',
											value : n.filePath
										}));
							});
				}
			}

		});
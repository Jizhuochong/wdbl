function replaceValue(value) {
	var reg=new RegExp("\n", "g");
		if(value!=null)
			return value.replace(reg,"<br>");
}

Ext.onReady(function() {
	requires : ['Meeting.views.InfoTime'],
	Ext.QuickTips.init();

	var store_meeting = Ext.create("Ext.data.JsonStore", {
				fields : [{
							name : "id"
						}, {
							name : "title"
						}, {
							name : "content"
						}, {
							name : "undertakeUnit"
						}, {
							name : "remark"
						}, {
							name : "contacter"
						}, {
							name : "phonenum"
						}, {
							name : "typeJuge"
						}, {
							name : "meetStatu"
						}, {
							name : "forwardUnit"
						}, {
							name : "oprUser"
						}, {
							name : "updateTime"
						}, {
							name : "isDeleted"
						}, {
							name : "oprstatus"
						}, {
							name : "timeList"
						}],
				pageSize : 20,
				proxy : {
					type : "ajax",
					url : "meeting/list.do",
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : "json",
						root : "rows",
						totalProperty : 'totalrows'
					},
					simpleSortMode : true
				}
			});

	var s_meeting_title = Ext.create('Ext.form.TextField', {
				fieldLabel : '会议名称',
				labelAlign : 'right',
				labelWidth : 60,
				emptyText : '输入查询会议标题',
				width : 200
			});
	var s_meeting_id = Ext.create('Ext.form.TextField', {
				fieldLabel : '会议内容',
				labelAlign : 'right',
				labelWidth : 40,
				emptyText : '输入查询会议内容',
				width : 200,
				minValue : 0
			});

	var addbu = Ext.create('Ext.Button', {
				pressed : true,
				text : '添加会议信息',
				iconCls : 'add',
				handler : add
			});
	var editbu = Ext.create('Ext.Button', {
				pressed : true,
				text : '编辑会议信息',
				iconCls : 'pencil',
				handler : edit
			});
	var delbu = Ext.create('Ext.Button', {
				pressed : true,
				text : '删除会议信息',
				iconCls : 'delete',
				handler : del
			});
	var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				iconCls : 'find',
				text : '搜索',
				handler : function() {
					store_meeting.loadPage(1);
				}
			});

	store_meeting.on('beforeload', function(store1, options) {
				var new_params = {
					title : s_meeting_title.getValue(),
					property : 'id',
					direction : 'desc'
				};
				Ext.apply(store1.proxy.extraParams, new_params);
			});
	store_meeting.loadPage(1);

	var toolbar = Ext.create('Ext.PagingToolbar', {
				store : store_meeting,
				beforePageText : "第 ",
				afterPageText : "页，共 {0} 页",
				firstText : "首页",
				prevText : "上一页",
				lastText : "末页",
				nextText : "下一页",
				refreshText : "刷新",
				emptyMsg : "没有要显示的数据",
				displayInfo : true,
				displayMsg : "<span style='font-size:13px;'>第{0}-{1}条,共{2}条</span>",
				emptyMsg : "没有数据"
			});

	var selModel = Ext.create('Ext.selection.CheckboxModel');
	var gridpanel_meeting = Ext.create("Ext.grid.Panel", {
				selModel : selModel,
				layout : 'fit',
				autoHeight : true,
				title : "会议信息录入",
				renderTo : "meetinglist",
				store : store_meeting,
				loadMask : true,
				columnLines : true,
				forceFit : true,
				columns : [Ext.create('Ext.grid.RowNumberer', {
									text : '',
									width : 30
								}), {
							header : "会议标题",
							dataIndex : "title",
							sortable : true,
							renderer : renderTopic,
							flex : 15
						}, {
							header : "承办单位",
							dataIndex : "undertakeUnit",
							sortable : true,
							flex : 5
						}, {
							header : "联系人",
							dataIndex : "contacter",
							sortable : true,
							flex : 5
						}, {
							header : "联系电话",
							dataIndex : "phonenum",
							sortable : true,
							flex : 5
						}, {
							header : "会议状态",
							dataIndex : "meetStatu",
							sortable : true,
							flex : 5,
							renderer : function(v) {
								if (v == 1) {
									return '<span style="color:green">拟办中</span>'
								}
								if (v == 2) {
									return '<span style="color:blue">已确定</span>'
								}
								if (v == 3) {
									return '<span style="color:red">已取消</span>'
								}
							}
						}, {
							header : "处理操作",
							dataIndex : "typeJuge",
							sortable : true,
							flex : 5,
							renderer : function(v) {
								if (v == 1) {
									return '<span style="color:blue">报送领导</span>'
								}
								if (v == 2) {
									return '<span style="color:green">转走</span>'
								}
							}
						}, {
							header : "更新时间",
							dataIndex : "updateTime",
							sortable : true,
							renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
							flex : 6
						}],
				plugins : [{
					ptype : 'rowexpander',
					rowBodyTpl : new Ext.XTemplate('<p class="meeting-rowexpander"><b>内容:</b>'+replaceValue('{content}')+'</p>'
							+ '<p class="meeting-rowexpander"><b>备注:</b>'+replaceValue('{remark}')+' </p>')
				}],
				collapsible : true,
				enableLocking : true,
				tbar : [addbu, editbu, delbu, {
							xtype : "tbfill"
						}, s_meeting_title, searchebu],
				bbar : toolbar
			});
	gridpanel_meeting.setHeight(document.getElementById("meetinglist").offsetHeight);

	function code_createForm(purl, load, id, str) {

		var grid_time = Ext.create('Meeting.views.InfoTime',{
			meid:id
		});
		var meStore = grid_time.storeTime;
		
		//Ext.create('MyForm.DateTimeField',{});		
		
		var form_meeting = Ext.create('Ext.form.FormPanel', {
			frame : true,
			margins : '6 6 6 6',
			layout : 'anchor',
			defaults : {
				xtype : "textfield",
				labelWidth : 70,
				anchor : '100%'
			},
			items : [{
						xtype : 'hidden',
						name : 'id'
					}, {
						xtype : "hiddenfield",
						name : "updateRecords"
					}, {
						xtype : "hiddenfield",
						name : "removeRecords"
					}, {
						name : "title",
						fieldLabel : '会议标题',
						allowBlank : false
					},  {
						xtype : 'textareafield',
						name : "content",
						fieldLabel : '会议内容',
						maxLength : 4000,
						maxLengthText : '内容最大值为4000',
						rows : 7,
						allowBlank : false
					},  grid_time, {
						xtype : 'fieldcontainer',
						layout : 'hbox',
						defaults : {
							labelWidth : 70,
							anchor : '100%'
						},
						items : [{
									xtype : 'textfield',
									flex : 1,
									name : "contacter",
									fieldLabel : '联系人',
									allowBlank : false
								}, {
									xtype : 'text',
									flex : 0.2
								}, {
									xtype : 'textfield',
									name : "phonenum",
									fieldLabel : '联系电话',
									allowBlank : false,
									flex : 1
								}, {
									xtype : 'text',
									flex : 0.2
								}, {
									xtype : 'combobox',
									fieldLabel : '承办单位',
									displayField : 'orgname',
									name : "undertakeUnit",
									flex : 1,
									store : new Ext.data.JsonStore({
												autoLoad : true,
												fields : [{
															name : "orgname"
														}],
												proxy : {
													type : "ajax",
													url : "meeting/orgList.do",
													actionMethods : {
														read : 'POST'
													},
													reader : {
														type : "json",
														root : "rows",
														totalProperty : 'totalrows'
													},
													simpleSortMode : true
												}
											}),
									queryMode : 'local',
									typeAhead : true
								}]
					}, {
						xtype : 'fieldcontainer',
						layout : 'hbox',
						defaults : {
							labelWidth : 70,
							anchor : '100%'
						},
						items : [{
									flex : 1.1,
									xtype : 'radiogroup',
									fieldLabel : '处理类型',
									vertical : true,
									items : [{
												boxLabel : '报送领导',
												name : 'typeJuge',
												inputValue : '1',
												checked : true
											}, {
												boxLabel : '转走',
												name : 'typeJuge',
												inputValue : '2'
											}]
								},  {
									flex : 1,
									xtype : 'radiogroup',
									fieldLabel : '会议状态',
									vertical : true,
									items : [{
												boxLabel : '拟办中',
												name : 'meetStatu',
												inputValue : '1'
											}, {
												boxLabel : '已确定',
												name : 'meetStatu',
												inputValue : '2',
												checked : true
											}]
								}, {
									flex : 1,
									xtype : 'text'
								}]
					}, {
						xtype : 'textareafield',
						name : "remark",
						fieldLabel : '备注',
						rows : 2
					}],
			buttons : [{
				text : '保存',
				formBind : true,
				iconCls : 'disk',
				monitorValid : true,
				handler : function() {

					var updatedes = meStore.getModifiedRecords();// 获取所有修改过的记录
					var removedes = meStore.getRemovedRecords();// 获取所有删除的记录

					var updateRecords = new Array();
					var removeRecords = new Array();
					for (i = 0; i < updatedes.length; i++) {
						updateRecords.push(updatedes[i].data);
						updateRecords[i].meStartTime=Ext.util.Format.date(updateRecords[i].meStartTime, 'm/d/Y H:i');
						updateRecords[i].meEndTime=Ext.util.Format.date(updateRecords[i].meEndTime, 'm/d/Y H:i');
					}

					if (removedes != null) {
						for (i = 0; i < removedes.length; i++) {
							removeRecords.push(removedes[i].data);
						}
					}

					form_meeting.getForm().findField("updateRecords").setValue(Ext.JSON.encode(updateRecords));
					form_meeting.getForm().findField("removeRecords").setValue(Ext.JSON.encode(removeRecords));

					form_meeting.getForm().submit({
								waitTitle : "请稍候",
								waitMsg : "正在提交表单数据，请稍候",
								url : purl,
								method : "POST",
								success : function(form, action) {
									Ext.Msg.alert("提示", action.result.msg);
									store_meeting.loadPage(1);
									win.close();
								},
								failure : function(form, action) {
									Ext.Msg.alert("提示", action.result.msg);
								}
							});
				}
			}, {
				text : '关闭',
				iconCls : 'close',
				handler : function() {
					win.close();
				}
			}]
		});

		var win = Ext.create('Ext.window.Window', {
					title : str,
					layout : 'fit',
					modal : true,
					plain : true,
					width : 735,
					border : 0,
					frame : false,
					items : form_meeting
				}).show();

		if (load == true, id != null) {
			form_meeting.getForm().load({
						url : 'meeting/eidtLoad.do',
						params : {
							id : id
						},
						failure : function(form, action) {
							Ext.Msg.alert("加载失败", action.result.errorMessage);
						}
					});
		}

	}
	// 增加
	function add() {
		code_createForm('meeting/addSave.do', false, null, '添加会议信息');
	}
	// 编辑
	function edit() {
		var selModel = gridpanel_meeting.getSelectionModel();
		if (selModel.hasSelection()) {
			var selected = selModel.getSelection();
			if (selected.length == 1 && selected[0].data.id != null) {
				code_createForm('meeting/editSave.do', true,
						selected[0].data.id, '编辑会议信息');
			} else {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
		}
	}

	// 删除
	function del() {
		var selModel = gridpanel_meeting.getSelectionModel();
		if (selModel.hasSelection()) {
			Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
						if (button == "yes") {
							var selected = selModel.getSelection();
							var ids = [];
							Ext.each(selected, function(item) {
										ids.push(item.data.id);
									})
							Ext.Ajax.request({
										url : 'meeting/delete.do',
										method : "post",
										success : function(response, opts) {
											Ext.MessageBox.hide();
											var json = Ext.JSON
													.decode(response.responseText);
											if (json.success) {
												store_meeting.reload();
											}
										},
										failure : function() {
											Ext.Msg.alert('系统提示',
													'删除失败,刷新后重新操作!');
										},
										params : {
											ids : ids
										}
									});
						}
					});
		} else {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
		}

	}

	function renderTopic(value, p, record) {
		return Ext.String.format('<span>{0}</span>', value);
	}
});

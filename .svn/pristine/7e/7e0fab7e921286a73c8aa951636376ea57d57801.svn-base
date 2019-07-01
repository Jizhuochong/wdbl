Ext.onReady(function() {
	requires : ['Meeting.views.InfoLeader', 'Meeting.views.InfoPerson'],

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
						}],
				pageSize : 20,
				proxy : {
					type : "ajax",
					url : "meetingInfo/list.do",
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

	var editbu = Ext.create('Ext.Button', {
				pressed : true,
				text : '录入参会领导信息',
				iconCls : 'add',
				handler : edit
			});
	var editperbu = Ext.create('Ext.Button', {
				pressed : true,
				text : '录入参会人员信息 ',
				iconCls : 'add',
				handler : editper
			});
	var addtab = Ext.create('Ext.Button', {
				pressed : true,
				text : '录入会议文档信息',
				iconCls : 'add',
				handler : editdoc
			});

	var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text : '搜索',
				iconCls : 'find',
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
				title : "参会领导信息录入/编辑",
				renderTo : "meetingInfo",
				frame : true,
				store : store_meeting,
				loadMask : true,
				columnLines : true,
				forceFit : true,
				height : 140,
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
							flex : 5,
							height:30
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
						},{
							header : "更新时间",
							dataIndex : "updateTime",
							sortable : true,
							renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
							flex : 5
						}],
				plugins : [{
					ptype : 'rowexpander',
					rowBodyTpl : new Ext.XTemplate('<div class="meeting-rowexpander"><b>内容:</b>{content}</div>'
							+ '<div class="meeting-rowexpander"><b>备注:</b>{remark}</div>')
				}],
				listeners : {
					'itemdblclick' : function(view, record, item, index, e) {
						var id = record.get('id');
						var tabid = 'meetingDeail' + id;
						var url = 'meetingDetail/toList.do?id=' + id;
						var text = record.get('title');
						gridaddTab(tabid, url, text)
					},
					scope : this
				},
				collapsible : true,
				enableLocking : true,
				tbar : [editbu, editperbu, addtab, {
							xtype : "tbfill"
						}, s_meeting_title, searchebu],
				bbar : toolbar
			}); 
	gridpanel_meeting
			.setHeight(document.getElementById("meetingInfo").offsetHeight);

	function code_createForm(purl, meGrid, meStore, id, str) {

		var form_meeting = Ext.create('Ext.form.FormPanel', {
					frame : true,
					margins : '6 6 6 6',
					defaults : {
						xtype : "textfield",
						labelAlign : 'right',
						labelWidth : 80
					},
					items : [{
								xtype : "hiddenfield",
								name : "meid",
								value : id
							}, {
								xtype : "hiddenfield",
								name : "updateRecords"
							}, {
								xtype : "hiddenfield",
								name : "removeRecords"
							}, meGrid],
					buttons : [{
						text : '保存',
						iconCls : 'disk',
						formBind : true,
						monitorValid : true,
						handler : function() {
							var updatedes = meStore.getModifiedRecords();// 获取所有修改过的记录
							var removedes = meStore.getRemovedRecords();// 获取所有删除的记录

							var updateRecords = new Array();
							var removeRecords = new Array();
							for (i = 0; i < updatedes.length; i++) {
								updateRecords.push(updatedes[i].data);
							}

							if (removedes != null) {
								for (i = 0; i < removedes.length; i++) {
									removeRecords.push(removedes[i].data);
								}
							}

							form_meeting.getForm().findField("updateRecords")
									.setValue(Ext.JSON.encode(updateRecords));
							form_meeting.getForm().findField("removeRecords")
									.setValue(Ext.JSON.encode(removeRecords));

							form_meeting.getForm().submit({
										waitTitle : "请稍候",
										waitMsg : "正在提交表单数据，请稍候",
										url : purl,
										method : "POST",
										success : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
											meStore.reload();
										},
										failure : function(form, action) {
											Ext.Msg.alert("提示",
													action.result.msg);
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
					width : 900,
					height : 300,
					border : 0,
					frame : false,
					items : form_meeting
				}).show();
	}

	// 编辑
	function edit() {
		var selModel = gridpanel_meeting.getSelectionModel();
		if (selModel.hasSelection()) {
			var selected = selModel.getSelection();
			if (selected.length == 1 && selected[0].data.id != null) {

				var meid = selected[0].data.id;

				var grid_leader = Ext.create('Meeting.views.InfoLeader',{
					meid : meid
				});

				var storeLeader = grid_leader.storeLeader;
				code_createForm('meetingInfo/editSave.do', grid_leader,
						storeLeader, meid, '录入/编辑参会领导信息');
			} else {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "请选择一条会议记录！");
		}
	}
	function editper() {
		var selModel = gridpanel_meeting.getSelectionModel();
		if (selModel.hasSelection()) {
			var selected = selModel.getSelection();
			if (selected.length == 1 && selected[0].data.id != null) {

				var meid = selected[0].data.id;

				var grid_person = Ext.create('Meeting.views.InfoPerson',{
					meid : meid
				});
				var storePerson = grid_person.storePerson;
				code_createForm('meetingInfo/editPerson.do', grid_person,
						storePerson, meid, '录入/编辑参会人员信息');
			} else {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "请选择一条会议记录！");
		}
	}

	//会议文档
	function editdoc() {
		var selModel = gridpanel_meeting.getSelectionModel();
		if (selModel.hasSelection()) {
			var selected = selModel.getSelection();
			if (selected.length == 1 && selected[0].data.id != null) {

				var meid = selected[0].data.id;

				var grid_doc = Ext.create('Meeting.views.InfoDoc',{
					meid : meid
				});
				var storeDoc = grid_doc.storeDoc;
				code_createForm('meetingInfo/editPerson.do', grid_doc,
						storeDoc, meid, '录入/编辑会议文档');
			} else {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
			}
		} else {
			Ext.Msg.alert("错误", "请选择一条会议记录！");
		}
	}

	function renderTopic(value, p, record) {
		return Ext.String.format('<span>{0}</span>', value);
	}

	function gridaddTab(tabid, purl, text) {
		var tab = this.rightPanel.getComponent('tab' + tabid);
		if (tab == null) {
			rightPanel.add({
						loader : {
							url : purl,
							autoLoad : true,
							contentType : 'html',
							scripts : true
						},
						id : 'tab' + tabid,
						closable : true,
						title : text
					}).show();
		} else {
			rightPanel.setActiveTab(tab);
		}
	}
});
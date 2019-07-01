/**
 * 全部文件
 */

Ext.define('Wdbl.view.FileAnyTab', {
	extend : 'Ext.tab.Panel',
	requires : ['Ext.*', 'Wdbl.form.op.*'],
	layout : 'fit',
	// id : 'fileRegTab',
	border : false,
	frame : true,
	innerGrid : null,
	label_search_win : null,
	api : {
		add : null,// 添加
		load : null,// 加载
		edit : null,// 修改
		del : null, // 删除
		toSend : null
	},
	/**
	 * 删除
	 */
	$del : function() {
		var me = this;
		if (!me.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
			return;
		}
		var delFlag = false;
		Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
					if (button == "yes") {
						me.delData();
					}
				}, this);

	},
	// private
	delData : function() {
		var me = this;
		var selected = me.innerGrid.selModel.getSelection();
		var ids = [];
		Ext.each(selected, function(item) {
					ids.push(item.data.id);
				});
		Ext.Ajax.request({
					url : this.api.del,
					method : "post",
					params : {
						ids : ids
					},
					scope : this,
					success : function(response, opts) {
						var json = Ext.JSON.decode(response.responseText);
						Ext.Msg.alert('系统提示', json.msg);
						if (json.success) {
							me.innerGrid.store.reload();
						}
					},
					failure : function() {
						Ext.Msg.alert('系统提示', '删除失败,请刷新后重新操作!');
					}
				}, this);
	},
	//扫描枪扫描后的代码处理
	$analysis_code : function(params){
		var me = this;
		Ext.apply(me.innerGrid.store.proxy.extraParams, params);
		me.innerGrid.store.loadPage(1);
		this.label_search_win.close();
	},
	// 打印条码
	$f_tm : function() {
		var me = this;
		if (me.innerGrid.id == me.getActiveTab().id) {
			if (!me.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有选中任何记录，无法进行打印条码！");
				return;
			}
			var selected = me.innerGrid.selModel.getSelection();
			var id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能打印一条条码！");
				return;
			}
			PrintLabel5025(
		    "create_any_file", 
		    "自动", 
			selected[0].data.barNo?selected[0].data.barNo:"无",
			selected[0].data.receiveTime?selected[0].data.receiveTime:"无", 
			selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
			selected[0].data.receiveTime?selected[0].data.receiveTime:"无", 
			selected[0].data.docType?selected[0].data.docType:"无",
			selected[0].data.docNumber?selected[0].data.docNumber:"无",
		    selected[0].data.urgencyDegree?selected[0].data.urgencyDegree:"无",
		    selected[0].data.securityGrade?selected[0].data.securityGrade:"无", 
		    "无", 
		    selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
		    selected[0].data.title?selected[0].data.title:"无", 
		    selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
		    "", //other
		    selected[0].data.barNo.replace(/(^\s*)/g,"   "), "", "", "", 10, 10,  2);
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			var id = activeTab.p_id;
			Ext.Ajax.request({
				url : 'reg_file/loadBean.do',
				params:{id : id, sessionID:id},
				method : "POST",
				success : function(form, action) {
					var json = Ext.JSON.decode(form.responseText);
					PrintLabel5025(
				    "create_any_file", 
				    "自动", 
					json.data.barNo?json.data.barNo:"无",
					json.data.receiveTime?json.data.receiveTime:"无", 
					json.data.fromUnit?json.data.fromUnit:"无", 
					json.data.receiveTime?json.data.receiveTime:"无", 
					json.data.docType?json.data.docType:"无",
					json.data.docNumber?json.data.docNumber:"无",
				    json.data.urgencyDegree?json.data.urgencyDegree:"无",
				    json.data.securityGrade?json.data.securityGrade:"无", 
				    "无", 
				    json.data.fromUnit?json.data.fromUnit:"无", 
				    json.data.title?json.data.title:"无", 
				    json.data.fromUnit?json.data.fromUnit:"无", 
				    "", //other
				    json.data.barNo.replace(/(^\s*)/g,"   "), "", "", "", 10, 10,  2);
				},
				failure : function(form, action) {
					Ext.Msg.alert("提示", "打印失败，请重试");
				}
			}, this);
		}
	},
	// 打印审批单
	$f_print : function(docType) {
		var me = this;
		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行打印操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能打印一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		Ext.Ajax.request({
					url : 'reg_file/loadBean.do',
					params : {
						id : id,
						sessionID : id
					},
					method : "POST",
					success : function(form, action) {
						var json = Ext.JSON.decode(form.responseText);
						if (docType == 'TEL_RCD_DOC'
								&& json.data.docType != '3') {
							Ext.Msg.alert("提示", "当前文件类型不是电话记录，无法打印");
						} else {
							var _url = 'file_export/' + docType + '/' + id
									+ '/exportFile.do';
							window.open(_url);
						}
					},
					failure : function(form, action) {
						Ext.Msg.alert("提示", "打印失败，请重试");
					}
				}, this);
	},
	// 录入领导批示
	$f_leaderInstructions : function() {
		var me = this;
		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.leaderInstructionsForm',
				{
					recordID : id,
					btype : 'any_'
				});
		process_common.showWin();
	},
	// 文件处理
	$f_transfer : function() {
		var me = this;
		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.transferForm', {
					recordID : id
				});
		process_common.showWin();
	},
	// 呈送
	$f_draft : function() {
		var me = this;
		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行呈送操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.submitForm', {
					recordID : id,
					parent : me,
					store : me.innerGrid.store,
					gridpanel : me.innerGrid
				});
		process_common.showWin();
	},
	// 刷新
	$f_refresh : function() {
		var me = this;
		me.innerGrid.getStore().reload();
	},
	// 按条码查询
	$code_search : function() {
		var gunSearchFrame = Ext.create('Ext.ux.IFrame', {
					closable : true,
					border : false,
					src : 'gun_search_any.jsp?r=' + Math.random()
				});
		this.label_search_win = Ext.create('Ext.window.Window', {
					title : '扫描二维码',
					layout : 'fit',
					modal : true,
					plain : true,
					width : 480,
					height : 310,
					border : 0,
					frame : true,
					items : gunSearchFrame,
					listeners : {
						'close' : function() {
							window.frames[0].pageClose();
						}
					}
				});
		this.label_search_win.show();
	},
	// 修改类型
	$f_update_type : function() {
		var me = this;
		if (!this.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
			return;
		}
		var selected = this.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提醒", "只能编辑一条记录！");
			return;
		}
		var update_docType_common = Ext.create('Wdbl.form.op.updateTypeForm', {
					recordID : id,
					store : me.innerGrid.store,
					gridpanel : me.innerGrid
				});
		update_docType_common.showWin();
	},
	/**
	 * 批量办结
	 */
	$f_finish : function() {
		var me = this;
		var id = null;
		var ids = [];
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行办结操作！");
				return;
			}
			var selected = me.innerGrid.selModel.getSelection();
			Ext.each(selected, function(item) {
						ids.push(item.data.id);
					});
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
			ids.push(id);
		}

		var delFlag = false;
		Ext.Msg.confirm("警告", "确定要办结吗？", function(button) {
			if (button == "yes") {
				Ext.Ajax.request({
							url : 'reg_file/finish.do',
							method : "post",
							params : {
								ids : ids
							},
							scope : this,
							success : function(response, opts) {
								var json = Ext.JSON
										.decode(response.responseText);
								if (json.success) {
									me.innerGrid.store.reload();
									if (me.innerGrid.id != me.getActiveTab().id) {
										me.getActiveTab().close();
									}
								}
							},
							failure : function() {
								Ext.Msg.alert('系统提示', '办结失败,请刷新后重新操作!');
							}
						}, this);
			}
		}, this);

	},
	// 取消办结
	$f_noFinish : function() {
		var me = this;
		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行取消办结操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}

		var ids = [];
		ids.push(id);
		Ext.Ajax.request({
					url : 'over_file/noFinish.do',
					method : "post",
					params : {
						ids : ids
					},
					scope : this,
					success : function(response, opts) {
						var json = Ext.JSON.decode(response.responseText);
						// Ext.Msg.alert('系统提示', json.msg);
						if (json.success) {
							me.innerGrid.store.reload();
							if (me.innerGrid.id != me.getActiveTab().id) {
								me.getActiveTab().close();
							}
						}
					},
					failure : function() {
						Ext.Msg.alert('系统提示', '办结失败,请刷新后重新操作!');
					}
				}, this);
	},
	// 大范围文件(分发设置)
	$f_ffsz : function(count) {
		var me = this;
		var id = null;

		var id = null;
		if (me.innerGrid.id == me.getActiveTab().id) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}

		var sendSetWin = Ext.create('Wdbl.form.op.WideSetForm', {
					regID : id,
					unitNumber : count
				});
		sendSetWin.showWin();
	},
	/**
	 * 编辑
	 */
	$edit : function() {
		var me = this;
		if (!me.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有选中任何记录，无法进行编辑！");
			return;
		}
		var selected = me.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		var title = selected[0].data.title;
		var docType = selected[0].data.docType;
		var barNo = selected[0].data.barNo;
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提醒", "只能编辑一条记录！");
			return;
		}
		var formTypeName = 'Wdbl.form.'
				+ WDBL$Globals.DocTypeEditForms[docType];
		
		var ffsz = "0";
		if(formTypeName == "Wdbl.form.WideEditForm") {
			ffsz = "1";
		}
		this.add(Ext.create('Wdbl.view.FileAnyDetail', {
					tooltip : '编辑:' + title,
					title : '编辑' + title,
					closable : true,
					parent : me.ownerCt,
					grid : me,
					load : true,
					type : docType,
					barNo : barNo,
		    		ffszType: ffsz,
					loadUrl : this.api.load,
					loadParam : {
						'id' : 'any_' + id
					},
					p_id : id,
					addType : 'newEdit',
					url : this.api.edit
				})).show();
	},
	initComponent : function() {
		var me = this;
		var editBtn = Ext.create('Ext.button.Button', {
					text : '编辑',
					iconCls : 'table_edit',
					handler : function() {
						me.$edit();
					}
				});
		var tmBtn = Ext.create('Ext.button.Button', {
					text : '打印条码',
					iconCls : 'attach',
					handler : function() {
						me.$f_tm()
					}
				});
		var updateTypeBtn = Ext.create('Ext.button.Button', {
					text : '修改类型',
					iconCls : 'report_edit',
					handler : function() {
						me.$f_update_type()
					}
				});
		var searchBtn = Ext.create('Ext.button.Button', {
					text : '条码查询',
					iconCls : 'page_code',
					handler : function() {
						me.$code_search()
					}
				});
		var delBtn = Ext.create('Ext.button.Button', {
					text : '删除',
					iconCls : 'delete',
					handler : function() {
						me.$del()
					}
				});
		var printBtn = Ext.create('Ext.button.Button', {
					text : '打印',
					iconCls : 'printer',
					menu : [{
								text : '阅批单',
								handler : function() {
									me.$f_print('CITY_GOV_DOC');
								}
							}, {
								text : '密电单',
								handler : function() {
									me.$f_print('CIPHER_TELEGRAPH_DOC');
								}
							}, {
								text : '电话记录',
								handler : function() {
									me.$f_print('TEL_RCD_DOC');
								}
							}, {
								text : '批示抄清',
								handler : function() {
									me.$f_print('INSTRUCTIONS_DOC');
								}
							}]
				});
		var noFinishBtn = Ext.create('Ext.button.Button', {
					text : '取消办结',
					iconCls : 'table_gear',
					handler : function() {
						me.$f_noFinish();
					}
				});
		var refreshBtn = Ext.create('Ext.button.Button', {
					text : '刷新',
					iconCls : 'arrow_refresh',
					handler : function() {
						me.$f_refresh()
					}
				});
		var draftBtn = Ext.create('Ext.button.Button', {
					text : '呈送',
					iconCls : 'report_edit',
					handler : function() {
						me.$f_draft()
					}
				});
		var leaderInstructionsBtn = Ext.create('Ext.button.Button', {
					text : '录入批示',
					iconCls : 'note_edit',
					handler : function() {
						me.$f_leaderInstructions()
					}
				});
		var transferBtn = Ext.create('Ext.button.Button', {
					text : '发送文件',
					iconCls : 'folder_page',
					handler : function() {
						me.$f_transfer()
					}
				});
		var finishBtn = Ext.create('Ext.button.Button', {
					text : '办结',
					iconCls : 'table_key',
					handler : function() {
						me.$f_finish()
					}
				});
		var ffszBtn = Ext.create('Ext.button.Button', {
					text : '大范围文件',
					iconCls : 'arrow_out',
					menu : [{
								text : '20个单位',
								handler : function() {
									me.$f_ffsz('20');
								}
							}, {
								text : '60个单位',
								handler : function() {
									me.$f_ffsz('60');
								}
							}]
				});
		var btns = [editBtn, tmBtn, updateTypeBtn, searchBtn, delBtn, printBtn,
				noFinishBtn, refreshBtn, draftBtn, leaderInstructionsBtn,
				transferBtn, finishBtn, ffszBtn];
		var editNbtns = [refreshBtn, editBtn, searchBtn, updateTypeBtn, delBtn];
		var ffszBtns = [ffszBtn];
		Ext.applyIf(me, {
					xtype : 'tabpanel',
					layout : 'fit',
					activeTab : 0,
					minTabWidth : 100,
					maxTabWidth : 100,
					items : [this.innerGrid],
					dockedItems : [{
								xtype : 'toolbar',
								dock : 'top',
								items : [editBtn, {
											xtype : "tbseparator"
										}, tmBtn, {
											xtype : "tbseparator"
										}, updateTypeBtn, {
											xtype : "tbseparator"
										}, searchBtn, {
											xtype : "tbseparator"
										}, delBtn, {
											xtype : "tbseparator"
										}, printBtn, {
											xtype : "tbseparator"
										}, noFinishBtn, {
											xtype : "tbseparator"
										}, draftBtn, {
											xtype : "tbseparator"
										}, leaderInstructionsBtn, {
											xtype : "tbseparator"
										}, transferBtn, {
											xtype : "tbseparator"
										}, finishBtn, {
											xtype : "tbseparator"
										}, ffszBtn, {
											xtype : "tbseparator"
										}, refreshBtn]
							}],
					listeners : {
						'beforerender': function(){
							Ext.Array.each(btns, function(name, index, _self) { btns[index].setDisabled(false); });
							Ext.Array.each(ffszBtns, function(name, index, _self) { ffszBtns[index].setDisabled(true); });
				        },
						tabchange : function(tabPanel, newCard, oldCard) {
							if (newCard.id == me.innerGrid.id) {
								Ext.Array.each(btns, function(name, index, _self) { btns[index].setDisabled(false); });
								Ext.Array.each(ffszBtns, function(name, index, _self) { ffszBtns[index].setDisabled(true); });
								me.innerGrid.getStore().reload();
							} else {
								Ext.Array.each(btns, function(name, index, _self) { btns[index].setDisabled(false); });
								Ext.Array.each(editNbtns, function(name, index, _self) { editNbtns[index].setDisabled(true); });
								
								if(newCard.ffszType == 1) {
									Ext.Array.each(ffszBtns, function(name, index, _self) { ffszBtns[index].setDisabled(false); });
								} else {
									Ext.Array.each(ffszBtns, function(name, index, _self) { ffszBtns[index].setDisabled(true); });
								}
							}
						}
					}

				});

		me.callParent(arguments);
	}
});

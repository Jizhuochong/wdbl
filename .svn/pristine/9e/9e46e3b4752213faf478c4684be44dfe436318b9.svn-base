Ext.define('Meeting.views.InfoMeetingBase', {
	extend : 'Ext.form.Panel',

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.MeetingBase'],
	xtype : 'cell-editing',

	meid:null,
	
	// title : '录入参会领导信息',
	frame : false,

	initComponent : function() {

		var grid_time = Ext.create('Meeting.views.InfoTime',{
			meid:this.meid
		});
		
		var meStore = grid_time.storeTime;
		
		Ext.apply(this, {
			frame : true,
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
					}, {
						xtype : 'textareafield',
						name : "content",
						fieldLabel : '会议内容',
						maxLength : 4000,
						maxLengthText : '内容最大值为4000',
						anchor    : '100%',
						grow:true,
						allowBlank : false
					}, grid_time, {
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
									flex : 1,
									xtype : 'radiogroup',
									fieldLabel : '处理类型',
									vertical : true,
									items : [{
												boxLabel : '处理',
												name : 'typeJuge',
												inputValue : '1',
												checked : true
											}, {
												boxLabel : '转走',
												name : 'typeJuge',
												inputValue : '2'
											}]
								}, {
									xtype : 'text',
									flex : 0.2
								}, {
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
									flex : 1.2,
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
				handler : function() {
					win.close();
				}
			}] 
		});
		this.callParent();
		
		this.getForm().load({
						url : 'meetingDetail/getDetail.do',
						params : {
							id : this.meid
						},
						failure : function(form, action) {
							Ext.Msg.alert("加载失败", action.result.errorMessage);
						}
					});
	}
	
})
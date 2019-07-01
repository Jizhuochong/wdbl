Ext.define('Meeting.meetingSchedule', {
	extend : 'Ext.panel.Panel',

	initComponent : function() {
		var time = new Date();
		var nowtime = Ext.util.Format.date(time, 'Y年 m月 d日');
		var thisweek = Ext.Date.getWeekOfYear(time); // 记录当前是第几周
		var day = Ext.util.Format.date(time, 'm/d/Y');
		var toDayCom = Ext.create('Meeting.views.ScheduleDay', {day : day});
		var weekCom;

		Ext.apply(this, {
					frame : true,
					padding : '5 5 5 5',
					autoHeight : true,
					border : '0',
					renderTo : 'meetingSchedule',
					layout : 'border',
					items : [{
						autoHeight : true,
						region : 'west',
						flex : 1,
						xtype : 'datepicker',
						format : 'm/d/Y',
						listeners : {
							'select' : {
								fn : function(dp, dt) {
									var checkweek = Ext.Date.getWeekOfYear(dt);// 记录所选周数
									dt = Ext.util.Format.date(dt, 'm/d/Y')

									if (toDayCom.isVisible()) {
										if (dt != day) {// 判断，如果当前状态不为今天,重新加载
											day = dt;// 赋值
											toDayCom.storeTime.setProxy({
														type : 'ajax',
														url : 'meSchedule/timeList.do',
														actionMethods : {
															read : 'POST'
														},
														reader : {
															type : "json",
															root : "rows",
															totalProperty : 'totalrows'
														},
														// //传参
														extraParams : {
															day : dt
														}
													});
											toDayCom.storeTime.load();// 重载todaycom
											Ext.getCmp('selectTime').setText(Ext.util.Format.date(dt, 'Y年 m月 d日'));
										}
									}

									if (weekCom != null && weekCom.isVisible()) {
										day = dt;// 赋值
										// 判断，如果当前状态不为本周,重载
										if (checkweek != thisweek) {
											thisweek = checkweek;
											Ext.getCmp('schedulebd')
													.remove(weekCom);
											weekCom = Ext
													.create(
															'Meeting.views.ScheduleWeek',
															{
																today : dt
															});
											Ext.getCmp('schedulebd')
													.add(weekCom);
											setstText(day);
										}
									}
								},
								scope : this
							}
						}
					}, {
						region : 'center',
						flex : 4,
						items : [{
									xtype : 'toolbar',
									border : '0',
									height : 30,
									items : ['->', {
												text : nowtime,
												id : 'selectTime',
												xtype : 'label'
											}, '->']
								}, {
									xtype : 'toolbar',
									border : '0',
									height : 30,
									items : ['->', {
										text : '今天',
										handler : function() {
											toDayCom.setVisible(true);
											if (weekCom != null) {
												weekCom.setVisible(false);
											}
											Ext
													.getCmp('selectTime')
													.setText(Ext.util.Format
															.date(dt,
																	'Y年 m月 d日'));
										}
									}, {
										text : '本周',
										handler : function() {
											toDayCom.setVisible(false);
											if (weekCom != null) {
												weekCom.setVisible(true);
											} else {
												Ext.getCmp('schedulebd')
														.remove(weekCom);
												weekCom = Ext
														.create(
																'Meeting.views.ScheduleWeek',
																{
																	today : day
																});
												Ext.getCmp('schedulebd')
														.add(weekCom);
												setstText(day);
											}
										}
									}, '->']
								}, {
									id : 'schedulebd',
									xtype : 'panel',
									border : 0,
									autoScroll : true,
									layout : 'fit',
									items : [toDayCom, weekCom]
								}]
					}]
				});
		this.callParent();

		function setstText(day) {
			var disday = new Date(day);
			var startDay, endDay;
			if (disday.getDay() == 0) {
				startDay = Ext.Date.add(disday, Ext.Date.DAY, -6);
				endDay = disday;
			} else {
				startDay = Ext.Date.add(disday, Ext.Date.DAY, 1
								- disday.getDay());
				endDay = Ext.Date
						.add(disday, Ext.Date.DAY, 7 - disday.getDay());
			}
			Ext.getCmp('selectTime').setText(Ext.util.Format.date(startDay,'Y年 m月 d日')
					+ '--' + Ext.util.Format.date(endDay, 'Y年 m月 d日'));
		}
	}
})
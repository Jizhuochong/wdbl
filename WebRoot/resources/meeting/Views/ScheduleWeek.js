Ext.define('Meeting.views.ScheduleWeek', {
			extend : 'Ext.grid.Panel',

			// xtype: 'grouped-grid',
			requires : ['Ext.grid.*', 'Ext.data.*', 'Ext.util.*', 'Ext.form.*',
					'Meeting.model.WeekTime'],

			today : null,

			initComponent : function() {
				var me = this;
				me.today = new Date(me.today);
				
				var nowTime=new Date();
				var nowdays=nowTime.getDay()-1;
				if(nowdays<0){
					nowdays=6;
				}

				dayNames = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"];
				var items = [];// items 数组值

				if (me.today.getDay() == 0) {
					this.today = Ext.Date.add(this.today, Ext.Date.DAY, -6);
				} else {
					this.today = Ext.Date.add(this.today, Ext.Date.DAY,
							-me.today.getDay() + 1);
				}

				for (i = 0; i < 7; i++) {
					var date = Ext.Date.add(this.today, Ext.Date.DAY, i);
					items.push(Ext.util.Format.date(date, 'm/d/Y'));
				}

				me.weekStore = Ext.create('Ext.data.Store', {
							autoLoad : true,
							model : 'Meeting.model.WeekTime',
							groupField : 'day',
							groupDir : 'asc',
							proxy : {
								type : 'ajax',
								url : 'meSchedule/weekList.do',
								actionMethods : {
									read : 'POST'
								},
								reader : {
									type : "json",
									root : "rows",
									totalProperty : 'totalrows',
									idProperty : 'weeks'
								},
								// //传参
								extraParams : {
									days : items
								},
								sorters : [{
											property : 'day',
											direction : 'ASC'
										}]
							}
						});

				Ext.apply(this, {
							title : '周会议一览',
							store : me.weekStore,
							border : 0,
							layout : 'fit',
							height : 560,
							columns : [{
								text : '会议开始时间',
								dataIndex : 'meStartTime',
								renderer : Ext.util.Format
										.dateRenderer('m/d/Y H:i'),
								flex : 2,
								sortable : false
							}, {
								text : '会议结束时间',
								dataIndex : 'meEndTime',
								renderer : Ext.util.Format
										.dateRenderer('m/d/Y H:i'),
								flex : 2,
								sortable : false
							}, {
								text : '开会地址',
								dataIndex : 'meAddress',
								flex : 3,
								sortable : false
							}, {
								text : 'DAY',
								hidden : true,
								dataIndex : 'day',
								flex : 1,
								renderer:function(val){
									if(val==nowdays){
										Ext.getCmp('groupingweek').expend(dayname[val],false);
									}
								}
							}],
							features : [{
								ftype : 'grouping',
								id:'groupingweek',
								startCollapsed: true,
								groupHeaderTpl : ['{name:this.formatName} ({rows.length}个会议)', {
											formatName : function(name) {
												return dayNames[name];
											}
										}]
							}]
						});
				me.callParent();
			}
		});
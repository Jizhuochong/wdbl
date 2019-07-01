Ext.define('Meeting.views.ScheduleWeek', {
			extend : 'Ext.panel.Panel',
			requires:['Ext.Date'],
			
			// /构造器传参
			today:null,

			frame : false,

			initComponent : function() {
				var dayNames = ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六","星期日"];
				var weekDay=this.today.getDay();					//周几?
				var items=[];//items 数组值

				for(i=0;i<7;i++){
					var date=Ext.Date.add(this.today,Ext.Date.DAY,i+1-weekDay);
					date=date.toString();
					var scWeek=Ext.create('Ext.panel.Panel',{//控件
						title:dayNames[i],
						autoScroll : true,
						minHeight:300,
						items:[Ext.create('Meeting.views.ScheduleDay',{day:date})]
					});
					items.push(scWeek);
				}

				Ext.apply(this, {
							layout : 'accordion',
							minHeight:400,
							autoScroll : true,
							layoutConfig : {
								animate : true
							},
							defaults:{
								border : false,
								autoScroll : true
							},
							items : items
						});
				this.callParent();
			}
		})
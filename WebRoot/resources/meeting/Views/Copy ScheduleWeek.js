Ext.define('Meeting.views.ScheduleWeek', {
    extend: 'Ext.grid.Panel',
    
//    xtype: 'grouped-grid',

    day:null,
    
    initComponent: function() {
    	var me= this;
        me.weekStore = Ext.create('Ext.data.Store', {
		    storeId:'employeeStore',
		    fields:['name', 'seniority', 'day'],
		    groupField: 'day',
		    data: {'employees':[
		        { "name": "Michael Scott",  "seniority": 7, "day": "Management" },
		        { "name": "Dwight Schrute", "seniority": 2, "day": "Sales" },
		        { "name": "Jim Halpert",    "seniority": 3, "day": "Sales" },
		        { "name": "Kevin Malone",   "seniority": 4, "day": "Accounting" },
		        { "name": "Angela Martin",  "seniority": 5, "day": "Accounting" }
		    ]},
		    proxy: {
		        type: 'memory',
		        reader: {
		            type: 'json',
		            root: 'employees'
		        }
		    }
		});
		
		Ext.apply(this, {
		    title: '周会议一览',
		    store: me.weekStore,
		    border:0,
		    columns: [
		        { text: '会议开始时间',dataIndex: 'name',flex:2 },
		        { text: '会议结束时间', dataIndex: 'seniority',flex:2 },
		        { text: '开会地址', dataIndex: 'seniority',flex:3 },
		        { text: '', dataIndex: '',flex:1 }
		    ],
		    features: [{ftype:'grouping'}],
		    autoHeight:true,
		    autoScroll : true
		});
		
		me.callParent();
    }
});
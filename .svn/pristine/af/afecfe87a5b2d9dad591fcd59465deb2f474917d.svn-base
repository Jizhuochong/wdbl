Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $formUnitStatistics_id = "$formUnit_statistics";
	var store1 = Ext.create("Ext.data.JsonStore", {
    	fields: [
            { name: "id" },
            { name: "day" },
            { name: "num" }
        ],
        sortInfo: { field: 'id', direction: 'asc' }, 
        proxy: {
            type: "ajax",
            url: "statistics/listLoadFormUnit.do?year="+new Date().getFullYear()+"&month="+(new Date().getMonth()+1),
           	actionMethods: { read: 'POST'},
            reader: {
                type: "json",
                root: "rows",
                totalProperty: 'totalrows'
            },
            simpleSortMode: true
        }
    });
	store1.loadPage(1);
    
    var chart = Ext.create('Ext.chart.Chart', {
        animate: true,
        shadow: true,
        store: store1,
        axes: [{
        	title: '个数',
            type: 'Numeric',
            position: 'left',
            fields: ['num'],
            grid: true
        }, {
        	title: '单位',
            type: 'Category',
            position: 'bottom',
            fields: ['day']
        }],
        series: [{
            type: 'line',
            axis: 'left',
            gutter: 50,
            xField: 'day',
            yField: ['num']
        }]
    });
    
    var panel1 = Ext.create('widget.panel', {
        width: 800,
        height: 400,
        title: 'Line Chart',
        layout: 'fit',
        items: [chart]
    });
    
    var panel = Ext.create('Ext.tab.Panel', {
    	renderTo: $formUnitStatistics_id,
        items: [panel1],
        dockedItems: [{
			xtype:'toolbar',
			dock:'top',
			items:[{
				id : 'yearFormUnit',
				columnWidth : 0.5,
				xtype : "combo",
				fieldLabel : "年份",
				store:[['2014','2014'],['2015','2015']],
				value:new Date().getFullYear(),
				emptyText:'请选择'
			},{
				id : 'monthFormUnit',
				columnWidth : 0.5,
				xtype : "combo",
				fieldLabel : "月份",
				store:[['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6'],['7','7'],['8','8'],['9','9'],['10','10'],['11','11'],['12','12']],
				value:new Date().getMonth()+1,
				emptyText:'请选择'
			},
			{
				xtype : 'button',
				pressed : true,
				text : '查找',
				iconCls: 'find',
				scope : this,
				handler: function() {
					store1.getProxy().url = "statistics/listLoadFormUnit.do?year="+Ext.getCmp("yearFormUnit").getValue()+"&month="+Ext.getCmp("monthFormUnit").getValue();  
				    store1.load(); 
			    }
			}]      
		}]
    });
    
    panel.setHeight(document.getElementById($formUnitStatistics_id).offsetHeight);
});
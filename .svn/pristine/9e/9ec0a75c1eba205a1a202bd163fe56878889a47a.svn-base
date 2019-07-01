Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	var $stateStatistics_id = "$state_statistics";
	var store1 = Ext.create("Ext.data.JsonStore", {
    	fields: [
            { name: "id" },
            { name: "state" },
            { name: "num" }
        ],
        sortInfo: { field: 'id', direction: 'asc' }, 
        proxy: {
            type: "ajax",
            url: "statistics/listLoadState.do?year="+new Date().getFullYear()+"&month="+(new Date().getMonth()+1),
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
        width: 100,
        height: 100,
        animate: false,
        store: store1,
        shadow: false,
        insetPadding: 0,
        theme: 'Base:gradients',
        series: [{
            type: 'pie',
            field: 'num',
            showInLegend: false,
            label: {
                field: 'state',
                display: 'rotate',
                contrast: true,
                font: '9px Arial'
            }
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
    	renderTo: $stateStatistics_id,
        items: [panel1],
        dockedItems: [{
			xtype:'toolbar',
			dock:'top',
			items:[{
				id : 'yearState',
				columnWidth : 0.5,
				xtype : "combo",
				fieldLabel : "年份",
				store:[['2014','2014'],['2015','2015']],
				value:new Date().getFullYear(),
				emptyText:'请选择'
			},{
				id : 'monthState',
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
					store1.getProxy().url = "statistics/listLoadState.do?year="+Ext.getCmp("yearState").getValue()+"&month="+Ext.getCmp("monthState").getValue();  
				    store1.load(); 
			    }
			}]      
		}]
    });
    
    panel.setHeight(document.getElementById($stateStatistics_id).offsetHeight);
});
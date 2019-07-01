Ext.onReady(function() {
	Ext.QuickTips.init();
	////////
	Ext.chart.LegendItem.override({
		getLabelText : function() {
			var me = this, series = me.series, idx = me.yFieldIndex;
			function getSeriesProp(name) {
				var val = series[name];
				return (Ext.isArray(val) ? val[idx] : val);
			}
			return getSeriesProp('dispalyField') || getSeriesProp('yField');
		}
	});
	var $yearStatistics_id = "$year_statistics";
	var store = Ext.create("Ext.data.JsonStore", {
    	fields: [
            { name: "id" },
            { name: "month" },
            { name: "num" },
            { name: "oldnum" }
        ],
        sortInfo: { field: 'id', direction: 'asc' }, 
        proxy: {
            type: "ajax",
            url: "statistics/listLoad.do?year="+new Date().getFullYear(),
           	actionMethods: { read: 'POST'},
            reader: {
                type: "json",
                root: "rows",
                totalProperty: 'totalrows'
            },
            simpleSortMode: true
        }
    });
	store.loadPage(1);
    
    var chart = Ext.create('Ext.chart.Chart', {
        animate: true,
        shadow: true,
        store: store,
        theme: 'Category1',
        legend: {
            position: 'bottom'
        },
        axes: [{
        	title: '个数',
            type: 'Numeric',
            position: 'left',
            fields: ['num','oldnum'],
            grid: true
        }, {
        	title: '月份',
            type: 'Category',
            position: 'bottom',
            fields: ['month']
        }],
        series: [{
            type: 'line',
            highlight: {
                size: 7,
                radius: 7
            },
            axis: 'left',
            gutter: 50,
            xField: 'month',
            yField: ['num'],
            dispalyField : ['当前'],
            markerConfig: {
                type: 'circle',
                size: 4,
                radius: 4,
                'stroke-width': 0
            }
        },{
            type: 'line',
            highlight: {
                size: 7,
                radius: 7
            },
            axis: 'left',
            gutter: 50,
            xField: 'month',
            yField: ['oldnum'],
            dispalyField : ['上一年'],
            markerConfig: {
                type: 'circle',
                size: 4,
                radius: 4,
                'stroke-width': 0
            }
        }]
    });
    
    var panelWid = Ext.create('widget.panel', {
        width: 800,
        height: 400,
        title: 'Line Chart',
        layout: 'fit',
        items: [chart]
    });
    
    var panel = Ext.create('Ext.tab.Panel', {
    	renderTo: $yearStatistics_id,
        items: [panelWid],
        dockedItems: [{
			xtype:'toolbar',
			dock:'top',
			items:[{
				id : 'combosYear',
				xtype : "combo",
				fieldLabel : "年份",
				store:[['2014','2014'],['2015','2015']],
				value:new Date().getFullYear(),
				emptyText:'请选择'
			},
			{
				xtype : 'button',
				pressed : true,
				text : '查找',
				iconCls: 'find',
				scope : this,
				handler: function() {
					store.getProxy().url = "statistics/listLoad.do?year="+Ext.getCmp("combosYear").getValue();  
				    store.load(); 
			    }
			}]      
		}]
    });
    
    panel.setHeight(document.getElementById($yearStatistics_id).offsetHeight);
});
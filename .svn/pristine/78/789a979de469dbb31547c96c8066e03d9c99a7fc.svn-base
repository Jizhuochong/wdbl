<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试</title>
<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
<script type="text/javascript" src="extjs/ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="extjs/resources/ext-theme-classic/ext-theme-classic-all.css"/>
<link rel="stylesheet" type="text/css" href="extjs/resources/css/TabScrollerMenu.css" />
<link rel="stylesheet" type="text/css" href="resources/css/icon.css" />
<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="resources/js/commons_util.js"></script>
<script type="text/javascript" src="resources/js/modifyPwd.js"></script>
<style type="text/css">
.x-grid-record-red{
    color: #FF0000;
 }
</style>
<script type="text/javascript">
	Ext.onReady(function() {  
		/*Ext.define('WeatherPoint', {
		    extend: 'Ext.data.Model',
		    fields: ['temperature', 'date']
		});
		
		var store = Ext.create('Ext.data.Store', {
		    model: 'WeatherPoint',
		    data: [
		        { temperature: 58, date: new Date(2011, 1, 1, 8) },
		        { temperature: 63, date: new Date(2011, 1, 1, 9) },
		        { temperature: 73, date: new Date(2011, 1, 1, 10) },
		        { temperature: 78, date: new Date(2011, 1, 1, 11) },
		        { temperature: 81, date: new Date(2011, 1, 1, 12) }
		    ]
		});

		Ext.create('Ext.chart.Chart', {
		   renderTo: 'id',
		   width: 400,
		   height: 300,
		   store: store,
		   theme: 'Green',
		   axes: [
		          {
		              title: 'Temperature',
		              type: 'Numeric',
		              position: 'left',
		              fields: ['temperature'],
		              minimum: 0,
		              maximum: 100
		          },
		          {
		              title: 'Time',
		              type: 'Time',
		              position: 'bottom',
		              fields: ['date'],
		              dateFormat: 'ga',
		              fromDate: new Date(2011, 1, 1, 8),
		              toDate: new Date(2011, 1, 1, 12)
		          }
		      ],
		      series: [
		               {
		                   type: 'line',
		                   xField: 'date',
		                   yField: 'temperature'
		               }
		           ]
		});*/
		
	    var store1 = Ext.create('Ext.data.JsonStore', {
	        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5'],
	        data: [
	            {'name':'January', 'data1':10, 'data2':12, 'data3':14, 'data4':8, 'data5':13},
	            {'name':'Febuary', 'data1':7, 'data2':8, 'data3':16, 'data4':10, 'data5':3},
	            {'name':'March', 'data1':5, 'data2':2, 'data3':14, 'data4':12, 'data5':7},
	            {'name':'April', 'data1':2, 'data2':14, 'data3':6, 'data4':1, 'data5':23},
	            {'name':'May', 'data1':27, 'data2':38, 'data3':36, 'data4':13, 'data5':33}
	        ]
	   });
	    
	    var chart = Ext.create('Ext.chart.Chart', {
	            animate: true,
	            shadow: true,
	            store: store1,
	            axes: [{
	                type: 'Numeric',
	                position: 'left',
	                fields: ['data1'],
	                title: false,
	                grid: true
	            }, {
	                type: 'Category',
	                position: 'bottom',
	                fields: ['name'],
	                title: false
	            }],
	            series: [{
	                type: 'line',
	                axis: 'left',
	                gutter: 80,
	                xField: 'name',
	                yField: ['data1']
	            }]
	        });
        
	    var pieModel = [
	                    {
	                        name: 'data1',
	                        data: "2"
	                    },
	                    {
	                        name: 'data2',
	                        data: "8"
	                    },
	                    {
	                        name: 'data3',
	                        data: "13"
	                    },
	                    {
	                        name: 'data4',
	                        data: "6"
	                    },
	                    {
	                        name: 'data5',
	                        data: "20"
	                    }
	                ];

	    var pieStore = Ext.create('Ext.data.JsonStore', {
	        fields: ['name', 'data'],
	        data: pieModel
	    });
	    
	    var pieChart = Ext.create('Ext.chart.Chart', {
	        width: 100,
	        height: 100,
	        animate: false,
	        store: pieStore,
	        shadow: false,
	        insetPadding: 0,
	        theme: 'Base:gradients',
	        series: [{
	            type: 'pie',
	            field: 'data',
	            showInLegend: false,
	            label: {
	                field: 'name',
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
	        renderTo: 'id',
	        layout: 'fit',
	        items: [pieChart]
	    });

	    
	});
</script>
</head>
<body>
<div id="id" style="height: 100%; width: 100%;"></div>
</body>
</html>
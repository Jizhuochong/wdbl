<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src="resources/js/jquery-1.8.3.min.js">
</script>
		<script type="text/javascript">
var meetingid = ${result.data.id};
var bd='meetingDetail'+meetingid;

Ext.onReady(function() {
	requires : ['Meeting.views.InfoDetail'];
	Ext.QuickTips.init();
	var detailinfo=Ext.create('Ext.panel.Panel',{
	title: '会议详情',
    frame:false,
    autoScroll:true,
    renderTo: bd,
	layout :'border',
			items:[{
						region : 'west',
						title : '会议基本信息',
						split : true,
						width : 700,
						collapsible : true,
						margins : '0 0 5 5',
						layout : 'accordion',
						layoutConfig : {
							animate : true
						},
						items : [{
									title : '会议基本信息',
									autoScroll : true,
									border : false,
									height:500,
									iconCls : 'nav'
								}, {
									title : '会议时间信息',
									border : false,
									autoScroll : true,
									height:500,
									iconCls : 'settings'
								}, {
									title : '与会领导信息',
									border : false,
									height:500,
									autoScroll : true,
									iconCls : 'settings'
								}, {
									title : '与会人员信息',
									border : false,
									height:500,
									autoScroll : true,
									iconCls : 'settings'
								},{
									title : '会议文档信息',
									border : false,
									height:500,
									autoScroll : true,
									iconCls : 'settings'
								}]
					}, {
						xtype : 'tabpanel',
						region : 'center',
						title : '信息录入/编辑',
						animCollapse : true,
						collapsible : true,
						split : true,
						margins : '0 5 0 0',
						activeTab : 1,
						tabPosition : 'bottom',
						items : [{
							html : '<p>A TabPanel component can be a region.</p>',
							title : '会议时间',
							autoScroll : true
						},{
							html : '<p>A TabPanel component can be a region.</p>',
							title : '与会领导',
							autoScroll : true
						},{
							html : '<p>A TabPanel component can be a region.</p>',
							title : '与会人员',
							autoScroll : true
						},{
							html : '<p>A TabPanel component can be a region.</p>',
							title : '会议文档',
							autoScroll : true
						}]
					}]
					});
	detailinfo
			.setHeight(document.getElementById(bd).offsetHeight);
});

		</script>
	</head>
	<body>
		<div id="meetingDetail${result.data.id}" style="height: 100%; width: 100%;"></div>
	</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
		<meta name="renderer" content="webkit"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<title>日程安排</title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/icon.css'/>" />
		<script type="text/javascript">
		
			Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
			var ICONS_PATH = 'resources/icons';
		</script>
		<script type="text/javascript" src="resources/meeting/meetingSchedule.js"></script>
		<link rel="stylesheet" type="text/css" href="resources/css/meeting.css"/>
	</head>
	<script type="text/javascript">
Ext.onReady(function() {
	Ext.QuickTips.init();
	requires : ['Meeting.meetingSchedule'];
	var detailinfo=Ext.create('Meeting.meetingSchedule',{});
	detailinfo.setHeight(document.getElementById('meetingSchedule').offsetHeight);
});
		</script>
		<style type="text/css">
			.x-grid-group-title{
				font-size:16;
				margin:10 0 10 0;
				font-family: "Microsoft Yahei", Arial, "宋体" !important;
			}
		</style>
	<body>
		<div id="meetingSchedule" style="height: 100%; width: 100%;">
		</div>
	</body>
</html>
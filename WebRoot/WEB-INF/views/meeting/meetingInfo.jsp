<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
		<meta name="renderer" content="webkit"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<title>参会信息录入</title>
		<link rel="stylesheet" type="text/css" href="resources/css/meeting.css"/>
		<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/icon.css'/>" />
		<script type="text/javascript">
			Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
			var ICONS_PATH = 'resources/icons';
		</script>
		<script type="text/javascript" src="resources/meeting/meetingInfo.js?r="+Math.random()></script>
		
	</head>
	<body>
		<div id="meetingInfo" style="height: 100%; width: 100%;"></div>
	</body>
</html>
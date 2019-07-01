<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript" src='<c:url value="/resources/js/jquery-1.8.3.min.js"/>'>
</script>
		<script type="text/javascript">
		var meeting={
			id:${result.data.id},
			title:  '${result.data.title}',
			content:  '${result.data.content}',
			undertakeUnit:  '${result.data.undertakeUnit}',
			remark:  '${result.data.remark}',
			contacter:  '${result.data.contacter}',
			phonenum:  '${result.data.phonenum}',
			typeJuge:  '${result.data.typeJuge}',
			meetStatu:  '${result.data.meetStatu}',
			forwardUnit:  '${result.data.forwardUnit}',
			oprUser:  '${result.data.oprUser}',
			oprUserUnit:  '${result.data.oprUserUnit}',
			updateTime:  '${result.data.updateTime}',
			oprstatus:  '${result.data.oprstatus}'
		};
		if (meeting.meetStatu == 1) {
			meeting.meetStatu='拟办中';
		}
		if (meeting.meetStatu == 2) {
			meeting.meetStatu='确定召开';
		}
		if (meeting.meetStatu == 3) {
			meeting.meetStatu=='已取消';
		}
		if (meeting.typeJuge == 1) {
			meeting.typeJuge='处理中';
		}
		if (meeting.typeJuge == 2) {
			meeting.typeJuge='已转走';
		}
var bd='meetingDetail'+meeting.id;

Ext.onReady(function() {
	requires : ['Meeting.views.InfoDetail'];
	Ext.QuickTips.init();

	var detailinfo=Ext.create('Meeting.views.InfoDetail',{
			detailStore:meeting,
			divrenderer:bd
		});
	detailinfo.setHeight(document.getElementById(bd).offsetHeight);
	});

		</script>
		<link rel="stylesheet" type="text/css" href="resources/css/meeting.css"/>
	</head>
	<body>
		<div id="meetingDetail${result.data.id}" style="height: 100%; width: 100%;"></div>
		<div id="west" style="height: 100%; width: 100%;"></div>
		<div id="center" style="height: 100%; width: 100%;"></div>
	</body>
</html>
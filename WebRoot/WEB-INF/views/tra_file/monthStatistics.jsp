<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>月办理量统计列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="resources/css/icon.css" />
		<script type="text/javascript">
		Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
		Ext.Loader.setPath('Wdbl.form', 'resources/wdbl/file_form');
		Ext.Loader.setPath('Wdbl.view', 'resources/wdbl/view');
		Ext.Loader.setPath('Wdbl.model', 'resources/wdbl/model');
		Ext.Loader.setPath('Wdbl.utils', 'resources/wdbl/utils');
		</script>
		
		<script type="text/javascript" src="resources/wdbl/reg_file/monthStatistics.js"></script>
	</head>
	<body>
		<div id="$month_statistics" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
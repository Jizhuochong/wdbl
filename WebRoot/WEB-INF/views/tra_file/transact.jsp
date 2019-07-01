<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
  <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 <base href="<%=basePath%>">
		<title>文件办理列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="resources/css/icon.css" />
		<script type="text/javascript" src="resources/js/printlabel.js"></script>
		<script type="text/javascript">
		Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
		Ext.Loader.setPath('Wdbl.form', 'resources/wdbl/file_form');
		Ext.Loader.setPath('Wdbl.view', 'resources/wdbl/view');
		Ext.Loader.setPath('Wdbl.model', 'resources/wdbl/model');
		Ext.Loader.setPath('Wdbl.utils', 'resources/wdbl/utils');
		</script>
		<script type="text/javascript" src="resources/wdbl/tra_file/init.js?num=<%=java.lang.Math.random()%>"></script>
	</head>
	<body>
		<div id="$main_tab_tra" style="height: 100%;width: 100%;"></div>
		<OBJECT ID='create_tra_file' style='WIDTH:0px; HEIGHT:0px' classid='clsid:385EC1A7-F1B2-446B-B289-4DC8DD4C935A' VIEWASTEXT></OBJECT>
	</body>
</html>
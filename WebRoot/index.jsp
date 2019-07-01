<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<base href="<%=basePath%>"/>
		<title>文电办理系统</title>
		<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
		<meta name="renderer" content="webkit"/>
		<meta http-equiv="pragma" content="no-cache"/>
		<meta http-equiv="cache-control" content="no-cache"/>
		<meta http-equiv="expires" content="0"/>
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
		<link rel="stylesheet" type="text/css" href="extjs/resources/ext-theme-classic/ext-theme-classic-all.css"/>
		<link rel="stylesheet" type="text/css" href="extjs/resources/css/TabScrollerMenu.css" />
		<link rel="stylesheet" type="text/css" href="resources/css/icon.css" />
		
		<script type="text/javascript" src="extjs/bootstrap.js"></script>
		<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
		<script type="text/javascript" src="resources/js/commons_util.js"></script>
		<script type="text/javascript" src="resources/js/modifyPwd.js"></script>
		<script type="text/javascript" src="resources/wdbl/wdbl_globals.js"></script>  
		<script type="text/javascript" src="resources/wdbl/utils/PagingToolBar.js"></script>  
		<script type="text/javascript" src="extjs/ux/TabScrollerMenu.js"></script>
		<script type="text/javascript" src="extjs/ux/MultiSelect.js"></script>
		<style type="text/css">
			body{
				font-size:14px;
			}
			.logout {
				background-image: url(images/man.gif) !important;
			}
			.modifyPwd {
				background-image: url(images/key.gif) !important;
			}
			.x-grid-record-red{
    			color: #FF0000;
 			}
 			tr.x-grid-record-yellow .x-grid-td {
    			background: YELLOW;
			} 
			x-grid-record-white .x-grid-td{
				background: WHITE;
			}
			#div_userinfo {
				font-size: 14px;
				background:'repeat-x';
			}
			#div_userinfo a {
				text-decoration: none;
				color: black;
			}
			.top_lbl {
				display: inline;
				margin-top: 15px;
			}
			.x-form-field, .x-form-text{font-family:'宋体',arial,verdana,sans-serif}
			.x-grid-cell{font-family:'Microsoft Yahei', sans-serif}
			.x-tab-active .x-tab-wrap .x-tab-button .x-tab-inner {
				color:#6B8E23;
			}
		</style>
		<script type="text/javascript">
			var sgif_path = '<%=path%>';
		</script>
		<script type="text/javascript" src="resources/wdbl/index.js"></script>  
   
	</head>
	<body>
		
	</body>
</html>
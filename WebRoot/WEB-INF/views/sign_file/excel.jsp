<%@ page language="java" contentType="application/vnd.ms-excel; charset=GBK"  pageEncoding="GBK"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String filename = new String(("退件登记列表").getBytes("GBK"),"ISO-8859-1");     
	response.setHeader("Content-disposition","attachment; filename="+filename+".xls");
%>
<html>
<head>
	<meta name="Generator" content="Microsoft Excel 11">  
</head>
<body>
	<table border="1" width="700px">
		<c:if test="${empty data.result}">
			<tr>
				<th height="100" colspan="2" border=0 align="center" style="font-family:宋体; color:black; font-size:32px;">文&nbsp;件&nbsp;签&nbsp;收&nbsp;登&nbsp;记&nbsp;表</th>
			</tr>
			<tr>
				<td colspan="2" height="50" border=0  align="right">${backTime }&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    </tr>
		    <tr>
		    	<th height="70" width="50">序号</th>
		    </tr>
		    <tr>
		    	<th width="100">来文单位</th>
		    </tr>
		    <tr>
		    	<th width="150">文件标题</th>
		    </tr>
		    <tr>
		    	<th width="100">文号</th>
		    </tr>
		    <tr>
		    	<th width="100">签收人</th>
		    </tr>
		    <tr>
		    	<th width="100">退件时间</th>
		    </tr>
		    <tr>
		    	<th width="100">备注</th>
		    </tr>
		</c:if>
    	<c:if test="${not empty data.result}">
			<tr>
				<th height="100" colspan="${data.totalCount+1}" border=0 align="center" style="font-family:宋体; color:black; font-size:32px;">文&nbsp;件&nbsp;签&nbsp;收&nbsp;登&nbsp;记&nbsp;表</th>
			</tr>
			<tr>
				<td colspan="${data.totalCount+1}" height="50" border=0  align="right">${backTime }&nbsp;&nbsp;&nbsp;&nbsp;</td>
		    </tr>
		    <tr>
		    	<th height="70" width="50">序号</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    		<td align="center">${vs.count }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
				   		<td align="center">${vs.count }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="70" width="80">来文单位</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    		<td align="center">${rst.fromUnit }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
			    		<td align="center">${rst.fromUnit }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="70" width="150">文件标题</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    	<td>${rst.title }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
			    	<td>${rst.title }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="70" width="80">文号</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    		<td align="center">${rst.docNumber }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
			    		<td align="center">${rst.docNumber }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="70" width="80">签收人</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
<%-- 			    		<td colspan="${data.totalCount}">&nbsp;</td> --%>
			    		<td>&nbsp;</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
			    		<td>&nbsp;</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="50" width="80">退件时间</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    	<td align="center">${fn:substring(rst.backTime, 11, 17) }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
			    	<td align="center">${fn:substring(rst.backTime, 11, 17) }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
		    <tr>
		    	<th height="70" width="80">备注</th>
			    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
			    	<c:if test="${vs.count == 1 }">
			    		<td align="center">${rst.remark }</td>
				    </c:if>
				    <c:if test="${vs.count > 1 }">
				   		<td align="center">${rst.remark }</td>
				    </c:if>
			    	</c:forEach>
		    </tr>
	     </c:if>
<%-- 	    <tr>
	    	<th height="70" width="50">序号</th>
	    	<th width="100">来文单位</th>
	    	<th width="150">文件标题</th>
	    	<th width="100">文号</th>
	    	<th width="100">签收人</th>
	    	<th width="100">退件时间</th>
	    	<th width="100">备注</th>
	    </tr>
	   
	    <c:if test="${not empty data.result}">
	    	<c:forEach var="rst" items="${data.result }" varStatus="vs">
	    	<c:if test="${vs.count == 1 }">
	    	<tr>
		    	<td align="center">${vs.count }</td>
		    	<td align="center">${rst.fromUnit }</td>
		    	<td>${rst.title }</td>
		    	<td align="center">${rst.docNumber }</td>
		    	<td rowspan='${data.totalCount}'>&nbsp;</td>
		    	<td align="center">${fn:substring(rst.backTime, 11, 17) }</td>
		    	<td align="center">${rst.remark }</td>
		    </tr>
		    </c:if>
		    <c:if test="${vs.count > 1 }">
		    <tr>
			    <td align="center">${vs.count }</td>
			    <td align="center">${rst.fromUnit }</td>
			    <td>${rst.title }</td>
			    <td align="center">${rst.docNumber }</td>
			    <td align="center">${fn:substring(rst.backTime, 11, 17) }</td>
			    <td align="center">${rst.remark }</td>
		    </tr>
		    </c:if>
	    	</c:forEach>
	    </c:if> --%>
  </table>
 </body>
</html>
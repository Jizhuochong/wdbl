<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<object id="objGun" style="WIDTH: 0px; HEIGHT: 0px" classid="clsid:9E64D350-96BB-4D20-A978-978F2CE31139" VIEWASTEXT></object>
<head>
	<title>扫描条码</title>
	<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
	<script type="text/javascript" src="resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
	function pageOpen(){
		document.all("objGun").OpenPort(4, 9600, 800);
	}
	
	//关闭
	function pageClose() {
	  document.all("objGun").ClosePort();
	
	}
	function search() {
		var search_val={};
		var form_array = $("#sForm").serializeArray();
		var temp;
		$.each(form_array,function(){
			temp = this;
			if(temp.name == 'barNo' && temp.value)
				search_val.barNo=temp.value;
			if(temp.name == 'docNumber' && temp.value)
				search_val.docNumber=temp.value;
			if(temp.name == 'iTitle' && temp.value)
				search_val.iTitle=temp.value;
			if(temp.name == 'labelInfo' && temp.value)
				search_val.labelInfo=temp.value;
		});
		if(parent.tabobjAny)
			parent.tabobjAny.$analysis_code(search_val);
		document.all("objGun").ClosePort();
	}
	function pageReadCode(code){
		document.getElementById("labelInfo").value = code;
		var oStr = document.getElementById("labelInfo").value.split("^");
		if(oStr[1])
	  		document.getElementById("barNo").value = oStr[1]; 
		if(oStr[4])
        	document.getElementById("docNumber").value = oStr[4]; 
		if(oStr[6])
        	document.getElementById("iTitle").value = oStr[6];  
	}
</script>
</head>
<body onunload="javascript:pageClose();">
<form action="" id="sForm">
<Table border=0 width="98%" align="center">
	<tr>
	    <td>条码编号: </td><td><input id="barNo" name="barNo" style="width:100%" type="text"></td>
	</tr>
    <tr>
	    <td>发文字号: </td><td><input id="docNumber" name="docNumber" style="width:100%" type="text"></td>
    </tr>
   <tr>
	    <td> 标&nbsp;&nbsp;题: </td><td><textarea id="iTitle" name="iTitle" style="height:40px;width:100%"></textarea></td>
    </tr>  
    <tr>
	    <td>条码信息: </td><td><textarea id="labelInfo" name="labelInfo" style="height:80px;width:100%"> </textarea></td>
    </tr>
    <tr>
	    <td colspan=2 align="center"> <br/><input type="button" value="搜 索" onclick="search()"></td>
    </tr>
</Table>
</form>
<script language="javascript" for="objGun" event="ReadCode(code)">
	pageReadCode(code);
</script>
</body>
<script language="javascript">
	pageOpen();
</script>
</html>

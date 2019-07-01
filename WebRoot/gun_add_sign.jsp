<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<object id="objGun" style="WIDTH: 0px; HEIGHT: 0px" classid="clsid:9E64D350-96BB-4D20-A978-978F2CE31139" VIEWASTEXT></object>
<head>
	<title>扫描条码</title>
	<meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
	<script type="text/javascript" src="resources/js/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
	var formunit="";
	var barno;
	function pageOpen(){
		document.all("objGun").OpenPort(4, 9600, 800);
	}
	
	//关闭
	function pageClose() {
		document.all("objGun").ClosePort();
	}
	function add(rmk) {
		var add_val={remark:rmk};
		var form_array = $("#sForm").serializeArray();
		var temp;
		$.each(form_array,function(){
			temp = this;
			if(temp.name == 'barNo' && temp.value)
				add_val.barNo=temp.value;
			if(temp.name == 'docNumber' && temp.value)
				add_val.docNumber=temp.value;
			if(temp.name == 'iTitle' && temp.value)
				add_val.title=temp.value;
			if(temp.name == 'fromUnit' && temp.value)
				add_val.fromUnit=temp.value;
		});
		if(parent){
			parent.$analysis_code(add_val);
			return;
		}	
	}
	function change(oStr){
		var url ="sign_file/search.do"; 
		$.post(url,{barNo:oStr[1]},function(data){
			if(data.data != null && data.data != ""){
				formunit=data.data;
			}
			if(oStr[1] && oStr[1] != 'null')
	  			document.getElementById("barNo").value = oStr[1];
			if(oStr[4] && oStr[4] != 'null')
        		document.getElementById("docNumber").value = oStr[4]; 
			if(oStr[5] && oStr[5] != 'null'){
				if(formunit && formunit != null){
    				document.getElementById("fromUnit").value =formunit; 
    			}else{
    				document.getElementById("fromUnit").value = oStr[5];
    			}
    		}
			if(oStr[6] && oStr[6] != 'null'){
        		document.getElementById("iTitle").value = oStr[6];
			}
		},'json');
		
	}
	function pageReadCode(code){
		document.getElementById("labelInfo").value = code;
		var oStr = document.getElementById("labelInfo").value.split("^");
		barno = oStr[1];
    	change(oStr);
	}
	
</script>
</head>
<body onunload="javascript:pageClose();">
<form action="" id="sForm">
<Table border=0 width="98%" align="center">
	<tr>
	    <td>条码编号: </td><td><input id="barNo" name="barNo" style="width:100%"  type="text"></td>
	</tr>
	<tr>
	    <td>来文单位: </td><td><input id="fromUnit" name="fromUnit" style="width:100%" type="text"></td>
	</tr>
    <tr>
	    <td>发文字号: </td><td><input id="docNumber" name="docNumber" style="width:100%" type="text"></td>
    </tr>
   <tr>
	    <td> 标&nbsp;&nbsp;题: </td><td><textarea id="iTitle" name="iTitle" style="height:40px;width:100%"></textarea></td>
    </tr>
     <tr>
	    <td>条码信息: </td><td><textarea id="labelInfo" name="labelInfo" style="height:80px;width:100%"></textarea></td>
    </tr>  
    <tr>
	    <td colspan=2 align="center"> <br/>
	    <input type="button" value="添加阅件" onclick="add('阅件')">&nbsp;&nbsp;
	    <input type="button" value="添加批件" onclick="add('批件')"></td>
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
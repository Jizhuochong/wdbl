<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="extjs/resources/css/ext-all.css"/>
	<link rel="stylesheet" type="text/css" href="extjs/resources/ext-theme-classic/ext-theme-classic-all.css"/>
	<script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
<title>文件扫描</title>
</head>
<body>
<% String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/"; %>
<object id = "ocx" classid = "CLSID:49903B72-9F44-41E1-A79B-B85A8BCEB89A"></object>
<object  classid="clsid:1C1F24E1-6636-4460-9EFB-25B169B77168" width="0" height="0" id="upload_obj"></object>
<script language="javascript">
/**
 * 上传文件所需函数
 */
function setFileContainer(obj,sID){
	parent_file_container = obj;
	sessionID = sID;
}
var parent_file_container,sessionID;
function uploadFile(img_path)
{  
		var up_obj = document.getElementById("upload_obj");
		up_obj.Local_File=img_path;
		up_obj.Form_Data = "?a=1";
		up_obj.url = "<%=url%>wdbl/reg_file/" + sessionID + "/activeUpload.do;jsessionid=<%=session.getId()%>";
		var backStr =up_obj.upload();
		var backObj = Ext.decode(backStr);
		parent_file_container.addFile(backObj.data);
		//window.close();
}
function Open(){  
	 ocx.CloseScanner();
     ocx.DSMode(0);
     var pp= ocx.openScanner();
	// ocx.SetScan();
     if(1==pp){
     	alert("已打开扫描仪");
	 }else{
		var Error = ocx.GetErrorCode();
		alert(Error);
	}
}
	
function Scan(){
	ocx.ShowUI = 0;
	ocx.AutoScan = 0;
	var Path= document.getElementById("FilePath").value;
	var FPrefix= document.getElementById("FilePrefix").value;
	ocx.SetImageName(Path,FPrefix, 1, 5);
	var Resol =  document.getElementById("Resolution").value;
	ocx.ScanResolution = Resol;
	var currSelectIndex = document.getElementById("PicType").selectedIndex;    
	ocx.ScanPixelType = currSelectIndex;
	var picTypeEx = document.getElementById("PicTypeEx").selectedIndex;
	ocx.ImageFormat = picTypeEx;
	var scanTypeIdx = document.getElementById("ScannerType").selectedIndex;
	ocx.ScanSourceType = scanTypeIdx;
	var TiffIdx = document.getElementById("TiffCom").selectedIndex;
	ocx.TiffCompressType = TiffIdx;
	var comRate = document.getElementById("CompressRate").value;
	ocx.CompressionRate = comRate;
	ocx.AutoDeskew = 1;
	var ContVal = document.getElementById("Contrast").value;
	ocx.Contrast = ContVal;
	var bSmooth = document.getElementById("SetSmooth").checked;
	var nStrength = document.getElementById("smoothStrength").selectedIndex;
	var bremoveBG = document.getElementById("removeBG").checked;
	ocx.TextDirection = 1;
	ocx.Contrast=0;
	ocx.Brightness=0;
	ocx.Gamma=1;
	if(nStrength == 0){
		nStrength = 2;
	}
	
	if(bSmooth){
		ocx.SetBackgroundSmooth(bSmooth, 0,0,0,0,nStrength,bremoveBG);
	}else{
		ocx.SetBackgroundSmooth(bSmooth, 0,0,0,0,nStrength,bremoveBG);
	}
	
	if (1==ocx.Scan(-1,0)){
		var img_count=ocx.GetScanImageCount();
		var savepatha="test"
		var imgpaths=document.getElementById("imgpath").value;
	//alert(imgpaths);
       //ocx.UploadToFtpServer(img_count+"?"+imgpaths,"11.43.80.31","amsftp","amsftp", 21,savepatha,false );
          alert("扫描成功,共计"+img_count+"张");
	}
}

function Close(){
    if(1==ocx.CloseScanner())
    {
       alert("已关闭扫描仪");
    }
}

function save(){
	var imgpath=document.getElementById("imgpath").value;
	if(imgpath.indexOf("|") != -1){
  		var paths = imgpath.split("|");
  		for (i=1; i<paths.length; i++)   
  		{   
  			uploadFile(paths[i-1]);  
  		} 

  	}else{
  		uploadFile(imgpath);
  	}
	window.close();
}

function Rotation(){
	var AngleValue = document.getElementById("Angle").value;
	if(0 == AngleValue)
	{
		return;
	}
	var ImgPathFor = "data:image/gif;base64,"
	var nRet = ocx.RotationImage(AngleValue);
	var ImgValue = ImgPathFor + ocx.GetImageBase64String();
}
</script>
<script  LANGUAGE=Javascript  FOR= "ocx" EVENT= "PostScanEveryPage(bSuccess)" defer>
	var ImgPathValue; 
	var imgpath="";
	if(2 != document.getElementById("PicTypeEx").selectedIndex)
	{
		ImgPathValue = ocx.GetCurrentScanImagePath();
		imgpath+=ImgPathValue+"|";
	}
	else
	{
		//var ImgPathFor = "data:image/gif;base64,"
		//ImgPathValue = ImgPathFor + GetImageBase64String();
		//imgpath+=ImgPathValue+"|";
		ImgPathValue = ocx.GetCurrentScanImagePath();
		imgpath+=ImgPathValue+"|";
	}
    document.getElementById("picshow").innerHTML+="<img src="+ ImgPathValue+" width=200 height=300 />&nbsp;&nbsp;";
	document.getElementById("imgpath").value+=ImgPathValue+"|";
</script>

<script  LANGUAGE=Javascript  FOR= "ocx" EVENT= "PostScanEveryPage(bSuccess)" defer>
	var ImgPathValue; 
	var imgpath="";
	if(2 != document.getElementById("PicTypeEx").selectedIndex)
	{
		ImgPathValue = ocx.GetCurrentScanImagePath();
		imgpath+=ImgPathValue+"|";
	}
	else
	{
		//var ImgPathFor = "data:image/gif;base64,"
		//ImgPathValue = ImgPathFor + GetImageBase64String();
		//imgpath+=ImgPathValue+"|";
		ImgPathValue = ocx.GetCurrentScanImagePath();
		imgpath+=ImgPathValue+"|";

	}
    document.getElementById("picshow").innerHTML+="<img src="+ ImgPathValue+" width=200 height=300 />&nbsp;&nbsp;";
	document.getElementById("imgpath").value+=ImgPathValue+"|";
</script>

<table style = "width:85%;height:100%" align="center" border = "1">
	<tr> <th style = "height:50px">文件展示</th><th style = "width:100%; height:50px">扫描设置</th></tr>
    <tr>
	<td style = "width:60%; height:100%">
	   <div id="picshow"></div>
	</td>
	<td rowspan=2>
	    <table border = "1" style = "width:100%; height:100%">
		<tr>
			<td align = "center" width="150">图像路径</td>
		    <td><input id = "FilePath" type = "text" value = "c:\image" /></td>
		</tr>
		<tr>
			<td align = "center" width="150">图像前缀</td>
			<td><input id = "FilePrefix" type = "text" value = "abc" /></td>
		</tr>
		<tr>
			<td align = "center" width="150">分辨率</td>
		    <td><input id = "Resolution" type = "text" value = "200" /></td>
		</tr>
		<tr>
		    <td align = "center" width="150">图标保存类型</td>
		    <td>
				<select id="PicType">    
				         <option value="1111">BW</option>    
				         <option value="2222">GRAY</option>    
				         <option value="3333">RGB</option>    
				</select> 
		    </td>
		</tr>
		<tr>
		    <td align = "center" width="150">扫描仪类型</td>
		    <td>
				<select id="ScannerType">    
				         <option value="1111">平板</option>    
				         <option value="2222">ADF单扫</option>    
				         <option value="3333">ADF双扫</option>    
				</select>
		    </td>
		</tr>
		<tr>
		    <td align = "center" width="150">图标保存格式</td>
		    <td>
				<select id="PicTypeEx">    
				         <option value="1">bmp</option>    
				         <option value="2">jpg</option>    
				         <option value="3">tif</option>   
				         <option value="4">multitif</option>    
				         <option value="5">pdf</option>    
				         <option value="6">multipdf</option>   	
						 <option value="7">可检索的单页PDF</option>   			 
				</select>
		    </td>
		</tr>
		<tr>
		    <td align = "center" width="150">Tiff压缩</td>
		    <td>
				<select id="TiffCom">    
				         <option value="1">NULL</option>    
				         <option value="2">JPG压缩</option>    
				         <option value="3">G4压缩</option>     			 
				</select>
		    </td>
		</tr>
		<tr>
		    <td align = "center" width="150">压缩质量</td>
		    <td> <input id = "CompressRate" type = "text" value = "20" /></td>
		</tr>
		<tr>
			<td align = "center" width="150">对比度</td>
			<td> <input id="Contrast" type = "text" value = "0"/></td>
		</tr>
		<tr>
		    <td align = "center" width="150">使用背景平滑功能</td>
		    <td> <input type="checkbox" id = "SetSmooth" value="1" > </td>
		</tr>
		<tr>
		    <td align = "center" width="150">平滑强度</td>
		    <td>
				<select id="smoothStrength">    
			         <option value="1">默认</option>    
			         <option value="2">低</option>    
			         <option value="3">中</option>    
			         <option value="4">高</option>     			 
				</select> 
		    </td>
		</tr>
		<tr>
		    <td align = "center" width="150">删除背景 </td>
		    <td>
		    	<input type="checkbox" id = "removeBG" value="1" >
		    	
		    </td>
		</tr>
		<tr>
			<td align = "center" width="150">旋转角度</td>
			<td>
				<input id=btnOpen  type="button"  value="旋转" onclick="Rotation()">
				<input id = "Angle" type = "text" value = "0" />
		    </td>
		</tr>
		<tr>
			<td colspan=2>
				<input  id="imgpath" type="hidden" value="" style="width:200px;">
		    </td>
		</tr>
		<tr>
		    <td align = "center" colspan=2 height="50px">
			    <input	id=btnOpen  type="button"  value="打开扫描仪" onclick="Open()">
				<input	id=btnScan  type="button"  value="扫描" onclick="Scan()">
				<input	id=btnClose type="button"  value="关闭扫描仪" onclick="Close()">
				<input	id=btnClose type="button"  value="保存" onclick="save()">
		    </td>
		</tr>
	    </table>
	</td>
    </tr>
</table>

</body>
</html>
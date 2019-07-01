<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<% String url = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort()+"/"; %>
<script type="text/javascript">
var parent_file_container, sessionID;
function setFileContainer(obj, sID) {
	parent_file_container = obj;
	sessionID = sID;
}
function uploadFile(img_path) {
	var up_obj = document.getElementById("upload_obj");
	up_obj.Local_File = img_path;
	up_obj.Form_Data = "?a=1";
	up_obj.url = "<%=url%>wdbl/reg_file/" + sessionID + "/activeUpload.do;jsessionid=<%=session.getId()%>";
	var backStr = up_obj.upload();
	var backObj = Ext.decode(backStr);
	parent_file_container.addFile(backObj.data);
	window.close();
}
</script>
    <meta charset="utf-8" />
    <title>拍照</title>
    <link href="resources/css/sstyle.css" rel="stylesheet" />
    <script type="text/javascript" src="extjs/ext-all.js"></script>
	<script type="text/javascript" src="extjs/locale/ext-lang-zh_CN.js"></script>
	<script type="text/javascript"  src="resources/js/jq.js"></script>
    <script type="text/javascript" src="resources/js/rescore.js"></script>
</head>
<body>
    <div class="main">
        <object id="iVideo" codebase="resources/res/IVideo.cab" classid="clsid:8CAA584A-AC84-445B-89D6-D4BD455EAF96"></object>
        <object  classid="CLSID:1C1F24E1-6636-4460-9EFB-25B169B77168" width="0" height="0" id="upload_obj"></object>
        <table>
            <tr>
                <td>
                    <input id="opendevices" type="button" value="打开设备" /></td>
                <td rowspan="2">
                    <input id="snap" type="button" value="拍摄" style="height: 70px; font-size: 35px" /></td>
            </tr>
            <tr>
                <td>
                    <input id="closedevices" type="button" value="关闭设备" /></td>
            </tr>
            <tr>
                <td>
                    <input class="rotate" data-angle="90" type="button" value="转90°" /></td>
                <td>
                    <input class="rotate" data-angle="180" type="button" value="转180°" /></td>
            </tr>
            <tr>
                <td>
                    <input class="rotate" data-angle="270" type="button" value="转270°" /></td>
                <td>
                    <input class="rotate" data-angle="0" type="button" value="转0°" /></td>
            </tr>
            <tr>
                <td>
                    <select id="VideoResolution">
                    </select></td>
                <td>
                   <input type="button" value="切换摄像头" id="btnToggleDevice" /></td>
            </tr>
            <tr>
                <td>
                    <fieldset>
                        <legend>矫正类型</legend>
                        <label>
                            <input value="0" name="crop" type="radio"  checked="checked"/>从不矫正</label>
                        <label>
                            <input value="1" name="crop" type="radio" />智能矫正</label>
                        <label>
                            <input value="2" name="crop" type="radio" />手动裁切</label>
                    </fieldset>
                </td>
                <td>
                    <fieldset>
                        <legend>拍摄类型</legend>
                        <label>
                            <input value="0" name="color" type="radio" checked="checked" />彩色</label>
                        <label>
                            <input value="1" name="color" type="radio" />灰度</label>
                        <label>
                            <input value="2" name="color" type="radio" />黑白</label>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="2" class="exp">曝光度
                    <input class="setExposure" type="button" value="<" />
                    <input type="text" readonly="" value="-3" maxlength="1" class="Exposure" />
                    <input id="Button2" type="button" value=">" class="setExposure" />
                    <label>
                        <input id="autoExposure" type="checkbox" />自动</label></td>
            </tr>
            <tr>
                <td colspan="2" class="qua">压缩比例
                    <input class="setQuality" type="button" value="<" />
                    <input type="text" readonly="" value="80" maxlength="2" class="Quality" />
                    <input type="button" value=">" class="setQuality" /></td>
            </tr>
            <tr>
                <td colspan="2" class="savetype">
                    <fieldset>
                        <legend>保存类型</legend>
                        <label>
                            <input value="jpeg" type="radio" name="imageFormat" checked="checked" />*.Jpeg</label>
                        <label>
                            <input value="bmp" type="radio" name="imageFormat" />*.Bmp</label>
                        <label>
                            <input value="png" type="radio" name="imageFormat" />*.Png</label>
                        <label>
                            <input value="tiff" type="radio" name="imageFormat" />*.Tiff</label>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td>
                    <label>
                        <input type="checkbox" id="barcode" />条码识别</label></td>
                <td>
                    <input type="button" value="视频属性" id="DevicesProperty" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea id="log" readonly="readonly"></textarea></td>
            </tr>
            <tr>
                <td>
                    <input type="button" value="拍PDF图片" id="PDFImage" /></td>
                <td>
                    <input type="button" value="生成PDF" id="CreatePdf" /></td>
            </tr>
            
        </table>
    </div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>全部文件列表</title>
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" href="resources/css/icon.css" />
    <script type="text/javascript" src="resources/js/printlabel.js"></script>
    <script type="text/javascript">
    Ext.Loader.setPath('Ext.ux', 'extjs/ux/');
    Ext.Loader.setPath('Wdbl.form', 'resources/wdbl/file_form');
    Ext.Loader.setPath('Wdbl.view', 'resources/wdbl/view');
    Ext.Loader.setPath('Wdbl.model', 'resources/wdbl/model');
    Ext.Loader.setPath('Wdbl.utils', 'resources/wdbl/utils');
    function parentAddPhoto(obj){
      alert("ok");
    }
    </script>
    
    <script type="text/javascript" src="resources/wdbl/any_file/init.js">
    </script>
    
  </head>
  <body>

    <div id="$main_any_tab" style="height: 100%;width: 100%;"></div>
    <OBJECT ID='create_any_file' style='WIDTH:0px; HEIGHT:0px' classid='clsid:385EC1A7-F1B2-446B-B289-4DC8DD4C935A' VIEWASTEXT></OBJECT>
    <Table>
  </body>
</html>
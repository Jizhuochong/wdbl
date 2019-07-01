<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>市委市政府正式文件</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
            var grid_common =  Ext.create('Wdbl.fileprocess.grid_common');
            var cityGovOfficial =  Ext.create('Wdbl.fileprocess.file_cityGovOfficial');        
            var process_common =  Ext.create('Wdbl.fileprocess.process_common');   
           
		
			var grid_store = grid_common.grid_store('filehandle/listLoad.do');
			var s_file_title = grid_common.s_field('文件标题');
			
			grid_store.on('beforeload', function (store1, options) {
        		var new_params = { docType:1,title: s_file_title.getValue()+'##true'};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			
            var gridpanel = Ext.create("Ext.grid.Panel", {
            	selModel: grid_common.selModel(),
                layout : 'fit',
                autoHeight:true,
                title: "市委市政府正式文件",
                renderTo: "grid_filecitygovofficial",
                frame: true,
                store: grid_store,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),
                	{ header: "文号", dataIndex: "docNumber",  flex : 3},    
                	{ header: "标题", dataIndex: "title",  flex : 6} ,
                	{ header: "发文单位", dataIndex: "formUnit",  flex : 6} ,
                    { header: "接收时间", dataIndex: "receiveTime",  flex : 3},
                    { header: "状态", dataIndex: "handlestatus",  flex : 3}
                ],
                tbar: [
                		grid_common.bu('打印审批单',f_print),grid_common.bu('发送拟办',f_senddraft),
                		grid_common.bu('拟办',f_draft),grid_common.bu('送阅',f_sendread),
                		grid_common.bu('办结',f_end),
                		{xtype : "tbfill"},
                		s_file_title,grid_common.bu_search(grid_store)
                ] ,
                bbar: grid_common.toolbar(grid_store)
            });
          gridpanel.setHeight(document.getElementById("grid_filecitygovofficial").offsetHeight); 
          grid_store.loadPage(1); 
          
          //判断选择
          function checkchoose(){
          		var selModel = gridpanel.getSelectionModel();
				if(!selModel.hasSelection()){
					Ext.Msg.alert("提醒", "未选择！");
                	return null;
				}
				var selected = selModel.getSelection();
				if(selected.length!=1){
					Ext.Msg.alert("提醒", "只能编辑一条记录！");
                	return null;
				}
				return selected[0].data.id;
          } 
          //获取选择行数据
          function getSelectedData(msg){
        	  if(!msg){
        		  msg = "该操作只能选择一条记录!";
        	  }
          		var selModel = gridpanel.getSelectionModel();
				if(!selModel.hasSelection()){
					Ext.Msg.alert("提醒", "未选择！");
                	return null;
				}
				var selected = selModel.getSelection();
				if(selected.length!=1){
					Ext.Msg.alert("提醒",msg);
                	return null;
				}
				return selected[0].data;
          } 
          //发送拟办        
          function f_senddraft(){
          		var id = checkchoose();
          		if(id == null){return;}
            	cityGovOfficial.f_senddraft(id,grid_store,gridpanel);
          }
           //拟办        
          function f_draft(){
          		var id = checkchoose();
          		if(id == null){return;}
            	process_common.f_draft(id,grid_store,gridpanel,'filehandle');
          }
          //打印审批单 
          function f_print(){
        		var row = getSelectedData();
        		var _url = '<%=basePath%>'+'file_export/'+WDBL$Globals.DocTypeNames[row.docType]+'/' + row.id +'/export.do';
        		window.open(_url);
          }
          //送阅
          function f_sendread(){
          		var id = checkchoose();
          		if(id == null){return;}
          		var tab = rightPanel.getComponent('tabfcgoapproval');
          		if(tab!=null){tab.close();}
            	addTab ('fcgoapproval','市委市政府正式文件','fcgoapproval/toList.do?fileid='+id)
          }
          //办结      
          function f_end(){
          		var id = checkchoose();
          		if(id == null){return;}
          }
          
      });
     		
	</script>  

 
	</head>
	<body>  
		<div id="grid_filecitygovofficial" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
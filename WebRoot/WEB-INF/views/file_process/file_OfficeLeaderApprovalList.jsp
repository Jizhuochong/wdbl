<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>局长批示件</title>
		
		<script type="text/javascript">
         Ext.onReady(function () {
            var grid_common =  Ext.create('Wdbl.fileprocess.grid_common');
            var process_common =  Ext.create('Wdbl.fileprocess.process_common');
           
		
			var grid_store = grid_common.grid_store('filehandle/listLoad.do');
			var s_file_title = grid_common.s_field('文件标题');
			
			grid_store.on('beforeload', function (store1, options) {
        		var new_params = { docType:5,title: s_file_title.getValue()+'##true'};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			
            var gridpanel = Ext.create("Ext.grid.Panel", {
            	selModel: grid_common.selModel(),
                layout : 'fit',
                autoHeight:true,
                title: "局长批示件",
                renderTo: "grid_fileofficeleaderapproval",
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
                		grid_common.bu('打印阅批单',f_print),grid_common.bu('发送拟办',f_senddraft),
                		grid_common.bu('拟办',f_draft),grid_common.bu('领导意见',f_leaderop),
                		grid_common.bu('转送',f_send),grid_common.bu('办结',f_end),
                		{xtype : "tbfill"},
                		s_file_title,grid_common.bu_search(grid_store)
                ] ,
                bbar: grid_common.toolbar(grid_store)
            });
          gridpanel.setHeight(document.getElementById("grid_fileofficeleaderapproval").offsetHeight); 
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
          //打印阅批单
          function f_print(){
            	var id = checkchoose();
          		if(id == null){return;}
          }
          //领导意见  
          function f_leaderop(){
          		var id = checkchoose();
          		if(id == null){return;}
          		process_common.f_leaderop(grid_store,gridpanel,id,'processrecord')
          		
          }
          //转送       
          function f_send(){
          		var id = checkchoose();
          		if(id == null){return;}
          }
          //办结      
          function f_end(){
          		var id = checkchoose();
          		if(id == null){return;}
          }
            //发送拟办       
          function f_senddraft(){
          		var id = checkchoose();
          		if(id == null){return;}
            	process_common.f_senddraft(id,grid_store,gridpanel,'filehandle');
          }
          //拟办        
          function f_draft(){
          		var id = checkchoose();
          		if(id == null){return;}
            	process_common.f_draft(id,grid_store,gridpanel,'filehandle');
          }
         
        
          
      });
     		
	</script>  

 
	</head>
	<body>  
		<div id="grid_fileofficeleaderapproval" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
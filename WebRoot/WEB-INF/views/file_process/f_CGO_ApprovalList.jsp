<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>市委市政府正式文件-领导审批</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
            var grid_common =  Ext.create('Wdbl.fileprocess.grid_common');
            var processrecord_common =  Ext.create('Wdbl.fileprocess.processrecord_common');
            var f_CGO_Approval =  Ext.create('Wdbl.fileprocess.f_CGO_Approval');
            

			var grid_store = processrecord_common.grid_store('fcgoapproval/listLoad.do');
			var s_filed1 = grid_common.s_field('领导名称');
			
			grid_store.on('beforeload', function (store1, options) {
        		var new_params = { fileid:${fileid},title: s_filed1.getValue()+'##true'};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			
            var gridpanel = Ext.create("Ext.grid.Panel", {
            	selModel: grid_common.selModel(),
                layout : 'fit',
                autoHeight:true,
                title: "${filebean.docNumber}—>领导审批",
                renderTo: "grid_fcgoapproval",
                frame: true,
                store: grid_store,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),
                	{ header: "名称", dataIndex: "name",  flex : 3},    
                	{ header: "职务", dataIndex: "duty",  flex : 3} ,
                	{ header: "审批结果", dataIndex: "approvalResult",  flex : 3} ,
                    { header: "录入时间", dataIndex: "sys_date",  flex : 3}
                ],
                tbar: [ grid_common.bu('增加',f_add), grid_common.bu('修改',f_edit),
                		grid_common.bu('删除',f_del),
                		{xtype : "tbfill"},
                		s_filed1,grid_common.bu_search(grid_store)
                ] ,
                bbar: grid_common.toolbar(grid_store)
            });
          gridpanel.setHeight(document.getElementById("grid_fcgoapproval").offsetHeight); 
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
          //增加        
          function f_add(){
            	f_CGO_Approval.f_edit(null,grid_store,gridpanel,'增加',${fileid});
          }
          //修改     
          function f_edit(){
          		var id = checkchoose();
          		if(id == null){return;}
            	f_CGO_Approval.f_edit(id,grid_store,gridpanel,'修改',null);
          }
          //删除     
          function f_del(){
            	f_CGO_Approval.f_del(grid_store,gridpanel);
          }
          
          
          
      });
     		
	</script>  

 
	</head>
	<body>  
		<div id="grid_fcgoapproval" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
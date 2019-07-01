<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>电话记录</title>
		
		<script type="text/javascript">
        Ext.onReady(function () {
        	Ext.QuickTips.init(); 
            
            var store_filephonerecord = Ext.create("Ext.data.JsonStore", {
            	fields: [
            	 	'id','telephone','phone_person','phone_unit','phone_time','phone_content','leader_opinion','handle_result','process_state'
                ],
                pageSize: 20,
                proxy: {
                    type: "ajax",
                    url: "filephonerecord/listLoad.do",
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                },
                remoteSort : false,
				sortInfo : {sort : 'phone_time',dir : 'desc'}
            });

		
			var s_filed_file_telephone = Ext.create('Ext.form.TextField', {
		    	fieldLabel: '电话',
	    		labelAlign: 'right',
         		labelWidth: 60,
				emptyText:'输入要查询的内容',
			 	width:200
			}); 
			var searchebu = Ext.create('Ext.Button', {
				pressed : true,
				text:'搜索',
				handler : function () {
	            	store_filephonerecord.loadPage(1);
	        	}
			});

			store_filephonerecord.on('beforeload', function (store1, options) {
        		var new_params = {telephone:s_filed_file_telephone.getValue()};
       		 	Ext.apply(store1.proxy.extraParams, new_params);
    		});
			 store_filephonerecord.loadPage(1);
			
			var leaderbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'领导批示',
				handler : f_leader
			});
			var sendbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'转送',
				handler : f_send
			});
			var finishbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'办结',
				handler : f_finish
			});
			var printbu= Ext.create('Ext.Button',{
				pressed : true,
				text:'打印记录单',
				handler : f_print
			});
			
			
			
			var toolbar = Ext.create('Ext.PagingToolbar', {
                    store: store_filephonerecord,
					beforePageText:"第 ",  
					afterPageText:"页，共 {0} 页",  
					firstText:"首页",  
					prevText:"上一页",  
					lastText:"末页",  
					nextText:"下一页",  
					refreshText:"刷新",  
					emptyMsg:"没有要显示的数据",  
					displayInfo: true,  
					displayMsg:"<span style='font-size:13px;'>第{0}-{1}条,共{2}条</span>",  
                    emptyMsg: "没有数据"
                });
			
			var selModel = Ext.create('Ext.selection.CheckboxModel'); 
            var gridpanel_filephonerecord = Ext.create("Ext.grid.Panel", {
            	selModel: selModel,
                layout : 'fit',
                autoHeight:true,
                title: "电话记录",
                renderTo: "grid_filephonerecord",
                frame: true,
                store: store_filephonerecord,
                loadMask:true,
                columnLines:true,
                forceFit : true,
                height:140, 
                columns: [
                	Ext.create('Ext.grid.RowNumberer',{text : '',width:30}),
                	{ header: "联系电话", dataIndex: "telephone",  flex : 3},    
                	{ header: "来电时间", dataIndex: "phone_time",  flex : 3} ,
                	{ header: "来电人", dataIndex: "phone_person",  flex : 3} ,
                    { header: "来电单位", dataIndex: "phone_unit",  flex : 3},
                    { header: "来电内容", dataIndex: "phone_content",  flex : 3},
                    { header: "状态", dataIndex: "process_state",  flex : 3}
                ],
                tbar: [printbu,leaderbu,sendbu,finishbu,{xtype : "tbfill"},s_filed_file_telephone,searchebu] ,
                bbar: toolbar
            });
          	gridpanel_filephonerecord.setHeight(document.getElementById("grid_filephonerecord").offsetHeight); 
          
          	//打印
			function f_print(){
				var selModel = gridpanel_filephonerecord.getSelectionModel();
	            if (selModel.hasSelection() == false) {
	            	 Ext.Msg.alert("提示", "未选中，无法进行操作！");
	            	 return;
	            }
	            var selected = selModel.getSelection();
	            
	            if(selected.length!=1||selected[0].data.id==null){
	             	Ext.Msg.alert("提示", "只能编辑一条记录！");
	             	return;
                }  
        		var row = selected[0].data;
        		var _url = '<%=basePath%>'+'file_export/'+WDBL$Globals.DocTypeNames[3]+'/' + row.id +'/export.do';
        		window.open(_url);
			}
			
			//转送
			function f_send(){
				changeState('转送');
			}
			//办结
			function f_finish(){
				changeState('办结');
			}
			//改变状态
			function changeState(state){
				var selModel = gridpanel_filephonerecord.getSelectionModel();
	            if (selModel.hasSelection() == false) {
	            	 Ext.Msg.alert("提示", "未选中，无法进行操作！");
	            	 return;
	            }
	            var selected = selModel.getSelection();
	            
	            if(selected.length!=1||selected[0].data.id==null){
	             	Ext.Msg.alert("提示", "只能编辑一条记录！");
	             	return;
                }  
                
                if(selected[0].data.process_state=='办结'){
                	Ext.Msg.alert("提示", "已办结！");
	             	return;
                }
                
                //判断状态
                if(selected[0].data.process_state!=null&&selected[0].data.process_state==state){
                	Ext.Msg.alert("提示", "已"+state+"！");
	             	return;
                }
                //如果是转送判断  
                if((selected[0].data.leader_opinion==null||selected[0].data.leader_opinion=="")&&state=='转送'){
                	Ext.Msg.alert("提示", "没有领导批示，不能转送！");
	             	return;
                }
                
                //如果是办结判断
                if(selected[0].data.leader_opinion!=null&&selected[0].data.leader_opinion!=""&&selected[0].data.process_state!='转送'&&state=='办结'){
                	Ext.Msg.alert("提示", "未转送，不能办结！");
	             	return;
                }
                
                
	            Ext.Msg.confirm("提示", "确定要"+state+"吗？", function (button) {
                    if (button == "yes") {
				        Ext.Ajax.request({
				            url: 'filephonerecord/processStateSave.do',
				            method: "post",
				            success: function (response, opts) {
				                Ext.MessageBox.hide();
				                var json = Ext.JSON.decode(response.responseText); 
				                if(json.success){
				                	store_filephonerecord.reload();
				                	selModel.clearSelections();
				                }
				            },
				            failure: function () {
				                Ext.Msg.alert('提示', '操作失败,刷新后重新操作!');
				            },
				            params: {id:selected[0].data.id,process_state:state}
				        });
                    }
                });
	                  		
			}
			
			
			//领导批示
			function f_leader(){
				var selModel = gridpanel_filephonerecord.getSelectionModel();
	            if (selModel.hasSelection() == false) {
	            	 Ext.Msg.alert("提示", "未选中，无法进行操作！");
	            	 return;
	            }
	            var selected = selModel.getSelection();
	            if(selected.length!=1||selected[0].data.id==null){
	             	Ext.Msg.alert("提示", "只能编辑一条记录！");
	             	return;
                }  
                var id = selected[0].data.id;
                if(selected[0].data.process_state=='转送'){
                	Ext.Msg.alert("提示", "已转送！");
	             	return;
                }
                if(selected[0].data.process_state=='办结'){
                	Ext.Msg.alert("提示", "已办结！");
	             	return;
                }
                
                var filed_id = Ext.create('Ext.form.Hidden', {name: "id", fieldLabel: 'ID'});
			 	var filed_leader_name = Ext.create('Ext.form.Text', {name: "leader_name", fieldLabel: '领导名称',labelAlign: 'right',allowBlank:false});
			 	var filed_leader_opinion = Ext.create('Ext.form.TextArea', {name: "leader_opinion", fieldLabel: '批示',labelAlign: 'right',allowBlank:false});
			 	
				var form_1 = Ext.create('Ext.form.FormPanel',{
					frame: true,
					margins: '6 6 6 6',
	             	defaults:{xtype:"textfield",labelAlign: 'right',labelWidth: 80,width:300},
		            items: [filed_id,filed_leader_name,filed_leader_opinion],
			        buttons: [{
			            text: '保存',
			            formBind:true,
			            monitorValid:true,
			            handler: function() {
							form_1.getForm().submit({
								waitTitle:"请稍候",
	                       		waitMsg:"正在提交表单数据，请稍候",  
	                       		url: 'filephonerecord/leaderSave.do',
								method : "POST", 
						        success: function(form, action) {  
						           	Ext.Msg.alert("提示",action.result.msg);
						           	gridpanel_filephonerecord.getStore().loadPage(1);
						           	selModel.clearSelections();
						           	win.close();
						       	},  
						      	failure: function(form, action) {  
						        	Ext.Msg.alert("提示","保存失败！"); 
						        }  
						   });
			            }
			        },{
			            text: '关闭',
			            handler: function() {
			                win.close();
			            }
			        }]
	            });
				
			    var win = Ext.create('Ext.window.Window',{
			        title: '拟办意见',
			        layout: 'fit',  
			        modal: true, 
			        plain:true,
			        height: 230,  
			        width: 400,  
			        border: 0,   
			        frame: false, 
			        items: form_1
			    });
			    win.show();
			    
	    		form_1.getForm().load({
			    	url: 'regphonerecord/loadBean.do',
				    params: {
				        id: id
				    },
				    failure: function(form, action) {
				        Ext.Msg.alert("加载失败", "加载数据失败！");
				    }
				});
		    
                
                
                
			}
			
			
      });
     		
	</script>  

 
	</head>
	<body>  
		<div id="grid_filephonerecord" style="height: 100%;width: 100%;"></div> 
	</body>
</html>
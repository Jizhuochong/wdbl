/**
 * 
 */
Ext.define('Wdbl.form.op.FileList',{extend:'Ext.panel.Panel',
	requires : ['Ext.*','Wdbl.form.op.FileItem'],
	label : '',
	labelWidth : 60,
	formObj : null,
	border : false,
	autoScroll : true,
	addFile : function(obj){
		var me = this;
		me.insert(Ext.create('Wdbl.form.op.FileItem',{
			text:obj.fileName,
			parent:me,
			fileData:obj,
			listeners: {
        		'btnClick': function(child){
        			Ext.MessageBox.confirm("提示","你确定要删除吗？",function(btn){
        				if(btn == 'no')
        					return;
        				Ext.Ajax.request({
							url : 'reg_file/delFile.do',
							method : "post",
							params : {id : child.fileData.id, sessionID : me.formObj.id, filePath : child.fileData.filePath},
							success : function(response, opts) {
							},
							failure : function(response, opts) {
								var json = Ext.JSON.decode(response.responseText);
								Ext.Msg.alert('系统提示',json.msg);
							}
						}, this);
	        			me.remove(child);
        			});
        			
        		},
        		'labelClick': function(child){
        			if(child.fileData.id){
        				window.open('reg_file/downLoadFile.do?fileID='+child.fileData.id);
        			}else{
        				Ext.Msg.alert('系统提示',"请先保存后再下载.");
        			}
        		}
  			}
		}));
	},
	
    initComponent: function () {
    	var me = this;
		Ext.applyIf(me,{
		    layout: 'hbox',
		    frame: false,
		    items: [{xtype:'label',text:me.label+": ",width:me.labelWidth}]
		});
		me.callParent(arguments);
	}
   
});
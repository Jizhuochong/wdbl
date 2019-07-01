/**
 * 基本Form
 */
Ext.define('Wdbl.form.BaseEditForm', {
	extend : 'Ext.form.Panel',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label','Wdbl.form.op.FileUploadForm'],
	margin : '6 6 6 6',
	bodyPadding : 5,
	layout : 'form',
	fileSetNum : 1,
	fileContainer : null,
	overflowY : 'auto',
	/**
	 * 加载数据
	 * 
	 * @type Boolean
	 */
	load : false,
	/**
	 * Form提交路径
	 * 
	 * @type String
	 */
	url : null,
	/**
	 * 加载数据路径
	 * 
	 * @type String
	 */
	loadUrl : null,
	/**
	 * 加载数据请求参数
	 * 
	 * @type Object
	 */
	loadParam : null,
	/**
	 * 列表
	 * 
	 * @type Ext.grid.Panel
	 */
	grid : null,
	
	/**
	 * 上级panel
	 * 
	 * @type String
	 */
	parent : null,
	/**
	 * 如果是new:新建的form,process:处理的form
	 * @type string
	 */
	addType:null,
	/**
	 * 添加保存
	 */
	addSave : function(){},
	/**
	 * 关闭
	 */
	close : function(){
		var me = this;
		me.parent.remove(me.ownerCt);
	},
	/**
	 * 添加原文件
	 */
	addFile : function() {
		var me = this;
    	var addFileWin = Ext.create('Wdbl.form.op.FileUploadForm',{recordID:me.id,fileContainer:me.fileContainer});
    	addFileWin.show();
	},
	addFileFromOther : function(obj) {
		var me = this;
		return;
		me.fileContainer.addFile(obj);
	},
	/**
	 * 高拍仪
	 */
	addPhoto : function(){
		var me = this;
		var newWindow =( window.open("photo.jsp"));
		setTimeout(function(){newWindow.setFileContainer(me.fileContainer,me.id);},3000); 
	},
	/**
	 * 扫描仪
	 */
	addScan : function(){
		var me = this;
		var newWindow = (window.open("scan.jsp"));
		setTimeout(function(){newWindow.setFileContainer(me.fileContainer,me.id);},3000); 
	},
	/**
	 * 移除原文件
	 * @param {} btn
	 */
	removeFile : function(btn) {
		this.fileContainer.remove(btn.findParentByType('fieldcontainer'));
		this.fileSetNum--;
	},
	loadData : function() {
		var me = this;
		me.loadParam.sessionID = me.id;
		me.getForm().load({
			url : me.loadUrl,
			params : me.loadParam,
			method : "POST",
			failure : function(form, action) {
				Ext.Msg.alert("加载失败", action.result.errorMessage);
			},
			success : function(form, action) {
				Ext.each(action.result.data.originalFiles, function(n, i) {
					me.fileContainer.addFile(n);
				});
				if(action.result.data.slcList && action.result.data.slcList.length > 0){
					Ext.each(action.result.data.slcList,function(data){
						me.commandContainer.add(Ext.create('Ext.form.FieldContainer', {
							layout : "column",
							items : [{
								columnWidth : 0.2,
								xtype : 'textfield',
								name : "leaderName",
								padding : '0 5 0 0',
								fieldLabel : '上级领导',
								value : data.leaderName,
								labelWidth: 60
							},{
								columnWidth : 0.7,
								xtype : 'textfield',
								name : "commandContent",
								padding : '0 5 0 0',
								fieldLabel : '批示内容',
								value : data.commandContent,
								labelWidth: 60
							}, {
								columnWidth : 0.1,
								xtype : 'button',
								text : '移除',
								handler : function(){me.removeLeaderCommand(this);}
							}]
						})); 
					});
				}
			}

		});
		
	},
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {
			defaults : {labelAlign : 'right'},
			buttons : [{
				text : '保存',
				formBind : true,
				monitorValid : true,
				handler : function(){
					if(!me.load)
						me.addSave();
					else
						me.editSave();
				}
			}, {
				text : '关闭',
				handler : function(){me.closeForm();}
			}]
		});
	
		me.callParent(arguments);
	},
	autoDestroy:true,
	destroy:function(){
		if(this.fireEvent("destroy",this) != false){
			this.remove();
			if(Ext.isIE){
				CollectGarbage();
			}
		}
	}	
});
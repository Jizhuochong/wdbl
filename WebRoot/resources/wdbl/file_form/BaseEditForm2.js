/**
 * 基本Form
 */
Ext.define('Wdbl.form.BaseEditForm2', {
	extend : 'Ext.form.Panel',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label'],
	margin : '6 6 6 6',
	bodyPadding : 5,
	layout : 'form',
	fileSetNum : 1,
	fileContainer : null,
	autoScroll : true,
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
		me.parent.activeTab.close();
	},
	/**
	 * 添加原文件
	 */
	addFile : function() {
		var me = this;
		if (me.fileSetNum > WDBL$Globals.MAX_ALLOWED_UPLOAD_FILE_SIZE) {
			Ext.Msg.alert("操作提示", '最多允许上传' + WDBL$Globals.MAX_ALLOWED_UPLOAD_FILE_SIZE + '个原文件');
			return;
		}
		me.fileContainer.add(Ext.create('Ext.form.FieldContainer', {
			layout : "column",
			items : [{
				columnWidth : 0.94,
				xtype : 'filefield',
				name : "files",
				padding : '0 5 0 0',
				fieldLabel : '原文件',
				labelWidth: 60,
				buttonText : '浏览'
			}, {
				columnWidth : 0.06,
				xtype : 'button',
				text : '移除',
				scope : this,
				handler : this.removeFile
			}]
		}));
		me.fileSetNum++;
	},
	addFileFromOther : function(path) {
		var me = this;
		me.fileContainer.add(Ext.create('Ext.form.FieldContainer', {
			layout : "column",
			items : [{
				columnWidth : 0.94,
				id:'testA',
				xtype : 'filefield',
				name : "files",
				padding : '0 5 0 0',
				fieldLabel : '原文件',
				labelWidth: 60,
				buttonText : '浏览',
				emptyText : path
			}, {
				columnWidth : 0.06,
				xtype : 'button',
				text : '移除',
				scope : this,
				handler : this.removeFile
			}]
		}));
		me.fileSetNum++;
	},
	/**
	 * 高拍仪
	 */
	addPhoto : function(){
		window.open("photo.jsp");
	},
	/**
	 * 扫描仪
	 */
	addScan : function(){
		window.open("scan.jsp");
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
		me.getForm().load({
			url : me.loadUrl,
			params : me.loadParam,
			method : "POST",
			failure : function(form, action) {
				Ext.Msg.alert("加载失败", action.result.errorMessage);
			},
			success : function(form, action) {
				Ext.each(action.result.data.originalFiles, function(n, i) {
					me.fileContainer.add(Ext.create('Ext.form.FieldContainer', {
						layout : "column",
						items : [{
							xtype : "hidden",
							name : "fileIds",
							value : n.id
						}, {
							columnWidth : 0.94,
							xtype : 'textfield',
							padding : '0 5 0 0',
							fieldLabel : '原文件',
							readOnly : true,
							name : 'prevFileNames',
							value : n.fileName
						}, {
							columnWidth : 0.06,
							xtype : 'button',
							text : '移除',
							scope : me,
							handler : me.removeFile
						}]
					 }));
				});
			}

		});
		
	},
	initComponent : function() {
		var me = this;
		bindEvent(parentAddPhoto,function(path){me.addFileFromOther(path);});
		Ext.applyIf(me, {
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
				handler : function(){me.close()}
			}]
		});
	
		me.callParent(arguments);
	}
});
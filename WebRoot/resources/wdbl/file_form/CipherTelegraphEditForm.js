/**
 * 密码电报文件
 */
var barNo = null;
Ext.define('Wdbl.form.CipherTelegraphEditForm', {
	extend : 'Wdbl.form.BaseEditForm',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label','Ext.ux.datetime.DateTimeField'],
	readPushBtn : null, 
	endingBtn : null, 
	modelId : null,
	/**
	 * 登记按钮组
	 * 
	 * @type Ext.grid.Panel
	 */
	newBtns : null,
	/**
	 * 办理按钮组
	 * 
	 * @type Ext.grid.Panel
	 */
	processBtns : null,
	/////function//////
	addSave : function() {
		var me = this;
		var urladdress = null;
		if(me.addType=="newAdd" || me.addType=="newEdit")
		{
			urladdress = me.parent.api.add;
		}
		if(me.addType=="process")
		{
			urladdress = 'reg_file/addSave.do';
		}
		me.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : urladdress,
			params:{docTypeName:'CIPHER_TELEGRAPH_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				me.modelId = action.result.data.id;
				//barNo = action.result.data.barNo;
				Ext.Array.each(me.ownerCt.ownerCt.editYBtns,function(name,index,_self){me.ownerCt.ownerCt.editYBtns[index].setDisabled(false);});
				me.ownerCt.innerType = "2";
				
				me.addType = "newEdit";
				me.loadParam = {'id' : me.modelId},
				me.ownerCt.loadParam = {'id' : me.modelId},
				me.load = true;
				me.loadData();
				me.commandContainer.removeAll();
				Ext.Msg.alert("提示", action.result.msg);
			},
			failure : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
			}
		});
	},
	/**
	 * 修改保存
	 */
	editSave : function() {
		var me = this;
		var urladdress = null;
		if(me.addType=="newAdd" || me.addType=="newEdit")
		{
			urladdress = me.parent.api.edit;
		}
		if(me.addType=="process")
		{
			urladdress = 'reg_file/editSave.do';
		}
		me.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : urladdress,
			params:{docTypeName:'CIPHER_TELEGRAPH_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				//Ext.Msg.alert("提示", action.result.msg);
			},
			failure : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
			}
		});
	},
	removeLeaderCommand : function(btn) {
		var me = this;
		Ext.Msg.confirm("警告", "确定要移除吗？", function(button) {
			if (button == "yes") {
				me.commandContainer.remove(btn.findParentByType('fieldcontainer'));
			}
		}, this);
	},
	addLeaderCommand : function(){
		var me = this;
    	var addFileWin = Ext.create('Wdbl.form.op.LeaderCommandForm',{commandContainer:me.commandContainer,removeFun:me.removeLeaderCommand});
    	addFileWin.show();
	},
	// 是否显示按钮
	f_isShowBtn : function() {
		var me = this;
		Ext.Ajax.request({
			url : 'reg_file/isShowBtn.do',
			method : "post",
			params : {ids : me.loadParam.id},
			scope : this,
			success : function(response, opts) {
				var json = Ext.JSON.decode(response.responseText);
				if(json.msg == "1")
				{
					return;
				}
			},
			failure : function() {
				Ext.Msg.alert('系统提示',json.msg);
			}
		}, this);
	},
	
	initComponent : function() {
		var me = this;
		var date = Ext.Date.format(new Date(),'Y');
		me.fileContainer = Ext.create('Wdbl.form.op.FileList', {label:'附件',formObj:me});
		me.commandContainer = Ext.create('Ext.form.FieldContainer',{});
		var wjlx = Ext.create('Ext.form.ComboBox',{
					fieldLabel : "文件类型",
					columnWidth : 0.25,
					labelWidth: 80,
					padding : '0 0 0 5',
					store : getComboDataStore('wjlx'),
					displayField : 'node',
					valueField : 'code',
					emptyText:'请选择',
    				queryMode: 'local',
		  			typeAhead: true,
					value: '13',
					hidden: true
				});
		var baseInfoSet = {
			xtype:'fieldset',
			title : '密码电报文件('+(me.barNo==null?"":me.barNo)+')',
			items : [{
				xtype : "textarea",
				fieldLabel : "文件标题",
				name : "title",
				rows : 2,
				labelWidth: 80,
				padding : '0 0 0 5',
				anchor : '100%'
				},{
				xtype : "fieldcontainer",
				layout : 'column',
				items : [{
					xtype : "hidden",
					name : "id"
				},wjlx,{
					xtype : "combo",
					fieldLabel : "来文单位",
					columnWidth : 0.25,
					labelWidth: 80,
					padding : '0 0 0 5',
					name : "formUnit",
					store : getComboDataStore('lwdw'),
					displayField : 'node',
    				queryMode: 'local',
		    		typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				},{
					columnWidth : 0.25,
					xtype : "textfield",
					fieldLabel : "文号",
					labelWidth: 80,
					padding : '0 0 0 5',
					name : "docNumber",
					value:"﹝"+date+"﹞",
					maxLength : 30
				},Ext.create('Ext.ux.datetime.DateTimeField', {
					fieldLabel : "来文时间",
					columnWidth : 0.25,
					labelWidth: 80,
					padding : '0 0 0 5',
					value: new Date(),
					name : "receiveTime"
				}),Ext.create('Ext.ux.datetime.DateTimeField', {
					fieldLabel : "完成期限",
					columnWidth : 0.25,
					labelWidth: 80,
					padding : '0 0 0 5',
					format: 'Y-m-d H:i',
					name : "deadline"
				})]
			},{
				xtype : "fieldcontainer",
				layout : 'column',
				items : [{
					xtype : "hidden",
					name : "id"
				},{
					columnWidth : 0.25,
					xtype : "combo",
					fieldLabel : "密级",
					labelWidth: 80,
					padding : '0 0 0 5',
					name : "securityGrade",
					store : getComboDataStore('mj'),
					displayField : 'node',
    				queryMode: 'local',
		   			 typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				},{
					columnWidth : 0.25,
					xtype : "combo",
					fieldLabel : "紧急程度",
					name : "urgencyDegree",
					labelWidth: 80,
					padding : '0 0 0 5',
					store : getComboDataStore('jjcd'),
					displayField : 'node',
    				queryMode: 'local',
		   			 typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				},{
					columnWidth : 0.25,
					xtype : "numberfield",
					fieldLabel : "份数",
					labelWidth: 80,
					padding : '0 0 0 5',
					minValue:1,
					maxValue:999999,
					allowDecimals:false,
					name : "copies",
					value: 1
					},{
						columnWidth : 0.15,
						xtype : "numberfield",
						fieldLabel : "序号范围",
						labelWidth: 80,
						padding : '0 0 0 5',
						minValue:1,
						maxValue:999999999,
						allowDecimals:false,
						index:0,
						name : "numberRangeStart",
						listeners:{
							change: function(){
								var _minValue= this.minValue;
								var _allowBlank = true;
								if(this.getValue()) {
									_allowBlank = false;
									_minValue= this.getValue();
								}
								Ext.apply(this.nextNode(),{allowBlank:_allowBlank,minValue : _minValue});
								this.validate(); 
							}
						}
					},{
						columnWidth : 0.1,
						xtype : "numberfield",
						fieldLabel : "至",
						labelWidth:20,
						padding : '0 0 0 5',
						minValue:1,
						maxValue:999999999,
						allowDecimals:false,
						index:1,
						name : "numberRangeEnd",
						listeners:{
							change: function(){
								var _maxValue=this.maxValue;
								var _allowBlank = true;
								if(this.getValue()){
									_allowBlank = false;
									_maxValue= this.getValue();
								}
								Ext.apply(this.previousNode(),{allowBlank:_allowBlank,maxValue : _maxValue});
								this.validate(); 
							}
						}
					}]
			}
//			,{
//				xtype : "fieldcontainer",
//				layout : 'column',
//				items : [{
//					columnWidth : 0.25,
//					xtype : "textfield",
//					fieldLabel : "联合文号",
//					value:"﹝﹞",
//					labelWidth: 80,
//					padding : '0 0 0 5',
//					name : "fglb"
//				}]},{
//				xtype : "textarea",
//				fieldLabel : "上级领导批示",
//				name : "leadershipPS",
//				maxLength : 100,
//				padding : '0 0 0 5',
//				labelWidth: 80,
//				rows : 2,
//				anchor : '100%'
//			}
			]
		};
		var moreInfoSet = {
			xtype:'fieldset',
			title : "其它信息",
			xtype : "fieldset",
			items : [this.fileContainer, {
				xtype : 'panel',
				anchor : '100%',
				border:false,
				buttons : [{
					xtype : 'button',
					text : '添加本地文件',
					listeners : {
						click : function() {
							me.addFile();
						}
					}
				},{
					xtype : 'button',
					text : '扫描',
					listeners : {
						click : function() {
							me.addScan();
						}
					}
				},{
					xtype : 'button',
					text : '拍照',
					listeners : {
						click : function() {
							me.addPhoto();
						}
					}
				}]
			},{
					xtype : "textarea",
					fieldLabel : "关键字",
					name : "keyword",
					rows : 1,
					labelWidth: 80,
					maxLength:100,
					anchor : '100%'
				},{
				xtype : "textarea",
				fieldLabel : "备注",
				name : "remark",
				rows : 2,
				labelWidth: 80,
				maxLength: 2000,
				anchor : '100%'
			}, {
				xtype : "textarea",
				fieldLabel : "条码信息",
				name : "remark",
				rows : 2,
				labelWidth: 80,
				maxLength: 2000,
				anchor : '100%'
			}]
		};
		Ext.applyIf(me, {
			items : [baseInfoSet,{
				title : "上级领导批示",
				xtype : "fieldset",
				items : [me.commandContainer,{
					xtype : 'panel',
					anchor : '100%',
					border:false,
					buttons : [{
						xtype : 'button',
						text : '添加上级领导批示',
						listeners : {
							click : function() {
								me.addLeaderCommand();
							}
						}
					}]
				}]
			},moreInfoSet],
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
				handler : function(){me.close()},
				listeners: {
		            'beforerender': function(){
		            	if(me.addType == 'process' ){
		            		this.hide();
		            	}
	                }
		      	}
			}],
			listeners: {
	            'beforerender': function(){
                	if (me.load){
						me.loadData();
					} 
                	if(me.addType != 'newAdd'){
                		me.f_isShowBtn();
                	}
                }
	      	}
		});
		me.callParent(arguments);
	}
});
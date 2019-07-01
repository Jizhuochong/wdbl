/**
 * 市委市政府正式文件
 */
Ext.define('Wdbl.form.GovEditForm', {
	extend : 'Wdbl.form.BaseEditForm',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label','Ext.ux.datetime.DateTimeField'],
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
	addSave : function() {
		var me = this;
		var add = 'reg_file/addSave.do';
		var urladdress = null;
		if(me.addType=="newAdd" || me.addType=="newEdit")
		{
			urladdress = me.parent.api.add;
		}
		if(me.addType=="process")
		{
			urladdress = add;
		}
//		alert(me.loadUrl);
//		return;
		me.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : urladdress,
			params:{docTypeName:'CITY_GOV_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				me.modelId = action.result.data.id;
				
				Ext.Array.each(me.ownerCt.ownerCt.editYBtns,function(name,index,_self){me.ownerCt.ownerCt.editYBtns[index].setDisabled(false);});
				me.ownerCt.innerType = "2";
				
				me.addType = "newEdit";
				me.loadParam = {'id' : me.modelId},
				me.ownerCt.loadParam = {'id' : me.modelId},
				me.load = true;
				me.loadData();
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
		var edit = 'reg_file/editSave.do';
		var urladdress = null;
		if(me.addType=="newAdd" || me.addType=="newEdit")
		{
			urladdress = me.parent.api.edit;
		}
		if(me.addType=="process")
		{
			urladdress = edit;
		}
		me.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : urladdress,
			params:{docTypeName:'CITY_GOV_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				//Ext.Msg.alert("提示", action.result.msg);
			},
			failure : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
			}
		});
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
		var baseInfoSet = {
			xtype:'fieldset',
			title: '市委市政府正式文件('+(me.barNo==null?"":me.barNo)+')',
			items : [{
				xtype : "textarea",
				fieldLabel : "文件标题",
				name : "title",
				labelWidth:80,
				padding: '0 0 0 5',
				rows : 2,
				anchor : '100%'
			},{
				xtype : "fieldcontainer",
				layout : 'column',
				items : [Ext.create('Ext.form.ComboBox',{
					fieldLabel : "文件类型",
					labelWidth:80,
					padding: '0 0 0 5',
					columnWidth : 0.25,
					store : getComboDataStore('wjlx'),
					displayField : 'node',
					valueField : 'code',
					emptyText:'请选择',
    				queryMode: 'local',
		    		typeAhead: true,
					value: '1',
					hidden: true
				}),{
					xtype : "combo",
					fieldLabel : "来文单位",
					columnWidth : 0.25,
					labelWidth:80,
					padding: '0 0 0 5',
					name : "formUnit",
					store : getComboDataStore('lwdw'),
					displayField : 'node',
    				queryMode: 'local',
		    		typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				},{
					columnWidth : 0.25,
					labelWidth:80,
					padding: '0 0 0 5',
					xtype : "textfield",
					fieldLabel : "文号",
					name : "docNumber",
					value:"﹝"+date+"﹞",
					maxLength : 30
				},Ext.create('Ext.ux.datetime.DateTimeField', {
					fieldLabel : "来文时间",
					columnWidth : 0.25,
					labelWidth:80,
					padding: '0 0 0 5',
					value: new Date(),
					name : "receiveTime"
				}),Ext.create('Ext.ux.datetime.DateTimeField', {
					columnWidth : 0.25,
					labelWidth:80,
					padding: '0 0 0 5',
					fieldLabel : "完成期限",
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
					labelWidth:80,
					padding: '0 0 0 5',
					xtype : "combo",
					fieldLabel : "密级",
					name : "securityGrade",
					store : getComboDataStore('mj'),
					displayField : 'node',
    				queryMode: 'local',
		    		typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				}, {
					columnWidth : 0.25,
					labelWidth:80,
					padding: '0 0 0 5',
					xtype : "combo",
					fieldLabel : "紧急程度",
					name : "urgencyDegree",
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
					labelWidth:80,
					padding: '0 0 0 5',
					minValue:1,
					maxValue:999999,
					allowDecimals:false,
					name : "copies",
					value: 1
				},{
					columnWidth : 0.15,
					labelWidth:80,
					padding: '0 0 0 5',
					xtype : "numberfield",
					fieldLabel : "序号范围",
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
					padding: '0 0 0 5',
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
			}]
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
			}, {
				xtype : "textarea",
				fieldLabel : "备注",
				labelWidth: 80,
				name : "remark",
				rows : 2,
				maxLength: 2000,
				anchor : '100%'
			}, {
				xtype : "textarea",
				fieldLabel : "条码信息",
				labelWidth: 80,
				name : "labelInfo",
				rows : 2,
				maxLength: 2000,
				anchor : '100%'
			}]
		};
		Ext.applyIf(me, {
			items : [baseInfoSet,moreInfoSet],
			buttons : [{
				text : '保存',
				formBind : true,
				monitorValid : true,
				handler : function(){
					if(!me.load){
						me.addSave();
					}
					else
						me.editSave();
				}
			}, {
				text : '关闭',
				handler : function(){
					me.close();
				},
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
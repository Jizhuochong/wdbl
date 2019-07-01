//电话记录
Ext.define('Wdbl.form.PhoneEditForm', {
	extend : 'Wdbl.form.BaseEditForm',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label','Ext.ux.datetime.DateTimeField'],
	instructionsBtn : null,
	transferBtn : null,
	endingBtn : null,
	dy_phone_record : null,
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
		var load = 'reg_file/loadBean.do';
		var edit = 'reg_file/editSave.do';
		var del = 'reg_file/delete.do';
		var toSend = 'reg_file/toSend.do';
		var urladdress = null;
		if(me.addType=="newAdd" || me.addType=="newEdit")
		{
			urladdress = me.parent.api.add;
		}
		if(me.addType=="process")
		{
			urladdress = add;
		}
		me.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : urladdress,
			params:{docTypeName:'TEL_RCD_DOC',sessionID:me.id},
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
		var add = 'reg_file/addSave.do';
		var load = 'reg_file/loadBean.do';
		var edit = 'reg_file/editSave.do';
		var del = 'reg_file/delete.do';
		var toSend = 'reg_file/toSend.do';
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
			params:{docTypeName:'TEL_RCD_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
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
 		var filed_phone_time = Ext.create('Ext.ux.datetime.DateTimeField', {columnWidth : 0.5,name: "receiveTime", fieldLabel: '来电时间'});
 		var joint_fawen= Ext.create('Ext.ux.datetime.DateTimeField', {padding : '0 5 15 5',columnWidth : 0.5,name: "phone_time", fieldLabel: '联合发文'});
 		//me.fileContainer = Ext.create('Wdbl.form.op.FileList', {label:'附件',formObj:me});
		var baseInfoSet = {
			xtype:'fieldset',
			title : '电话记录('+(me.barNo==null?"":me.barNo)+')',
			items : [{
				xtype : "fieldcontainer",
				layout : 'column',
				items : [{
					xtype : "hidden",
					name : "id"
				},Ext.create('Ext.form.ComboBox',{
					fieldLabel : "文件类型",
					labelWidth: 80,
					padding: '0 0 0 5',
					columnWidth : 0.25,
					store : getComboDataStore('wjlx'),
					displayField : 'node',
					valueField : 'code',
					emptyText:'请选择',
    				queryMode: 'local',
		    typeAhead: true,
					value: '3',
					hidden:true
				}),{
					columnWidth : 0.25,
					name: "phonePerson",
					labelWidth: 80,
					padding: '0 0 0 5',
					xtype : "textfield",
					fieldLabel: '来电人',
					maxLength:10
				},{
					columnWidth : 0.25,
					xtype : "combo",
					fieldLabel : "来电单位",
					labelWidth: 80,
					padding: '0 0 0 5',
					name : "formUnit",
					store : getComboDataStore('lwdw'),
					displayField : 'node',
    				queryMode: 'local',
		    typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				},Ext.create('Ext.ux.datetime.DateTimeField', {
					fieldLabel : "来电时间",
					name : "receiveTime",
					labelWidth: 80,
					padding: '0 0 0 5',
					value: new Date(),
					columnWidth : 0.25
				}),{
				 	columnWidth : 0.25,
					name: "telephone",
					xtype : "textfield",
					labelWidth: 80,
					padding: '0 0 0 5',
					fieldLabel: '联系电话',
					maxLength:11
				}]
			},{
				xtype : "fieldcontainer",
				layout : 'column',
				items : [{
					xtype : "hidden",
					name : "id"
				},{
				 	columnWidth :  0.25,
					name: "handleUnit",
					labelWidth: 80,
					padding: '0 0 0 5',
					xtype : "textfield",
					fieldLabel: '接电单位',
					maxLength:60
				},{
				 	columnWidth :  0.25,
					name: "handleOperator",
					xtype : "textfield",
					labelWidth: 80,
					padding: '0 0 0 5',
					fieldLabel: '记录人',
					maxLength:10
				},Ext.create('Ext.ux.datetime.DateTimeField', {
					fieldLabel : "完成期限",
					columnWidth : 0.25,
					labelWidth: 80,
					padding : '0 0 0 5',
					format: 'Y-m-d H:i',
					name : "deadline"
				}),{
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
					padding : '10 0 0 5',
					store : getComboDataStore('jjcd'),
					displayField : 'node',
    				queryMode: 'local',
		    typeAhead: true,
					valueField : 'code',
					mode : 'remote'
				}]
			}]
		};
		var moreInfoSet = {
			xtype:'fieldset',
			title : "其它信息",
			xtype : "fieldset",
			items : [{
					columnWidth : 1,
					labelWidth:80,
					xtype : "textarea",
					fieldLabel : "电话内容",
					name : "title",
					maxLength : 300,
                    rows: 5,
					anchor : '100%'
				},{
					xtype : "textarea",
					fieldLabel : "关键字",
					name : "keyword",
					rows : 1,
					labelWidth: 80,
					maxLength:100,
					anchor : '100%'
				},{
					columnWidth : 1,
					labelWidth:80,
					xtype : "textarea",
					fieldLabel : "条码信息",
					name : "labelInfo",
                    rows: 2,
					maxLength : 2000,
					anchor : '100%'
				}]
		};
 		Ext.applyIf(me, {
			items: [baseInfoSet,moreInfoSet],
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
		 			if(me.addType != 'newAdd'){
		        		me.f_isShowBtn();
		        	}
                	if (me.load){
						me.loadData();
					}
                }
	      	}
		});
		me.callParent(arguments);
	}

});
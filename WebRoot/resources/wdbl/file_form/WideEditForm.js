 //大范围分发文件
var hidId = null;
Ext.define('Wdbl.form.WideEditForm', {
	extend : 'Wdbl.form.BaseEditForm',
	requires : ['Ext.form.FieldSet', 'Ext.form.FieldContainer', 'Ext.form.field.File', 'Ext.form.Label'],
	recoveryBtn : null,
	instructionsBtn : null,
	transferBtn : null,
	transferRecoveryBtn : null,
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
			params:{docTypeName:'WIDE_POST_DOC',sessionID:me.id},
			method : "POST",
			success : function(form, action) {
				me.modelId = action.result.data.id;
				
				Ext.Array.each(me.ownerCt.ownerCt.editYBtns,function(name,index,_self){me.ownerCt.ownerCt.editYBtns[index].setDisabled(false);});
				Ext.Array.each(me.ownerCt.ownerCt.ffszBtns,function(name,index,_self){me.ownerCt.ownerCt.ffszBtns[index].setDisabled(false);});
				me.ownerCt.innerType = "2";
				me.ownerCt.ffszType = "1";
				
				me.addType="newEdit";
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
			params:{docTypeName:'WIDE_POST_DOC',sessionID:me.id},
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
	//回收
	f_hs : function() {
		var me=this;
            Ext.tip.QuickTipManager.init();
            var unitStore = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "id" },
                    { name: "copyNum" },
                    { name: "numbers" },
                    { name: "unit" },
                    { name: "isRecovery"}
                ],
                pageSize:10,
                sortInfo: { field: 'id', direction: 'asc' }, 
                proxy: {
                    type: "ajax",
                    url: "filedispense/searchIdlistLoad.do?id="+hidId.getValue(),
                   	actionMethods: { read: 'POST'},
                    reader: {
                        type: "json",
                        root: "rows",
                        totalProperty: 'totalrows'
                    },
                    simpleSortMode: true
                }
            });
            //重新加载列表
            unitStore.loadPage(1);
        	var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            var grid = Ext.create('Ext.grid.Panel', { 
            	height : 200,
            	selModel: selModel, layout : 'fit', renderTo: Ext.getBody(),
                iconCls: 'icon-grid',
                store: unitStore,
                columns: [{
                    header: '单位名称',
                    flex : 12,
                    sortable: true,
                    dataIndex: 'unit'
                },{
                    header: '份数',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'copyNum',
                    field: {
                        xtype: 'numberfield'
                    }
                }, {
                    header: '号码',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'numbers',
                    field: {
                        xtype: 'textfield'
                    }
                },{
                    header: '是否回收',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'isRecovery',
                    store:[[true,'已回收'],[false,'未回收']],
                 	forceSelection:true,isRecovery : false,allowBlank:false,anchor: '100%'
                }]
            });
		var w = new Ext.Window({ 
			  buttonAlign:'right',//按钮的排列方式
			  width : 800,
			  height : 260,
			  items: grid,
			  buttons : [{
					text : '回收',
					handler : function() {
						var selModel = grid.getSelectionModel();
					    if (selModel.hasSelection()) {
			                var selected = selModel.getSelection();
			                var ids = []; 
			                Ext.each(selected, function (item) {
			                    ids.push(item.data.id);
			                });
							Ext.Ajax.request({
								url : "filedispense/hsUnit.do",
								method : "post",
								params: {ids:ids},
								scope : this,
								success : function(response, opts) {
									var json = Ext.JSON.decode(response.responseText);
									Ext.Msg.alert('系统提示', json.msg);
									me.unitStore.reload();
									//w.close();
								},
								failure : function() {
									Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
								}
							});
					    }
					    else{
					    	Ext.Msg.alert("错误", "没有任何行被选中，无法进行回收操作！");
					    }
				    }
				},{
					text : '取消回收',
					handler : function() {
						var selModel = grid.getSelectionModel();
					    if (selModel.hasSelection()) {
			                var selected = selModel.getSelection();
			                //console.log(selected);
			                var ids = []; 
			                Ext.each(selected, function (item) {
			                    ids.push(item.data.id);
			                    //console.log(item.data.id);
			                });
							Ext.Ajax.request({
								url : "filedispense/unHsUnit.do",
								method : "post",
								params: {ids:ids},
								scope : this,
								success : function(response, opts) {
									var json = Ext.JSON.decode(response.responseText);
									Ext.Msg.alert('系统提示', json.msg);
									me.unitStore.reload();
									//w.close();
								},
								failure : function() {
									Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
								}
							});
					    }
					    else{
					    	Ext.Msg.alert("错误", "没有任何行被选中，无法进行回收操作！");
					    }
				    }
				}, {
					text : '关闭',
					handler : function() {
						this.findParentByType('window').close();
					}
				}]
			});  
			w.show(); 
	},
	initComponent : function() {
		var me = this;
		var date = Ext.Date.format(new Date(),'Y');
		me.fileContainer = Ext.create('Wdbl.form.op.FileList', {label:'附件',formObj:me});
		hidId = Ext.create('Ext.form.field.Hidden',{
			name : "id"
		});
		var wjlx = 	Ext.create('Ext.form.ComboBox',{
							fieldLabel : "文件类型",
							columnWidth : 0.25,
							labelWidth: 60,
							padding : '0 0 0 5',
							store : getComboDataStore('wjlx'),
							displayField : 'node',
							valueField : 'code',
							emptyText:'请选择',
    						queryMode: 'local',
		    				typeAhead: true,
							value: '2',
							hidden:true
				});
		Ext.applyIf(me, {
			items : [{
				title : '大范围分发('+(me.barNo==null?"":me.barNo)+')',
				xtype : "fieldset",
				items : [{
    				xtype : "textarea",
    				fieldLabel : "文件标题",
    				name : "title",
    				labelWidth:80,
					padding : '0 0 0 5',
    				rows : 2,
    				anchor : '100%'
    			},{
					xtype : "fieldcontainer",
					layout : 'column',
					items : [hidId,wjlx,
					{
						columnWidth : 0.25,
						xtype : "combo",
						fieldLabel : "来文单位",
						name : "formUnit",
						labelWidth:80,
						padding : '0 0 0 5',
						store : getComboDataStore('lwdw'),
						displayField : 'node',
    					queryMode: 'local',
		    			typeAhead: true,
						valueField : 'code',
						mode : 'remote'
					},{
						columnWidth : 0.25,
						xtype : "textfield",
						labelWidth:80,
						padding : '0 0 0 5',
						fieldLabel : "文号",
						value:"﹝"+date+"﹞",
						name : "docNumber",
						maxLength : 30
					},Ext.create('Ext.ux.datetime.DateTimeField', {
						fieldLabel : "来文时间",
						labelWidth:80,
						padding : '0 0 0 5',
						columnWidth : 0.25,
						value: new Date(),
					    name : "receiveTime"
					}),Ext.create('Ext.ux.datetime.DateTimeField', {
						fieldLabel : "完成期限",
						columnWidth : 0.25,
						labelWidth:80,
						padding : '0 0 0 5',
						format: 'Y-m-d H:i',
						name : "deadline"
					})]
				},{
							xtype : "fieldcontainer",
							layout : 'column',
							items : [ {
								columnWidth : 0.25,
								xtype : "combo",
								fieldLabel : "密级",
								name : "securityGrade",
								labelWidth:80,
								padding : '0 0 0 5',
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
								labelWidth:80,
								padding : '0 0 0 5',
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
								labelWidth:80,
								padding : '0 0 0 5',
								fieldLabel : "份数",
								minValue:1,
								maxValue:999999,
								value:1,
								allowDecimals:false,
								name : "copies"
							},{
								columnWidth : 0.15,
								xtype : "numberfield",
								fieldLabel : "序号范围",
								minValue:1,
								maxValue:999999999,
								labelWidth:80,
								padding : '0 0 0 5',
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
						}]
					}, {
						title : "其它信息",
						xtype : "fieldset",
						items : [this.fileContainer,{
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
							labelWidth:80,
							maxLength:100,
							anchor : '100%'
						},{
							xtype : "textarea",
							fieldLabel : "备注",
							labelWidth:80,
							name : "remark",
							rows : 2,
							anchor : '100%'
						},{
							xtype : "textarea",
							fieldLabel : "条码信息",
							labelWidth:80,
							name : "labelInfo",
							rows : 2,
							anchor : '100%'
						}]
					}],
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
/**
 * 
 */
 
 
Ext.define('Wdbl.view.FileTraTab', {
	extend : 'Ext.tab.Panel', alias : 'widget.fileregTab',
	requires : ['Ext.*'],
	layout : 'fit',
	border : false,
	frame : true,
	innerGrid:null,
	label_search_win: null,
	api : {
		load : null,// 加载
		open : null,// 打开
		edit : null// 修改
	},
	// 修改类型
	$f_update_type : function() {
		var me = this;
		if (!this.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
			return;
		}
		var selected = this.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提醒", "只能编辑一条记录！");
			return;
		}
		var update_docType_common = Ext.create('Wdbl.form.op.updateTypeForm',{recordID:id, store:me.innerGrid.store, gridpanel:me.innerGrid});
		update_docType_common.showWin();
	},
	//按条码查询
	$code_search : function(){
		var gunSearchFrame = Ext.create('Ext.ux.IFrame',{
	        closable: true,
	        border:false,
	        src:'gun_search_tra.jsp?r='+Math.random()
		});
		this.label_search_win = Ext.create('Ext.window.Window',{
			title:'扫描二维码',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 480,
		    height:310,
		    border: 0,
		    frame: true,
		    items: gunSearchFrame,
			listeners:{
				'close': function(){
					window.frames[0].pageClose();
		        }
		    }
		});
		this.label_search_win.show();
		
	},
	// 打印条码
	$f_tm : function() {
		var me = this;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			if (!me.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有选中任何记录，无法进行打印条码！");
				return;
			}
			var selected = me.innerGrid.selModel.getSelection();
			var id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能打印一条条码！");
				return;
			}
		    PrintLabel5025(
		    "create_tra_file", 
		    "自动", 
			selected[0].data.barNo?selected[0].data.barNo:"无",
			selected[0].data.receiveTime?selected[0].data.receiveTime:"无", 
			selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
			selected[0].data.receiveTime?selected[0].data.receiveTime:"无", 
			selected[0].data.docType?selected[0].data.docType:"无",
			selected[0].data.docNumber?selected[0].data.docNumber:"无",
		    selected[0].data.urgencyDegree?selected[0].data.urgencyDegree:"无",
		    selected[0].data.securityGrade?selected[0].data.securityGrade:"无", 
		    "无", 
		    selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
		    selected[0].data.title?selected[0].data.title:"无", 
		    selected[0].data.fromUnit?selected[0].data.fromUnit:"无", 
		    "", //other
		    selected[0].data.barNo.replace(/(^\s*)/g,"   "), "", "", "", 10, 10,  2);
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			var id = activeTab.p_id;
			Ext.Ajax.request({
				url : 'reg_file/loadBean.do',
				params:{id : id, sessionID:id},
				method : "POST",
				success : function(form, action) {
					var json = Ext.JSON.decode(form.responseText);
					PrintLabel5025(
				    "create_tra_file", 
				    "自动", 
					json.data.barNo?json.data.barNo:"无",
					json.data.receiveTime?json.data.receiveTime:"无", 
					json.data.fromUnit?json.data.fromUnit:"无", 
					json.data.receiveTime?json.data.receiveTime:"无", 
					json.data.docType?json.data.docType:"无",
					json.data.docNumber?json.data.docNumber:"无",
				    json.data.urgencyDegree?json.data.urgencyDegree:"无",
				    json.data.securityGrade?json.data.securityGrade:"无", 
				    "无", 
				    json.data.fromUnit?json.data.fromUnit:"无", 
				    json.data.title?json.data.title:"无", 
				    json.data.fromUnit?json.data.fromUnit:"无", 
				    "", //other
				    json.data.barNo.replace(/(^\s*)/g,"   "), "", "", "", 10, 10,  2);
				},
				failure : function(form, action) {
					Ext.Msg.alert("提示", "打印失败，请重试");
				}
			}, this);
		}
	},
	/**
	 * 删除
	 */
	$del : function() {
		var me = this;
		if (!me.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
			return;
		}
		var delFlag = false;
		Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
			if (button == "yes") {
				me.delData();
			}
		}, this);
		
	},
	//private 
	delData : function(){
		var me = this;
		var selected = me.innerGrid.selModel.getSelection();
		var ids = [];
		Ext.each(selected, function(item) {ids.push(item.data.id);});
		Ext.Ajax.request({
			url : this.api.del,
			method : "post",
			params : {ids : ids},
			scope : this,
			success : function(response, opts) {
				var json = Ext.JSON.decode(response.responseText);
				Ext.Msg.alert('系统提示', json.msg);
				if (json.success) {
					me.innerGrid.store.reload();
				}
			},
			failure : function() {
				Ext.Msg.alert('系统提示','删除失败,请刷新后重新操作!');
			}
		}, this);
	},
	
	//扫描枪扫描后的代码处理
	$analysis_code : function(params){
		var me = this;
		Ext.apply(me.innerGrid.store.proxy.extraParams, params);
		me.innerGrid.store.loadPage(1);
		this.label_search_win.close();
	},
	/**
	 * 打开
	 */
	$open : function() {
		var me = this;
		if (!me.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有选中任何记录，无法进行打开！");
			return;
		}
		var selected = me.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提醒", "只能打开一条记录！");
			return;
		}
		var title = selected[0].data.title;
		var docType = selected[0].data.docType;
		var currTab = Ext.getCmp(id);
		if(currTab)
		{
			this.setActiveTab(currTab);
			return;
		}
		var IFrameView = Ext.create('Ext.ux.IFrame',{
			tooltip : '打开:'+title,
			title:'打开'+title,
	        closable: true,
	        innerObj:null,
	        innerType:docType,
	        border:false,
	        id:id,
	        loader: {
		        url: 'tra_file/jumpList.do',
		        autoLoad: true,
		        contentType: 'html',
		        loadMask: true,
		        scripts:true,
		        baseParams:{id : id,type : docType}
	    	}
		});
		this.add(IFrameView).show();
	},
	/**
	 * 批量办结
	 */
	$finish : function() {
		var me = this;
		var id = null;
		var ids = [];
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行办结操作！");
				return;
			}
			var selected = me.innerGrid.selModel.getSelection();
			Ext.each(selected, function(item) {ids.push(item.data.id);});
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
			ids.push(id);
		}
		
		var delFlag = false;
		Ext.Msg.confirm("警告", "确定要办结吗？", function(button) {
			if (button == "yes") {
				Ext.Ajax.request({
					url : 'reg_file/finish.do',
					method : "post",
					params : {ids : ids},
					scope : this,
					success : function(response, opts) {
						var json = Ext.JSON.decode(response.responseText);
						if (json.success) {
							me.innerGrid.store.reload();
							if( me.innerGrid.id != me.getActiveTab().id ) {
								me.getActiveTab().close();
							}
						}
					},
					failure : function() {
						Ext.Msg.alert('系统提示','办结失败,请刷新后重新操作!');
					}
				}, this);
			}
		}, this);
		
	},
	// 打印审批单
	$f_print : function(docType) {
		var me = this;
		var id = null;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行打印操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能打印一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		Ext.Ajax.request({
			url : 'reg_file/loadBean.do',
			params:{id : id, sessionID:id},
			method : "POST",
			success : function(form, action) {
				var json = Ext.JSON.decode(form.responseText);
				if(docType == 'TEL_RCD_DOC' && json.data.docType != '3') {
					Ext.Msg.alert("提示", "当前文件类型不是电话记录，无法打印");
				} else {
					var _url = 'file_export/'+ docType +'/' + id + '/exportFile.do';
					window.open(_url);
				}
			},
			failure : function(form, action) {
				Ext.Msg.alert("提示", "打印失败，请重试");
			}
		}, this);
	},
	// 录入领导批示
	f_leaderInstructions : function() {
		var me = this;
		var id = null;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.leaderInstructionsForm',{recordID:id, btype:'tra_'});
		process_common.showWin();
	},
	// 转送单位
	f_transfer : function() {
		var me = this;
		var id = null;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.transferForm',{recordID:id});
		process_common.showWin();
	},
	// 呈送
	f_submit : function() {
		var me = this;
		var id = null;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行呈送操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能处理一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		var process_common = Ext.create('Wdbl.form.op.submitForm',{type:"process",parent:me,recordID:id});
		process_common.showWin();
	},
	// 刷新
	$f_refresh : function() {
		var me = this;
		me.innerGrid.getStore().reload();
	},
	//回收
	f_hs : function() {
		var me=this;
		var selected = me.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		Ext.define('Task', {
            extend: 'Ext.data.Model'
        });
            Ext.tip.QuickTipManager.init();
            
            this.unitStore = Ext.create("Ext.data.JsonStore", {
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
                    url: "filedispense/searchIdlistLoad.do?id="+id,
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
            this.unitStore.loadPage(1);
        	var selModel = Ext.create('Ext.selection.CheckboxModel',{singleSelect:false}); 
            var grid = Ext.create('Ext.grid.Panel', { 
            	height : 200,
            	selModel: selModel, layout : 'fit', renderTo: Ext.getBody(),
                iconCls: 'icon-grid',
                store: this.unitStore,
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
									w.close();
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
			                var ids = []; 
			                Ext.each(selected, function (item) {
			                    ids.push(item.data.id);
			                });
							Ext.Ajax.request({
								url : "filedispense/unHsUnit.do",
								method : "post",
								params: {ids:ids},
								scope : this,
								success : function(response, opts) {
									var json = Ext.JSON.decode(response.responseText);
									Ext.Msg.alert('系统提示', json.msg);
									w.close();
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
	//分发设置
	f_ffsz : function() {
		var me=this;
		Ext.define('Task', {
            extend: 'Ext.data.Model'
        });
            Ext.tip.QuickTipManager.init();
            
            var unitStore = Ext.create("Ext.data.JsonStore", {
            	fields: [
                    { name: "id" },
                    { name: "idA" },
                    { name: "copyNumA" },
                    { name: "numbersA" },
                    { name: "unitA" },
                    { name: "isRecoveryA"},
                    { name: "idB" },
                    { name: "copyNumB" },
                    { name: "numbersB" },
                    { name: "unitB" },
                    { name: "isRecoveryB"}
                ],
                pageSize:10,
                sortInfo: { field: 'id', direction: 'asc' }, 
                proxy: {
                    type: "ajax",
                    url: "filedispense/listLoad.do?id="+id,
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
            unitStore.load({  
	            callback:function(r,options,success){
	                if(success == false) {  
	                    Ext.Msg.alert("系统提示","序号范围小于分送单位个数，请更改序号范围。");  
	                }
	            }  
	        });       

            //可编辑的列表
            var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
                clicksToEdit: 1
            });
            var grid = Ext.create('Ext.grid.Panel', {
            	scrollbar : true,
            	height : 200,
                iconCls: 'icon-grid',
                store: unitStore,
                plugins: [cellEditing],
                columns: [{
                    header: '单位名称',
                    flex : 12,
                    sortable: true,
                    dataIndex: 'unitA'
                }, {
                    header: '份数',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'copyNumA',
                    field: {
                        xtype: 'numberfield'
                    }
                }, {
                    header: '号码',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'numbersA',
                    field: {
                        xtype: 'textfield'
                    }
                },{
                    header: '单位名称',
                    flex : 12,
                    sortable: true,
                    dataIndex: 'unitB'
                }, {
                    header: '份数',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'copyNumB',
                    field: {
                        xtype: 'numberfield'
                    }
                }, {
                    header: '号码',
                    sortable: true,
                    flex : 12,
                    dataIndex: 'numbersB',
                    field: {
                        xtype: 'textfield'
                    }
                }]
            });
            grid.on('edit', function(editor, e) {
            	var jsonData = Ext.encode(Ext.pluck(grid.getStore().data.items, 'data'));
            	Ext.Ajax.request({
					url : "filedispense/updateListLoad.do?id="+id+"&rows="+e.rowIdx,
					headers: {'Content-Type':'application/json'},
					params : jsonData,
					scope : this,
					success : function(response, opts) {
						var json = Ext.JSON.decode(response.responseText);
						unitStore.removeAll();
						unitStore.loadData(json.rows);
					},
					failure : function() {
						Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
					}
				});
            });
			var w = new Ext.Window({ 
			  buttonAlign:'right',//按钮的排列方式
			  width : 800,
			  height : 260,
			  items: grid,
			  buttons : [{
					text : '保存',
					formBind : true,
					monitorValid : true,
					handler : function() {
						var jsonData = Ext.encode(Ext.pluck(grid.getStore().data.items, 'data'));
						Ext.Ajax.request({
							url : "filedispense/add.do?id="+id,
							headers: {'Content-Type':'application/json'},
							params : jsonData,
							scope : this,
							success : function(response, opts) {
								var json = Ext.JSON.decode(response.responseText);
								Ext.Msg.alert('系统提示', json.msg);
								w.close();
							},
							failure : function() {
								Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
							}
						});
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
	// 大范围文件(分发设置)
	$f_ffsz : function(count) {
		var me = this;
		var id = null;
		
		var id = null;
		if( me.innerGrid.id ==  me.getActiveTab().id ) {
			// 如果当前激活的是列表，id值从列表选中项获取
			if (!this.innerGrid.selModel.hasSelection()) {
				Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
				return;
			}
			var selected = this.innerGrid.selModel.getSelection();
			id = selected[0].data.id;
			if (selected.length != 1 || id == null) {
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.p_id;
		}
		
		var sendSetWin = Ext.create('Wdbl.form.op.WideSetForm',{regID:id,unitNumber:count});
		sendSetWin.showWin();
	},
	initComponent : function() {
		var me = this;
		var searchBtn = Ext.create('Ext.button.Button',{text : '条码查询',iconCls : 'page_code',handler : function(){me.$code_search()}});
		var updateTypeBtn = Ext.create('Ext.button.Button',{text : '修改类型',iconCls : 'report_edit',handler : function(){me.$f_update_type()}});
		var tmBtn = Ext.create('Ext.button.Button',{text : '打印条码',iconCls : 'attach',handler : function(){me.$f_tm()}});
		var delBtn = Ext.create('Ext.button.Button',{text : '删除',iconCls : 'delete',handler : function(){me.$del()}});
		var openBtn = Ext.create('Ext.button.Button',{text : '打开文件',iconCls : 'book_open',handler : function(){me.$open();}});
		var ffszBtn = Ext.create('Ext.button.Button',{text : '大范围文件',iconCls : 'arrow_out',menu:[{text:'20个单位',handler : function(){me.$f_ffsz('20');}},{text:'60个单位',handler : function(){me.$f_ffsz('60');}}]});
		//var hsBtn = Ext.create('Ext.button.Button',{text : '回收',iconCls : 'arrow_in',handler : function(){me.f_hs();}});
		var submitBtn = Ext.create('Ext.button.Button',{text : '呈送',iconCls : 'report_edit',handler : function(){me.f_submit();}});
		var printBtn = Ext.create('Ext.button.Button',{text : '打印',iconCls : 'printer',menu:[{text : '阅批单', handler : function(){me.$f_print('CITY_GOV_DOC');}},{text : '密电单', handler : function(){me.$f_print('CIPHER_TELEGRAPH_DOC');}},{text : '电话记录', handler : function(){me.$f_print('TEL_RCD_DOC');}},{text : '批示抄清', handler : function(){me.$f_print('INSTRUCTIONS_DOC');}}]});
		var leaderInstructionsBtn = Ext.create('Ext.button.Button',{text : '录入批示',iconCls : 'note_edit',handler : function(){me.f_leaderInstructions();}});
		var transferBtn = Ext.create('Ext.button.Button',{text : '发送文件',iconCls : 'folder_page',handler : function(){me.f_transfer();}});
		var finishBtn = Ext.create('Ext.button.Button',{text : '办结',iconCls : 'table_key',handler : function(){me.$finish();}});
		var refresh = Ext.create('Ext.button.Button',{text : '刷新', iconCls : 'arrow_refresh',handler : function(){me.$f_refresh()}});
		
		var btns = [updateTypeBtn,searchBtn,delBtn,refresh];
		var twoBtns = [ffszBtn];
		var threeBtns = [tmBtn,submitBtn,printBtn,leaderInstructionsBtn,transferBtn,finishBtn]
		Ext.applyIf(me, {
			xtype:'tabpanel',
			layout : 'fit',
			activeTab: 0,
            minTabWidth : 100,
            maxTabWidth : 100,
            items : [this.innerGrid],
			dockedItems: [{
				xtype:'toolbar',
				dock:'top',
				items:[ 
					tmBtn,{xtype : "tbseparator"}, 
					searchBtn,{xtype : "tbseparator"}, 
					updateTypeBtn,{xtype : "tbseparator"}, 
					delBtn,{xtype : "tbseparator"}, 
					ffszBtn, {xtype : "tbseparator"}, 
					submitBtn, {xtype : "tbseparator"}, 
					printBtn, {xtype : "tbseparator"}, 
					leaderInstructionsBtn, {xtype : "tbseparator"}, 
					transferBtn, {xtype : "tbseparator"}, 
					finishBtn, {xtype : "tbseparator"}, 
					refresh
				]
			}],
            listeners:{
				'beforerender': function(){
					Ext.Array.each(threeBtns,function(name,index,_self){threeBtns[index].setDisabled(false);});
					Ext.Array.each(twoBtns,function(name,index,_self){twoBtns[index].setDisabled(true);});
		        },
            	tabchange : function(tabPanel, newCard, oldCard) {
					if(newCard.id == me.innerGrid.id){
						Ext.Array.each(threeBtns,function(name,index,_self){threeBtns[index].setDisabled(false);});
						Ext.Array.each(twoBtns,function(name,index,_self){twoBtns[index].setDisabled(true);});
						Ext.Array.each(btns,function(name,index,_self){btns[index].setDisabled(false);});
						me.innerGrid.getStore().reload();
						return;
					}
					Ext.Array.each(twoBtns,function(name,index,_self){twoBtns[index].setDisabled(true);});
					Ext.Array.each(btns,function(name,index,_self){btns[index].setDisabled(true);});
					if(newCard.ffsz == 0){
						Ext.Array.each(twoBtns,function(name,index,_self){twoBtns[index].setDisabled(true);});
					}
					if(newCard.ffsz == 1){
						Ext.Array.each(twoBtns,function(name,index,_self){twoBtns[index].setDisabled(false);});
					}
					Ext.Array.each(threeBtns,function(name,index,_self){threeBtns[index].setDisabled(false);});
				}
            }
			
		});

		me.callParent(arguments);
	}
});

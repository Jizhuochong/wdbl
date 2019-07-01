/**
 * 
 */

var $analysis_code;

Ext.onReady(function() {
	Ext.QuickTips.init();
	function $code_add(){
		var gunAddFrame = Ext.create('Ext.ux.IFrame',{
		    closable: true,
		    border:false,
		    src:'gun_add_sign.jsp?r='+Math.random()
		});
		this.label_add_win = Ext.create('Ext.window.Window',{
			title:'二维码添加',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 600,
		    height:400,
		    border: 0,
		    frame: true,
		    items: gunAddFrame,
			listeners:{
				'close': function(){
					window.frames[0].pageClose();
		        }
		    }
		});
		this.label_add_win.show();
	}
	
	$analysis_code = function(params){
		var me = this;
		Ext.Ajax.request({
			url : "sign_file/addSave.do",
			method : "post",
			params : params,
			scope : this,
			success : function(response, opts) {
				var json = Ext.JSON.decode(response.responseText);
				Ext.Msg.alert('系统提示', json.msg);
				if (json.success) {
					signStore.reload();
				}
			},
			failure : function() {
				Ext.Msg.alert('系统提示','操作失败，请重新操作!');
			}
		}, this);
	}
	
	$xls_out = function(){
		var sDate = Ext.util.Format.date(s_date.getValue(),'Y-m-d');
		var _url = 'sign_file/'+ sDate + '/exportXls.do';
		window.open(_url);
	}
	var signStore = Ext.create("Ext.data.JsonStore", {
		fields : [{
			name : "id"
			}, {
				name : "title"
			}, {
				name : "docNumber"
			}, {
				name : "fromUnit"
			}, {
				name : "signUser"
			}, {
				name : "backTime"
			}, {
				name : "remark"
			}, {
				name : "barNo"
		}],
		sortInfo : {
			field : 'id',
			direction : 'asc'
		},
		proxy : {
			type : "ajax",
			url : "sign_file/listLoad.do",
			actionMethods : {read : 'POST'},
			reader : {
				type : "json",
				root : "rows",
				totalProperty : 'totalrows'
			},
			simpleSortMode : true
		}
	});

	var s_title = Ext.create('Ext.form.TextField', {
		fieldLabel : '标题',
		labelAlign : 'left',
		labelWidth : 40,
		padding:'0 0 0 5',
		emptyText : '输入要查询的标题'
	});
	var s_date = Ext.create('Ext.form.field.Date', {
		fieldLabel : "退件时间",
		labelAlign : 'left',
		labelWidth: 60,
		padding:'0 0 0 5',
		format : "Y-m-d",
		value: new Date()
	});
	
	var searcheBtn = Ext.create('Ext.Button', {
		pressed : true,
		text : '搜索',
		iconCls : 'find',
		padding:'0 5 0 5',
		handler : function() {
			signStore.loadPage(1);
		}
	});
	var addBtn = Ext.create('Ext.Button', {
		pressed : true,
		text : '添加',
		iconCls : 'add',
		padding:'0 5 0 5',
		handler : $code_add
	});
	
	var xlsBtn = Ext.create('Ext.Button', {
		pressed : true,
		text : '导出',
		iconCls : 'page_excel',
		padding:'0 5 0 5',
		handler : $xls_out
	});
	
	var editBtn = Ext.create('Ext.Button', {
		pressed : true,
		text : '编辑',
		iconCls : 'table_edit',
		padding:'0 5 0 5',
		handler : edit
	});
	var delBtn = Ext.create('Ext.Button', {
		pressed : true,
		text : '删除',
		iconCls : 'delete',
		padding:'0 5 0 5',
		handler : del
	});
	
	signStore.on('beforeload', function(store, options) {
		var dat
		var new_params = {
			title : s_title.getValue(),
			backTime : Ext.util.Format.date(s_date.getValue(),'Y-m-d'),
			property : 'id',
			direction : 'asc' 
		};
		Ext.apply(store.proxy.extraParams, new_params);
		s_title.setValue();
	});

	var gridpanel = Ext.create("Ext.grid.Panel", {
		selModel : Ext.create('Ext.selection.CheckboxModel'),
		layout : 'fit',
		autoHeight : true,
		title : "列表",
		renderTo : "$sign_grid",
		frame : true,
		store : signStore,
		loadMask : true,
		columnLines : true,
		forceFit : true,
		height : 140,
		columns : [{
				header : "条码编码",
				dataIndex : "barNo",
				sortable : true,
				flex : 1
			},{
				header : "来文单位",
				dataIndex : "fromUnit",
				sortable : true,
				flex : 1
			}, {
				header : "文件标题",
				dataIndex : "title",
				sortable : true,
				flex : 3
			}, {
				header : "文号",
				dataIndex : "docNumber",
				sortable : true,
				flex : 2
			}, {
				header : "签收人",
				dataIndex : "signUser",
				sortable : true,
				flex : 1
			}, {
				header : "退件时间",
				dataIndex : "backTime",
				sortable : true,
				flex : 1
			}, {
				header : "备注",
				dataIndex : "remark",
				sortable : true,
				flex : 1
		}],
		tbar : [s_date,s_title, searcheBtn, {xtype : "tbseparator"}, 
		addBtn, {xtype : "tbseparator"}, 
		xlsBtn, {xtype : "tbseparator"}, 
		editBtn, {xtype : "tbseparator"}, 
		delBtn],
		bbar : Ext.create('Wdbl.utils.PagingToolBar',{store:signStore})
	});
	signStore.loadPage(1);
	gridpanel.setHeight(document.getElementById("$sign_grid").offsetHeight);

	function editSave(){
		edit_form.getForm().submit({
			waitTitle : "请稍候",
			waitMsg : "正在提交表单数据，请稍候",
			url : "sign_file/editSave.do",
			method : "POST",
			success : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
				signStore.loadPage(1);
				edit_win.close();
			},
			failure : function(form, action) {
				Ext.Msg.alert("提示", action.result.msg);
			}
		});
	}
	var edit_form = Ext.create('Ext.form.FormPanel', {
		frame : true,
		margins : '6 6 6 6',
		defaults : {
			xtype : "textfield",
			labelAlign : 'right',
			labelWidth : 60,
			width : 380
		},
		items : [{
			xtype:'hidden',
			name : "id"
		}, {
			name : "fromUnit",
			fieldLabel : "来文单位"
		}, {
			name : "title",
			fieldLabel : "标题"
		},{
			name : "docNumber",
			fieldLabel : "文号"
		},{
			name : "signUser",
			fieldLabel : "签收人"
		},{
			name : "backTime",
			fieldLabel : "退件时间"
		},{
			name : "remark",
			fieldLabel : "备注"
		}],
		buttons : [{
			text : '保存',
			formBind : true,
			monitorValid : true,
			handler : function() {
				editSave();
			}
		}, {
			text : '关闭',
			handler : function() {
				edit_win.close();
			}
		}]
	});

	var edit_win = Ext.create('Ext.window.Window', {
		title : '编辑',
		layout : 'fit',
		modal : true,
		plain : true,
		height : 260,
		width : 400,
		border : 0,
		closeAction:'hide',
		frame : false,
		items : edit_form
	});

	function createForm(load, id) {
		if (load == true, id != null) {
			edit_form.getForm().load({url : 'sign_file/loadBean.do', params : {id : id},
				failure : function(form, action) {
					Ext.Msg.alert("加载失败", action.result.errorMessage);
				}
			});
		}
		edit_win.show();
		
	}
	// 编辑
	function edit() {
		var selModel = gridpanel.getSelectionModel();
		if (!selModel.hasSelection()) {
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行编辑操作！");
			return;
		}
		var selected = selModel.getSelection();
		if (selected.length != 1 || selected[0].data.id == null) {
			Ext.Msg.alert("提醒", "只能编辑一条记录！");
		}
		createForm(true, selected[0].data.id);
	}

	// 删除
	function del() {
		var selModel = gridpanel.getSelectionModel();
		if (!selModel.hasSelection()){ 
			Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
			return;
		}
		Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
			if (button != "yes") {
				return;
			}
			var selected = selModel.getSelection();
			var ids = [];
			Ext.each(selected, function(item) {
				ids.push(item.data.id);
			});
			Ext.Ajax.request({
				url : 'sign_file/delete.do',
				method : "post",
				success : function(response, opts) {
					Ext.MessageBox.hide();
					var json = Ext.JSON.decode(response.responseText);
					Ext.Msg.alert('系统提示', json.msg);
					if (json.success) {
						signStore.reload();
					}
				},
				failure : function() {
					Ext.Msg.alert('系统提示', '删除失败,刷新后重新操作!');
				},
				params : {ids : ids}
			});
		});
	}

});
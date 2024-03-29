/**
 * 
 */

Ext.define('Wdbl.view.FileRegTab', {extend : 'Ext.tab.Panel',
	requires : ['Ext.*','Wdbl.form.op.*'],
	layout : 'fit',
	//id : 'fileRegTab',
	border : false,
	frame : true,
	innerGrid : null,
	label_search_win: null,
	api : {
		add : null,// 添加
		load : null,// 加载
		edit : null,// 修改
		del : null, // 删除
		toSend :null
	},
	/**
	 * 添加
	 */
	$add : function(docType,docTypeName) {
		var me = this;
		var formTypeName = 'Wdbl.form.' + WDBL$Globals.DocTypeEditForms[docType];
		this.add(Ext.create('Ext.panel.Panel',{
			closable: true,
   			tooltip : '新建:'+docTypeName,
			title:'新建'+docTypeName,
			layout: 'border',
		    innerType: '0',
		    ffszType: '0',
	   		items: [Ext.create(formTypeName,{
	   			region:'center',
	   			parent:me,
		        grid:me.innerGrid,
		        loadUrl : this.api.load,
		        addType:'newAdd',
		        autoDestroy:true,
		        url : this.api.edit
	   		})]
		})).show();
	},
	/**
	 * 编辑
	 */
	$edit : function() {
		var me = this;
		if (!me.innerGrid.selModel.hasSelection()) {
			Ext.Msg.alert("提示", "没有选中任何记录，无法进行编辑！");
			return;
		}
		var selected = me.innerGrid.selModel.getSelection();
		var id = selected[0].data.id;
		if (selected.length != 1 || id == null) {
			Ext.Msg.alert("提示", "只能编辑一条记录！");
			return;
		}
		var title = selected[0].data.title;
		var docType = selected[0].data.docType;
		var barNo = selected[0].data.barNo;
		var formTypeName = 'Wdbl.form.' + WDBL$Globals.DocTypeEditForms[docType];
		
		var currTab = Ext.getCmp("reg_"+id);
		if(currTab){
			me.setActiveTab(currTab);
			return;
		}
		
		var ffsz = "0";
		if(formTypeName == "Wdbl.form.WideEditForm") {
			ffsz = "1";
		}
		
		this.add(Ext.create('Ext.panel.Panel',{
			id:"reg_"+id,
			tooltip : '编辑:'+title,
			closable: true,
        	title:'编辑'+title,
			layout: 'border',
		    innerType: '2',
		    ffszType: ffsz,
		    loadParam : {'id' : id},
	   		items: [Ext.create(formTypeName,{
	   			region:'center',
	   			parent:me,
		        grid:me.innerGrid,
		        load:true,
		        barNo:barNo,
		        loadUrl : this.api.load,
		        loadParam : {'id' : id},
		        addType:'newEdit',
		        autoDestroy:true,
		        url : this.api.edit
	   		})]
		})).show();
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
			id = activeTab.loadParam.id;
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
	// 呈送
	$f_draft : function() {
		var me = this;
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
			id = activeTab.loadParam.id;
		}
		var process_common = Ext.create('Wdbl.form.op.submitForm',{recordID:id, store:me.innerGrid.store, gridpanel:me.innerGrid});
		process_common.showWin();
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
	        src:'gun_search_reg.jsp?r='+Math.random()
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
	//扫描枪扫描后的代码处理
	$analysis_code : function(params){
		var me = this;
		Ext.apply(me.innerGrid.store.proxy.extraParams, params);
		me.innerGrid.store.loadPage(1);
		this.label_search_win.close();
	},
	// 打印条码
	$f_tm : function() {
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
				Ext.Msg.alert("提醒", "一次只能打印一条记录！");
				return;
			}
			
			PrintLabel5025(
			    "create_reg_file", 
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
			    selected[0].data.barNo.replace(/(^\s*)/g,"   "), "", "", "", 10, 10,  2
			);
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.loadParam.id;
			Ext.Ajax.request({
				url : 'reg_file/loadBean.do',
				params:{id : id, sessionID:id},
				method : "POST",
				success : function(form, action) {
					var json = Ext.JSON.decode(form.responseText);
					PrintLabel5025(
				    "create_reg_file", 
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
	// 录入领导批示
	$f_leaderInstructions : function() {
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
				Ext.Msg.alert("提醒", "只能编辑一条记录！");
				return;
			}
		} else {
			// 如果当前激活的是编辑页面，id从页面内获取
			var activeTab = me.getActiveTab();
			id = activeTab.loadParam.id;
		}
		var process_common = Ext.create('Wdbl.form.op.leaderInstructionsForm',{recordID:id});
		process_common.showWin();
	},
	// 文件处理
	$f_transfer : function() {
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
			id = activeTab.loadParam.id;
		}
		var process_common = Ext.create('Wdbl.form.op.transferForm',{recordID:id});
		process_common.showWin();
	},
	// 办结
	$f_finish : function() {
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
			id = activeTab.loadParam.id;
		}
		
		
		Ext.Msg.confirm("警告", "确定要办结吗？", function(button) {
			if (button == "yes") {
				var ids = [];
				ids.push(id);
				Ext.Ajax.request({
					waitTitle : "请稍候",
					waitMsg : "正在提交请求，请稍候",
					url : 'reg_file/finish.do',
					method : "post",
					params : {ids : ids},
					scope : this,
					success : function(response, opts) {
						var json = Ext.JSON.decode(response.responseText);
						if (json.success) {
							if( me.innerGrid.id != me.getActiveTab().id ) {
								me.getActiveTab().close();
							}
							me.innerGrid.store.reload();
						}
					},
					failure : function() {
						Ext.Msg.alert('系统提示','办结失败,请刷新后重新操作!');
					}
				}, this);
			}
		}, this);
		
	},
	// 刷新
	$f_refresh : function() {
		var me = this;
		me.innerGrid.getStore().reload();
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
			id = activeTab.loadParam.id;
		}
		
		var sendSetWin = Ext.create('Wdbl.form.op.WideSetForm',{regID:id,unitNumber:count});
		sendSetWin.showWin();
	},
	allBtns : null,
	editYBtns : null,
	editNBtns : null,
	finishNBtns : null,
	ffszBtns : null,
	initComponent : function() {
		var me = this;
		var editBtn = Ext.create('Ext.button.Button',{text : '编辑',iconCls : 'table_edit',handler : function(){me.$edit();}});
		var tmBtn = Ext.create('Ext.button.Button',{text : '打印条码',iconCls : 'attach',handler : function(){me.$f_tm()}});
		var searchBtn = Ext.create('Ext.button.Button',{text : '条码查询',iconCls : 'page_code',handler : function(){me.$code_search()}});
		var updateTypeBtn = Ext.create('Ext.button.Button',{text : '修改类型',iconCls : 'report_edit',handler : function(){me.$f_update_type()}});
		var delBtn = Ext.create('Ext.button.Button',{text : '删除',iconCls : 'delete',handler : function(){me.$del()}});
		var printBtn = Ext.create('Ext.button.Button',{text : '打印',iconCls : 'printer',menu:[{text : '阅批单', handler : function(){me.$f_print('CITY_GOV_DOC');}},{text : '密电单', handler : function(){me.$f_print('CIPHER_TELEGRAPH_DOC');}},{text : '电话记录', handler : function(){me.$f_print('TEL_RCD_DOC');}},{text : '批示抄清', handler : function(){me.$f_print('INSTRUCTIONS_DOC');}}]});
		var draftBtn = Ext.create('Ext.button.Button',{text : '呈送',iconCls : 'report_edit',handler : function(){me.$f_draft()}});
		var leaderInstructions = Ext.create('Ext.button.Button',{text : '录入批示',iconCls : 'note_edit',handler : function(){me.$f_leaderInstructions()}});
		var transfer = Ext.create('Ext.button.Button',{text : '发送文件', iconCls : 'folder_page',handler : function(){me.$f_transfer()}});
		var finish = Ext.create('Ext.button.Button',{text : '办结', iconCls : 'table_key',handler : function(){me.$f_finish()}});
		var refresh = Ext.create('Ext.button.Button',{text : '刷新', iconCls : 'arrow_refresh',handler : function(){me.$f_refresh()}});
		var ffszBtn = Ext.create('Ext.button.Button',{text : '大范围文件',iconCls : 'arrow_out',menu:[{text:'20个单位',handler : function(){me.$f_ffsz('20');}},{text:'60个单位',handler : function(){me.$f_ffsz('60');}}]});
		me.allBtns = [editBtn,tmBtn,searchBtn,updateTypeBtn,delBtn,printBtn,draftBtn,leaderInstructions,transfer,finish,refresh];
		me.editYBtns = [tmBtn,printBtn,draftBtn,leaderInstructions,transfer,finish];
		me.editNBtns = [editBtn,searchBtn,updateTypeBtn,delBtn,refresh];
		me.finishNBtns = [editBtn,searchBtn,updateTypeBtn,delBtn,refresh];
		me.ffszBtns = [ffszBtn];
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
				items:[{
					xtype:'button',
					text : '添加',
					iconCls:'add',
					menu:[
						{text : '局长批示件', handler : function(){me.$add(5,'局长批示件');}},
						{text : '市局主要领导文件', handler : function(){me.$add(12,'市局主要领导文件');}},
						{text : '局内文件', handler : function(){me.$add(6,'局内文件');}},
						{text : '上级领导批示件', handler : function(){me.$add(14,'上级领导批示件');}},
						{text : '市委市政府正式文件', handler : function(){me.$add(1,'市委市政府正式文件');}},
						{text : '公安部正式文件', handler : function(){me.$add(4,'公安部正式文件');}},
						{text : '大范围分发', handler : function(){me.$add(2,'大范围分发');}},
						{text : '密码电报文件', handler : function(){me.$add(13,'密码电报文件');}},
						{text : '电话记录', handler : function(){me.$add(3,'电话记录');}},
						{text : '领导交办件', handler : function(){me.$add(7,'领导交办件');}},
						{text : '领导兼职', handler : function(){me.$add(8,'领导兼职');}},
						{text : '直接转文', handler : function(){me.$add(9,'直接转文');}},
						{text : '亲启件', handler : function(){me.$add(10,'亲启件');}},
						{text : '其它', handler : function(){me.$add(11,'其它');}}
					]
				}, {xtype : "tbseparator"}, 
				editBtn,{xtype : "tbseparator"}, 
				tmBtn,{xtype : "tbseparator"}, 
				searchBtn,{xtype : "tbseparator"}, 
				updateTypeBtn, {xtype : "tbseparator"}, 
				delBtn, {xtype : "tbseparator"}, 
				ffszBtn, {xtype : "tbseparator"}, 
				printBtn, {xtype : "tbseparator"}, 
				draftBtn, {xtype : "tbseparator"}, 
				leaderInstructions, {xtype : "tbseparator"}, 
				transfer, {xtype : "tbseparator"}, 
				finish, {xtype : "tbseparator"}, 
				refresh]
			}],
            listeners:{
            	'beforerender': function(){
					Ext.Array.each(me.allBtns,function(name,index,_self){me.allBtns[index].setDisabled(false);});
					Ext.Array.each(me.ffszBtns,function(name,index,_self){me.ffszBtns[index].setDisabled(true);});
		        },
            	tabchange : function(tabPanel, newCard, oldCard) {
					if(newCard.id == me.innerGrid.id){
						Ext.Array.each(me.allBtns,function(name,index,_self){me.allBtns[index].setDisabled(false);});
						Ext.Array.each(me.ffszBtns,function(name,index,_self){me.ffszBtns[index].setDisabled(true);});
						me.innerGrid.getStore().reload();
					} else if((newCard.innerType == 2 || newCard.innerType == 1) && newCard.ffszType == 0){
						Ext.Array.each(me.editYBtns,function(name,index,_self){me.editYBtns[index].setDisabled(false);});
						Ext.Array.each(me.editNBtns,function(name,index,_self){me.editNBtns[index].setDisabled(true);});
						Ext.Array.each(me.ffszBtns,function(name,index,_self){me.ffszBtns[index].setDisabled(true);});
					} else if( (newCard.innerType == 2 || newCard.innerType == 1) && newCard.ffszType == 1){
						Ext.Array.each(me.editYBtns,function(name,index,_self){me.editYBtns[index].setDisabled(false);});
						Ext.Array.each(me.editNBtns,function(name,index,_self){me.editNBtns[index].setDisabled(true);});
						Ext.Array.each(me.ffszBtns,function(name,index,_self){me.ffszBtns[index].setDisabled(false);});
					} else {
						Ext.Array.each(me.ffszBtns,function(name,index,_self){me.ffszBtns[index].setDisabled(true);});
						Ext.Array.each(me.allBtns,function(name,index,_self){me.allBtns[index].setDisabled(true);});
					}
				}
            }
			
		});
		me.callParent(arguments);
	}
});

/**
 * for index.jsp
 */

Ext.Loader.setConfig({enabled : true});
Ext.Loader.setPath('Wdbl.view', 'resources/wdbl/view');
Ext.Loader.setPath('Wdbl.model', 'resources/wdbl/model');
Ext.Loader.setPath('Wdbl.file_reg', 'resources/wdbl/file_reg');
Ext.Loader.setPath('Wdbl.fileprocess', 'resources/wdbl/file_process');
Ext.Loader.setPath('Wdbl.utils', 'resources/wdbl/utils');
Ext.Loader.setPath('Meeting', 'resources/meeting');
Ext.Loader.setPath('Meeting.model', 'resources/meeting/Model');
Ext.Loader.setPath('Meeting.views', 'resources/meeting/Views');
Ext.require(['*']);
var ICONS_PATH = 'resources/icons';
// 显示修改密码面板
var showWin = new modifyWin();
function showModifyWin(id) {
	showWin.show(id);
}
Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.BLANK_IMAGE_URL = sgif_path + "/extjs/s.gif";

	/**
	 * 上,panel.Panel
	 */
	var topHTML = "<div>"
			+ "<img src='"
			+ ICONS_PATH
			+ "/user.png' /> <div class='top_lbl'> 当前用户：<security:authentication property='principal.username'/></div>"
			+ "<a style='margin-left: 10px;' title='修改密码' href='#' onclick='showModifyWin(\"modify\")'><img src='"
			+ ICONS_PATH
			+ "/key.png' /> <div class='top_lbl'>修改密码</div></a>"
			+ "<a style='margin-left: 10px;' title='退出登录' href='logout'><img src='"
			+ ICONS_PATH
			+ "/door_out.png' /> <div class='top_lbl'>退出系统</div></a>"
			+ "</div>";
	var topPanel = Ext.create('Ext.panel.Panel', {
		region : 'north',
		height : 70,
		margins : '0 0 0 0',
		border : false,
		bodyStyle : 'background-color: rgb(223, 232, 246)',
		html : '<img style="position:absolute;left:0px;top:0px;" src="resources/images/header.png" /><div id="div_userinfo" style="text-align:right;width:350px;top:40px;height:50px;position:relative;left:-40px;float:right;">'+ topHTML + '</div>'

	});
	/**
	 * 左,panel.Panel
	 */
	var leftPanel = Ext.create('Ext.panel.Panel', {
		region : 'west',
		title : '导航栏',
		margins : '0 0 5 0',
		width : 200,
		layout : 'accordion',
		collapsible : true,
		split : true
	});
	/**
	 * 右,tab.Panel
	 */
	this.rightPanel = Ext.create('Ext.tab.Panel', {
		region : 'center',
		layout : 'fit',
		margins : '0 0 5 0',
		minTabWidth : 100,
		plugins : [Ext.create('Ext.ux.TabScrollerMenu', {
					menuPrefixText : '标签',
					maxText : 15,
					pageSize : 5
				})],
		items : []
	});

	/**
	 * 组建树
	 */
	var buildTree = function(json) {
		Ext.Array.forEach(json.children, function(c) {
			if (c.text == '全部文件') {
				addTab(c.id, c.text, c.url);
			}
		});
		return Ext.create('Ext.tree.Panel', {
			rootVisible : false,
			border : false,
			store : Ext.create('Ext.data.TreeStore', {
						root : {
							expanded : true,
							children : json.children
						}
					}),
			listeners : {
				'itemclick' : function(view, record, item, index, e) {
	
					var id = record.get('id');
					var text = record.get('text');
					var leaf = record.get('leaf');
					if (leaf) {
						var url = record.raw.url;
						if (url != null && url != 'undefined') {
							addTab(id, text, url);
						}
					}
				},
				scope : this
			}
		});
	};
	/** 点击菜单加载右侧tab页面 */
	var addTab = function(id, text, purl) {
		var tab = rightPanel.getComponent('tab' + id);
		if (tab == null) {
			rightPanel.add({loader : {
					url : purl,
					autoLoad : true,
					contentType : 'html',
					loadMask : true,
					scripts : true
				},
				id : 'tab' + id,
				closable : true,
				title : text
			}).show();
		} else {
			rightPanel.setActiveTab(tab);
		}
	}

	/**
	 * attributes 加载菜单树
	 */
	Ext.Ajax.request({
		url : 'menu/json.do',
		success : function(response) {
			var json = Ext.JSON.decode(response.responseText);
			Ext.each(json, function(el) {
				var panel = Ext.create('Ext.panel.Panel', {
					id : 'menu' + el.id,
					title : el.text,
					layout : 'fit'
				});
				panel.add(buildTree(el));
				leftPanel.add(panel);
			});
		},
		failure : function(request) {
			Ext.MessageBox.show({
				title : '操作提示',
				msg : "连接服务器失败,请重新登录！",
				buttons : Ext.MessageBox.OK,
				icon : Ext.MessageBox.ERROR
			});
		},
		method : 'post'
	});
	/**
	 * Viewport
	 */
	Ext.create('Ext.container.Viewport', {
		layout : 'border',
		renderTo : Ext.getBody(),
		items : [topPanel, leftPanel, rightPanel]
	});
});
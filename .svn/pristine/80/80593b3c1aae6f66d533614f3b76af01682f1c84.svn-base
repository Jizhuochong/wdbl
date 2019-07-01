/**
 * 分页bar
 */

 Ext.define('Wdbl.utils.PagingToolBar',{
 	extend:'Ext.toolbar.Paging',
 	requires : ['Ext.*'],
	beforePageText : "第 ",
	afterPageText : "页，共 {0} 页",
	firstText : "首页",
	prevText : "上一页",
	lastText : "末页",
	nextText : "下一页",
	refreshText : "刷新",
	emptyMsg : "没有要显示的数据",
	displayInfo : true,
	displayMsg : "<span style='font-size:13px;'>第{0}-{1}条,共{2}条</span>",
	emptyMsg : "没有数据",
	initComponent : function() {
		var me = this;
		Ext.applyIf(me, {items : ['-', {
			xtype : 'label',
			text : '每页'
		}, {
			xtype : 'numberfield',
			width : 60,
			value : 20,
			minValue : 1,
			allowBlank : false,
			listeners : {
				change : function(Field, newValue, oldValue) {
					var num = parseInt(newValue);
					if (isNaN(num) || !num || num < 1) {
						num = 10;
						Field.setValue(num);
					}
					me.store.pageSize = num;
				}
			}
		}, {
			xtype : 'label',
			text : '条'
		}]});
		me.callParent(arguments);
	}
 });
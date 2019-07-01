Ext.define('Meeting.views.InfoDoc', {
	extend : 'Ext.grid.Panel',

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Doc'],
	xtype : 'cell-editing',

	meid:null,
	
	// title : '录入参会领导信息',
	frame : false,

	initComponent : function() {
		this.cellEditing = new Ext.grid.plugin.CellEditing({
					clicksToEdit : 1
				});
				
		this.storeDoc = new Ext.data.JsonStore({
					autoLoad : true,
					model : 'Meeting.model.Doc',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/docList.do',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : "json",
							root : "rows",
							totalProperty : 'totalrows'
						},
						////传参
						extraParams:{
							id:this.meid
						}
					},
					sorters : [{
								property : 'common',
								direction : 'ASC'
							}]
				});

		Ext.apply(this, {
					height : 215,
					columnLines : true,
					plugins : [this.cellEditing],
					store : this.storeDoc,
					columns : [{
								dataIndex : 'id',
								xtype:'hiddenfield'								
							}, {
								header : '文档名称',
								dataIndex : 'docName',
								flex : 2,
								editor : {
									allowBlank : false
								}
							}, {
								header : 'Url',
								dataIndex : 'docUrl',
								flex :4,
								editor : {
									allowBlank : false
								}
							}, {
								header : '数据中心',
								dataIndex : 'dateCenterId',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}],
					selModel : {
						selType : 'cellmodel'
					},
					tbar : [{
								text : '添加文档',
								xtype : 'button',
								pressed : true,
								scope : this,
								handler : this.onAddClick
							}]
				});
		
		this.callParent();

	},

	onAddClick : function() {
		var rec =Ext.create('Meeting.model.Doc',{
					isJoin : true,
					id:null
				});

		this.getStore().insert(0, rec);
		this.cellEditing.startEditByPosition({
					row : 0,
					column : 0
				});
	},

	onRemoveClick : function(grid, rowIndex) {
		this.getStore().removeAt(rowIndex);
	}
})
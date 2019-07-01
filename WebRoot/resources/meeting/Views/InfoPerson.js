Ext.define('Meeting.views.InfoPerson', {
	extend : 'Ext.grid.Panel',
	
	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Person'],
	xtype : 'cell-editing',

	///构造器传参
	meid:null,

	// title : '录入参会人员信息',
	frame : false,

	initComponent : function() {
		this.cellEditing = new Ext.grid.plugin.CellEditing({
					clicksToEdit : 1
				});

		this.storePerson = new Ext.data.JsonStore({
					// autoDestroy : true,
					autoLoad : true,
					model : 'Meeting.model.Person',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/personList.do',
						actionMethods : {
							read : 'POST'
						},
						reader : {
							type : "json",
							root : "rows",
							totalProperty : 'totalrows'
						},
						// //传参
						extraParams : {
							id : this.meid
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
					store : this.storePerson,
					columns : [{
								dataIndex : 'id',
								xtype : 'hiddenfield'
							}, {
								header : '姓名',
								dataIndex : 'name',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}, {
								header : '单位',
								dataIndex : 'unit',
								flex : 1,
								editor : {
									allowBlank : false
								}
							}, {
								header : '职务',
								dataIndex : 'job',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '联系电话',
								dataIndex : 'phone',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '家庭住址',
								dataIndex : 'address',
								flex : 2,
								editor : {
									allowBlank : true
								}
							}, {
								xtype : 'actioncolumn',
								width : 30,
								sortable : false,
								menuDisabled : true,
								items : [{
											iconCls : 'delete',
											tooltip : '删除该人员信息',
											scope : this,
											handler : this.onRemoveClick
										}]
							}],
					selModel : {
						selType : 'cellmodel'
					},
					tbar : [{
								text : '添加参会人员',
								xtype : 'button',
								pressed : true,
								scope : this,
								handler : this.onAddClick
							}]
				});

		this.callParent();

	},

	onAddClick : function() {
		// Create a model instance
		var rec = Ext.create('Meeting.model.Person',{
					id : null
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
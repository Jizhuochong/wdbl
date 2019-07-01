Ext.define('Meeting.views.InfoLeader', {
	extend : 'Ext.grid.Panel',

	requires : ['Ext.selection.CellModel', 'Ext.grid.*', 'Ext.data.*',
			'Ext.util.*', 'Ext.form.*', 'Meeting.model.Leader'],
	xtype : 'cell-editing',

	meid:null,
	
	// title : '录入参会领导信息',
	frame : false,

	initComponent : function() {
		this.cellEditing = new Ext.grid.plugin.CellEditing({
					clicksToEdit : 1
				});
				
		this.storeLeader = new Ext.data.JsonStore({
//					autoDestroy : true,
					autoLoad : true,
					model : 'Meeting.model.Leader',
					proxy : {
						type : 'ajax',
						url : 'meetingInfo/leaderList.do',
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
					store : this.storeLeader,
					columns : [{
								dataIndex : 'id',
								xtype:'hiddenfield'								
							}, {
								header : '参会领导',
								dataIndex : 'leaderName',
								flex : 0.8,
								editor : {
									allowBlank : false
								}
							}, {
								header : '参会时间',
								dataIndex : 'jionTime',
								flex : 1.5,
								renderer: Ext.util.Format.dateRenderer('m/d/Y H:i'),
								editor : {
									xtype : 'datetimefield',
									format:'m/d/Y H:i'
								}
							}, {
								header : '会议内容',
								dataIndex : 'contents',
								flex : 2,
								editor : {
									allowBlank : false
								}
							}, {
								header : '随行人员',
								dataIndex : 'followPerson',
								flex : 2,
								editor : {
									allowBlank : true
								}
							}, {
								header : '乘车路线',
								dataIndex : 'roadMap',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '车证',
								dataIndex : 'carNum',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								header : '着装',
								dataIndex : 'clothes',
								flex : 1,
								editor : {
									allowBlank : true
								}
							}, {
								xtype : 'checkcolumn',
								header : '是否参加',
								dataIndex : 'isJoin',
								flex : 1,
								stopSelection : true
							}, {
								xtype : 'actioncolumn',
								width : 30,
								sortable : false,
								menuDisabled : true,
								items : [{
											iconCls : 'delete',
											tooltip : '删除该领导信息',
											scope : this,
											handler : this.onRemoveClick
										}]
							}],
					selModel : {
						selType : 'cellmodel'
					},
					tbar : [{
								text : '添加参会领导',
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
		var rec =Ext.create('Meeting.model.Leader',{
//					meId:this.meid,
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
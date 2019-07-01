/**
 * 
 */
 Ext.define('Wdbl.form.op.WideSetForm',{ extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	regID:null,
	store : null,
	grid: null,
	unitNumber:null,
	showWin : function(){
		var me = this;
		me.store.load({
        	callback:function(r,options,success){
                if(success == false) {  
                    Ext.Msg.alert("系统提示","序号范围错误！");  
                }
            } 
        });
        me.show();
	},
	autoDestroy:true,
	destroy:function(){
		if(this.fireEvent("destroy",this) != false){
			this.el.remove();
			if(Ext.isIE){
				CollectGarbage();
			}
		}
	},
    initComponent: function () {
    	var me = this;
	 	this.store = Ext.create("Ext.data.JsonStore", {
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
	        pageSize:100,
	        sortInfo: { field: 'id', direction: 'asc' }, 
	        proxy: {
	            type: "ajax",
	            url: "filedispense/listLoad.do?id="+me.regID+"&unitNumber="+me.unitNumber,
	           	actionMethods: { read: 'POST'},
	            reader: {
	                type: "json",
	                root: "rows",
	                totalProperty: 'totalrows'
	            },
	            simpleSortMode: true
	        }
        });
        this.grid = Ext.create('Ext.grid.Panel', {
        	scrollbar : true,
        	height : 200,
            store: me.store,
            plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
	            clicksToEdit: 1
	        })],
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
        me.grid.on('edit', function(editor, e) {
        	var jsonData = Ext.encode(Ext.pluck(me.grid.getStore().data.items, 'data'));
        	Ext.Ajax.request({
				url : "filedispense/updateListLoad.do?id="+me.regID+"&rows="+e.rowIdx,
				headers: {'Content-Type':'application/json'},
				params : jsonData,
				scope : this,
				success : function(response, opts) {
					var json = Ext.JSON.decode(response.responseText);
					me.store.removeAll();
					me.store.loadData(json.rows);
				},
				failure : function() {
					Ext.Msg.alert('系统提示','保存失败,请刷新后重新操作!');
				}
			});
        });
        Ext.applyIf(me,{
			buttonAlign:'right',//按钮的排列方式
			width : 800,
			height : 260,
			items: me.grid,
			title : '文件分发设置',
			buttons : [{
				text : '保存',
				formBind : true,
				monitorValid : true,
				handler : function() {
					var jsonData = Ext.encode(Ext.pluck(me.grid.getStore().data.items, 'data'));
					Ext.Ajax.request({
						url : "filedispense/add.do?id="+me.regID,
						headers: {'Content-Type':'application/json'},
						params : jsonData,
						scope : this,
						success : function(response, opts) {
							var json = Ext.JSON.decode(response.responseText);
							Ext.Msg.alert('系统提示', json.msg);
							me.close();
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
		me.callParent(arguments);
	}
   
});
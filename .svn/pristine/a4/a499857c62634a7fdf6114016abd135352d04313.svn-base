Ext.define('Wdbl.form.op.labelSearchForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	parent:null,
    showWin : function(){
    	var me = this;
		me.form_submit.getForm().reset();
		me.show();
    },
    initComponent: function () {
    	var me = this;
		me.form_submit = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 1
		    },
         	defaults:{labelAlign: 'right',labelWidth: 80,width:400},
            items: [{
				xtype : "textfield",
				fieldLabel : "条码编号",
				name : 'barNo'
			},{
				xtype : "textfield",
				fieldLabel : "发文单位",
				name : 'formUnit'
			},{
				xtype : "text",
				fieldLabel : "公文种类",
				name : 'docType'
			},{
				xtype : "textfield",
				fieldLabel : "发文字号",
				name : 'docNumber'
			},{
				xtype : "textfield",
				fieldLabel : "标题",
				name : 'title'
			},{
				xtype : "textfield",
				fieldLabel : "秘密等级",
				name : 'securityGrade'
			},{
				xtype : "textfield",
				fieldLabel : "条码信息",
				name : 'labelInfo'
			}],
	        buttons: [{
	            text: '搜索',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
					Ext.apply(parent.innerGrid.store.proxy.extraParams, this.getForm().getValues()); 
					parent.innerGrid.store.loadPage(1);
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                me.close();
	            }
	        }]
        });
		
		Ext.applyIf(me,{
			title: '按条码信息搜索',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 450,  
		    border: 0,
		    frame: false,
		    items: me.form_submit
		});
		me.callParent(arguments);
	}
   
});
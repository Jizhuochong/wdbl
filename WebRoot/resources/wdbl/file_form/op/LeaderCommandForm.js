Ext.define('Wdbl.form.op.LeaderCommandForm',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],

	commandContainer:null,
	
	removeFun:null,

    initComponent: function () {
    	var me = this;
		me.form_submit = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 1
		    },
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [{
				xtype : "combo",
				fieldLabel : "上级领导",
				name : 'superLeader',
				store : getComboDataStore('sjld'),
				displayField : 'node',
				queryMode: 'local',
		    	typeAhead: true,
				valueField : 'code',
				emptyText:'请选择'
			},{
				xtype : "textarea",
				fieldLabel : "批示内容",
				name : "cmdContent",
				labelWidth:60,
				rows : 10,
				maxLength: 2000,
				anchor : '100%'
			}],
	        buttons: [{
	            text: '保存',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
	            	var form_values = me.down('form').getForm().getValues();
					me.commandContainer.add(Ext.create('Ext.form.FieldContainer', {
						layout : "column",
						items : [{
							columnWidth : 0.2,
							xtype : 'textfield',
							name : "leaderName",
							padding : '0 5 0 0',
							fieldLabel : '上级领导',
							value : form_values['superLeader'],
							labelWidth: 60
						},{
							columnWidth : 0.7,
							xtype : 'textfield',
							name : "commandContent",
							padding : '0 5 0 0',
							fieldLabel : '批示内容',
							value : form_values['cmdContent'],
							labelWidth: 60
						}, {
							columnWidth : 0.1,
							xtype : 'button',
							text : '移除',
							handler : function(){
								me.removeFun(this);
							}
						}]
					})); 
					me.close();
	            }
	        },{
	            text: '关闭',
	            handler: function() {
	                me.close();
	            }
	        }]
        });
		
		Ext.applyIf(me,{
			title: '上级领导批示',
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
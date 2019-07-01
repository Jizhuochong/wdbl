Ext.define('Wdbl.form.op.transferCheckbox',{
	extend : 'Ext.window.Window',
	requires : ['Ext.form.*', 'Ext.window.Window'],
	checkID : null,
	store : null,
	store2 : null,
    initComponent: function () {
    	var me = this;
    	var checkItems = new Array();
    	var checkItems2 = new Array();
    	
    	if(me.store != null) {
	    	for(var i =0;i<me.store.getCount();i++){
	    		checkItems.push({
	    			boxLabel: me.store.getAt(i).get("name"),
	    			inputValue: me.store.getAt(i).get("name")
	    		});
			}
    	}
    	if(me.store2 != null) {
	    	for(var i =0;i<me.store2.getCount();i++){
	    		checkItems2.push({
	    			boxLabel: me.store2.getAt(i).get("unit"),
	    			inputValue: me.store2.getAt(i).get("unit")
	    		});
			}
    	}
    	
    	// 第一组复选框
    	var myCheckboxGroup = new Ext.form.CheckboxGroup({  
            xtype : 'checkboxgroup',
            id : 'transferCheckBox',
            fieldLabel: '领导信息',
			width:800,
            columns : 5,
            items: checkItems
        });
        
        // 水平线
    	var myHr = new Ext.form.CheckboxGroup({  
            xtype : 'checkboxgroup',
            id : 'myhr',
            style: 'border-bottom: 2px solid #99BCE8; margin-bottom: 25px; margin-top: 20px;',
			width:800,
            columns : 5
        });
        
        // 第二组复选框
        var myCheckboxGroup2 = new Ext.form.CheckboxGroup({  
            xtype : 'checkboxgroup',
            id : 'transferCheckBox2',
            fieldLabel: '单位信息',
			width:800,
            columns : 5,
            items: checkItems2
        });
        
		me.form_checkBox = Ext.create('Ext.form.FormPanel',{
			frame: true,
			layout: {
		        type: 'table',
		        columns: 1
		    },
		    maxHeight: 550,
            overflowY: 'auto',
         	defaults:{labelAlign: 'right',labelWidth: 60,width:400},
            items: [myCheckboxGroup, myHr, myCheckboxGroup2],
	        buttons: [{
	            text: '确定',
	            formBind:true,
	            monitorValid:true,
	            handler: function() {
	            	var valueObj = Ext.getCmp('transferCheckBox').getChecked();
	            	var value = '';
					Ext.Array.each(valueObj, function(item){
						if(value == '') {
							value += item.inputValue;
						} else {
							value += ', ' + item.inputValue;
						}
					});
					
					var valueObj2 = Ext.getCmp('transferCheckBox2').getChecked();
	            	var value2 = '';
					Ext.Array.each(valueObj2, function(item){
						if(value2 == '') {
							value2 += item.inputValue;
						} else {
							value2 += ', ' + item.inputValue;
						}
					});
					
					if(value == '') {
						value += value2;
					} else if(value2 == '') {
						value += value2;
					} else {
						value += ', ' + value2;
					}

	            	try {
	            		Ext.getCmp(me.checkID).setValue(value);
	            	} catch(err) {
	            		console.error(err.message);
	            	}
					me.close();
	            }
	        },{
	            text: '取消',
	            handler: function() {
	                me.close();
	            }
	        }]
        });
        
		Ext.applyIf(me,{
			title: '局内单位',
		    layout: 'fit',  
		    modal: true, 
		    plain:true,
		    width: 850,  
		    border: 0,
		    frame: false,
		    items: [me.form_checkBox]
		});
		me.callParent(arguments);
	}
   
});
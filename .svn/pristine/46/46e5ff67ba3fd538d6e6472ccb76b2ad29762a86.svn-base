/**
 * 
 */
 Ext.define('Wdbl.form.op.FileItem',{extend:'Ext.form.FieldContainer', requires : ['Ext.*'],
	border:false,
	text:'',
	parent:null,
	fileData:null,
	rBtn:null,
	onBtnClick:function(){
		var me =this;
		this.fireEvent('btnClick', me);
	},
	onLabelClick:function(){
		var me = this;
		this.fireEvent('labelClick',me);
	
	},
    initComponent: function () {
    	var me = this;
    	me.addEvents({'btnClick':true,'labelClick':true});
    	me.rBtn = Ext.create('Ext.button.Button',{
    		iconCls:'page_delete',
			tooltip:'删除',
    		listeners: {
        		'click': function(){
	        		me.onBtnClick();
        		}
  			}
  		});
  		
		Ext.applyIf(me,{
		    layout: 'hbox',
		    frame: false,
		    defaults:{border:false,margin:'0 2 0 0'},
		    items: [
		    	{xtype:'button',iconCls:'attach',disabled:true},
		    	{xtype:'label',text:me.text,
		    		listeners: {
		        		'render' : function() {//渲染后添加click事件
				          Ext.fly(this.el).on('click', function(){ me.onLabelClick(); });
				        }
  				}},
		    	me.rBtn
	      	]
		});
		me.callParent(arguments);
	}
   
});

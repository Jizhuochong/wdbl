//下拉列表-公共资源
Ext.define('Wdbl.fileprocess.combobox_common',{ 
	//送阅类型
	sendtype: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['阅示', '阅示'], ['阅', '阅'], ['传阅', '传阅'], ['分送', '分送']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		colspan: 5,
      		editable:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
  //领导批示方式
	leadertype: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['圈阅', '圈阅'], ['批示', '批示']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		colspan: 5,
      		editable:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
    //文件处理方式
	transfertype: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['传真', '传真'], ['复印发', '复印发'], ['原件转', '原件转'], ['存档', '存档'], ['阅后退回', '阅后退回'],['另件及批示增发','另件及批示增发']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		colspan: 5,
      		editable:true,
      		labelAlign: 'right',	
			queryMode: 'local',
	    	typeAhead: true,
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
     //发送拟办开始
	draft_start: function(fieldname,lablename){  
			var store = Ext.create('Ext.data.ArrayStore',{
	          	fields: ['id', 'name'],
	          	data: [['请', '请'],['送', '送'],['让', '让']]
	      	});
	    return Ext.create('Ext.form.ComboBox',{
	    		width:80,
	      		editable:false,
	      		labelAlign: 'right',
      			name:fieldname,
	          	fieldLabel: lablename,
	          	store: store,
	          	displayField: 'name',
	          	valueField: 'id',
	          	mode: 'local'
	    });
    },
    //部门
	department: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['办公室', '办公室'], ['机要室', '机要室'], ['网站室', '网站室']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		width:80,
      		editable:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
    //办公室领导
	officeLeader: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['张三', '张三'], ['李四', '李四']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		width:80,
      		editable:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
     //办公室领导-职务
	officeLeaderJob: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['主任', '主任'], ['副主任', '副主任']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		width:80,
      		editable:false,
      		allowBlank:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
    //发送拟办行为
	officeLeaderAction: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['阅批', '阅批'], ['阅示', '阅示']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		width:80,
      		editable:false,
      		allowBlank:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
    
    
    //审批结果
	sp_result: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['同意', '同意'], ['不同意', '不同意']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		allowBlank:false,
      		editable:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    },
     //审批领导-职务
	spLeaderJob: function(fieldname,lablename){  
		var store = Ext.create('Ext.data.ArrayStore',{
          	fields: ['id', 'name'],
          	data: [['局长', '局长'], ['副局长', '副局长']]
      	});
      	return Ext.create('Ext.form.ComboBox',{
      		editable:false,
      		allowBlank:false,
      		labelAlign: 'right',
      		name:fieldname,
          	fieldLabel: lablename,
          	store: store,
          	displayField: 'name',
          	valueField: 'id',
          	mode: 'local'
      	});
    }
   
   
    
    
    
});
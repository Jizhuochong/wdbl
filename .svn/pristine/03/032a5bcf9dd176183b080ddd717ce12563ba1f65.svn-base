Ext.define('Meeting.model.Meeting', {
			extend : 'Ext.data.Model',

			requires : ['Meeting.model.Doc', 'Meeting.model.Time',
					'Meeting.model.Person', 'Meeting.model.Leader'],

			fields : [{
						name : 'id',type:'long'
					}, {
						name : 'title',type:'string'
					}, {
						name : 'content',type:'string'
					}, {
						name : 'undertakeUnit',type:'string'
					}, {
						name : 'remark',type:'string'
					}, {
						name : 'contacter',type:'string'
					}, {
						name : 'phonenum',type:'string'
					}, {
						name : 'typeJuge',type:'string'
					}, {
						name : 'meetStatu',type:'string'
					}, {
						name : 'forwardUnit',type:'string'
					}, {
						name : 'oprUser',type:'string'
					}, {
						name : 'oprUserUnit',type:'string'
					}, {
						name : 'updateTime',type:'string'
					}, {
						name : 'oprstatus',type:'string'
					}],

			hasMany : {
				model : 'Ext.model.Time',
				name : 'timeList'
			},
			hasMany : {
				model : 'Ext.model.Leader',
				name : 'leaderList'
			},
			hasMany : {
				model : 'Ext.model.Person',
				name : 'persons'
			},
			hasMany : {
				model : 'Ext.model.Doc',
				name : 'docList'
			}

		});
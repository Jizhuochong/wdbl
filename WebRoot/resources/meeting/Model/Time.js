Ext.define('Meeting.model.Time', {
			extend : 'Ext.data.Model',
			fields : [{
						name : 'id',
						type : 'long'
					},
					// {name: 'meId', type: 'long'},
					{
						name : 'meStartTime',
						type : 'String'
					}, {
						name : 'meEndTime',
						type : 'string'
					}, {
						name : 'meAddress',
						type : 'string'
					}]
		});
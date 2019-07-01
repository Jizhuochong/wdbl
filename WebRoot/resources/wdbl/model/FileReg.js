Ext.define('Wdbl.model.FileReg', {
			extend : 'Ext.data.Model',
			alias : 'model.filereg',

			requires : ['Ext.data.Field'],
			fields : [{
						name : "id"
					}, {
						name : "sn"
					},{
						name : "barNo"
					}, {
						name : "docType"
					}, {
						name : "title"
					}, {
						name : "copies"
					}, {
						name : "numberRange"
					}, {
						name : "summary"
					}, {
						name : "formUnit"
					}, {
						name : "docUnit"
					}, {
						name : "tabUnit"
					}, {
						name : "receiveTime"
					}, {
						name : "docNumber"
					}, {
						name : "securityGrade"
					}, {
						name : "urgencyDegree"
					}, {
						name : "keyword"
					}, {
						name : "toUnit"
					}, {
						name : "ccUnit"
					},{
						name : "deadline"
					}, {
						name : "remark"
					}, {
						name : "registrar"
					},{
						name: "sameNumTit"
					}, {
						type : 'date',
						dateFormat : 'time',
						name : "registerTime"
					},{
						name : 'handlestatus'
					},{
						name : 'fglb'
					}],
			hasMany : {
				model: 'Wdbl.model.OriginalFile',
				name : "originalFiles",
				associationKey:'originalFiles'
			}
		});
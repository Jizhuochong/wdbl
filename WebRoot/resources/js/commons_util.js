/**
 * 数据字典DataStore
 * 
 * @param {String}
 *            dictParentCode
 * @return {Ext.data.Store}
 */
function getComboDataStore(dictParentCode) {
	return Ext.create("Ext.data.Store", {
		fields : [{
			name : "code"
		}, {
			name : "node"
		}],
		autoLoad : true,
		proxy : {
			type : "ajax",
			url : "code/loadCode.do",
			actionMethods : {
				read : 'POST'
			},
			extraParams : {
				parent : dictParentCode
			},
			reader : {
				type : "json",
				root : "rows"
			}
		}
		});
}
//单位
function getComboDataUnit() {
	return Ext.create("Ext.data.Store", {
				fields : [{
							name : "id"
						}, {
							name : "unit"
						}],
				autoLoad : true,
				proxy : {
					type : "ajax",
					url : "pubExternUnitInfo/list.do",
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : "json",
						root : "rows"
					}
				},
				pageSize:1000
			});
}
//领导
function getComboLeader() {
	return Ext.create("Ext.data.Store", {
				fields : [{
							name : "id"
						}, {
							name : "name"
						}],
				autoLoad : true,
				proxy : {
					type : "ajax",
					url : "pubJleaderInfo/list.do",
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : "json",
						root : "rows"
					}
				},
				pageSize:9999
			});
}

//单位信息
function getUnitInfo() {
	return Ext.create("Ext.data.Store", {
				fields : [{
							name : "id"
						}, {
							name : "unit"
						}],
				autoLoad : true,
				proxy : {
					type : "ajax",
					url : "pubExternUnitInfo/list.do",
					actionMethods : {
						read : 'POST'
					},
					reader : {
						type : "json",
						root : "rows"
					}
				},
				pageSize:9999
			});
}


/**
 * 密级和保密期限Renderer
 */
function securityGradeRenderer(value) {
	return value;
}
/**
 * 紧急程度Renderer
 */
function urgencyDegreeRenderer(value) {
	return value;
}
/**
 * 空值Renderer
 * 
 * @param {}
 *            value
 * @return {}
 */
function emptyRenderer(value) {
	if (value) {
		return value;
	} else {
		return 'N/A';
	}
}
/**
 * 序号范围Renderer
 * 
 * @param {}
 *            value
 */
function numberRangeRenderer(val) {
	if (val) {
		if (val.indexOf(',') == val.length - 1)
			return val.replace(/,/, '');
		else
			return val.replace(/,/, ' ~ ');
	} else {
		return 'N/A';
	}
}

/**
 * 文件类型Renderer
 */
function docTypeRenderer(val) {
	var dname = "";
	if (val == 1)
		dname = "市委市政府正式文件";
	if (val == 2)
		dname = "大范围分发文件";
	if (val == 3)
		dname = "电话记录";
	if (val == 4)
		dname = "公安部正式文件";
	if (val == 5)
		dname = "局长批示件";
	if (val == 6)
		dname = "局内文件";
	if (val == 7)
		dname = "领导交办件";
	if (val == 8)
		dname = "领导兼职";
	if (val == 9)
		dname = "直接转文";
	if (val == 10)
		dname = "亲启件";
	if (val == 11)
		dname = "其它";
	if (val == 12)
		dname = "主要领导文件";
	if (val == 13)
		dname = "密码电报";
	if (val == 14)
		dname = "上级领导批示件";
	return dname;
}

function bindEvent(base,func){ //替换函数，使用时，现在页面进行定义，并在确定的时候重写，然后再调用
	if(base)
		base = func;
}

function getTransferCheckStore(dictParentCode) {
	return Ext.create("Ext.data.Store", {
		fields : [{
			name : "code"
		}, {
			name : "node"
		}],
		autoLoad : true,
		proxy : {
			type : "ajax",
			url : "code/loadCode.do",
			actionMethods : {
				read : 'POST'
			},
			extraParams : {
				parent : dictParentCode
			},
			reader : {
				type : "json",
				root : "rows"
			}
		},
		pageSize:9999
	});
}
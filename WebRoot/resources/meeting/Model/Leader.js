Ext.define('Meeting.model.Leader', {
    extend: 'Ext.data.Model',
    requires : ['Ext.data.Field'],
    fields: [
        {name: 'id',  type: 'long'},
//        {name: 'meId',  type: 'long'},
        {name: 'leaderName',  type: 'string'},
        {name: 'followPerson',  type: 'string'},
        {name: 'jionTime',  type: 'string'},
        {name: 'roadMap',  type: 'string'},
        {name: 'contents',  type: 'string'},
        {name: 'carNum',  type: 'string'},
        {name: 'clothes',  type: 'string'},
        {name: 'isJoin', type: 'boolean', defaultValue: true, convert: null}
    ]
});
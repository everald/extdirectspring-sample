Ext.define('sample.store.SampleGridStore', {
    'extend': 'Ext.data.Store',
    'model': 'sample.model.GridObject',
    'autoLoad': true,
    'remoteSort': true,
    'pageSize': 50,
    'sorters': [{
            'property': 'itemKey',
            'direction': 'DESC'
        }
    ]
});
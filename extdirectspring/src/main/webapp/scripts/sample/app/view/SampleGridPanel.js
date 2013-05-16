Ext.define('sample.view.SampleGridPanel', {
    'extend': 'Ext.panel.Panel',
    'alias': 'widget.samplegridpanel',
    'id': 'GridPanel', // could be mainwindow reference errors
    'title': 'Grid for Items',
    'layout': 'fit',
    'items': [{
            'xtype': 'grid',
            'id': 'gridlist',
            'store': 'SampleGridStore',
            'columns': {
                'items': [{
                    'text': 'Item Key',
                    'dataIndex': 'itemKey'
                }, {
                    'text': 'Name',
                    'dataIndex': 'name'
                }, {
                    'text': 'Creation Date',
                    'dataIndex': 'timestamp',
                    'renderer': function (v) {
                        return Ext.Date.format(new Date(v), "d/m/Y H:i:s");
                    }
                }
            ],
            'defaults': {
                'flex': 1
            }},
            'dockedItems': [{
                'xtype': 'pagingtoolbar',
                'store': 'SampleGridStore',
                'id': 'gridPaging',
                'dock': 'bottom',
                'displayInfo': true,
                'displayMsg': 'Displaying GridItems {0} - {1} of {2}',
                'emptyMsg': 'No GridItems to display'
            }
        ]
        }
    ]
});
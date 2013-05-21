Ext.define('sample.view.SampleGridPanel', {
    'extend': 'Ext.panel.Panel',
    'alias': 'widget.samplegridpanel',
    'id': 'GridPanel',
    'title': 'Grid for Items',
    'layout': 'fit',
    'items': [{
            'xtype': 'grid',
            'id': 'gridlist',
            'store': 'SampleGridStore',
            'features': [{
                    'ftype': 'filters',
                    'encode': true,
                    'local': false,
                    'filters': [{
                            'type': 'string',
                            'dataIndex': 'name'
                        }
                    ]
                }
            ],
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
                        'xtype': 'datecolumn',
                        'format': 'd/m/Y H:i:s'
                    }
                ],
                'defaults': {
                    'flex': 1,
                    'filterable': true
                }
            },
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
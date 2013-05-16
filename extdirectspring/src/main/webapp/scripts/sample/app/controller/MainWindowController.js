Ext.define('sample.controller.MainWindowController', {
    'extend': 'Ext.app.Controller',
    'id': 'MainWindowController',
    'views': ['SampleGridPanel'],
    'stores': ['SampleGridStore'],
    'models': ['GridObject'],
    'init': function () {
        var me = this;
        this.control({});
    }
});
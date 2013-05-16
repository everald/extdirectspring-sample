Ext.Loader.setConfig({
    'enabled': true
});

Ext.require('Ext.direct.*', function () {
    Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});

Ext.application({
    'name': 'sample',
    'appFolder': '/extdirectspring-example/scripts/sample/app',
    'controllers': ['MainWindowController'],
    'autoCreateViewport': true
});
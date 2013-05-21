Ext.Loader.setConfig({
    'enabled': true
});

Ext.Loader.setPath('Ext.ux', 'http://cdn.sencha.com/ext/gpl/4.2.0/examples/ux');

Ext.require(['Ext.ux.grid.FiltersFeature', 'Ext.grid.feature.*','Ext.direct.*'], function () {
    Ext.direct.Manager.addProvider(Ext.app.REMOTING_API);
});

Ext.application({
    'name': 'sample',
    'appFolder': '/extdirectspring-example/scripts/sample/app',
    'controllers': ['MainWindowController'],
    'autoCreateViewport': true
});
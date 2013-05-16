Ext.define('sample.view.Viewport', {
	'extend' : 'Ext.Viewport',
	'id' : 'viewport',
	'layout' : 'fit',
	'initComponent' : function() {
		this.items = [ {
			'xtype' : 'samplegridpanel'
		} ];
		this.callParent(arguments);
	}
});
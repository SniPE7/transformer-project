//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.Hoverer");

/*
** Hoverer is a general purpose class that should probably be declared somewhere else
**
** Create a new Hoverer on a DOM node, and it will fire onHover when the mouse has stood still for a while
*/
dojo.declare("ibm.tivoli.simplesrm.srm.dojo.Hoverer", null,
{
	jiggle: 3, 				// pixels the mouse can move in x or y and still be considered still
	still_time: 500,		// millisecs mouse must be still
	monitoredNode: null,	// the node I'm attached to and monitoring for a hovering mouse
	_x0: -1,				// the starting point and time
	_y0: -1,
	_t0: -1,
	_moveHandler: null,		// dojo.hitch(this, "_onmousemove") and save the result
	_onHoverHandler: null,	// dojo.hitch(this, "_onHover") and save the result
	_connHandles: null,		// event connection handles
	_move_connHandle: null,	// the connection handle for mousemove - gets connected and disconnedted a lot
	_timer: -1,				// the window.setTimeout timer ID
	_data: null,			// holds user-supplied data that might be needed in an onHover handler

	/*
	** create a new Hoverer
	** node: the DOM node we're waiting for the mouse to hover over
	** delay: optional, milliseconds the mouse must sit still to trigger OnHover
	**           defaults to 500ms.
	*/
	constructor: function(/*int?*/delay) 
	{
		//console.log("Hoverer.ctor");
		if( !isNaN(delay) ) {
			this.still_time = delay
		}
		this._connHandles = [];
		this._data = new Object();
	},
	/*
	** Call destroy when you're finished with this Hoverer to disconnect internal event handlers
	*/
	destroy: function()
	{
		//console.log("Hoverer.destroy");
		this._disconnectNode();
	},
	/*
	** Give the Hoverer a DOM node to monitor
	*/
	setDOMNode: function(/*DOM node*/node)
	{
		//console.log("Hoverer.setNode(%o)", node);
		if(this.monitoredNode) {
			this._disconnectNode();
		}
		this.monitoredNode = node;
		if(node) {
			this._connHandles.push(dojo.connect(this.monitoredNode, "onmouseover", this, "_onmouseover"));
			this._connHandles.push(dojo.connect(this.monitoredNode, "onmouseout",  this, "_onmouseout"));
		}
	},
	getDOMNode: function()
	{
		return this.monitoredNode;
	},
	/*
	** OnHover gets passed a copy of this
	** if there's data you need in your handler, 
	** use setData to store it here
	*/
	setData: function(/*string*/name, /*any*/value)
	{
		this._data[name] = value;
	},
	/*
	** retrieve data stored with setData
	*/
	getData: function(/*string*/name)
	{
		return this._data[name];	// could be undefined
	},
	/*
	** Start watching for the mouse to hover over my monitored DOM node
	** Since Hoverer starts monitoring onmouseover, call connect if you set the 
	** monitored node from it's onmouseover or onclick and you want to start immediatly
	**
	** evt: optional, the DOM event passed into your onmouseover or onclick event
	**
	*/
	connect: function(evt)
	{
		//console.log("Hoverer.connect");
		this._hook();
		this._restart(evt);
	},
	/********** internal helpers ****************/
	_disconnectNode: function()
	{
		//console.log("Hoverer._disconnectNode");
		this._unhook();
		this._clear();
		var c;
		while(c = this._connHandles.pop()) {
			dojo.disconnect(c);
		}
		this.monitoredNode = null;
	},
	_hook: function()
	{
		//console.log("Hoverer._hook(%o)", this.monitoredNode);
		if(this._moveHandler == null) {
			this._moveHandler = dojo.hitch(this, "_onmousemove");
			this._onHoverHandler = dojo.hitch(this, "_fire_onHover");
		}
		if(this._move_connHandle == null) {
			this._move_connHandle = dojo.connect(this.monitoredNode, "onmousemove", null, this._moveHandler);
		}
	},
	_unhook: function()
	{
		//console.log("Hoverer._unhook(%o)", this.monitoredNode);
		if(this._move_connHandle) {
			dojo.disconnect(this._move_connHandle);
			this._move_connHandle = null;
		}
	},
	/*** internal mouse event handlers ***/
	_onmouseover: function(evt)
	{
		this._hook();
	},
	_onmouseout: function(evt)
	{
		this._clear();
		this._unhook();
	},
	_onmousemove: function(evt)
	{
		//console.log("Hoverer._onmousemove: (%d)", evt.timeStamp);
		// ignore it if the mouse moves just a tiny bit
		if(this._t0 > 0 &&
		   Math.abs(evt.clientX - this._t0) < this.jiggle &&
		   Math.abs(evt.clientY - this._t0) < this.jiggle)
		{
			//console.log("Hoverer._onmousemove: looking good: (%d, %d)", evt.clientX, evt.clientY);

			// the mouse has moved a tiny bit.  Check to see if we've been
			// here long enough to be hovering
			if(evt.timeStamp - this._t0 >= this.still_time) {
				// ta da
				if(this._timer) {
					window.clearTimeout(this._timer);
					this._timer = -1;
				}
				this._fire_onHover();
				this._restart(evt);
			}
		}
		else {
			this._restart(evt);
		}
	},
	/*
	** start over
	*/
	_restart: function(evt)
	{
		//console.log("Hoverer._restart");
		this._clear();
		this._t0 = evt.timeStamp;
		this._x0 = evt.clientX;
		this._y0 = evt.clientY;
		this._timer = window.setTimeout(this._onHoverHandler, this.still_time);
	},
	/*
	** clear the internal timer
	*/
	_clear: function()
	{
		//console.log("Hoverer._clear");
		if(this._timer > 0) {
			window.clearTimeout(this._timer);
		}
		this._timer = this._t0 = this._x0 = this._y0 = -1;
	},
	/*
	** fire the OnHover event, passing this Hoverer to handlers
	*/
	_fire_onHover: function()
	{
		try {
			this.OnHover(this);
		}
		catch(ex) {
			new ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError(ex);
			console.error("An error occured handling Hoverer.OnHover: ", ex);
		}
	},
	/*
	** override, or use dojo.connect to handle OnHover
	*/
	OnHover: function(hoverer)
	{
		//console.log("***** you are hovering *****");
	}
});


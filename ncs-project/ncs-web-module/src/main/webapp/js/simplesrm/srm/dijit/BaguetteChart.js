//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.BaguetteChart");

dojo.require("dijit._Widget");
dojo.require("dojox.gfx");
dojo.require("dojox.color");
dojo.require("ibm.tivoli.simplesrm.srm.dojo.SimpleSRMError");

dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.BaguetteChart", 
	[dijit._Widget],
{	
	backgroundColor: [162, 178, 202],	// the background color
	graysOnly: false,				// if true, render the data in grayscale
	barWidth: 0,					// width of the bar, not including padding
	barHeight: 20,					// height of the bar, not including padding
	captionHeight: 16,				// height of a row of the captions
	captionFontSize: 12,			// in pixels.  Should be > k
	outlineWidth: 4,				// inpixels, thickness of the graph's outline
	num_cells: 20,					// the number of cells drawn over the bar
	paddingx: 5,					// padding
	paddingy: 5,
	showLegend: true,				// do I draw the legend?
	showTotal: false,				// do I include a Total in the legend?
	totalLegend: "Total",			// replace to localize
	surface: null,					// the drawing surface
	bg_rect: null,					// the background color
	top_grp: null,					// container of everything else, transform creates the padding
	data_grp: null,					// data colored rectangles are drawn here, child of top_grp
	outline_grp: null,				// the white outline, child of top_grp
	legend_grp: null,				// group holding the data legend
	_legend_lines: 1,				// the number of lines of legend
	_clickhandlers: null,
	_data: null,					// my rendered data
	_nominalHeight: 0,				// basic height of the widget, taking into account the bar, outline and padding
	_canRender: true,				// might be false for ie + silverlight
	
	isReady: false,
	
	constructor: function(/*object*/params, /*domNode*/domNode)
	{
		if(params && params.id) {
			this.id = params.id;
		}
		this.data_group = null;
		this._clickhandlers = [];
	},
	postMixInProperties: function()
	{
		// make sure the outline is wide enough to mask the corners of the data bar
		this.outlineWidth = Math.round(Math.sqrt(2*Math.pow(this.barHeight/2, 2)) - this.barHeight/2);
	},
	buildRendering: function()
	{
		this.inherited(arguments);
		if(dojo.isIE) {
			this._canRender = ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.testIEForSilverlight();
			if(this._canRender)
				dojo.style(this.domNode, "position", "relative");
			else
				dojo.style(this.domNode, "display", "none");
		}
	},
	startup: function()
	{
		if(this._canRender)
			this._createSurface();
		this.inherited(arguments);
	},
	_createSurface: function()
	{
		// if we haven't been given a width, size the chart to fit in its parent
		if(this.barWidth <= 0) {
			this._findBarWidth();
		}
		if(this.barWidth <= 0) {
			// at this point, this.domNode doesn't always have a size yet
			window.setTimeout(dojo.hitch(this, this._createSurface), 500);
			return;
		}

		this._nominalHeight = this.barHeight + 2*this.outlineWidth + 2*this.paddingy;
		this.surface = dojox.gfx.createSurface(this.domNode, 
									this.barWidth + 2*this.outlineWidth + 2*this.paddingx, 
									this._nominalHeight + (this.showLegend ? (this.captionHeight + this.paddingy) : 0));
		
		this.surface.whenLoaded(this, this._createGraphics);
	},
	_findBarWidth: function()
	{
		if(this.domNode) {
			var sz = dojo.contentBox(this.domNode);
			this.barWidth = sz.w - 2 * this.outlineWidth - 2 * this.paddingx;
		}
	},
	_createGraphics: function()
	{
		try {
			if(!this.surface) {
				// shouldn't be here
				throw new Error("Don't call _createGraphics yourself");
			}
	
			bg_grp = this.surface.createGroup();
			h = (this.barHeight + 2*this.outlineWidth + 2*this.paddingy) 
				+ (this.showLegend ? (this.captionHeight + this.paddingy) : 0);
			this.bg_rect = bg_grp.createRect({x: 0, y: 0, 
									width: this.barWidth + 2*this.outlineWidth + 2*this.paddingx, 
									height: h})
			// FYI: if the bg_rect doesn't fill the entire space around the graph, it looks pretty bad
			//		.setFill({type: "linear", x1:0, y1: 0, x2:0, y2:h,
			//					colors: [{offset:0, color:"#F0F0F0"}, {offset:1, color:"#B3B3B3"}]})
					;
	
			this.top_grp = bg_grp.createGroup();
			this.top_grp.setTransform({dx: this.outlineWidth + this.paddingx, dy: this.outlineWidth + this.paddingy});	// shift the origin over to create a margin
					
			// data will go here
			this.data_grp = this.top_grp.createGroup();
			this._drawNoData();
					
			// dojox.gfx doesn't support clipping.  
			// create a mask the background color around the rounded ends
			var endcap_radius = this.barHeight/2;
			var buf = this.paddingy;
	
			// super deluxe new outline
			// TODO: should the outline gradient be a function of the background color?
			ocolora = "#f0f0f0";
			ocolorz = "#b3b3b3";
			this.outline_grp = this.top_grp.createGroup();
			this.outline_grp.createShape({type: "path"})
				.setAbsoluteMode("relative")
				.setStroke(null)
				.moveTo(this.barWidth-endcap_radius, 2*endcap_radius)
				.lineTo(-(this.barWidth-2*endcap_radius), 0)
				.arcTo(endcap_radius, endcap_radius, 0, 0, 1, 0, -2*endcap_radius)
				.lineTo(this.barWidth-2*endcap_radius, 0)
				.arcTo(endcap_radius, endcap_radius, 0, 0, 1, 0, 2*endcap_radius)
				.closePath()
				.moveTo(0, this.outlineWidth)
				.lineTo(-(this.barWidth-2*endcap_radius), 0)
				.arcTo(endcap_radius+this.outlineWidth, endcap_radius+this.outlineWidth, 0, 0, 1, 0, -2*(endcap_radius+this.outlineWidth))
				.lineTo(this.barWidth-2*endcap_radius, 0)
				.arcTo(endcap_radius+this.outlineWidth, endcap_radius+this.outlineWidth, 0, 0, 1, 0, 2*(endcap_radius+this.outlineWidth))
				.closePath()
				.setFill({type: "linear", x1:0, y1:2*(endcap_radius+this.outlineWidth), x2:0, y2:-this.outlineWidth,
							colors:[{offset:0, color:ocolora}, {offset:1, color:ocolorz}]})
				;
	
	
			// the inner grid
			var cell_w = this.barWidth/this.num_cells;
			var grid = this.outline_grp.createShape({type: "path"})
				.setStroke({color: "white", style: "Solid", width: 1})
				.setAbsoluteMode("relative")
				.moveTo(cell_w, 0);
			for(var i = 0; i < this.num_cells-1; ++i) {
				grid.lineTo(0, this.barHeight).moveTo(cell_w, -this.barHeight);
			}
			
			// the legend area
			this.legend_grp = bg_grp.createGroup();
			this.isReady = true;
		}
		catch(ex) {
//			delete this.surface;
//			this.surface = null;
			console.error("BaguetteChart._createGraphics failed: ", ex);
		}
		return;
	},	
	_drawNoData: function()
	{
		try {
			this.data_grp.createShape({type: "rect", x: 0, y: 0, width: this.barWidth, height: this.barHeight})
						.setFill({type: "linear", x1: 0, y1: 0, x2: 0, y2: this.barHeight,
									colors: [{offset: 0, color: "white"}, {offset: 0.4, color: [140,140,140]}, {offset: 1, color: [200,200,200]}] });
		}
		catch(ex){
			// ie troubles
			console.log("BaguetteChart._drawNoData choked: ", ex);
		}
	},
	destroy: function()
	{
		this._disconnect();
		this.inherited(arguments);
	},
	_disconnect: function()
	{
		var h;
		while(h = this._clickhandlers.pop()) {
			dojo.disconnect(h);
		}
	},
	/*
	** the incoming data is an array of objects with the following properties
	**	name: name of this datum. gets passed to the onDatumClick event handler
	** 	value: integer value representing this datum
	**	color: an array representing the RGB color value for this datum (Note: must be RGB)
	**	caption: [optional] if not provided, the graph will create a caption as "name (value)"
	** TODO: use the dojox.color facilities to convert if I have to, or take a dojox.color.Color as input
	** for example:
	**	[
	** 		{name: 'one', value: 25, color: [166, 110, 219], caption: "caption 1"}, 
	**		{name: 'two', value: 25, color: [85, 186, 65], caption: "caption 2"}, 
	**		{name: 'three', value: 45, color: [81, 122, 204], caption: "Caption 3"}
	**	]
	*/
	// TODO: if the legend gets wider than the available space, I start a second row
	// but each row is left justified.  If I need 2 rows, I should put half in each row,
	// 3 rows 1/3 etc.
	_data_set: null,
	setData: function(/*array*/data_set)
	{
		if(!this._canRender)
			return;
		
		if(!dojo.isArray(data_set))
			data_set = null;	// when called on the timer, get an int sometimes
		
		if(undefined != data_set)
			this._data_set = data_set;	// cache for later

		if(!this.isReady) {
			//this.connect(this, this._createGraphics, this.setData);	didn't work
			window.setTimeout(dojo.hitch(this, this.setData), 1000);
			return;
		}
		
		if(undefined == data_set) { 
			data_set = this._data_set;
		}
		// copy the data
		if(undefined != data_set) {
			this._data = data_set.slice(0);
		}
		
		// disconnect the onclick handlers
		this._disconnect();
		this.data_grp.clear();
		this._drawNoData();
		this.legend_grp.clear();
		this._legend_lines = 0;
		this._sizeToFitLegend();		
		
		if(undefined == this._data)
			return;
		
		var data = this._data;
		
		// the colored rectangles for each datum
		var total = 0;
		for(var i in data) {
			total += data[i].value;
		}
		
		if(this.showTotal) {
			if(data.length >0 && data[data.length-1].name == this.totalLegend)
				data[data.length-1].value = total;
			else 
				data.push({name: this.totalLegend, value: total, color: [200, 200, 200]});
		}		
		
		if(total > 0) {
			var current_legend_lines = 1;
			var data_left = 0;
			var cap_left = this.paddingx;
			var max_cap_left = 0;
			var cap_top = (this.barHeight + 2*this.outlineWidth + 2*this.paddingy);
			for(var i = 0; i < data.length; ++i) {
				// draw the data bar
				var bar = data[i].value / total;
				var datum_rect_width = Math.round(this.barWidth * bar);
				var mono = this._makeGradient(new dojox.color.Color(data[i].color));
				var gradient = [mono[2], mono[3], mono[1], mono[0]];
				if(this.graysOnly) {
					gradient = this._makeGrayscale(gradient);
				}
				var fill = {type: "linear", x1: 0, y1: this.barHeight, x2: 0, y2: 0,
							colors: [{offset: 0, color: gradient[0]}, {offset: 0.6, color: gradient[1]}, 
							         {offset: 0.7, color: gradient[2]}, {offset: 1, color: gradient[3]}] };
				if(datum_rect_width > 0 && !(this.showTotal && data[i].name == this.totalLegend) ) {
					// Total doesn't go in the graph
					var datum  = this.data_grp.createShape({type: "rect", x: data_left, y: 0, width: datum_rect_width, height: this.barHeight})
											.setFill(fill);
					if(!dojo.isIE) {
						datum.getNode().setAttribute("name", data[i].name);
						this._clickhandlers.push(datum.connect("onclick", this, "_clickDatum"));
						this._clickhandlers.push(datum.connect("onmouseover", this, "_overDatum"));
						this._clickhandlers.push(datum.connect("onmouseout", this, "_outDatum"));
					}
					data_left += datum_rect_width;
				}
				if(this.showLegend) {
					// draw the caption	
					var one_cap = this.legend_grp.createGroup();
					if(!dojo.isIE) {
						one_cap.getNode().setAttribute("name", data[i].name);
						this._clickhandlers.push(one_cap.connect("onclick", this, "_clickDatum"));
						this._clickhandlers.push(one_cap.connect("onmouseover", this, "_overDatum"));
						this._clickhandlers.push(one_cap.connect("onmouseout", this, "_outDatum"));
					}
					
					// the colored box
					fill.y1 = this.captionHeight;
					if(data[i].name !== this.totalLegend) {	// no box for the total
						var capbox = one_cap.createShape({type: "rect", x: 0, y: 0, width: this.captionHeight, height: this.captionHeight})
										.setFill(fill)
										.setStroke({color: "#333333", width: 1})
										;
					}
					// the text, which is a little tricky, since it's origin is at it's bottom, where everything else has y=0 at the top
					var cap_text_string = data[i].caption == undefined ? data[i].name + " (" + data[i].value + ")" : data[i].caption;
					var cap_text = one_cap.createText({
									x: this.captionHeight + this.paddingx, 
									y: this.captionFontSize+(this.captionHeight-this.captionFontSize)/2, 
									align: "start", text: cap_text_string})
							.setFont({family: "Arial", style: "normal", weight: (data[i].name === this.totalLegend?"bold":"normal"), size: this.captionFontSize + "px"})
							.setFill("#333333")
							.setStroke(null)
							;
	
					var text_width = Math.round((cap_text_string.length * this.captionFontSize) * .7);	// rough estimate
					try {
						text_width = cap_text.getTextWidth();
					}
					catch(ex) {/* if display:none, can't getTextWidth */}
					var cap_width = this.captionHeight + this.paddingx + text_width;
					if(cap_left + cap_width + this.paddingx > this.barWidth) {
						cap_left = this.paddingx;
						cap_top += this.captionHeight + this.paddingy;
						var ssz = this.surface.getDimensions();
						if(++current_legend_lines > this._legend_lines) {
							this._legend_lines = current_legend_lines;
							this._sizeToFitLegend();
						}
					}
					one_cap.setTransform({dx: cap_left, dy: cap_top});
					this.legend_grp.add(one_cap);
					
					cap_left += cap_width + this.paddingx*2;
					if(cap_left > max_cap_left) max_cap_left = cap_left;
				}
			}
			if(this.showLegend) {
				max_cap_left -= this.paddingx;	// remove the unused right-side padding
				this.legend_grp.setTransform({dx: (this.barWidth - max_cap_left)/2});
			}
			if(current_legend_lines < this._legend_lines) {
				this._legend_lines = current_legend_lines;
				this._sizeToFitLegend();
			}
		}
		this.outline_grp.moveToFront();
	},
	_makeGrayscale: function(colors)
	{
		var grays = [];
		for(var i in colors)
		{
			var c = colors[i];
		    var luma = Math.round(c.r*0.3 + c.g*0.59+ c.b*0.11);
		    grays.push(new dojox.color.Color([luma, luma, luma]));
		}
		return grays;
	},
	_makeGradient: function(basecolor)
	{
		// I don't like the gradient generated by dojox.color.Palette.generators.monochromatic or shades.
		// cook up my own based on some experiements with the data the old dojox.color.Generator
		var g = new Array(4);
		g[2] = basecolor;

		var cmyk = basecolor.toCmyk();
		cmyk.c = Math.round(cmyk.c / 1.5);
		cmyk.m = Math.round(cmyk.m / 1.5);
		cmyk.y = Math.round(cmyk.y / 1.5);
		cmyk.b = Math.round(cmyk.b / 1.5);
		g[1] = dojox.color.fromCmyk(cmyk);
		cmyk.c = Math.round(cmyk.c / 1.4);
		cmyk.m = Math.round(cmyk.m / 1.4);
		cmyk.y = Math.round(cmyk.y / 1.4);
		cmyk.b = Math.round(cmyk.b / 1.4);
		g[0] = dojox.color.fromCmyk(cmyk);
		cmyk = basecolor.toCmyk();
		cmyk.c *= 2;
		cmyk.m *= 2;
		cmyk.y *= 2
		cmyk.b *= 2;
		// bringing the data w/in bounds is handled by from Cmyk, but I feel dirty not taking care of business myself
		if(cmyk.c > 100) cmyk.c = 100;
		if(cmyk.m > 100) cmyk.m = 100;
		if(cmyk.y > 100) cmyk.y = 100;
		if(cmyk.b > 100) cmyk.b = 100;
		g[3] = dojox.color.fromCmyk(cmyk);
		return g;
	},
	resize: function() {
		if(this.surface) {
			this.surface.clear();
			this._createGraphics();
			this.setData();
		}
	},
	_sizeToFitLegend: function()
	{
		var ssz = this.surface.getDimensions();
		var legend_lines = (this._legend_lines == 0 && this.showLegend) ? 1 : this._legend_lines;
		this._resize(parseInt(ssz.width), this._nominalHeight + legend_lines * (this.captionHeight + this.paddingy));
	},
	_resize: function(w, h) {
		if(this.surface) {
			this.surface.setDimensions(w, h);
			var s = this.bg_rect.getShape();
			s.width = w;
			s.height = h;
			this.bg_rect.setShape(s);
		}
	},
	_clickDatum: function(evt) 
	{
		console.log("Clicked ", evt.currentTarget.getAttribute("name"));
		if("function" == typeof this.onDatumClick ) {
			this.onDatumClick(evt, evt.currentTarget.getAttribute("name"));
		}
		evt.stopPropagation();
	},
	_overDatum: function(evt)
	{
		if("function" == typeof this.onDatumClick ) {
			dojo.style(evt.currentTarget, "cursor", "pointer");
		}
	},
	_outDatum: function(evt)
	{
		if("function" == typeof this.onDatumClick ) {
			dojo.style(evt.currentTarget, "cursor", "default");
		}		
	}
});
/**
 * these colors will work even if the UI is in greyscale, or the user is colorblind
 */
ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.defaultColors = 
[
	[127, 102, 153],
	[226, 141, 0],
	[139, 173, 231],
	[76, 204, 51],
	[0, 0, 93],
	[204, 160, 72],
	[7, 88, 196],
	[195, 195, 0],
	[102, 102, 127],
	[39, 14, 150],
	[188, 210, 232],
	[152, 96, 48],
	[255, 170, 28],
	[32, 88, 112],
	[127, 173, 220],
	[153, 153, 29],
	[153, 127, 178],
	[248, 208, 128],
	[21, 60, 5]
];

ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.getDefaultColor = function(i)
{
	return ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.defaultColors[i % ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.defaultColors.length];
}

ibm.tivoli.simplesrm.srm.dijit.BaguetteChart.testIEForSilverlight = function()
{
	slInstalled = false;
	if(dojo.isIE) {
		 try 
		 {
			 var slControl = new ActiveXObject('AgControl.AgControl');
			 slInstalled = true;
		 }
		 catch (e) {}
	}
	return slInstalled;
}

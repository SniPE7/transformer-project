/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * (C) Copyright IBM Corp. 2008 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dijit.TIPClientChart");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.themes.PlotKit.orange");

dojo.declare(
	"ibm.tivoli.tip.dijit.TIPClientChart",
	[dijit._Widget, dijit._Templated],
{
//	templateString: "<div>hello world</div>",
	
	constructor: function () {
		console.log ("enter TIPClientChart.constructor");
		this.chart = null;
		this.limit=10;
		this.magnitude = 30;
		console.log ("exit TIPClientChart.constructor");
	},
	postCreate: function () {
		console.log ("enter TIPClientChart.postCreate");
		this.seriesA = this.makeSeries(this.limit);
		this.seriesB = this.makeSeries(this.limit);
		this.seriesC = this.makeSeries(this.limit);
		this.makeObjects();
		console.log ("exit TIPClientChart.postCreate");
	},
    randomValue: function() {
		return Math.random() * this.magnitude;
	},
	makeSeries: function(len){
	    var s = [];
	    do{
    	    s.push(this.randomValue());
		}while(s.length < len);
	    return s;
	},
	makeObjects: function(){
		this.chart = new dojox.charting.Chart2D("test");
		this.chart.setTheme(dojox.charting.themes.PlotKit.orange);
	   	this.chart.addAxis("x", {fixLower: "minor", natural: true, min: 1, max: this.limit});
		this.chart.addAxis("y", {vertical: true, min: 0, max: 30, majorTickStep: 5, minorTickStep: 1});
		this.chart.addPlot("default", {type: "Lines"});
		this.chart.addSeries("Series A", this.seriesA);
		this.chart.addSeries("Series B", this.seriesB);
		this.chart.addSeries("Series C", this.seriesC);
		this.chart.addPlot("grid", {type: "Grid", hMinorLines: true});
		this.chart.render();
	    setInterval(dojo.hitch (this, this.updateTest), 3000);
	},
	updateTest: function() {
		this.seriesA.shift();
		this.seriesA.push(this.randomValue());
		this.chart.updateSeries("Series A", this.seriesA);

		this.seriesB.shift();
		this.seriesB.push(this.randomValue());
		this.chart.updateSeries("Series B", this.seriesB);

		this.seriesC.shift();
		this.seriesC.push(this.randomValue());
		this.chart.updateSeries("Series C", this.seriesC);

		this.chart.render();
	}
	
});

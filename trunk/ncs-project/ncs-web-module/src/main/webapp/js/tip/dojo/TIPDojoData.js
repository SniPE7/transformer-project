/******************************************************* {COPYRIGHT-TOP-OCO} ***
 * Licensed Materials - Property of IBM
 *
 * 5724-C51
 *
 * (C) Copyright IBM Corp. 2007 All Rights Reserved.
 *
 * US Government Users Restricted Rights - Use, duplication, or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 ******************************************************* {COPYRIGHT-END-OCO} ***/
dojo.provide("ibm.tivoli.tip.dojo.TIPDojoData");

// dojox.grid._data.model is the module with dojox.grid.data.DojoData
dojo.require("dojox.grid._data.model");

dojo.declare("ibm.tivoli.tip.dojo.TIPDojoData",
	          dojox.grid.data.DojoData,
{
//	returnTimes: 0,
	realCount: 0,
	
    // custom override of requestRows()
    requestRows: function (inRowIndex, inCount) {
         // creates serverQuery-parameter
         var row  = inRowIndex || 0;
         var params = {
	                start: row,
                    count: inCount || this.rowsPerPage,
                    serverQuery: dojo.mixin(
                      { start: row,
                        count: inCount || this.rowsPerPage,
                        sort:(this.sortColumn || '')
                      },
                      this.query
                    ),
                    query: this.query,
                    onBegin: dojo.hitch(this, "beginReturn"),
                    onComplete: dojo.hitch(this, "processRows")
                }
          this.store.fetch(params);
    },

	beginReturn: function (inCount) {
		console.log ("in beginReturn with inCount: " + inCount);
		this.setRowCount (inCount);
		this.realCount = inCount;
		dojo.publish ("/ibm.tip/TIPDojoData#rowCountUpdated", [this.id, inCount]);
	},

	processRows: function (items, store) {
		console.log ("in processRows with items: ");
		console.log (items);
		console.log ("and store: ");
		console.log (store);
		if (!items || items.length===0){
		    console.log("not processing any rows");
		    return;
		}
		this.inherited (arguments);
	},

	getRealRowCount: function () {
		return (this.realCount);
	},
       
    getRowCount: function () {
    	return (this.rowsPerPage > this.count ? this.rowsPerPage : this.count);
    },
    
    // src: String
    setData: function(inData) {
//    	this.returnTimes = 0;
//    	this.setRowCount(this.rowsPerPage + 1);
          console.log("in setData() with data: ");
          console.log(inData);
          // edited not to reset the store
          this.data = [];
          this.allChange();
    },
    sort: function(colIndex) {
    	console.log ("enter sort for col: " + colIndex);
//    	this.returnTimes = 0;
        this.clearData();
        this.sortColumn = colIndex;
        this.requestRows(); 
    },
    
    getSortColumn: function () {
    	return (this.sortColumn);
    },
    
    // Overriding this method only to prevent the '?' from showing up
	getDatum: function(inRowIndex, inColIndex){
		//console.debug("getDatum", inRowIndex, inColIndex);
		var row = this.getRow(inRowIndex);
		var field = this.fields.values[inColIndex];
		return row && field ? row[field.name] : field ? field.na : '...';
		//var idx = row && this.fields._nameMaps[inColIndex+".idx"];
		//return (row ? row[idx] : this.fields.get(inColIndex).na);
	},
    canSort: function () {
          return true;
    }
});
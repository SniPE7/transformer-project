//////////////////////////////////////////////////////////////////
// @JS_LONG_COPYRIGHT_BEGIN@
// @JS_LONG_COPYRIGHT_END@
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dojo.data.OfferingTree");

dojo.declare("ibm.tivoli.simplesrm.srm.dojo.data.OfferingTree",
			 null,
{
    _tree : null,

	/**
	 * Produces an empty object that will be the offering tree.
	 */
	constructor : function() {
		this._tree = new Array();
	},
	
	addOffering : function(offering) {
		//console.log("OfferingTree.addOffering() entry", offering);
		var lastParent = null;
		
		/* include the parents in the tree */
		var parents = offering.CLASSSTRUCTURE;
		//debugger;
		if (parents != undefined) {
			for (var i = 0; i < parents.length; i++) {
				if (lastParent == null) {
					/* We're at top level */
					lastParent = this.addElt(this._tree, parents[i]);
				} else {
					//debugger;
					if (lastParent.Category == undefined) {
						lastParent.Category = new Array();
					}
					lastParent = this.addElt(lastParent.Category, parents[i]);
				}
			}
		}
		
		/* then the offering itself */
		if (lastParent != undefined) {
			if (lastParent.Offering == undefined) {
				lastParent.Offering = new Array();
			}
			this.addElt(lastParent.Offering, offering);
		} else {
			this.addElt(this._tree, offering);
		}

		//debugger;
		//console.log("OfferingTree.addOffering() exit", this._tree);
	},
	
	addElt : function(array, elt) {
		/* search for elt in array */
		var found = false;
		var leaf = null;
		for (var i = 0; i < array.length; i++) {
			//console.log("comparing ", array[i], " & " , elt);
			if (((elt.CLASSSTRUCTUREUID != undefined)
						&& (array[i].ID == elt.CLASSSTRUCTUREUID))
					|| ((elt.ITEMID != undefined)
						&&(array[i].ItemID == elt.ITEMID))) {
				found = true;
				leaf = array[i];
			}
		}
		/* not found, add it */
		if (found == false) {
			//debugger;
			leaf = { Ancestor : elt.CLASSSTRUCTUREUID,
					 ClassStructureID : elt.CLASSSTRUCTUREUID,
					 Description : elt.DESCRIPTION,
					 ID : elt.CLASSSTRUCTUREUID,
					 ItemID : elt.ITEMID,
					 ItemNum : elt.ITEMNUM,
					 ItemSetID : elt.ITEMSETID
				   };
			if (elt.LONGDESCRIPTION != undefined) {
				leaf.LongDescription = elt.LONGDESCRIPTION[0].LDTEXT;
			}
			if (elt.IMGLIB != undefined) {
				leaf.ImageName = elt.IMGLIB[0].IMAGENAME;
			}
			if (elt.CLASSIFICATIONID != undefined) {
				leaf.ClassificationID = elt.CLASSIFICATIONID;
			} 
			//else {
			//	console.log("No classif ID here");
			//}
			array.push(leaf);
		}
		return leaf;
	},
	
	clean : function() {
		this._tree = new Array();
	}

});

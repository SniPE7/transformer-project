//////////////////////////////////////////////////////////////////
// IBM Confidential
// OCO Source Material
// 5724-W78
// (C) Copyright IBM Corp. 2009
// The source code for this program is not published or otherwise
// divested of its trade secrets, irrespective of what has been
// deposited with the U. S. Copyright Office.
//////////////////////////////////////////////////////////////////

dojo.provide("ibm.tivoli.simplesrm.srm.dijit.MultipleModal");

dojo.require("dijit.DialogUnderlay");
/*
 *  
 */

dojo.declare(
	"ibm.tivoli.simplesrm.srm.dijit.MultipleModal",
	[],
	{		
		// "property signature" to recognise if class was mixed with MultipleModal
		supportsMultipleModal : true,
		
		
			patchCode: function(){	
				
				if(dijit.DialogUnderlay.prototype.counter == null) {				
					var newHide = function(){
						if(this.counter < 1) {
							console.log("dijit.DialogUnderlay : oldHide invoked ");
							this.counter = -1;
							this.oldHide();
						}
						else{
							this.counter--;
							this.domNode.style.zIndex = this.getUnderlayZIndex();
							console.log("dijit.DialogUnderlay : counter decremented. current = " + this.counter);
						}
					}; 
					var newShow = function() {
						this.counter++;
						this.domNode.style.zIndex = this.getUnderlayZIndex();						
						console.log("dijit.DialogUnderlay : counter incremented. current = " + this.counter);
						this.oldShow();
					};
					
					var getDialogZIndex = function(){
						// modal panels will have even z-index
						// underlay and modal panel shouldn't have the same z-index
						return 499 + 2 * dijit._underlay.counter;
					};
					
					var getUnderlayZIndex = function(){
						// underlay will have odd z-index
						return 498 + 2 *this.counter;
					};
					
					dijit.DialogUnderlay.extend({
						counter: -1 ,
						oldHide: dijit.DialogUnderlay.prototype.hide,
						oldShow: dijit.DialogUnderlay.prototype.show,
						hide: newHide ,
						show: newShow,
						getUnderlayZIndex: getUnderlayZIndex,
						getDialogZIndex: getDialogZIndex
						
					});
				}				
			},
			
				

			preOnKeyTest: function(event){
				/*
				 * traverse dom hierarchy. if event comes from widget mixed
				 * with MultipleModal (and it's not you) then let that event go
				 */
				var node = event.target; 
				while(node && (node.nodeName != "#document")){				
					var widget = dijit.byNode(node);
					//console.log("widget: " + widget);
					if(widget && widget.supportsMultipleModal && (widget != this)){
						//console.log("it's event from another modal dialog");
						return false;
					}					
					node = node.parentNode;
					//console.log("node: " + node);
				}
				//console.log("it's your event");
				return true;
			}

		}
	);

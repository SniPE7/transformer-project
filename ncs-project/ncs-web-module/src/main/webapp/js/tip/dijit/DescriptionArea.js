/****************************************************** {COPYRIGHT-TOP} ***
* Licensed Materials - Property of IBM
* 5724-Q87 
*
* (C) Copyright IBM Corp. 2008
*
* US Government Users Restricted Rights - Use, duplication, or
* disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
********************************************************** {COPYRIGHT-END} **/
dojo.require( "dojo.fx" );
dojo.require( "dijit._Templated" );
dojo.require( "dijit.layout.ContentPane" );

dojo.provide( "ibm.tivoli.tip.dijit.DescriptionArea" );
/******************************************************************************
* Colorful description area for Portlet Descriptions per Tivoli Guidlines
*
* Use innerDIV attachpoint to add graphics to the the description area
* background.
*
* Can be opened and closed.
******************************************************************************/
dojo.declare("ibm.tivoli.tip.dijit.DescriptionArea", [dijit.layout.ContentPane, dijit._Templated],
{
  portletTitle: "Portlet Title Here",
  
  description: "Portlet description here...",

  imageSrc: null,
  
  backgroundClass: null,

  templateString: "<div class='tipDescriptionArea open'><div dojoAttachPoint='innerDIV'><div dojoAttachPoint='hideNode'><div class='dijitReset' dojoAttachPoint='wipeNode'><div dojoAttachPoint='containerNode'><table border='0' cellpadding='0' cellspacing='0' width='100%'><tr><td rowspan='2' dojoAttachPoint='imageNode'></td><td width='100%' class='tipDescriptionAreaTitle'>${portletTitle}</td></tr><tr><td  width='100%' valign='top' class='tipDescriptionAreaContent'>${description}</td></tr></table></div></div></div></div><div class='tipDescriptionAreaClose' dojoAttachPoint='closeNode' dojoAttachEvent=\"onclick:_toggle\"></div></div>",

  // open: Boolean
  //    Whether description is opened or closed on first render.
  open: true,

  // duration: Integer
  //    Time in milliseconds to wipe in/wipe out
  duration: 500,
  
  /*****************************************************************************
  * Setup initial styles and state of the widget
  * Create and save all animations
  *****************************************************************************/
  postCreate: function()
  {
    if( !this.open )
    {
      this.hideNode.style.display = this.wipeNode.style.display = "none";
      this._updateClass();    
    }

    this.inherited("postCreate",arguments);
    
    if ( this.imageSrc !== null )
    {
      var image = dojo.doc.createElement( 'img' );
      image.src = this.imageSrc;
      image.border = "0";
      this.imageNode.appendChild( image );
    }

    if ( this.backgroundClass !== null )
    {
      this.innerDIV.className = this.backgroundClass;
    }
  
    // setup open/close animations
    var hideNode = this.hideNode, 
        wipeNode = this.wipeNode;
        
    this._wipeIn = dojo.fx.wipeIn({
      node: this.wipeNode,
      duration: this.duration,
      beforeBegin: function(){
        hideNode.style.display="";
      }
    });
    
    this._wipeOut = dojo.fx.wipeOut({
      node: this.wipeNode,
      duration: this.duration,
      onEnd: function(){
        hideNode.style.display="none";
      }
    });
  },
  
  /*****************************************************************************
  * The toggle open/close button div was clicked -- toggle open/close state
  *****************************************************************************/
  _toggle: function()
  {
    dojo.forEach([this._wipeIn, this._wipeOut], function(animation){
      if(animation.status() == "playing"){
        animation.stop();
      }
    });

    this[this.open ? "_wipeOut" : "_wipeIn"].play();
    this.open =! this.open;    
    this._updateClass();    
  },
  
  /*****************************************************************************
  * based on the open/close state of widget change CSS to update button
  *****************************************************************************/
  _updateClass: function()
  {
    if ( this.open === true )
    {
      dojo.removeClass( this.domNode, "close"  );
         dojo.addClass( this.domNode, "open" );          
    }
    else
    {
      dojo.removeClass( this.domNode, "open" );
         dojo.addClass( this.domNode, "close"  );          
    }  
  }  
});

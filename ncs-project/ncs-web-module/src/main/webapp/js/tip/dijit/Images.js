dojo.provide("ibm.tivoli.tip.dijit.Images");

// images cache
ibm.tivoli.tip.dijit.Images._images = 
{
    ICON_COLLAPSED:         dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "icon_collapsed.gif"),
    ICON_EXPANDED:          dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "icon_expanded.gif"),
    ICON_TREE_EMPTY:        dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "icon_tree_empty.gif"),
    OK_ICON:                dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "blank.gif"),
    WARNING_ICON:           dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "warning.gif"),
    WARNING_16_ICON:        dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "tree_warning.gif"),
    ERROR_ICON:             dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "error.gif"),
    ERROR_16_ICON:          dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "tree_error.gif"),
    BLANK_ICON:             dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "blank.gif"),
    ASTERISK_ICON:          dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "asterisk.gif"),
    PICKER_ICON:            dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "asterisk.gif"),
    CALENDAR_ICON:          dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "datatype_date.gif"),
    TIME_ICON:              dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "datatype_time.gif"),
    UP_ARROW_ICON:          dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "uparrow.gif"),
    DOWN_ARROW_ICON:        dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "downarrow.gif"),
    TABLE_ADD_ROW_ICON:     dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "add_row.gif"),
    TABLE_REMOVE_ROW_ICON:  dojo.moduleUrl("ibm.tivoli.tip.dijit.templates.images", "remove_row.gif")
};

ibm.tivoli.tip.dijit.Images.get = function() 
{
    return ibm.tivoli.tip.dijit.Images._images; 
};

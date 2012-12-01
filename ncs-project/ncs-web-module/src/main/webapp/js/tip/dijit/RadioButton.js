dojo.provide("ibm.tivoli.tip.dijit.RadioButton"); 

dojo.require("ibm.tivoli.tip.dijit.HtmlSelection");

/** 
 * A Radio Button widget.
 * 
 * Such widget displays a radio button. 
 * Multiple radio buttons are grouped by sharing the same name. 
 * The widget calls the onValueChanged callback when the checked 
 * state changes even if the widget still has the focus. 
 *   
 * The widget accepts the following parameters:
 * 
 *  - value: the initial value for the radio button. 
 *      - "true": the radio button must be checked
 *      - "false": the radio button must not be checked
 *  - checked: same as the HTML input attribute.
 *      - "", "checked", "true": the radio button must be checked
 *      - any other value: the radio button must not be checked
 * 
 * Example of a declaration:
 * 
 *       <div 
 *          dojoType="ibm.tivoli.tip.dijit.RadioButton" 
 *          name="radioGroup"
 *          checked="checked"/>
 *
 * @author: Marco Lerro (marco.lerro@it.ibm.com)    
 */
dojo.declare(
    "ibm.tivoli.tip.dijit.RadioButton",
    [ibm.tivoli.tip.dijit.HtmlSelection],
    {
        /** HTML input type */
        _type: "radio" 
    }
);


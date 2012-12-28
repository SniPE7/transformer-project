$(function() {
    var fixedValue = $( "#fixedValue" ),
    defaultValue = $( "#defaultValue" ),
    expressionOperation1 = $( "#expressionOperation1" ),
    expressionValue1 = $( "#expressionValue1" ),
    expressionLogic1 = $( "#expressionLogic1" ),
    expressionOperation2 = $( "#expressionOperation2" ),
    expressionValue2 = $( "#expressionValue2" ),
    expressionLogic2 = $( "#expressionLogic2" ),
    allFields = $( [] ).add( fixedValue ).add( defaultValue ).add( expressionOperation1 ).add( expressionValue1 ).add( expressionLogic1 ).add( expressionOperation2 ).add( expressionValue2 ).add( expressionLogic2 ),
    tips = $( ".validateTips" );

    function updateTips( t ) {
        tips
            .text( t )
            .addClass( "ui-state-highlight" );
        setTimeout(function() {
            tips.removeClass( "ui-state-highlight", 1500 );
        }, 500 );
    }

    function checkEmpty( o, n ) {
        if ( o.val().length < 1 ) {
            o.addClass( "ui-state-error" );
            updateTips( "必须设置" + n );
            return false;
        } else {
            return true;
        }
    }
    
    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 360,
        width: 580,
        modal: true,
        buttons: {
            "确定": function() {
                var bValid = true;
                allFields.removeClass( "ui-state-error" );
                var ruleMode = "";
                $("[name='ruleMode']:checked").each(function(){ 
                	ruleMode+=$(this).val(); 
                });
                
                if (ruleMode == "fixed") {
                    bValid = bValid && checkEmpty( fixedValue, "fixedValue");
                } else if (ruleMode == "expression") {
                  bValid = bValid && checkEmpty( expressionOperation1, "expressionOperation1");
                  bValid = bValid && checkEmpty( expressionValue1, "expressionValue1");
                } else {
                	updateTips( "必须设置阀值规则!" );
                	bValid = false;
                }
                
                var setDefaultValue = false;
                $("[name='setDefaultValue']:checked").each(function(){ 
                	setDefaultValue = true; 
                  });
                if (setDefaultValue) {
                	bValid = bValid && checkEmpty( defaultValue, "defaultValue");
                }	 else {
                	if (ruleMode == "expression") {
                		updateTips( "由于使用了表达式规则, 必须设置缺省取值!" );
                    bValid = false;
                	}
                }
                if ( bValid ) {
                   ruleCheckboxIndex = document.getElementById("rule_checkbox_index").value;
              	   var ruleString = "";
                   prefix = document.getElementById("rule_checkbox_elementPrefixelementPrefix").value;
                	 if (ruleMode == "fixed")  {
                		ruleString = "rule:{expression:threshold==" + fixedValue.val() + ", display:阀值必须为" + fixedValue.val() + "}";
                        $("#" + prefix +"_Rule_" + ruleCheckboxIndex)[0].value = ruleString;
                        $("#" + prefix +"_" + ruleCheckboxIndex)[0].value = fixedValue.val();
                        $("#" + prefix).value = fixedValue.val();
                	 } else {
                	   ruleString = "threshold " + expressionOperation1.val() + " " + expressionValue1.val();
                	   if (expressionLogic1.val().length > 0) {
                		   ruleString += expressionLogic1.val() + " threshold " + expressionOperation2.val() + " " + expressionValue2.val();
                	   }
                	   ruleString = "rule:{expression:" + ruleString + ", display:" + ruleString + "}";
                       $("#" + prefix +"_Rule_" + ruleCheckboxIndex)[0].value = ruleString;
                       $("#" + prefix +"_" + ruleCheckboxIndex)[0].value = defaultValue.val();
                       $("#" + prefix).value = defaultValue.val();
                	 }
                	 $( this ).dialog( "close" );
                	 $("#" + prefix +"_Icon_" + ruleCheckboxIndex).attr("title", ruleString);
                	 //alert($("#" + prefix +"_Icon_" + ruleCheckboxIndex).attr("alt"));
                }
            },
            "取消": function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });

    $( "#define-rule" )
        .button()
        .click(function() {
            
        });
});

function openRuleDialog(rule_checkbox_index, elementPrefix) {
	document.getElementById("rule_checkbox_index").value = rule_checkbox_index;
	document.getElementById("rule_checkbox_elementPrefixelementPrefix").value = elementPrefix;
	// Reset all elements
	document.getElementById("setDefaultValue").checked = false;
	
    rule = $("#" + elementPrefix +"_Rule_" + rule_checkbox_index)[0].value;
    //alert(rule);
    defaultValue = $("#" + elementPrefix +"_" + rule_checkbox_index)[0].value;
	
    $("#defaultValue")[0].value = defaultValue;
    
    // Parse rule
    fixedMode = rule.indexOf("rule:{expression:threshold==") == 0;
    if (fixedMode) {
    	$("#ruleFixMode").attr("checked",'checked');
        $("#fixedValue")[0].value = defaultValue;
    } else {
      expressionMode = rule.indexOf("rule:{expression:") == 0;
      if (expressionMode) {
    	  $("#ruleExpressionMode").attr("checked",'checked');
    	  
    	  expression = rule.substring("rule:{expression:".length, rule.indexOf(","));
    	  //alert(expression);
    	  r = jQuery.trim(expression.substring("threshold ".length, rule.length));
    	  op1 = r.substring(0, r.indexOf(" "));
          $("#expressionOperation1")[0].value = jQuery.trim(op1);
          
          r = r.substring(op1.length, r.length);
          if (r.indexOf("&&") < 0 && r.indexOf("||")){
              $("#expressionValue1")[0].value = jQuery.trim(r);
          } else {
            $("#expressionValue1")[0].value = r.substring(0, r.indexOf((r.indexOf("&&") >= 0)?"&&":"||"));
            $("#expressionLogic1")[0].value = (r.indexOf("&&") >= 0)?"&&":"||";
            
            r = jQuery.trim(r.substring(r.indexOf("threshold") + "threshold".length));
      	    op2 = r.substring(0, r.indexOf(" "));
            $("#expressionOperation2")[0].value = jQuery.trim(op2);
            $("#expressionValue2")[0].value = jQuery.trim(r.substring(op2.length, r.length));
          }
          document.getElementById("setDefaultValue").checked = true;

      }
    }
    
	$( "#dialog-form" ).dialog( "open" );
}


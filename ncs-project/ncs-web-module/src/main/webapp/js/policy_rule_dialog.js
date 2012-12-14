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
                   prefix = document.getElementById("rule_checkbox_elementPrefixelementPrefix").value;
                	 var ruleString = "";
                	 if (ruleMode == "fixed")  {
                		  ruleString = "rule:{fixed=" + fixedValue.val() + "}";
                      $("#" + prefix +"_Rule_" + ruleCheckboxIndex)[0].value = ruleString;
                      $("#" + prefix +"_" + ruleCheckboxIndex)[0].value = fixedValue.val();
                      $("#value1").value = fixedValue.val();
                	 } else {
                		 ruleString = "rule:{expression:''}";
                     $("#" + prefix +"_Rule_" + ruleCheckboxIndex)[0].value = ruleString;
                     $("#" + prefix +"_" + ruleCheckboxIndex)[0].value = defaultValue.val();
                     $("#" + prefix)[parseInt(ruleCheckboxIndex)].value = defaultValue.val();
                	 }
                	 $( this ).dialog( "close" );
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
	$( "#dialog-form" ).dialog( "open" );
}


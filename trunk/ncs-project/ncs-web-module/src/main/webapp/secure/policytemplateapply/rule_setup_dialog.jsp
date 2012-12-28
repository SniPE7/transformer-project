<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="dialog-form" title="阀值规则设置">
  <p class="validateTips">请设置取值规则或固定取值, 如果设置取值规则, 则需要同时设置缺省值.</p>

  <form>
    <input type="radio" name="ruleMode" id="ruleFixMode" class="text ui-widget-content ui-corner-all" value="fixed" ><label for="name">设置固定取值: </label>
    <input type="text" name="fixedValue" id="fixedValue" class="text ui-widget-content ui-corner-all" style="margin-bottom: 20px;width: 40px;"/>
    <br/>

    <input type="radio" name="ruleMode" id="ruleExpressionMode" class="text ui-widget-content ui-corner-all" value="expression" ><label for="name">设置取值规则: </label>
    <fieldset title="设置取值规则" style="margin-bottom: 20px;">
      阀值
      <select name="expressionOperation1" id="expressionOperation1" size="1" class="text ui-widget-content ui-corner-all">
        <option value="<">&lt;</option>
        <option value="<=">&lt;=</option>
        <option value=">">&gt;</option>
        <option value=">=" selected="selected">&gt;=</option>
        <option value="==">==</option>
      </select>
      <input type="text" name="expressionValue1" id="expressionValue1" style="width: 40px;"/>
      <select name="expressionLogic1" id="expressionLogic1" size="1" class="text ui-widget-content ui-corner-all">
        <option value="">无</option>
        <option value="&&">AND</option>
        <option value="||">OR</option>
      </select>
      <br/>
     阀值
      <select name="expressionOperation2" id="expressionOperation2" size="1" class="text ui-widget-content ui-corner-all">
        <option value="<">&lt;</option>
        <option value="<=" selected="selected">&lt;=</option>
        <option value=">">&gt;</option>
        <option value=">=">&gt;=</option>
        <option value="==">==</option>
      </select>
      <input type="text" name="expressionValue2" id="expressionValue2" style="width: 40px;"/>
    <br/><br/>
    <label for="name">设置缺省取值: </label>
    <input type="text" name="defaultValue" id="defaultValue" class="text ui-widget-content ui-corner-all" style="width: 40px;"/>
    </fieldset>
    
  </form>
</div>
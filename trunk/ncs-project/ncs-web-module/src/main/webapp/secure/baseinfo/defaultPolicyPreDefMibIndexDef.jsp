<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->

<script language="JavaScript" type="text/javascript">



function filtery(patterns, list){
 /* backed up dropdown list */
 if (!list.bak){

   list.bak = new Array();
   for (n=0; n<list.length; n++){
     list.bak[list.bak.length] = new Array(list[n].value, list[n].text);
   }
 }
 
 patternArray = patterns.split(" ");

 match = new Array();
 nomatch = new Array();
 for (n=0;n<list.bak.length;n++){

   matchflag = 1;
   for (j=0;j<patternArray.length; j++){
     pattern = patternArray[j];
	 if(list.bak[n][1].toLowerCase().indexOf(pattern.toLowerCase())==-1){
	   matchflag = 0;
	   continue;	  
     }
   }
   if(matchflag==1){
      match[match.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }else{ 
      nomatch[nomatch.length] = new Array(list.bak[n][0], list.bak[n][1]);
   }
 }

 for (n=0; n<match.length; n++){
   list[n].value = match[n][0];
   list[n].text = match[n][1];
 }
 for (n=0; n<nomatch.length; n++){
   list[n+match.length].value = nomatch[n][0];
   list[n+match.length].text = nomatch[n][1];
 }

 /* we make the 1st item selected  */
 list.selectedIndex=0;
}

</script>






<script language="javascript">
<!--
var com_ibm_isclite_currentModuleRef="";
var com_ibm_isclite_currentPageRef="";
var com_ibm_isclite_currentNodeType="";
var com_ibm_isclite_currentNodeTitle="";

function setkookie(key, value)
{
var cokie=getwholekookie();
if (cokie == null) 
 {// first time
  document.cookie = "TE3="+key+":"+value+" ; path=/";  
 } else {
  var start = cokie.indexOf(key + ":" );
  if ( start == -1 ) // not in list, so just append
    {
     document.cookie = "TE3="+cokie+" "+key+":"+value+" ; path=/";
    } else { // replace
     var end = cokie.indexOf(" ", start);
     if ( end == -1 ) end = cokie.length;
       document.cookie = "TE3="+cokie.substring(0, start-1)+" "+key+":"+value+" "+
                         cokie.substring(end+1, cokie.length)+" ; path=/";
    }
  
 }
}

function getwholekookie()
{
var len = 4;  // TE3=
var start = document.cookie.indexOf("TE3=");
if ( start == -1)
  { return null; }
var end = document.cookie.indexOf( ";", start );
if ( end == -1 ) end = document.cookie.length;
// this should return N1=E N2=C ....
return unescape( document.cookie.substring(start+len, end) );
}

function getkookie(key)
{
var cookiestr = getwholekookie();
if (cookiestr == null) return null;

var start = cookiestr.indexOf(key + ":" ); // start of the key
if ( start == -1 ) return null; // never found <key>
var value = start + key.length + 1; //begin of the value
var end = cookiestr.indexOf(" ", value); //end of the value
if ( end == -1 ) end = cookiestr.length; 
return cookiestr.substring(value,end); 
}

var dgeid=document.getElementById;
function expandCollapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

if (!dgeid) return;
if (document.getElementById(a).style.display=='none') {
 document.getElementById(a).style.display = 'block';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
 document.getElementById(g).title = '-';
 document.getElementById(g).alt = '-';
 setkookie(a, "E");
 } else {
 document.getElementById(a).style.display = 'none';
 document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
 document.getElementById(g).title = '+';
 document.getElementById(g).alt = '+';
 setkookie(a, "C");
 }
}

function Collapse(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'none';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_collapsed.gif';
document.getElementById(g).title = '+';
document.getElementById(g).alt = '+';
setkookie(a, "C");
}

function Expand(n)
{
N=new String("N");
I=new String("I");
a=N.concat(n);
g=I.concat(n);

document.getElementById(a).style.display = 'block';
document.getElementById(g).src = '<%=request.getContextPath() %>/images/arrow_expanded.gif';
document.getElementById(g).title = '-';
document.getElementById(g).alt = '-';
setkookie(a, "E");
}

function initAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) 
 {

	Collapse(i);
 }
}

function CollapseAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Collapse(i);
}

function ExpandAll()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) Expand(i);
}



function modifyform1(){
	form1.type.value="modify";
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss";
	this.form1.submit();
}
function saveform1(){
	form1.type.value="save";
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss";
	if(!checkobject()){
	   alert("私有Index OiD或私有Descr OiD非法，不能含有字符。");
	   return;
	}
	if(checkNull()){
	this.form1.submit();
	}
}
function deleteform1(){
	form1.type.value= 'delete';
	var v = confirm('确定删除信息?');
	if( ! v ){ return false;	}
	if(v){
	form1.action = "<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss";
	this.form1.submit();
	}
}
function   Trim(m){   
  while((m.length>0)&&(m.charAt(0)==' '))   
  m   =   m.substring(1, m.length);   
  while((m.length>0)&&(m.charAt(m.length-1)==' '))   
  m = m.substring(0, m.length-1);   
  return m;   
  }
  
  function checkNull(){
  if(Trim(document.getElementById("gname").value )== ""){
  alert("私有Mib Group名称不能为空！");
  return false;
  }
   if(Trim(document.getElementById("indexoid").value )== ""){
  alert("私有Index OiD不能为空！");
  return false;
  }
   if(Trim(document.getElementById("indexvar").value )== ""){
  alert("私有Index 等效名不能为空！");
  return false;
  }
   if(Trim(document.getElementById("descroid").value )== ""){
  alert("私有Descr OiD不能为空！");
  return false;
  }
   if(Trim(document.getElementById("descrvar").value )== ""){
  alert("私有Descr 等效名不能为空！");
  return false;
  }
  
  return true;
  }
	
function checkobject()
{

 var exp=form1.descroid.value;
 var exp1=form1.indexoid.value;
  var bRt=true;
  if(exp==null || exp1 == null) return true;
  if(exp.indexOf('..')>=0 || exp1.indexOf('..')>=0) return false;
  
  for(var j=0;j<exp1.length;j++)
  {
    b = exp1.charCodeAt(j);
    if(!((b>=48&&b<=57)||(b==46)))
    {
      bRt=false;
      break;
    }
  }
  
  for(var i=0;i<exp.length;i++)
  { 
    b = exp.charCodeAt(i);
    if(!((b>=48&&b<=57)||(b==46)))
    {
      bRt=false;
      break;
    }
  }
  
  return bRt;
}	
// -->
</script>
<% int nodei=0; %>


<body  class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >


 <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">基础信息</TD>
             <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">基础信息维护</TH>

  </TR>
  

  

    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">私有MIB Index</h1>


<form action="" method="post" id="form1" name="form1"> 
<input type="hidden" name="midcannotbedeleted" value="${model.mides }">       
<!-- button section --><br>
				<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">
         
        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
	
    	<td><input type="button" name="addoid" value="保存" onClick="saveform1();" class="buttons" id="functions" /></td>
    	
        
    </tr></table>  
    <c:if test="${model.message != null &&  model.message != ''}">
									<div id="errmsg"><fmt:message>${model.message }</fmt:message></div>
	        </c:if>
 
        </td>
        </tr>
        </table>
       
  <input type="hidden" name="type" value=""/>
  <input type="hidden" name="mid" value="${model.mid}">
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-row" nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="3" cellpadding="5" border="0"><tr> 
		<td colspan="2">
		<fieldset style="height: 20px">
    	<legend>私有Mib Group名称</legend>
    	
 
    		&nbsp;<input type="text" id="gname" name="gname" value="${model.gname}"  style="width: 245px"/>
		</fieldset>
    	</td>
    	</tr><tr>
<td>
    	<fieldset style="height: 80px">
    	<legend>添加 / 移除 OID</legend>
    	<table >
    	<tr>
    	<td> 私有Index OiD</td><td><input type="text" id="indexoid" name="indexoid" value="${model.indexoid}"  /></td>
    	<td> 私有Index 等效名</td><td><input type="text" id="indexvar" name="indexvar" value="${model.indexvar}"  /></td>
    	</tr>    	<tr>
    	<td> 私有Descr OiD</td><td><input type="text" id="descroid" name="descroid" value="${model.descroid}" /></td>
    	<td> 私有Descr 等效名</td><td><input type="text" id="descrvar" name="descrvar" value="${model.descrvar}" /></td>
    	</tr> 
    	<tr>
    	
    	</tr>    
    	</table>
    	</fieldset>
    	
    	</td>
    	</tr><tr>
    	
    	<td>
    	<fieldset style="height: 80px">
    	<legend>
    	私有Mib列表</legend>
<!-- Data Table -->
<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" WIDTH="5%">序号</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" WIDTH="70">编辑/ 删除</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >名称</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >私有Index OiD</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >私有Index 等效名</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >私有Descr OiD</th>
<th NOWRAP VALIGN="BOTTOM" CLASS="column-head-name" SCOPE="col" >私有Descr 等效名</th>
</tr>

<c:forEach items="${model.grplist}" var="gl" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="id"><c:out value="${gl.mid}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="idsel">
<a href="<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss?mid=${gl.mid}&type=modify"  >
			<img border="0" src="../../images/wtable_edit_sorts.gif" width="16" height="16" alt="编辑"></a>

&nbsp;
<a href="<%=request.getContextPath() %>/secure/baseinfo/defaultPolicyPreDefMibIndexDef.wss?mid=${gl.mid}&type=delete" onclick="return confirm('确定删除信息?');">
			<img border="0" src="../../images/delete.gif" width="16" height="16" alt="删除"></a>

</td>
<td VALIGN="top"  class="collection-table-text" headers="name"><c:out value="${gl.name }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="indexoid"><c:out value="${gl.indexoid }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="indexvar"><c:out value="${gl.indexvar }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="descroid"><c:out value="${gl.descroid }"/></td>
<td VALIGN="top"  class="collection-table-text" headers="descrvar"><c:out value="${gl.descrvar }"/></td>
</tr>
</c:forEach>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			
            <TD CLASS="table-totals" VALIGN="baseline">               
            Total:<c:out value="${model.total }"/>
             &nbsp;&nbsp;&nbsp;              
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE></fieldset>
    	</td> 
    	
    </tr>
    </table>
						<!-- Data Table -->  


        </td>
        </tr>
        </table>


<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
           
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>

</form>
 
</TD></TR></TABLE>


</TD></TR></TABLE>
</body>
</html>
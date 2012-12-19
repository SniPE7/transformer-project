<%@ include file="/include/include.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires",-1);
%>
<html>
<head>
<script type="text/javascript" src="../../dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
<style type="text/css">
@import "../../dojo/dojo/resources/dojo.css";

@import "../../dojo/dijit/themes/tundra/tundra.css";

@import "../../dojo/dijit/themes/dijit.css";
</style>
<link href='<%=request.getContextPath() %>/include/Styles.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>policyinfonavi</title>
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

function initAll00()
{
base=new String("N");
for (i=0; document.getElementById(base.concat(i)); i++) 
 {

	Collapse(i);
 }
}
function initAll()
{
base=new String("N");
//var txt="";
for (i=1; i<4; i++) 
 {
	try{
	Collapse(i);
	}
	catch(err){
	//txt+=i+" , ";
	}

 }
 //alert ("txt:"+txt);
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

function determineAction(e) {
    if (document.layers) {
        document.captureEvents(Event.KEYPRESS);
        document.captureEvents(Event.onClick);
    }
    
    document.onkeypress = function (evt) {
        var key = document.all ? event.keyCode : evt.which ? evt.which : evt.keyCode;
        
        if (key == 13) 
            document.navFilter.submit();
    };

    document.onclick =  function (evt) {
        document.navFilter.submit();
    };
}
window.onload =  function (){
	initAll();        
};

// -->
</script>
<% int nodei=0; %>



<body onLoad="initAll();" class="navtree" style="background-color:#FFFFFF;"  leftmargin="0" topmargin="0" marginwidth="0" marginheight="0"  >

<table height="26" width="100%" cellspacing="0" cellpadding="0"
	style="background-color: #FFFFFF;">

	<tr>
		<td align="left" colspan="2" class="navtree"
			style="background-color: #FFFFFF">
		<!-- title of the navi welcome page -->
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>策略应用</li>
		</ul>
		<!--<a style='color: #000000; text-decoration: none;' 	
				onclick="javascript:CollapseAll();"			
				href="javascript:CollapseAll();"
				dir="ltr" title="Collapse All">
				<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
				title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>展开所有</a>!-->
<%--
<div class="wpsGpFilter" valign="center" height="26" align="left" width="100%" style="border-left: 1px solid #3970B1;background-image:url(<%=request.getContextPath() %>/images/grpfilter_background.gif);background-repeat: repeat-x;">
 <form id="navifilter" name="navFilter" method="GET" action="<%=request.getContextPath() %>/secure/policyapply/policyApply.wss" target="detail">
<input type="text" id="categorypatterns" name="category_patterns"  value="filter" style="width:38px;color:gray" onclick="this.value='';" 
onkeypress="return;" onmouseup="return" onkeyup="filtery(this.value,navifilter.navpolicy)" onchange="filtery(this.value,navifilter.navpolicy)" />
                      
        <select name="navpolicy"  style="width:280px;" id="mfcate" > 
        	<option value="0:0">Select an item</option>
            
            <c:forEach items="${model.navitree}" var="cate" >    
            <c:forEach items="${cate.value}" var="devpol" >                                
            <option value="${cate.key}:${devpol.key }" >
          	${cate.key}...${devpol.key }...${cate.value[devpol.key].mpname }
            </option>  
            </c:forEach>
            </c:forEach>
            
			<c:forEach  items="${model.period}" var="thePe" >	
				<option value="16:${model.period[thePe.key].ppid }">
				16...${model.period[thePe.key].ppid }...${model.period[thePe.key].ppname }
				</option>
			</c:forEach>


        </select> 
        <input class="ibm-btn-arrow-sec" name="imbProgCty" type="submit" value="->" />
</form></div>
--%>		
		</td></tr><tr><td>
<!--	Policy in a category	-->

<% nodei++ ;%>
<div nowrap class='main-task' style='margin-left: 0.3em;'>			
            <a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title=""> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			</a>
            <a style='color: #000000; text-decoration: none;'
            	onClick="javascript:expandCollapse('<%=nodei %>');" 
				href="<%=request.getContextPath() %>/secure/policyapply/policyApplyWel.jsp" 
				title="设备策略"
                target="detail" dir="ltr">设备策略&nbsp;&nbsp;</a>
</div>
<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
	
		<c:forEach  items="${model.mtree_Device}" var="dev">	
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/policyApply.wss?mpid=${dev.mpid }&cate=1"
				target="detail" dir="ltr"
				title="${dev.mpname}(...${dev.mpid})">
				${dev.mpname }
          <c:if test="${dev.polictTemplateVer != null}"> 
          - [${dev.polictTemplateVer.policyPublishInfo.versionTag}] V[${dev.polictTemplateVer.policyPublishInfo.version}]
          </c:if>
				</a></li>
		</ul>
	
		</c:forEach>
</div>

<% nodei++ ;%>
<div nowrap class='main-task' style='margin-left: 0.3em;'>			
            <a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title=""> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			</a>
            <a style='color: #000000; text-decoration: none;'
            	onClick="javascript:expandCollapse('<%=nodei %>');" 
				href="<%=request.getContextPath() %>/secure/policyapply/policyApplyWel.jsp" 
				title="端口策略"
                target="detail" dir="ltr">端口策略&nbsp;&nbsp;</a>
</div>
<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<c:forEach  items="${model.mtree_Port}" var="dev">	
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/policyApply.wss?mpid=${dev.mpid }&cate=4"
				target="detail" dir="ltr"
				title="${dev.mpname}(...${dev.mpid})">
				${dev.mpname }
          <c:if test="${dev.polictTemplateVer != null}"> 
          - [${dev.polictTemplateVer.policyPublishInfo.versionTag}] V[${dev.polictTemplateVer.policyPublishInfo.version}]
          </c:if>
				</a></li>
		</ul>
	
		</c:forEach>
</div>
            
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				href="javascript:expandCollapse('<%=nodei %>');" 
				title=""> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			</a>
            <a style='color: #000000; text-decoration: none;'            	
            	onClick="javascript:expandCollapse('<%=nodei %>');" 
				href="<%=request.getContextPath() %>/secure/policyapply/policyApplyWel.jsp" 
				title="私有MIB策略&"
                target="detail" dir="ltr">私有MIB策略&nbsp;&nbsp;</a>
         </div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<c:forEach  items="${model.mtree_MIB}" var="dev">	        
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/policyApply.wss?mpid=${dev.mpid }&cate=9"
				target="detail" dir="ltr"
				title="${dev.mpname}(...${dev.mpid})">
				${dev.mpname }
          <c:if test="${dev.polictTemplateVer != null}"> 
          - [${dev.polictTemplateVer.policyPublishInfo.versionTag}] V[${dev.polictTemplateVer.policyPublishInfo.version}]
          </c:if>
				</a></li>
		</ul>
	
		</c:forEach>
</div>
      
<!-- period policy -->
<% nodei++ ;%>
		<div nowrap class='main-task' style='margin-left: 0.3em;'>
			<a style='color: #000000; text-decoration: none;'
				onClick="javascript:expandCollapse('<%=nodei %>');"
				href="<%=request.getContextPath() %>/secure/policyapply/policyApplyWel.jsp"
				target="detail" title="TimeFrame ..."> 
			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'>
			时间段策略</a></div>

		<div class='nav-child-container'
			style='margin-left: 0.3em; display: block' id='N<%=nodei %>'>
		<c:catch var="ex2">	
		
		<c:forEach  items="${model.period}" var="theP" begin="0" step="1" varStatus="idx">	

		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>
				<a style='text-decoration: none'
				href="<%=request.getContextPath() %>/secure/policyapply/policyApply.wss?ppid=${theP.key}&cate=16"
				target="detail" dir="ltr"
				title="${model.period[theP.key].ppname }(${theP.key })">
				${model.period[theP.key].ppname }
				</a></li>
		</ul>
	
		</c:forEach>
		</c:catch>
		</div>
</td></tr></table>		
</body>
</html>
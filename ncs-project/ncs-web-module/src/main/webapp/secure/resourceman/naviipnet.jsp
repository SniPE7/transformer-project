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

<link href='<%=request.getContextPath() %>/include/Styles.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
<title>baseinfonavi</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<!-- list of menu -->


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
for (i=2; i<100; i++) 
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

<div class="navBody" style="background-color: #FFFFFF;">
<table height="26" width="100%" cellspacing="0" cellpadding="0"
	style="background-color: #FFFFFF;">

	<tr>
		<td align="left" colspan="2" class="navtree"
			style="background-color: #FFFFFF">
		<!-- title of the navi welcome page -->
		<ul class='nav-child' dir='ltr'>
			<li class='navigation-bullet'>IP地址规划  </li>
		</ul>
<!-- New tree---->
<% nodei++ ;%>
<!--First IP addr node -->
<div nowrap class='main-task' style='margin-left: 0.3em;'>
	<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
    	onClick="javascript:expandCollapse('<%=nodei %>');" 
        title='+' alt='+' id='I<%=nodei %>' border='0' align='absmiddle'> 
        <a style='color: #000000; text-decoration: none;'
            href="javascript:expandCollapse('<%=nodei %>');"
            dir="ltr" title="IP net"> 				
        </a>
    </img>
    <a style='color:#000000;text-decoration:none;'     			
		href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?prepareAdd=add&gid=0"
		target="detail" title="IP Net">IP地址规划
    </a>
</div>

<!-- zero level -->
<div class="nav-child-container" style="margin-left: 0.3em; display: block" id="N<%=nodei %>">
	<div id="naviMF">
		<c:forEach  items="${model.rootlist}" var="theRoot" >	
			<c:catch var="ex0">
				<c:forEach  items="${model.ipnettree[theRoot]}" var="theL0"  > 
				
					<% nodei++; %>
   					<div nowrap class='sub-task'>
                    	<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                onClick="javascript:expandCollapse('<%=nodei %>');"
                                title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
                        <a style='color:#000000;text-decoration:none;' 
                        	href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL0.key].gid}"
                            target="detail" title="${model.grpnetNames[theL0.key].gname}">    				
                            ${model.grpnetNames[theL0.key].gname}
                         </a>
    				
    			
    				<div class='sub-child-container' style='display:block' id='N<%=nodei %>' >	
                        <c:choose>
                        <c:when test="${fn:length(model.ipnettree[theL0.key]) > 0}" >
                        	<c:catch var="ex1">
                            <c:forEach  items="${model.ipnettree[theL0.key]}" var="theL1" >
                                <c:choose>
                                <c:when test="${fn:length(model.ipnettree[theL1.key]) > 0}" >
    		
  									<% nodei++; %>
  				 					<c:if test="${model.grpnetNames[theL1.key].unmallocedipsetflag eq 0}" >
  										<div nowrap class='sub-task'>
                                            <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                                onClick="javascript:expandCollapse('<%=nodei %>');"
                                                title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
                                            <a style='color:#000000;text-decoration:none;'
                                                href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL1.key].gid}"
                                                target="detail" 
                                                title="${model.grpnetNames[theL1.key].gname}">
                                                ${model.grpnetNames[theL1.key].gname}</a>
			    						</div>
			    					</c:if>
			    					<!-- L2 more levels  -->                       
			    					<div class='sub-child-container' style='display:block' id='N<%=nodei %>' >	
                                    <c:forEach  items="${model.ipnettree[theL1.key]}" var="theL2" >             
                                    <c:choose>
                                    <c:when test="${fn:length(model.ipnettree[theL2.key]) > 0}" >
    									<% nodei++; %>
  				 						<c:if test="${model.grpnetNames[theL2.key].unmallocedipsetflag eq 0}" >
  											<div nowrap class='sub-child-container' style='display:block' >	
                                                <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                                    onClick="javascript:expandCollapse('<%=nodei %>');"
                                                    title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
                                                <a style='color:#000000;text-decoration:none;'
                                                    
                                                    href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL2.key].gid}"
                                                    target="detail" 
                                                    title="${model.grpnetNames[theL2.key].gname}">
                                                    ${model.grpnetNames[theL2.key].gname}</a>
			    							</div>
			    						</c:if>
			    						
                                        <!-- L3 more levels  -->
                                        <div class='third-child-container' style='display:block' id='N<%=nodei %>' >
                                        <c:forEach  items="${model.ipnettree[theL2.key]}" var="theL3" >	
                                            <c:choose>
                                            <c:when test="${fn:length(model.ipnettree[theL3.key]) > 0}" >
    		
  												<% nodei++; %>
  				 								<c:if test="${model.grpnetNames[theL3.key].unmallocedipsetflag eq 0}" >
  													<div class='third-child-container' style='display:block'>	
                                                        <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                                            onClick="javascript:expandCollapse('<%=nodei %>');"
                                                            title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
			    										<a style='color:#000000;text-decoration:none;'
   															href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL3.key].gid}"
                                                            target="detail" 
                                                            title="${model.grpnetgrpnetNames[theL3.key].gname}">
                                                            ${model.grpnetNames[theL3.key].gname}</a>
			    									</div>
			    								</c:if>
			    				
                                				<!-- L4 more levels  -->
                                                <div class='fourth-child-container' style='display:block' id='N<%=nodei %>' >				    				
                                                <c:forEach  items="${model.ipnettree[theL3.key]}" var="theL4" >
                                                    <c:choose>
                                                    <c:when test="${fn:length(model.ipnettree[theL4.key]) > 0}" >
    		
														<% nodei++; %>
                                 						<c:if test="${model.grpnetNames[theL4.key].unmallocedipsetflag eq 0}" >
  															<div nowrap class='fourth-child-container' style='display:block' >	
                                                                <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                                                    onClick="javascript:expandCollapse('<%=nodei %>');"
                                                                    title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
                                                                <a style='color:#000000;text-decoration:none;'
                                                                    
                                                                    href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL4.key].gid}"
                                                                    target="detail" 
                                                                    title="${model.grpnetgrpnetNames[theL4.key].gname}">
                                                                    ${model.grpnetNames[theL4.key].gname}</a>
			    											</div>
			    										</c:if>
			    				
                                						<!-- L5 more levels  -->
                                                        <div class='fifth-child-container' style='display:block' id='N<%=nodei %>' >				    				
                                                		<c:forEach  items="${model.ipnettree[theL4.key]}" var="theL5" >
                                                      
                                                        <c:if test="${fn:length(model.ipnettree[theL5.key]) > 0}" >
    		
															<% nodei++; %>
                                                            <c:if test="${model.grpnetNames[theL5.key].unmallocedipsetflag eq 0}" >
                                                                <div nowrap class='sixth-child-container' style='display:block' >	
                                                                    <img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
                                                                        onClick="javascript:expandCollapse('<%=nodei %>');"
                                                                        title='Collapse' alt='Collapse' align='absmiddle' id='I<%=nodei %>' border='0'/>
                                                                    <a style='color:#000000;text-decoration:none;'
                                                                        
                                                                        href="<%=request.getContextPath() %>/secure/resourceman/ipnetOfGid.wss?gid=${model.grpnetNames[theL5.key].gid}"
                                                                        target="detail" 
                                                                        title="${model.grpnetgrpnetNames[theL5.key].gname}">
                                                                        ${model.grpnetNames[theL5.key].gname}</a>
                                                                </div>
                                                            </c:if>
                                                         </c:if>
                                                         </c:forEach>
                                                         </div>
                                    					<!-- L5 more levels end -->
			   										</c:when>
                                             		</c:choose>	
  												</c:forEach>
                                                </div>
                                             	<!-- L4 more levels end -->
			   								</c:when>
			   								<c:otherwise>
                                            
                                            </c:otherwise>
			   								</c:choose>	
  										</c:forEach>	
                                        </div>
                                        <!-- L3 more levels end -->
			   						</c:when>
			   						<c:otherwise>
                                    </c:otherwise>
			   						</c:choose>	
  									</c:forEach>
                                    
                                    </div>
                                    <!-- L2 more levels end -->
			   					</c:when>
			   					<c:otherwise>
  								</c:otherwise>
			   					</c:choose>	
  								</c:forEach>
 
  							</c:catch>
                        </c:when>
    					</c:choose>
                 	</div>
     			</div>	
   			</c:forEach>
   			</c:catch>

		</c:forEach>
       
		</div>
    </div>
</td></tr></table></div>

</body>
</html>
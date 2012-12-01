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
<title>category test</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href='<%=request.getContextPath() %>/include/wasimp.css' rel="styleSheet" type="text/css">
<link href='<%=request.getContextPath() %>/login.css' rel="styleSheet" type="text/css">
</head>
<body>


  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" class="portalPage">
      <TR>
          <TD CLASS="pageTitle">详细内容</TD>
           <TD CLASS="pageClose"><img border="0" src="../../images/back.gif" width="16" height="16" onClick=history.go(-1)></TD>
      </TR>

  </TABLE>
<TABLE WIDTH="100%" CELLPADDING="0" CELLSPACING="0" BORDER="0">
  <TR>
  
    
  
  <TD valign="top">
  
  <TABLE WIDTH="98%" CELLPADDING="0" CELLSPACING="0" BORDER="0" CLASS="wasPortlet">

  <TR>
      <TH class="wpsPortletTitle" width="100%">详细内容</TH>

  </TR>
 

  
  <TBODY ID="wasUniPortlet">
    <TR>   
  <TD CLASS="wpsPortletArea" COLSPAN="3" >
    
        <a name="important"></a> 
        
        <h1 id="title-bread-crumb">详细内容</h1>
        <p ></p>

<a name="main"></a>
<!-- button section -->
<table border="0" cellpadding="3" cellspacing="0" valign="top" width="100%" summary="Framing Table" CLASS="button-section">

        <tr valign="top">
        <td class="table-button-section"  nowrap> 
    
    <table style="display: inline; font-size: 95%;" cellspacing="0" cellpadding="0" border="0"><tr> 
		<td>
    		<input type="submit" name="button.new" value="新建" class="buttons" id="functions"/>
    	</td>
        <input type="submit" name="hiddenButton1290018101908118000" value="hiddenButton" style="display:none" class="buttons" id="hiddenButton1290018101908118000"/>
    </tr></table>    
 
    <input type="hidden" name="definitionName" value="JDBCProvider.collection.buttons.panel"/>
    <input type="hidden" name="buttoncontextType" value="JDBCProvider"/>
    <input type="hidden" name="buttoncontextId" value="All Scopes"/>
    <input type="hidden" name="buttonperspective" value="null"/> 
     <input type="hidden" name="formAction" value="jDBCProviderCollection.do"/>  

        </td>
        </tr>
        </table>
 <!-- ncc -->
        
        <form name="form1" method="post" action="" >
		<input name="_name" value="T_DEVICE_TYPE_INIT" type="hidden">
		<input name="_pk" value="429" type="hidden">
		<div class="expandCollapseLink" align="right"><a href="#" class="expandCollapseLink"><span class="expandCollapseText"></span></a></div><div class="sectionHeading" value="详细内容"><span><a href="#"></a></span>详细内容&nbsp;&nbsp;</div><div>
		<div class="sectionContent">
		<table class="loginTable" border="0" bordercolor="#999999" cellpadding="0" cellspacing="1" width="100%">


			
                
				<tbody><tr class="table-row" >
					<td class="tdlabel" width="30%">#:</td>
					<td class="tdcontent">${model.dtid}&nbsp;
					
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">生产厂商:</td>

					<td class="tdcontent">${model.mrName }&nbsp;
					
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">类型:</td>
					<td class="tdcontent">${model.cateName }&nbsp;
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">子类型:</td>
					<td class="tdcontent">${ model.subCategory}&nbsp;
					
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">型号:</td>

					<td class="tdcontent">${model.model }&nbsp;
					
					</td>
				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">OBJECTID:</td>
					<td class="tdcontent">${model.objectid }&nbsp;
					
					</td>

				</tr>
               
			
                
				<tr class="table-row">
					<td class="tdlabel" width="30%">描述:</td>
					<td class="tdcontent">${nmodel.description }&nbsp;
					
					</td>
				</tr>
               
			
			
		</tbody></table>
		</div>
		
		<div class="backToTopLink" align="right"><a href="#" class="backToTopLink"><img src="../images/back_to_top.gif" align="absmiddle" border="0" height="16" width="16"><span class="backToTopLink">回到顶部</span></a></div></div></form>
<!-- Data Table -->

<TABLE BORDER="0" CELLSPACING="0" CELLPADDING="0" WIDTH="100%" SUMMARY="List layout table">

	<TBODY>
		<TR>
			<TD CLASS="layout-manager" id="notabs">


<table BORDER="0" CELLPADDING="3" CELLSPACING="1" WIDTH="100%" SUMMARY="List table" CLASS="framing-table">
<tr>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >#</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >编辑</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >厂商名</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >类型</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >子类型</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >型号</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >ObjectID</th>
<th NOWRAP VALIGN="TOP" CLASS="column-head-name" SCOPE="col" >描述</th>
</tr>

<c:forEach items="${model.devicelist}" var="c1" >
<tr class="table-row">
<td VALIGN="top"  class="collection-table-text" headers="mrname"><c:out value="${c1.dtid}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="mrname"><c:out value="${c1.dtid}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="mrname"><c:out value="${c1.mrName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="catename"><c:out value="${c1.cateName}" /> </td>
<td VALIGN="top"  class="collection-table-text" headers="subcategory"><c:out value="${c1.subCategory}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="dtid"><c:out value="${c1.model}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="objectid"><c:out value="${c1.objectid}"/></td>
<td VALIGN="top"  class="collection-table-text" headers="description"><c:out value="${c1.description}"/></td>
</tr>
</c:forEach>
</table>
<TABLE class="paging-table" BORDER="0" CELLPADDING="5" CELLSPACING="0" WIDTH="100%" SUMMARY="Table for displaying paging function">

	<TR>
			

            <TD CLASS="table-totals" VALIGN="baseline">               
 
             &nbsp;&nbsp;&nbsp;               


        
		</TD>
	</TR>

</TABLE>


</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TABLE>

</body>
</html>
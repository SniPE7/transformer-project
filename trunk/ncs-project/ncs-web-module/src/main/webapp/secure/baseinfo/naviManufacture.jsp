<%@ include file="/include/include.jsp" %>

<% int mfsubnodei=100;  %>
<c:forEach  items="${model.mfTree}" var="theManufacturer" >
   <% mfsubnodei++; %>
   
   <div nowrap class='sub-task'>
   				<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
   					onClick="javascript:expandCollapse('<%=mfsubnodei %>');"
   					title='Collapse' alt='Collapse' align='absmiddle' id='I<%=mfsubnodei %>' border='0'/>
    			<a style='color:#000000;text-decoration:none;' 
    				onClick="javascript:expandCollapse('<%=mfsubnodei %>');"
    				href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}" 
    				target="detail" 
    				title="${theManufacturer.key}">    				
    				${theManufacturer.key}</a>
    			</div>	
    			
    			<div class='sub-child-container' style='display:block' id='N<%=mfsubnodei %>' >	
  				<c:forEach  items="${theManufacturer.value}" var="theCategory" >
  				<% mfsubnodei++; %>
  				<div nowrap class='sub-task'>
			    			<img src='<%=request.getContextPath() %>/images/arrow_expanded.gif' 
   								onClick="javascript:expandCollapse('<%=mfsubnodei %>');"
			    				title='Collapse' alt='Collapse' align='absmiddle' id='I<%=mfsubnodei %>' border='0'/>
			    			<a style='color:#000000;text-decoration:none;'
   								onClick="javascript:expandCollapse('<%=mfsubnodei %>');changecolor(this);" 
			    				href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}"
			    				target="detail" 
			    				title="${theManufacturer.key}   ...   ${theCategory.key}">
			    				${theCategory.key}</a>
			    				</div>
			    				
			    				<div class='sub-child-container' style='display:block' id='N<%=mfsubnodei %>'>	
			    				<c:forEach  items="${theCategory.value}" var="theSubCategory" >
			    				<% mfsubnodei++; %>
			    			   
					    		<ul class='nav-child' dir='ltr'><li class='navigation-bullet'>
					    		<a style='text-decoration:none' 
					    		onclick="changecolor(this);" 
					    			href="<%=request.getContextPath() %>/secure/baseinfo/devicetypelist.wss?mf=${theManufacturer.key}&cate=${theCategory.key}&subcate=${theSubCategory.key}" 
					    			target="detail" 
					    			dir="ltr" 
					    			title="${theManufacturer.key}   ...   ${theCategory.key}   ...   ${theSubCategory.key}">${theSubCategory.key}</a>
					    		</li></ul>
								   			
			    				</c:forEach>
			    </div>  				
  				</c:forEach>
    </div>
    
    		
   </c:forEach>
   
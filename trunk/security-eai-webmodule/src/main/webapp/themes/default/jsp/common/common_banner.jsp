<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
      <div id="banner">
        <div id="logo"></div>
        <div id="slogan">
          <h1><a href="#"></a></h1>
          <h2><a href="#"></a></h2>
        </div>
      </div><!-- end #banner -->
      <div id="menu" style="clear:both; ">
        <div style="float: left; height:62px"><img src="themes/default/images/login/sinopec-logo24.png"></img></div>
        <div id="slides" style="padding: 20px 0 15px 5px;">上海通用汽车统一身份认证中心</div>
        <div id="actionName" style="float:right">
	        <h1 class="read_h1">
				<span id="subject_tpc"> <spring:message code="${title}" /> </span>
			</h1>
		</div>
     </div>
     <div class="clear"></div>
     <!-- end of #menu -->
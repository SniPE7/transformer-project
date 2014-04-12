<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="edu.internet2.middleware.shibboleth.common.profile.AbstractErrorHandler"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <div id="content">
          <div class="aui-message error invisible" id="errorDivMsg">
            <!-- shown with class="aui-message error" -->
          </div>
          <div id="acloginpod">
            <div id="acloginpanel" class="loginpanel">
                <fieldset>
                <spring:message code="error.message.content"/>
  <% 
     Throwable error = (Throwable) request.getAttribute(AbstractErrorHandler.ERROR_KEY);
  %>
  <strong><spring:message code="error.message.title"/><%= (error != null)?error.getMessage():""%></strong>
                </fieldset>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->
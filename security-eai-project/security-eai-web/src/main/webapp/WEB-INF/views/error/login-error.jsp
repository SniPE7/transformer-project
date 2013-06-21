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
  <%
  Throwable error = (Throwable) request.getAttribute(AbstractErrorHandler.ERROR_KEY);
  if (error != null) {
      org.owasp.esapi.Encoder esapiEncoder = org.owasp.esapi.ESAPI.encoder();
  %>
  <spring:message code="error.message.title"/><%= esapiEncoder.encodeForHTML(error.getMessage()) %>
  <% } %>
                </fieldset>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->
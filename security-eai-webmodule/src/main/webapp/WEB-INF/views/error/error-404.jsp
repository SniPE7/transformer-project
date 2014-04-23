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
                <strong><spring:message code="error.message.title"/><spring:message code="error.message"/></strong>
                <% String uri = (String)request.getAttribute("javax.servlet.forward.request_uri"); %>
                <br/><strong>[<%=uri %>]</strong>
                </fieldset>
            </div><!-- End of #acloginpanel -->
          </div><!-- End of #acloginpod -->
        </div><!-- End of #content -->
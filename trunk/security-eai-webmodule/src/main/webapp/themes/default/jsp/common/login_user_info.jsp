<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
				<h3 class="active">认证状态</h3>
				<div class="desc">
				  <p>登录用户：${PrincipalName}</p>
				  <c:forEach items="${ServiceInformation}" var="item">
				  <p>SP：${item.entityid}</p>
				  <p>认证方式：<spring:message code="${item.method}"/></p>
				  <p>会话最后活动时间：${item.loginInstant}</p>
				  <p>会话预计终止时间：${item.expirationInstant}</p>
				  </c:forEach>
				</div>
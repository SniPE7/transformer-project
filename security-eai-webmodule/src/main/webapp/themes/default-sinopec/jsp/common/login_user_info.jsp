<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page isELIgnored="false"%>
				<h3 class="active">认证状态</h3>
				<div class="desc">
				  <p>登录用户：${PrincipalName}</p>
				  <c:forEach items="${ServiceInformation}" var="item">
				  <p>认证方式：${item.authenticationMethod.authenticationMethod}</p>
				  <p>会话最后活动时间：${item.loginInstant}</p>
				  </c:forEach>
				</div>
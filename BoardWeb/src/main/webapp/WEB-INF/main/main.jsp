<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="../includes/header.jsp"></jsp:include>
<h3>Main Page.</h3>

<p><c:out value="${logName }"></c:out> 님 환영합니다.</p>

<jsp:include page="../includes/footer.jsp"></jsp:include>

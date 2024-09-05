<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../includes/header.jsp"></jsp:include>
<h3>로그인화면</h3>
<!-- 로그인 실패일 경우. -->
<c:if test="${!empty message }">
  <p style="color: red;"><c:out value="${message }" /></p>
</c:if>

<form action="login.do" method="post">
<table class="table">
  <tr>
    <th>아이디</th><td><input type="text" name="id"></td>
  </tr>
  <tr>
    <th>비밀번호</th><td><input type="password" name="pw"></td>
  </tr>
  <tr>
    <td colspan="2"><input class="btn btn-primary" type="submit" value="로그인"></td>
  </tr>
</table>
</form>

<jsp:include page="../includes/footer.jsp"></jsp:include>

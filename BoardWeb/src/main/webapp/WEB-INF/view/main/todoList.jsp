<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h3>오늘의 할일</h3>
<table class="table">
	<thead>
		<tr>
			<th>번호</th>
			<th>할일</th>
		</tr>
	</thead>
	<tbody>
	  <c:forEach var="todo" items="${list }">
		<tr>
			<td>${todo.no }</td>
			<td>${todo.title }</td>
		</tr>
	  </c:forEach>
	</tbody>
</table>
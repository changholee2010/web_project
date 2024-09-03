<%@page import="com.yedam.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="../includes/header.jsp"></jsp:include>
<h3>회원상세정보</h3>
<%
    MemberVO member = (MemberVO) request.getAttribute("memberInfo");
%>
    <table class="table">
        <tr>
        	<th>회원아이디</th><td><%=member.getMemberId() %></td>
        </tr>
        <tr>
        	<th>회원이름</th><td><%=member.getMemberName() %></td>
        </tr>
        <tr>
        	<th>비밀번호</th><td><%=member.getPassword() %></td>
        </tr>
        <tr>
        	<th>이메일</th><td><%=member.getEmail() %></td>
        </tr>
        <tr>
        	<th>가입일자</th><td><%=member.getCreationDate() %></td>
        </tr>
        <tr>
        	<td colspan="2" align="center">
        	    <button class="btn btn-info" onclick="location.href='modifyForm.do?id=<%=member.getMemberId() %>'">수정</button>
        	    <button class="btn btn-danger" onclick="location.href='removeMember.do?id=<%=member.getMemberId() %>'">삭제</button>
        	</td>
        </tr>
    </table>
<jsp:include page="../includes/footer.jsp"></jsp:include>
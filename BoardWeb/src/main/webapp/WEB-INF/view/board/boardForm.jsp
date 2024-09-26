<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h3>등록화면</h3>
<form action="addBoard.do" method="post" enctype="multipart/form-data">
<input type="hidden" name="writer" value="${logid }">
<table class="table">
  <tr>
    <th>제목</th><td><input class="form-control" name="title" type="text"></td>
  </tr>
  <tr>
    <th>내용</th><td><textarea class="form-control" name="content"></textarea></td>
  </tr>
  <tr>
    <th>작성자</th><td>${logid }</td>
  </tr>
  <tr>
    <th>이미지</th><td><input type="file" name="srcImage"></td>
  </tr>
  <tr>
    <td colspan="2" align="center">
      <input class="btn btn-primary" type="submit">
      <input type="reset" class="btn btn-secondary">
    </td>
  </tr>
</table>
</form>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
  div.reply div {
    margin: auto;
  }

  div.reply ul {
    list-style-type: none;
  }

  div.reply span {
    display: inline-block;
  }
</style>

<link rel="stylesheet" href="//cdn.datatables.net/2.1.5/css/dataTables.dataTables.min.css">
<script src="js/jquery-3.7.1.js"></script>
<script src="//cdn.datatables.net/2.1.5/js/dataTables.min.js"></script>

<script>
  const bno = '${board.boardNo }'; // 원본글번호.
  const writer = '${logid}'; // 로그인 정보.
</script>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- ---------------- -->
<!-- 글상세 정보 시작. -->
<!-- ---------------- -->

<h3>글 상세 페이지.</h3>
<table class="table">
  <tr>
    <th>글번호</th>
    <td>${board.boardNo }</td>
    <th>조회수</th>
    <td>${board.viewCnt }</td>
  </tr>
  <tr>
    <th>제목</th>
    <td colspan="3">${board.title }</td>
  </tr>
  <tr>
    <th>내용</th>
    <td colspan="2">${board.content }</td>
    <td colspan="2" rowspan="3">
      <c:if test="${!empty board.image }">
        <img width="150px" src="images/${board.image }">
      </c:if>
    </td>
  </tr>
  <tr>
    <th>작성자</th>
    <td colspan="2">${board.writer }</td>
  </tr>
  <tr>
    <th>작성일시</th>
    <td colspan="2">
      <fmt:formatDate value="${board.creationDate }" pattern="yyyy-MM-dd HH:mm:ss" />
    </td>
  </tr>
</table>

<!-- 컨트롤 이동하기 위한 폼. -->
<form action="removeBoard.do" name="actForm">
  <input type="hidden" name="keyword" value="${model.keyword }">
  <input type="hidden" name="searchCondition" value="${model.searchCondition }">
  <input type="hidden" name="page" value="${model.page }">
  <input type="hidden" name="bno" value="${board.boardNo }">
</form>

<a class="btn btn-secondary" onclick="form_submit('boardList.do')">목록으로</a>
<a class="btn btn-warning ${board.writer ne logid ? 'disabled' : '' }" onclick="form_submit('modifyForm.do')">수정</a>
<a class="btn btn-danger" onclick="form_submit('removeBoard.do')">삭제</a>
<c:if test="${!empty message }">
  <span style="color: red;">${message }</span>
</c:if>
<!-- ---------------- -->
<!-- 글상세 정보 종료. -->
<!-- ---------------- -->


<!-- ---------------- -->
<!-- 댓글관련 시작 -->
<!-- ---------------- -->
<div class="container reply">
  <!-- 댓글등록 -->
  <div class="header">
    <input type="text" id="reply" class="col-sm-9">
    <button id="addReply" class="btn btn-primary">댓글등록</button>
    <button id="delReply" class="btn btn-danger">댓글삭제</button>
  </div>

  <!-- 댓글목록 데이터테이블 활용 -->
  <!-- 댓글페이징 데이터테이블 활용 -->
  <table id="example" class="display" style="width:100%">
    <thead>
      <tr>
        <th>댓글번호</th>
        <th>내용</th>
        <th>작성자</th>
        <th>작성일시</th>
      </tr>
    </thead>
    <tfoot>
      <tr>
        <th>댓글번호</th>
        <th>내용</th>
        <th>작성자</th>
        <th>작성일시</th>
      </tr>
    </tfoot>
  </table>
</div>
<!-- ---------------- -->
<!-- 댓글관련 종료 -->
<!-- ---------------- -->

<script>
  // 매개값으로 이동할 컨트롤을 받아서 파라미터를 전달.
  function form_submit(uri) {
    document.forms.actForm.action = uri;
    document.forms.actForm.submit();
  }
</script>

<script src="js/boardDataTable.js"></script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<style>
  #srcImage {
    position: relative;
    left: -210px;
    background: white;
    display: inline-block;
    width: 300px;
  }
</style>
<h3>수정화면</h3>
<form action="modifyBoard.do" method="post" enctype="multipart/form-data">
  <input type="hidden" name="bno" value="${board.boardNo }">
  <input type="hidden" name="searchCondition" value="${model.searchCondition }">
  <input type="hidden" name="keyword" value="${model.keyword }">
  <input type="hidden" name="page" value="${model.page }">
  <table class="table">
    <tr>
      <th>제목</th>
      <td><input class="form-control" name="title" type="text" value="${board.title }"></td>
    </tr>
    <tr>
      <th>내용</th>
      <td><textarea class="form-control" name="content">${board.content }</textarea></td>
    </tr>
    <tr>
      <th>작성자</th>
      <td>${board.writer }</td>
    </tr>
    <tr>
      <th>이미지</th>
      <td><input type="file" name="srcImage"><span id="srcImage">${board.image }</span></td>
    </tr>
    <tr>
      <td colspan="2" align="center">
        <input class="btn btn-primary" type="submit">
        <input type="reset" class="btn btn-secondary">
      </td>
    </tr>
  </table>
</form>

<script>
  // 이미지를 선택하면 span태그의 내용을 변경해주도록 해야한다.
  document.querySelector('input[name="srcImage"]').addEventListener('change', function (e) {
    console.log(e.target.files[0].name);
    document.getElementById('srcImage').innerText = e.target.files[0].name;

  })
</script>

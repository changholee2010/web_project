package com.yedam.control.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yedam.common.Control;
import com.yedam.service.BoardService;
import com.yedam.service.BoardServiceImpl;
import com.yedam.vo.BoardVO;

public class ModifyBoardControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// application/x-www-form-urlencoded => key=value&key=value&....
		// multipart/form-data => key=value....
		// multipart 요청을 처리. 서버의 위치(images) 파일복사.
		String saveDir = request.getServletContext().getRealPath("images");
		int maxSize = 5 * 1024 * 1024; // 5MB
		MultipartRequest mr;
		mr = new MultipartRequest(request // 1.요청
				, saveDir // 2.파일저장경로
				, maxSize // 3.최대파일크기
				, "utf-8" // 4.인코딩
				, new DefaultFileRenamePolicy() // 5.리네임정책
		);
		String sc = mr.getParameter("searchCondition");
		String kw = mr.getParameter("keyword");
		String page = mr.getParameter("page");

		String title = mr.getParameter("title");
		String content = mr.getParameter("content");
		String writer = mr.getParameter("writer");
		String bno = mr.getParameter("bno");
		String img = mr.getFilesystemName("srcImage");

		BoardVO board = new BoardVO();
		board.setBoardNo(Integer.parseInt(bno));
		board.setTitle(title);
		board.setContent(content);
		board.setWriter(writer);
		board.setImage(img);

		BoardService svc = new BoardServiceImpl();
		svc.modifyBoard(board);

		response.sendRedirect("boardList.do?page=" + page + "&searchCondition=" + sc + "&keyword=" + kw);

	}

}

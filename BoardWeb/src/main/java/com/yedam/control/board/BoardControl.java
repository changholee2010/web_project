package com.yedam.control.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.common.ModelVO;
import com.yedam.service.BoardService;
import com.yedam.service.BoardServiceImpl;
import com.yedam.vo.BoardVO;

public class BoardControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bno = request.getParameter("bno"); // 상세조회할 게시글번호.

		// 검색조건. searchCondition & keyword.
		String sc = request.getParameter("searchCondition");
		String kw = request.getParameter("keyword");
		String page = request.getParameter("page");

		BoardService svc = new BoardServiceImpl();
		BoardVO board = svc.geBoard(Integer.parseInt(bno));
		request.setAttribute("board", board);

		// ModelVO로 변경해서 값을 담는다.
		ModelVO model = new ModelVO();
		model.addProp("searchCondition", sc);
		model.addProp("keyword", kw);
		model.addProp("page", page);

		// jsp페이지에 전달.
		request.setAttribute("model", model);

		// 카운트증가.
		svc.addViewCount(Integer.parseInt(bno));

		RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/board/board.jsp");
		rd.forward(request, response);

	}

}

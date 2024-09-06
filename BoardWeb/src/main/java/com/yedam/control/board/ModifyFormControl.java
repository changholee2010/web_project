package com.yedam.control.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.BoardService;
import com.yedam.service.BoardServiceImpl;
import com.yedam.vo.BoardVO;

public class ModifyFormControl implements Control {

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
		Map<String, String> model = new HashMap<>();
		model.put("searchCondition", sc);
		model.put("keyword", kw);
		model.put("page", page);

		// jsp페이지에 전달.
		request.setAttribute("model", model);

		request.getRequestDispatcher("board/modifyForm.tiles")//
				.forward(request, response);

	}

}

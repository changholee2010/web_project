package com.yedam.control.board;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.common.PageDTO;
import com.yedam.common.SearchDTO;
import com.yedam.service.BoardService;
import com.yedam.service.BoardServiceImpl;
import com.yedam.vo.BoardVO;

public class BoardListControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// 검색조건 파라미터.
		String sc = request.getParameter("searchCondition");
		String kw = request.getParameter("keyword");
		String page = request.getParameter("page");
		page = page == null ? "1" : page; // 페이지 값이 없을 경우 1페이지로 이동.

		if (sc == null || kw == null || sc.isEmpty() || kw.isEmpty()) { // 검색조건이 없으면...
			request.setAttribute("message", "검색조건을 입력하세요.");

		} else {

			SearchDTO search = new SearchDTO(); // 검색조건 지정.
			search.setSearchCondition(sc); // T, W, TW
			search.setKeyword(kw); // Java, html
			search.setPage(Integer.parseInt(page));

			BoardService svc = new BoardServiceImpl();
			List<BoardVO> list = svc.boardList(search);
			request.setAttribute("list", list);

			// 페이징 처리를 위한 기능.
			int totalCnt = svc.getTotalCnt(search);
			PageDTO paging = new PageDTO(Integer.parseInt(page), totalCnt);
			request.setAttribute("paging", paging);

			// ModelVO로 변경해서 값을 담는다.
			Map<String, String> model = new HashMap<>();
			model.put("searchCondition", sc);
			model.put("keyword", kw);
			model.put("page", page);

			// jsp페이지에 전달.
			request.setAttribute("model", model);

		} // end of if.

		RequestDispatcher rd = request.getRequestDispatcher("board/boardList.tiles");
		rd.forward(request, response);
	}

}

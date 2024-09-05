package com.yedam.common;

import com.yedam.service.BoardService;
import com.yedam.service.BoardServiceImpl;
import com.yedam.vo.BoardVO;

public class AppTest {
	public static void main(String[] args) {
		SearchDTO search = new SearchDTO();
		search.setSearchCondition("W");
		search.setKeyword("user01");
		search.setPage(2);

		// 목록.
		BoardService svc = new BoardServiceImpl();
		svc.boardList(search).forEach(System.out::println); // board ->
		// System.out.println(board)

	}
}

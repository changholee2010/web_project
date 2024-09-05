package com.yedam.web;
// 게시글관련 메뉴와 컨트롤 등록. 팀원1

import java.util.HashMap;
import java.util.Map;

import com.yedam.common.Control;
import com.yedam.control.board.AddBoardControl;
import com.yedam.control.board.BoardControl;
import com.yedam.control.board.BoardFormControl;
import com.yedam.control.board.BoardListControl;
import com.yedam.control.board.ModifyBoardControl;
import com.yedam.control.board.ModifyFormControl;
import com.yedam.control.board.RemoveControl;

public class MenuBoard {
	private static MenuBoard instance = new MenuBoard();

	private MenuBoard() {
	}

	public static MenuBoard getInstance() {
		return instance;
	}

	public Map<String, Control> menuMap() {
		Map<String, Control> menu = new HashMap<>();
		// 기능등록.
		menu.put("/boardList.do", new BoardListControl());
		// 단건조회.
		menu.put("/getBoard.do", new BoardControl());
		menu.put("/removeBoard.do", new RemoveControl());

		// 등록(화면, 기능)
		menu.put("/addBoardForm.do", new BoardFormControl());
		menu.put("/addBoard.do", new AddBoardControl());

		// 수정(화면, 기능)
		menu.put("/modifyForm.do", new ModifyFormControl());
		menu.put("/modifyBoard.do", new ModifyBoardControl());

		return menu;
	}
}

package com.yedam.web;
// 게시글관련 메뉴와 컨트롤 등록. 팀원1

import java.util.HashMap;
import java.util.Map;

import com.yedam.common.Control;

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

		return menu;
	}
}

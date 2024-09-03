package com.yedam.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;

public class MainControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("메인컨트롤 실행.");
	}

}

package com.yedam.control.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;

public class AddFormControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// /addForm.do 요청 -> 요청재지정(WEB-INF/html/addForm.jsp)

		request.getRequestDispatcher("html/addForm.tiles")//
				.forward(request, response);

		// 커밋테스트하려고 수정했습니다.

	}

}

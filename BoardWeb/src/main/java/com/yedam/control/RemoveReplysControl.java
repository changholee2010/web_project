package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;

public class RemoveReplysControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] params = request.getParameterValues("rno"); // ?rno=1&rno=2&rno=3

		ReplyService svc = new ReplyServiceImpl();
		if (svc.removeReplys(params)) {
			// {"retCode": "OK"}
			response.getWriter().print("{\"retCode\": \"OK\"}");
		} else {
			// {"retCode": "NG"}
			response.getWriter().print("{\"retCode\": \"NG\"}");

		}
	}

}

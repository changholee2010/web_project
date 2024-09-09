package com.yedam.control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;

public class RemoveReplyControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 삭제할 댓글번호 (rno) 삭제 -> 반환.
		String rno = request.getParameter("rno");

		ReplyService svc = new ReplyServiceImpl();
		if (svc.removeReply(Integer.parseInt(rno))) {
			// {"retCode": "OK"}
			response.getWriter().print("{\"retCode\": \"OK\"}");
		} else {
			// {"retCode": "NG"}
			response.getWriter().print("{\"retCode\": \"NG\"}");
		}

	}

}

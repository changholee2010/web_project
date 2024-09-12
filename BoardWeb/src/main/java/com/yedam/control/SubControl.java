package com.yedam.control;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;

public class SubControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("서브컨트롤 실행.");
		ReplyService svc = new ReplyServiceImpl();
		List<Map<String, Object>> list = svc.todoList();
		request.setAttribute("list", list);

		request.getRequestDispatcher("main/todoList.tiles").forward(request, response);
	}

}

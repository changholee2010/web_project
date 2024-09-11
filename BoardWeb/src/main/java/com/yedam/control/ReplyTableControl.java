package com.yedam.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yedam.common.Control;
import com.yedam.common.SearchDTO;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;
import com.yedam.vo.ReplyVO;

public class ReplyTableControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");

		// TODO Auto-generated method stub
		String bno = request.getParameter("bno");
		SearchDTO search = new SearchDTO();
		search.setBoardNo(Integer.parseInt(bno));

		ReplyService svc = new ReplyServiceImpl();
		List<ReplyVO> list = svc.replyList(search);

		Map<String, Object> map = new HashMap<>();
		map.put("data", list);

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(map);

		response.getWriter().print(json);

	}

}

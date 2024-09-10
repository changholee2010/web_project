package com.yedam.control;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yedam.common.Control;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;
import com.yedam.vo.ReplyVO;

public class AddReplyControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");

		Gson gson = new GsonBuilder().setPrettyPrinting().create();

		Map<String, Object> map = new HashMap<>(); // json객체를 생성하기 위한 map선언.

		// 댓글작성자, 원본글번호, 댓글내용.
		String replyer = request.getParameter("replyer");
		String reply = request.getParameter("reply");
		String bno = request.getParameter("bno");

		ReplyVO rvo = new ReplyVO();
		rvo.setBoardNo(Integer.parseInt(bno));
		rvo.setReply(reply);
		rvo.setReplyer(replyer);
		rvo.setReplyDate(new Date());

		ReplyService svc = new ReplyServiceImpl();
		if (svc.addReply(rvo)) { // replyNo에 값이 지정.
			// {"retCode": "OK", "retVal": {"replyNo": 19, "reply": "reply", "replyer" :
			// "replyer", "boardNo": 148}}
			map.put("retCode", "OK");
			map.put("retVal", rvo);

		} else {
			map.put("retCode", "NG");

		}

		String json = gson.toJson(map); // map객체를 json문자열로 변환.
		response.getWriter().print(json); // 출력스트림에 json문자열 출력.

	}

}

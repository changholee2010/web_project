package com.yedam.control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.ReplyService;
import com.yedam.service.ReplyServiceImpl;
import com.yedam.vo.ReplyVO;

public class ReplyListControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// DB 추출된 정보를 활용해서 json 포맷의 문자열을 반환.
//[{"replyNo": 1, "replyer": "user01", "reply": "1번댓글", "boardNo": 148...}
		// ,{},{}...{}]
		response.setContentType("text/json;charset=utf-8");

		String bno = request.getParameter("bno");
		ReplyService svc = new ReplyServiceImpl();
		List<ReplyVO> list = svc.replyList(Integer.parseInt(bno));
		String json = "[";
		for (int i = 0; i < list.size(); i++) {
			json += "{\"replyNo\": " + list.get(i).getReplyNo() + "," //
					+ "\"replyer\": \"" + list.get(i).getReplyer() + "\" , "//
					+ "\"reply\": \"" + list.get(i).getReply() + "\" , "//
					+ "\"boardNo\": " + list.get(i).getBoardNo() + ", " //
					+ "\"replyDate\": \"" + list.get(i).getReplyDate() + "\"}";
			// [{},{},{}]
			if (i != list.size() - 1) {
				json += ",";
			}
		}
		json += "]";
		response.getWriter().print(json);
	}
}

package com.yedam.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.control.EventControl;
import com.yedam.control.IntroControl;
import com.yedam.control.JavaScriptControl;
import com.yedam.control.MainControl;
import com.yedam.control.SubControl;

// @WebServlet("*.do")
public class FrontController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// url pattern - 실행되는 기능 -> map 컬렉션에 지정.
	Map<String, Control> map;

	public FrontController() {
		map = new HashMap<>();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		map.put("/main.do", new MainControl());
		map.put("/today.do", new SubControl());
		map.put("/intro.do", new IntroControl());
		map.put("/javascript.do", new JavaScriptControl());

		// fullcalendar 관련.
		map.put("/eventList.do", new EventControl());
		map.put("/addEvent.do", new EventControl());
		map.put("/removeEvent.do", new EventControl());
		map.put("/chart.do", new EventControl());
		map.put("/showChart.do", new EventControl());

		Map<String, Control> memberMenu = MemberMenu.getInstance().menuMap();
		Map<String, Control> boardMenu = BoardMenu.getInstance().menuMap();
		Map<String, Control> replyMenu = ReplyMenu.getInstance().menuMap();

		map.putAll(memberMenu);// 멤버관련 메뉴추가.
		map.putAll(boardMenu); // 게시글관련 메뉴추가.
		map.putAll(replyMenu); // 댓글관련 메뉴추가.

	}

	// HttpServletRequest
	// HttpServletResponse

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8"); // 요청방식이 POST 일 경우에 body포함된 문자열 인코딩.

		String uri = req.getRequestURI();// /BoardWeb/main.do
		String context = req.getContextPath(); // /BoardWeb
		String page = uri.substring(context.length()); // /main.do
		System.out.println(page);

		Control control = map.get(page);
		control.exec(req, resp);
	}
}

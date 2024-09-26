package com.yedam.control;

import java.io.IOException;
import java.lang.reflect.Method;
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

/*
 *   "/eventList.do" -> eventList 메소드.
 *   "/addEvent.do"  -> addEvent 메소드.
 *   "/removeEvent.do" -> removeEvent 메소드.
 */
public class EventControl implements Control {

	ReplyService svc = new ReplyServiceImpl(); //

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=utf-8");

		String uri = request.getRequestURI();
		String context = request.getContextPath();
		String page = uri.substring(context.length());

		// 요청uri값에서 /와 .do 제외한 값을 실행할 메소드의 지정.
		String methodName = page.substring(1, page.indexOf("."));

		try {
//			Class<?> cls = Class.forName(this.getClass().getName());
			Method method = this.getClass().getDeclaredMethod(methodName // 메소드명
					, HttpServletRequest.class // 파라미터1
					, HttpServletResponse.class // 파라미터2
			);
			method.invoke(this, request, response); // 메소드 실행.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void eventList(HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> list = svc.eventList();

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(list);

		try {
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addEvent(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end");

		SearchDTO event = new SearchDTO();
		event.setTitle(title);
		event.setStart(start);
		event.setEnd(end);

		try {
			if (svc.addEvent(event)) {
				// {"retCode": "OK"}
				response.getWriter().print("{\"retCode\": \"OK\"}");
			} else {
				response.getWriter().print("{\"retCode\": \"NG\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void removeEvent(HttpServletRequest request, HttpServletResponse response) {
		String title = request.getParameter("title");
		String start = request.getParameter("start");
		String end = request.getParameter("end");

		SearchDTO event = new SearchDTO();
		event.setTitle(title);
		event.setStart(start);
		event.setEnd(end);

		try {
			if (svc.removeEvent(event)) {
				// {"retCode": "OK"}
				response.getWriter().print("{\"retCode\": \"OK\"}");
			} else {
				response.getWriter().print("{\"retCode\": \"NG\"}");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// chart 의 json 데이터 .
	public void chart(HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> list = svc.countPerWriter();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(list); // list객체를 -> json문자열로 생성.

		try {
			response.getWriter().println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// chart의 페이지 호출.
	public void showChart(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("admin/chart.tiles").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

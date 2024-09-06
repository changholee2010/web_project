package com.yedam.control.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yedam.common.Control;
import com.yedam.service.MemberService;
import com.yedam.service.MemberServiceImpl;
import com.yedam.vo.MemberVO;

public class LoginControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");

		MemberService svc = new MemberServiceImpl();
		MemberVO member = svc.loginCheck(id, pw);

		// 로그인 실패를 했을 경우에는 다시 로그인화면으로 이동. 메세지를 전달.
		if (member == null) {
			request.setAttribute("message", "아이디와 비밀번호를 확인하세요!");
			request.getRequestDispatcher("html/logForm.tiles")//
					.forward(request, response);
			return;
		}

		// session 객체 - 요청정보(브라우저)를 확인해서 쿠키를 통해서 생성된 정보 구분.
		HttpSession session = request.getSession();
		session.setAttribute("logid", id);
		session.setAttribute("logName", member.getMemberName());

		// 일반사용자 . 관리자용 main 페이지를 다른 템플릿에서 실행.
		if (member.getAuthority().equals("User")) {
			request.getRequestDispatcher("main/main.tiles")//
					.forward(request, response);

		} else if (member.getAuthority().equals("Admin")) {
			request.getRequestDispatcher("admin/main.tiles")//
					.forward(request, response);

		}

	}

}

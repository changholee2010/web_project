package com.yedam.control.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.MemberService;
import com.yedam.service.MemberServiceImpl;
import com.yedam.vo.MemberVO;

public class ModifyMemberControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 파리미터 4개 값을 읽어서 db 반영 -> 목록으로 이동.
		response.setContentType("text/html;charset=utf-8");

		String id = request.getParameter("id"); // 사용자의 요청정보중에서 id값을 읽도록 함.
		String name = request.getParameter("name");
		String pw = request.getParameter("pass");
		String mail = request.getParameter("email"); // input태그의 name속성.

		MemberVO mvo = new MemberVO();
		mvo.setMemberId(id);
		mvo.setMemberName(name);
		mvo.setPassword(pw);
		mvo.setEmail(mail);

		MemberService svc = new MemberServiceImpl();
		if (svc.modifyMember(mvo)) {
			response.sendRedirect("memberList.do");
		} else {
			request.setAttribute("message", "아이디 " + id + "의 수정할 정보가 없습니다.");
			request.getRequestDispatcher("admin/modifyForm.tiles")//
					.forward(request, response);
		}
	}

}

package com.yedam.control.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yedam.common.Control;
import com.yedam.service.MemberService;
import com.yedam.service.MemberServiceImpl;
import com.yedam.vo.MemberVO;

public class RemoveMemberControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// id 파라미터를 받아서 db 삭제처리. 목록이동.
		response.setContentType("text/html;charset=utf-8");

		String id = request.getParameter("id"); // 사용자의 요청정보중에서 id값을 읽도록 함.
		 

		MemberService svc = new MemberServiceImpl();
		// 회원등록이 정상적일 경우 => 회원목록 페이지 출력.
		// 회원등록이 비정상적일 경우 => 회원등록 페이지 이동 (msg 전달)
		// 현재 페이지 : addMember.do -> 페이지 재지정 방식 1)forwarding:파라미터전달. 2)redirect:파라미터 불가.
		if (svc.removeMember(id)) {
			// PrintWriter out = response.getWriter(); // 출력스트림을 반환.
			// out.print("등록됨"); // 웹브라우저에 전달.
			response.sendRedirect("memberList.do");
		} else {
			request.setAttribute("message", "등록중에 오류가 있습니다.");
			request.getRequestDispatcher("WEB-INF/html/memberInfo.jsp")//
					.forward(request, response);
		}
	}

}

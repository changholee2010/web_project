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

public class RemoveMemberControl implements Control {

	@Override
	public void exec(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// id 파라미터를 받아서 db 삭제처리. 목록이동.
		response.setContentType("text/html;charset=utf-8");

		String id = request.getParameter("id"); // 사용자의 요청정보중에서 id값을 읽도록 함.

		MemberService svc = new MemberServiceImpl();

		// 삭제권한 체크. 관리자가 아니면 메세지전달.
		HttpSession session = request.getSession();
		String logid = (String) session.getAttribute("logid");
		MemberVO member = svc.getMember(logid);
		
		if (logid == null || !member.getAuthority().equals("Admin")) {
			request.setAttribute("message", "삭제권한이 없습니다.");
			request.getRequestDispatcher("admin/memberInfo.tiles")//
					.forward(request, response);
			return;
		}

		// 회원삭제 처리.
		if (svc.removeMember(id)) {
			response.sendRedirect("memberList.do");

		} else {
			request.setAttribute("message", "등록중에 오류가 있습니다.");
			request.getRequestDispatcher("admin/memberInfo.tiles")//
					.forward(request, response);

		}
	}

}

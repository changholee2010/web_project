package com.yedam.web;

import java.util.HashMap;
import java.util.Map;

import com.yedam.common.Control;
import com.yedam.control.board.AddFormControl;
import com.yedam.control.member.AddMemberControl;
import com.yedam.control.member.GetMemberControl;
import com.yedam.control.member.LoginControl;
import com.yedam.control.member.LoginFormControl;
import com.yedam.control.member.LogoutControl;
import com.yedam.control.member.MemberListControl;
import com.yedam.control.member.ModFormControl;
import com.yedam.control.member.ModifyMemberControl;
import com.yedam.control.member.RemoveMemberControl;

//회원관련 메뉴와 컨트롤 등록. 팀장.
public class MenuMember {
	private static MenuMember instance = new MenuMember();

	private MenuMember() {
	}

	public static MenuMember getInstance() {
		return instance;
	}

	public Map<String, Control> menuMap() {
		Map<String, Control> menu = new HashMap<>();
		// 기능등록.
		menu.put("/addForm.do", new AddFormControl()); // 회원등록페이지.
		menu.put("/addMember.do", new AddMemberControl()); // 회원등록처리.
		menu.put("/memberList.do", new MemberListControl());
		menu.put("/getMember.do", new GetMemberControl()); // 회원아이디로 상세조회.
		menu.put("/modifyForm.do", new ModFormControl()); // 수정화면 호출.
		menu.put("/modifyMember.do", new ModifyMemberControl()); // 수정처리.
		menu.put("/removeMember.do", new RemoveMemberControl()); // 삭제처리.

		// 로그인 관련.
		menu.put("/loginForm.do", new LoginFormControl());
		menu.put("/login.do", new LoginControl());
		menu.put("/logout.do", new LogoutControl());
		return menu;
	}
}

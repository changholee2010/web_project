package com.yedam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yedam.vo.MemberVO;

public interface MemberMapper {
	List<MemberVO> memberList();
	int insertMember(MemberVO member);
	int updateMember(MemberVO member);
	int deleteMember(String memberId);
	MemberVO selectMember(String memberId);
	
	// id, pw 로그인정보 확인.
	MemberVO loginMember(@Param("id") String id, @Param("pw") String pw);
}

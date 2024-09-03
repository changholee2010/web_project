package com.yedam.mapper;

import java.util.List;

import com.yedam.vo.MemberVO;

public interface MemberMapper {
	List<MemberVO> memberList();
	int insertMember(MemberVO member);
	int updateMember(MemberVO member);
	int deleteMember(String memberId);
	MemberVO selectMember(String memberId);
}

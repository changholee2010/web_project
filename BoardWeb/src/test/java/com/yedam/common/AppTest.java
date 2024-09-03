package com.yedam.common;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.yedam.mapper.MemberMapper;
import com.yedam.vo.MemberVO;

public class AppTest {
	public static void main(String[] args) {
		MemberVO mvo = new MemberVO();
		mvo.setMemberId("user04");
		mvo.setMemberName("김김치");
		mvo.setPassword("1111");
		mvo.setEmail("kim@email.com");

		SqlSessionFactory factory = DataSource.getInstance();
		SqlSession session = factory.openSession();
		MemberMapper mapper = session.getMapper(MemberMapper.class);

		if (mapper.insertMember(mvo) == 1) {
			session.commit();
		}

		List<MemberVO> list = mapper.memberList();
		list.forEach(member -> {
			System.out.println(member.toString());
		});

	}
}

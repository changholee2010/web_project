package com.yedam.common;

import org.apache.ibatis.session.SqlSession;

import com.yedam.mapper.ReplyMapper;

public class AppTest {
	public static void main(String[] args) {
		SqlSession sqlSession = DataSource.getInstance().openSession(true);
		ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);

		SearchDTO search = new SearchDTO();
		search.setBoardNo(147);
		search.setPage(2);

		mapper.selectListPaging(search)//
				.forEach(reply -> System.out.println(reply.toString()));

	}
}

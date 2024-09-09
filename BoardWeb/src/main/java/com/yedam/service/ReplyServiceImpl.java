package com.yedam.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.yedam.common.DataSource;
import com.yedam.mapper.ReplyMapper;
import com.yedam.vo.ReplyVO;

public class ReplyServiceImpl implements ReplyService {

	SqlSession sqlSession = DataSource.getInstance().openSession(true);
	ReplyMapper mapper = sqlSession.getMapper(ReplyMapper.class);

	@Override
	public List<ReplyVO> replyList(int bno) {
		return mapper.selectList(bno);
	}

	@Override
	public boolean removeReply(int rno) {
		return mapper.deleteReply(rno) == 1;
	}

	@Override
	public boolean removeReplys(String[] array) {
		return mapper.deleteReplys(array) > 0;
	}

	@Override
	public boolean addReply(ReplyVO rvo) {
		int rno = mapper.selectKey();
		rvo.setReplyNo(rno);
		return mapper.insertReply(rvo) == 1;
	}
}

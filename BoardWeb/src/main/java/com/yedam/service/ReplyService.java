package com.yedam.service;

import java.util.List;

import com.yedam.common.SearchDTO;
import com.yedam.vo.ReplyVO;

public interface ReplyService {
	List<ReplyVO> replyList(SearchDTO search); // 목록.
	boolean removeReply(int rno); // 삭제.
	boolean removeReplys(String[] array); // 다건삭제.
	boolean addReply(ReplyVO rvo);
	
	// 댓글건수.
	int getReplyCount(int bno);
}

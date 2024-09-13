package com.yedam.service;

import java.util.List;
import java.util.Map;

import com.yedam.common.SearchDTO;
import com.yedam.vo.ReplyVO;

public interface ReplyService {
	List<ReplyVO> replyList(SearchDTO search); // 목록.
	boolean removeReply(int rno); // 삭제.
	boolean removeReplys(String[] array); // 다건삭제.
	boolean addReply(ReplyVO rvo);

	// 댓글건수.
	int getReplyCount(int bno);

	// fullcalendar 관련.
	List<Map<String, Object>> eventList();
	boolean addEvent(SearchDTO event);
	boolean removeEvent(SearchDTO event);

	// todo관련.
	List<Map<String, Object>> todoList();
	
	// chart 관련.
	List<Map<String, Object>> countPerWriter();
	

}

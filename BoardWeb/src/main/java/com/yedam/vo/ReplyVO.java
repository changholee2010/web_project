package com.yedam.vo;

import java.util.Date;

import lombok.Data;

// 댓글에 대한 VO선언.

@Data
public class ReplyVO {
	private int replyNo;
	private String replyer;
	private String reply;
	private int boardNo;
	private Date replyDate;
}

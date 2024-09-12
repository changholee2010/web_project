package com.yedam.common;

import lombok.Data;

// 검색조건을 담기 위한 클래스.
@Data
public class SearchDTO {
	private String searchCondition; // 검색조건.
	private String keyword; // 검색단어.
	private int page; // 페이지.
	private int boardNo; // 몇번글의 댓글.
	
	// event 관련.
	private String title;
	private String start;
	private String end;
}

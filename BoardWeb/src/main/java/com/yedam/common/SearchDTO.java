package com.yedam.common;

import lombok.Data;

// 검색조건을 담기 위한 클래스.
@Data
public class SearchDTO {
	private String searchCondition;
	private String keyword;
	private int page;
}

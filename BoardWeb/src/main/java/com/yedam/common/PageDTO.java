package com.yedam.common;

import lombok.Data;

// 페이징 계산 위한 클래스.
@Data
public class PageDTO {
	// 현재페이지정보. 11 .. 13 .. 20
	// 이전, 이후 정보.
	int page;
	int startPage, endPage;
	boolean prev, next;

	public PageDTO(int page, int totalCnt) { // page: 3, totalCnt: 76건. 16page.
		this.page = page;
		this.endPage = (int) Math.ceil(page / 10.0) * 10; // 20
		this.startPage = this.endPage - 9; // 11

		int realEnd = (int) Math.ceil(totalCnt / 5.0); // 16
		this.endPage = this.endPage > realEnd ? realEnd : this.endPage;

		// 이전, 이후 여부
		prev = this.startPage > 1; // 1, 11, 21
		next = this.endPage < realEnd ? true : false;

	}

}

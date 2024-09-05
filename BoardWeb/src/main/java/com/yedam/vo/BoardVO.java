package com.yedam.vo;

import java.util.Date;

import lombok.Data;
import lombok.Setter;

@Data
//@Setter
public class BoardVO {
	private int boardNo;
	private String title;
	private String content;
	private String writer;
	private int viewCnt;
	private Date creationDate;
	private String image;
}

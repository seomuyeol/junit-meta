package com.junitP.junit.web.dto;

import com.junitP.junit.domain.Book;

import lombok.Setter;

@Setter // Controller から Setterが呼ばれてDtoに入れる
public class BookSaveReqDto {
	private String title;
	private String content;
	
	public Book toEntity() {
		return Book.builder()
				.title(title)
				.author(content)
				.build();
	}
}

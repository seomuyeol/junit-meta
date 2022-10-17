package com.junitP.junit.web.dto;

import com.junitP.junit.domain.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Controller から Setterが呼ばれてDtoに入れる
public class BookSaveReqDto {
	private String title;
	private String author;
	
	public Book toEntity() {
		return Book.builder()
				.title(title)
				.author(author)
				.build();
	}
}

package com.junitP.junit.web.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.junitP.junit.domain.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter // Controller から Setterが呼ばれてDtoに入れる
public class BookSaveReqDto {
	
	@Size(min = 1, max = 50)
	@NotBlank //null, 空白検索
	private String title;

	@Size(min = 2, max = 20)
	@NotBlank
	private String author;
	
	public Book toEntity() {
		return Book.builder()
				.title(title)
				.author(author)
				.build();
	}
}

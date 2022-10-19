package com.junitP.junit.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.junitP.junit.web.dto.BookRespDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Book {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	
	@Column(length = 50, nullable = false)
	private String title;
	
	@Column(length = 20, nullable = false)
	private String author;
	
	@Builder
	public Book(Long id, String title, String author) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	public void update(String title, String author) {
		this.title = title;
		this.author = author;
	}
	
	public BookRespDto toDto() {
		return BookRespDto.builder()
				.id(id)
				.title(title)
				.author(author)
				.build();
	}
	
}
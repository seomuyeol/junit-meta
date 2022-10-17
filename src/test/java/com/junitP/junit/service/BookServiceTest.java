package com.junitP.junit.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.junitP.junit.domain.BookRepository;
import com.junitP.junit.util.MailSenderStub;
import com.junitP.junit.web.dto.BookRespDto;
import com.junitP.junit.web.dto.BookSaveReqDto;

@DataJpaTest
public class BookServiceTest {

	@Autowired
	private BookRepository bookRepository;
	
	@Test
	public void bookSaveTest() {
		//given
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit-seo");
		dto.setAuthor("meta-seo");
		
		//stub
		
		//when
		MailSenderStub mailSenderStub = new MailSenderStub();
		BookService bookService = new BookService(bookRepository, mailSenderStub);
		BookRespDto bookRespDto = bookService.bookSave(dto);
		
		//then
		assertEquals(dto.getTitle(), bookRespDto.getTitle());
		assertEquals(dto.getAuthor(), bookRespDto.getAuthor());

	}
}

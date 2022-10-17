package com.junitP.junit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junitP.junit.domain.BookRepository;
import com.junitP.junit.util.MailSender;
import com.junitP.junit.web.dto.BookRespDto;
import com.junitP.junit.web.dto.BookSaveReqDto;

//@DataJpaTest //Tranjectional あるため、テスト後ロールバックします。
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
	
	@InjectMocks
	private BookService bookService;
	@Mock
	private BookRepository bookRepository;
	@Mock
	private MailSender mailSender;

//	@Autowired
//	private BookRepository bookRepository;
	
	// 気になる…　サービスだけテストしたいけど、レポジトリもテストになる　Mockito使用
	@Test
	public void bookSaveTest() {
		//given
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit-seo");
		dto.setAuthor("meta-seo");
		
		//stub（行動定義）
		when(bookRepository.save(any())).thenReturn(dto.toEntity());
		when(mailSender.send()).thenReturn(true);
		
		//when
		BookRespDto bookRespDto = bookService.bookSave(dto);
		
		//then
//		assertEquals(dto.getTitle(), bookRespDto.getTitle());
//		assertEquals(dto.getAuthor(), bookRespDto.getAuthor());
		assertThat(dto.getTitle()).isEqualTo(bookRespDto.getTitle());
		assertThat(dto.getAuthor()).isEqualTo(bookRespDto.getAuthor());
		

	}
}

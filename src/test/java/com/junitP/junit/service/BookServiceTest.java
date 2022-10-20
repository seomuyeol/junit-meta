package com.junitP.junit.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.junitP.junit.domain.Book;
import com.junitP.junit.domain.BookRepository;
import com.junitP.junit.util.MailSender;
import com.junitP.junit.web.dto.request.BookSaveReqDto;
import com.junitP.junit.web.dto.response.BookRespDto;

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
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
		
	}
	
	@Test
	public void bookListTest() {
		//given(パラメータから入るデータ)
		
		//stub(仮説)
		List<Book> books = new ArrayList<>();
		books.add(new Book(1L, "junit-seo", "meta-seo"));
		books.add(new Book(2L, "spring-seo", "coding-seo"));
		when(bookRepository.findAll()).thenReturn(books);
		
		//when(実行)
		List<BookRespDto> bookRespDtoList = bookService.bookList();
		
		//print
		bookRespDtoList.stream().forEach((dto)-> {
			System.out.println(dto.getId());
			System.out.println(dto.getTitle());
			System.out.println(dto.getAuthor());
			System.out.println("2.-------------------------------------------------------------------------");
		});
		
		//then（検証）
		assertThat(bookRespDtoList.get(0).getTitle()).isEqualTo("junit-seo");
		assertThat(bookRespDtoList.get(0).getAuthor()).isEqualTo("meta-seo");
		assertThat(bookRespDtoList.get(1).getTitle()).isEqualTo("spring-seo");
		assertThat(bookRespDtoList.get(1).getAuthor()).isEqualTo("coding-seo");
		
	}
	
	@Test
	public void bookSearchTest() {
		//given
		Long id = 1L;
		
		//stub
		Book book = new Book(1L, "junit-1", "coding-1");
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);
		
		//when
		BookRespDto bookRespDto = bookService.booksSearch(id);
		
		//then
		assertThat(bookRespDto.getTitle()).isEqualTo(book.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(book.getAuthor());
	}
	
	@Test
	public void bookUpdateTest() {
		//given
		Long id = 1L;
		BookSaveReqDto dto = new BookSaveReqDto();
		dto.setTitle("junit-seo");
		dto.setAuthor("coding-seo");
		
		//stub
		Book book = new Book(1L, "junit-1", "coding-1");
		Optional<Book> bookOP = Optional.of(book);
		when(bookRepository.findById(id)).thenReturn(bookOP);
	
		//when
		BookRespDto bookRespDto = bookService.bookUpdate(id, dto);
		
		//then
		assertThat(bookRespDto.getTitle()).isEqualTo(dto.getTitle());
		assertThat(bookRespDto.getAuthor()).isEqualTo(dto.getAuthor());
		
	}
	
}

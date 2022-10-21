package com.junitP.junit.web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.junitP.junit.domain.Book;
import com.junitP.junit.domain.BookRepository;
import com.junitP.junit.web.dto.request.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

//総合テスト
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

	@Autowired
	private TestRestTemplate rt;
	
	@Autowired
	private BookRepository bookRepository;
	
	private static ObjectMapper om;
	private static HttpHeaders headers;
	
	@BeforeAll
	public static void init() {
		om = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}
	
	@BeforeEach //各テスト以前に実行
	public void dataSetting() {
		String title = "junit";
		String author = "coding";
		Book book = Book.builder()
				.title(title)
				.author(author)
				.build();
		bookRepository.save(book);
	}
	
//	@Test
//	public void di_test() {
//		if (bookService == null) {
//			System.out.println("null");
//		} else {
//			System.out.println("nullX");
//		}
//	}
	
	@Test
	public void saveBook_test() throws Exception {
		//given
		BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
		bookSaveReqDto.setTitle("spring1");
		bookSaveReqDto.setAuthor("get-in11");
		
		String body = om.writeValueAsString(bookSaveReqDto);
		
		//when
		HttpEntity<String> request = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.POST, request,  String.class);
//		System.out.println(response.getBody());
		
		//then
		DocumentContext dc = JsonPath.parse(response.getBody());
		String title = dc.read("$.body.title");
		String author = dc.read("$.body.author");
		
		assertThat(title).isEqualTo("spring1");
		assertThat(author).isEqualTo("get-in11");
	}
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void getBookList_test() {
		//given
		
		//when
		HttpEntity<String> request = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = rt.exchange("/api/v1/book", HttpMethod.GET, request,  String.class);

		//then
		DocumentContext dc = JsonPath.parse(response.getBody());
		Integer code = dc.read("$.code");
		String title = dc.read("$.body.items[0].title");
		
		assertThat(code).isEqualTo(1);
		assertThat(title).isEqualTo("junit");
	}
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void getBookSearch_test() {
		//given
		Integer id = 1;
		
		//when
		HttpEntity<String> request = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.GET, request,  String.class);
		
		System.out.println(response.getBody());
		
		//then
		DocumentContext dc = JsonPath.parse(response.getBody());
		Integer code = dc.read("$.code");
		String title = dc.read("$.body.title");
		
		assertThat(code).isEqualTo(1);
		assertThat(title).isEqualTo("junit");
	}
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void deleteBook_test() {
		//given
		Integer id = 1;
		
		//when
		HttpEntity<String> request = new HttpEntity<>(null, headers);
		ResponseEntity<String> response = rt.exchange("/api/v1/book/" + id, HttpMethod.DELETE, request,  String.class);
		
		//then
		System.out.println(response.getStatusCode());
		DocumentContext dc = JsonPath.parse(response.getBody());
		Integer code = dc.read("$.code");
		
		assertThat(code).isEqualTo(1);
	}
}

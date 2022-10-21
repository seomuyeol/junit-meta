package com.junitP.junit.web;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.junitP.junit.service.BookService;
import com.junitP.junit.web.dto.request.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

//総合テスト
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookApiControllerTest {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private TestRestTemplate rt;
	
	private static ObjectMapper om;
	private static HttpHeaders headers;
	
	@BeforeAll
	public static void init() {
		om = new ObjectMapper();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
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
	
	
	
}

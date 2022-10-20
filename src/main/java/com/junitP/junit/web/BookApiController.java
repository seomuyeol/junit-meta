package com.junitP.junit.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.junitP.junit.service.BookService;
import com.junitP.junit.web.dto.request.BookSaveReqDto;
import com.junitP.junit.web.dto.response.BookListRespDto;
import com.junitP.junit.web.dto.response.BookRespDto;
import com.junitP.junit.web.dto.response.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class BookApiController { 
	
	private final BookService bookService;
	
	//1. book save
	//{ "key" : value, "key" : value } //Jsonは＠RequestBodyが必要
	@PostMapping("/api/v1/book")
	public ResponseEntity<?> saveBook(@RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError fe : bindingResult.getFieldErrors()) {
				errorMap.put(fe.getField(), fe.getDefaultMessage());
			}
			System.out.println("==============================================");
			System.out.println(errorMap.toString());
			System.out.println("==============================================");
			
			throw new RuntimeException(errorMap.toString()); 
		}
		
		BookRespDto bookRespDto = bookService.bookSave(bookSaveReqDto);
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("Bookセーブ成功").body(bookRespDto).build(), 
				HttpStatus.CREATED); // 201 = insert , saveした、BODYデータを一緒に戻り値に上げる
	}
	
	//1. book save
	//{ "key" : value, "key" : value } //Jsonは＠RequestBodyが必要
	@PostMapping("/api/v2/book")
	public ResponseEntity<?> saveBookV2(@RequestBody BookSaveReqDto bookSaveReqDto) {
		
		BookRespDto bookRespDto = bookService.bookSave(bookSaveReqDto);
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("Bookセーブ成功").body(bookRespDto).build(), 
				HttpStatus.CREATED); // 201 = insert , saveした、BODYデータを一緒に戻り値に上げる
	}
	
	//2. book list
	@GetMapping("api/v1/book")
	public ResponseEntity<?> getBook() {
		BookListRespDto bookListRespDto = bookService.bookList();
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("BookList成功").body(bookListRespDto).build(), 
				HttpStatus.OK); // 200 = OK , saveした、BODYデータを一緒に戻り値に上げる
	}
	
	//3. book search
	@GetMapping("api/v1/book/{id}")
	public ResponseEntity<?> searchBook(@PathVariable Long id) {
		BookRespDto bookRespDto = bookService.booksSearch(id);
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("BookSearch成功").body(bookRespDto).build(), 
				HttpStatus.OK);
	}
	
	//4. book delete
	@DeleteMapping("api/v1/book/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable Long id) {
		bookService.bookDelete(id);
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("BookDelete成功").body(null).build(), 
				HttpStatus.OK);

	}
	
	//5. book Update
	@PostMapping("api/v1/book/{id}")
	public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, 
			BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError fe : bindingResult.getFieldErrors()) {
				errorMap.put(fe.getField(), fe.getDefaultMessage());
			}
			
			throw new RuntimeException(errorMap.toString()); 
		}
		
		BookRespDto bookRespDto = bookService.bookUpdate(id, bookSaveReqDto);
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("BookUpdate成功").body(bookRespDto).build(), 
				HttpStatus.OK);
	}
}

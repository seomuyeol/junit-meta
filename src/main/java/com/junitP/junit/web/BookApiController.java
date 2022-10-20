package com.junitP.junit.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.junitP.junit.service.BookService;
import com.junitP.junit.web.dto.request.BookSaveReqDto;
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
		return new ResponseEntity<>(CMRespDto.builder().code(1).msg("セーブ成功").body(bookRespDto).build(), HttpStatus.CREATED); // 201 = insert , saveした、BODYデータを一緒に戻り値に上げる
	}
	
	//2. book list
	public ResponseEntity<?> getBook() {
		return null;
	}
	
	//3. book search
	public ResponseEntity<?> searchBook() {
		return null;
	}
	
	//4. book delete
	public ResponseEntity<?> deleteBook() {
		return null;
	}
	
	//5. book collect
	public ResponseEntity<?> updateBook() {
		return null;
	}
}

package com.junitP.junit.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest //DB 関連のみテスト
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
//	@BeforeAll //テスト以前一回実行
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
	
	// 1. book 登録テスト
	@Test
	public void book_登録() {
		//given (データの準備)
		String title = "new_junit";
		String author = "new_coding";
		Book book = Book.builder()
				.title(title)
				.author(author)
				.build();
		
		
		//when (テスト実行)
		Book bookPS = bookRepository.save(book);
		
		
		//then (検証)
		assertEquals(title, bookPS.getTitle());
		assertEquals(author, bookPS.getAuthor());
//		System.out.println(bookPS.getTitle());
	}
	
	// 2. book リスト
	@Test
	public void bookList_test() {
		//given
		String title = "junit";
		String author = "coding";


		//when
		List<Book> booksPS = bookRepository.findAll();
		
		//then
		assertEquals(title , booksPS.get(0).getTitle());
		assertEquals(author, booksPS.get(0).getAuthor());
	}
	
	// 3. book 詳細画面
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void bookSearch_test() {
		//given
		String title = "junit";
		String author = "coding";
		
		//when
		Book bookPS = bookRepository.findById(1L).get();
		

		//then
		assertEquals(title, bookPS.getTitle());
		assertEquals(author, bookPS.getAuthor());
		
//		System.out.println(bookPS.getTitle());

	}
	
	// 4. book 削除
	@Sql("classpath:db/tableInit.sql")
	@Test
	public void book_delete_test() {
		//given
		Long id = 1L;
		
		//when
		bookRepository.deleteById(id);

		//then
//		Optional<Book> bookPS = bookRepository.findById(id);
		assertFalse(bookRepository.findById(id).isPresent());
	}
	
	// 5. book 修正
}

package com.junitP.junit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest //DB 関連のみテスト
public class BookRepositoryTest {

	@Autowired
	private BookRepository bookRepository;
	
	// 1. book 登録テスト
	@Test
	public void book_登録() {
		//given (データの準備)
		String title = "junit";
		String author = "coding";
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
	
	// 3. book 詳細画面
	
	// 4. book 修正
	
	// 5. book 削除
}

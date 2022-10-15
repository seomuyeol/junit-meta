package com.junitP.junit.domain;

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
		System.out.println("book 登録テスト実行");
	}
	
	// 2. book リスト
	
	// 3. book 詳細画面
	
	// 4. book 修正
	
	// 5. book 削除
}

package com.junitP.junit.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.junitP.junit.domain.Book;
import com.junitP.junit.domain.BookRepository;
import com.junitP.junit.util.MailSender;
import com.junitP.junit.web.dto.BookRespDto;
import com.junitP.junit.web.dto.BookSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BookService {
	
	private final BookRepository bookRepository;
	private final MailSender mailSender;
	
	//1. book 登録
	@Transactional(rollbackFor = RuntimeException.class)
	public BookRespDto bookSave(BookSaveReqDto dto) {
		Book bookPS = bookRepository.save(dto.toEntity());
		if (bookPS != null) {
			if (!mailSender.send()) {
				throw new RuntimeException("メールの転送が失敗しました。");
			}
		}
		return new BookRespDto().toDto(bookPS);
	}

	//2. book list
	public List<BookRespDto> bookList() {
		return bookRepository.findAll().stream()
				.map(new BookRespDto()::toDto)
				.collect(Collectors.toList());
	}
	
	
	//3. book 詳細 search
	public BookRespDto booksSearch(Long id) {
		Optional<Book> bookOP = bookRepository.findById(id);
		if(bookOP.isPresent()) {
			return new BookRespDto().toDto(bookOP.get());
		} else {
			throw new RuntimeException("IDが見つかりません");
		}
	}
	
	
	//4. book delete
	@Transactional(rollbackFor = RuntimeException.class)
	public void bookDelete(Long id) {
		bookRepository.deleteById(id);
	}
	
	
	//5. book 修正
	@Transactional(rollbackFor = RuntimeException.class)
	public void bookCollection(Long id, BookSaveReqDto dto) {
		Optional<Book> bookOP = bookRepository.findById(id);
		if(bookOP.isPresent()) {
			Book bookPS = bookOP.get();
			bookPS.update(dto.getTitle(), dto.getAuthor());
		} else {
			throw new RuntimeException("IDが見つかりません");
		}
	}
}

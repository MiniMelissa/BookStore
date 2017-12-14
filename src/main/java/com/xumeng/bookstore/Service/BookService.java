package com.xumeng.bookstore.Service;

import java.util.List;

import com.xumeng.bookstore.domain.Book;

public interface BookService {
	List<Book> findAll();

	Book findOne(Long id);
	
	List<Book> findByCategory(String category);
	
	List<Book> blurrySearch(String title);
}

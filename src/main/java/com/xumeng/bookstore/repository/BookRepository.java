package com.xumeng.bookstore.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

	List<Book> findByCategory(String category);
	
	//provided by spring boot
	List<Book> findByTitleContaining(String title);
}

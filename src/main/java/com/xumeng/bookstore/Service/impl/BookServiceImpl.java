package com.xumeng.bookstore.Service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumeng.bookstore.Service.BookService;
import com.xumeng.bookstore.domain.Book;
import com.xumeng.bookstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> findAll(){
		return (List<Book>) bookRepository.findAll();
	}
}

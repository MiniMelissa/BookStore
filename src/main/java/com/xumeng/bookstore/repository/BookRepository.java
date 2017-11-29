package com.xumeng.bookstore.repository;


import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{

}

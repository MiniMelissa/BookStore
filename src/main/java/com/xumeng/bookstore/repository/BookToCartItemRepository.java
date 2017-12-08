package com.xumeng.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.BookToCartItem;

public interface BookToCartItemRepository extends CrudRepository<BookToCartItem, Long>{

}

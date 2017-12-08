package com.xumeng.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long>{

}

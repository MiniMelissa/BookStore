package com.xumeng.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.CartItem;
import com.xumeng.bookstore.domain.ShoppingCart;

public interface CartItemRepository extends CrudRepository<CartItem, Long>{

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}

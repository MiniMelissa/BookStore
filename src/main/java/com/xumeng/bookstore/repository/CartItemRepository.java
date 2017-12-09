package com.xumeng.bookstore.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.xumeng.bookstore.domain.CartItem;
import com.xumeng.bookstore.domain.ShoppingCart;

@Transactional
public interface CartItemRepository extends CrudRepository<CartItem, Long>{

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}

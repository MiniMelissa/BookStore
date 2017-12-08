package com.xumeng.bookstore.Service;

import java.util.List;

import com.xumeng.bookstore.domain.Book;
import com.xumeng.bookstore.domain.CartItem;
import com.xumeng.bookstore.domain.ShoppingCart;
import com.xumeng.bookstore.domain.User;

public interface CartItemService {

	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
	
	CartItem updateCartItem(CartItem cartItem);
	
	CartItem addBookToCartItem(Book book, User user, int qty);
}

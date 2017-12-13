package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.BillingAddress;
import com.xumeng.bookstore.domain.Order;
import com.xumeng.bookstore.domain.Payment;
import com.xumeng.bookstore.domain.ShippingAddress;
import com.xumeng.bookstore.domain.ShoppingCart;
import com.xumeng.bookstore.domain.User;

public interface OrderService {

	Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress, BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user);
	
	Order findOne(Long id);
}

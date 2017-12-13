package com.xumeng.bookstore.Service.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumeng.bookstore.Service.CartItemService;
import com.xumeng.bookstore.Service.OrderService;
import com.xumeng.bookstore.domain.BillingAddress;
import com.xumeng.bookstore.domain.Book;
import com.xumeng.bookstore.domain.CartItem;
import com.xumeng.bookstore.domain.Order;
import com.xumeng.bookstore.domain.Payment;
import com.xumeng.bookstore.domain.ShippingAddress;
import com.xumeng.bookstore.domain.ShoppingCart;
import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;

	
	@Autowired
	private CartItemService cartItemService;
	
	
	public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress,
			BillingAddress billingAddress,
			Payment payment, String shippingMethod, User user) {
		
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);
		
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		
		for(CartItem cartItem : cartItemList) {
			Book book = cartItem.getBook();
			cartItem.setOder(order);
			book.setInStockNumber(book.getInStockNumber() - cartItem.getQty());			
		}
		
		order.setOrderDate(Calendar.getInstance().getTime());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);
		
		return order;
	}
	
	public Order findOne(Long id) {
		return orderRepository.findOne(id);
	}
}

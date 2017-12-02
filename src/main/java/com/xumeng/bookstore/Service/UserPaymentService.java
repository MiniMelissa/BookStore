package com.xumeng.bookstore.Service;


import com.xumeng.bookstore.domain.UserPayment;


public interface UserPaymentService {

	UserPayment findById(Long id);
	
	void removeById(Long id);
}

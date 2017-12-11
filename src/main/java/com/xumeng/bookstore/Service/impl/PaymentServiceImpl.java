package com.xumeng.bookstore.Service.impl;

import org.springframework.stereotype.Service;

import com.xumeng.bookstore.Service.PaymentService;
import com.xumeng.bookstore.domain.Payment;
import com.xumeng.bookstore.domain.UserPayment;

@Service
public class PaymentServiceImpl implements PaymentService{

	public 	Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());
		payment.setCardNumber(userPayment.getCardNumber());
		
		return payment;
	}

}

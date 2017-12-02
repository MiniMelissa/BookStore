package com.xumeng.bookstore.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumeng.bookstore.Service.UserPaymentService;
import com.xumeng.bookstore.domain.UserPayment;
import com.xumeng.bookstore.repository.UserPaymentRepository;

@Service
public class UserPaymentServiceImpl implements UserPaymentService{

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	public UserPayment findById(Long id) {
		return userPaymentRepository.findOne(id);
	}
}

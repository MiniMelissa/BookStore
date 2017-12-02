package com.xumeng.bookstore.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xumeng.bookstore.Service.UserShippingService;
import com.xumeng.bookstore.domain.UserShipping;
import com.xumeng.bookstore.repository.UserShippingRepository;

@Service
public class UserShippingServiceImpl implements UserShippingService{

	@Autowired
	private UserShippingRepository userShippingRepository;
	
	public UserShipping findById(Long id){
		return userShippingRepository.findOne(id);
	}
	
	public void removeById(Long id) {
		userShippingRepository.delete(id);
	}
}

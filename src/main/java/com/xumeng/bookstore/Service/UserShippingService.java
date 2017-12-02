package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.UserShipping;

public interface UserShippingService {
	
	UserShipping findById(Long id);

	void removeById(Long id);

}

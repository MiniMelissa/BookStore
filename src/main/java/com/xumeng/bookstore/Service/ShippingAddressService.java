package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.ShippingAddress;
import com.xumeng.bookstore.domain.UserShipping;

public interface ShippingAddressService {
	
	ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);

}

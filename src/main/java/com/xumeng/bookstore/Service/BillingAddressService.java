package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.BillingAddress;
import com.xumeng.bookstore.domain.UserBilling;

public interface BillingAddressService {

	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}

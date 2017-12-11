package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.Payment;
import com.xumeng.bookstore.domain.UserPayment;

public interface PaymentService {
	
	Payment setByUserPayment(UserPayment userPayment, Payment payment);

}

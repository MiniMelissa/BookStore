package com.xumeng.bookstore.Service;

import java.util.Set;

import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.domain.UserBilling;
import com.xumeng.bookstore.domain.UserPayment;
import com.xumeng.bookstore.domain.security.PasswordResetToken;
import com.xumeng.bookstore.domain.security.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	User createUser(User user, Set<UserRole> userRoles);
	
	User save(User user);
	
	void updateUserBilling(UserBilling userBilling, UserPayment userPaymnet, User user);
}

package com.xumeng.bookstore.Service;

import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.domain.security.PasswordResetToken;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user, final String token);
}

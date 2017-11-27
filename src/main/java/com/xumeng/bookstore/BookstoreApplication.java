package com.xumeng.bookstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.xumeng.bookstore.Service.UserService;
import com.xumeng.bookstore.domain.User;
import com.xumeng.bookstore.domain.security.Role;
import com.xumeng.bookstore.domain.security.UserRole;
import com.xumeng.bookstore.utility.SecurityUtility;

@SpringBootApplication
public class BookstoreApplication implements CommandLineRunner{

	@Autowired
	private UserService userService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception{
		User user1 = new User();
		user1.setFirstName("Amy");
		user1.setLastName("xu");
		user1.setUsername("h");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("xumeng@gmail.com");
		Set<UserRole> userRoles = new HashSet<>();
		Role role1 = new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1, role1));
		
		userService.createUser(user1, userRoles);
	}
}

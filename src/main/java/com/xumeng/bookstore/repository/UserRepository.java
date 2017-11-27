package com.xumeng.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByUsername(String username);
	
	
	User findByEmail(String email);

}

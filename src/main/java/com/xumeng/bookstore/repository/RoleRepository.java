package com.xumeng.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.security.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByname(String name);
}

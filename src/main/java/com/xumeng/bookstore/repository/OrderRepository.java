package com.xumeng.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.xumeng.bookstore.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Long>{

}

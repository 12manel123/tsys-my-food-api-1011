package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Order;

@Repository
public interface IOrderDAO extends JpaRepository<Order, Long> {

}

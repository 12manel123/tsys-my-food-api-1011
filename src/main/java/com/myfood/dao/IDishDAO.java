package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myfood.dto.Dish;

public interface IDishDAO extends JpaRepository<Dish, Long> {

}
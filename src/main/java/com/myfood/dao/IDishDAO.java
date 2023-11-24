package com.myfood.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myfood.dto.Dish;

public interface IDishDAO extends JpaRepository<Dish, Long> {
	
	Optional<Dish> findByName(String name);
	List<Dish> findByCategory(String category);

}
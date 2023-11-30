package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myfood.dto.Dish;

public interface IDishService {

	List<Dish> getAllDishes();

    Optional<Dish> getOneDish(Long entity);
    
    Optional<Dish> getDishByName(String name);
    
    List<Dish> getDishesByCategory(String category);

    Dish createDish(Dish entity);

    Dish updateDish(Dish entity);

    void deleteDish(Long id);
    
	Page<Dish> getAllDishesWithPagination(Pageable pageable);
	
}

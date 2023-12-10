package com.myfood.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myfood.dto.Atribut_Dish;
import com.myfood.dto.Dish;

public interface IAtribut_DishDAO extends JpaRepository<Atribut_Dish, Long> {
	

    Page<Atribut_Dish> findAll(Pageable pageable);
    
    List<Atribut_Dish> findByAttributes(String atribut);
    
}

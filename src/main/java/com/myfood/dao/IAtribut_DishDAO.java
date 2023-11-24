package com.myfood.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myfood.dto.Atribut_Dish;

public interface IAtribut_DishDAO extends JpaRepository<Atribut_Dish, Long> {
	
    List<Atribut_Dish> findByAtributes(String atributes);

}

package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myfood.dto.Atribut_Dish;
import com.myfood.dto.Dish;

public interface IAtribut_DishService {

    
    List<Dish> getDishesByAtribut(String atribut);

    Atribut_Dish createAtribut_Dish(Atribut_Dish entity);

    Atribut_Dish updateAtribut_Dish(Atribut_Dish entity);
    
    Optional<Atribut_Dish> getOneAtribut_Dish(Long id);
   
    Page<Atribut_Dish> getAllAtribut_Dishes(Pageable pageable);

    void deleteAtribut_Dish(Long id);

    Page<Atribut_Dish> findAll(Pageable pageable);
    
    List<Atribut_Dish> getAtributsByAttributes(String attributes);

	
}

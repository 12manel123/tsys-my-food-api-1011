package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Atribut_Dish;

public interface IAtribut_DishService {

	List<Atribut_Dish> getAllAtribut_Dishes();

    Optional<Atribut_Dish> getOneAtribut_Dish(Long id);
    
    List<Atribut_Dish> getAtributByAtributes(String atributes);

    Atribut_Dish createAtribut_Dish(Atribut_Dish entity);

    Atribut_Dish updateAtribut_Dish(Atribut_Dish entity);

    void deleteAtribut_Dish(Long id);
	
}

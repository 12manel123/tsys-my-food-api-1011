package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfood.dto.Atribut_Dish;
import com.myfood.dao.IAtribut_DishDAO;

@Service
public class Atribut_DishServiceImpl implements IAtribut_DishService{
	
	@Autowired
    private IAtribut_DishDAO atribut_DishDAO;

    @Override
    public List<Atribut_Dish> getAllAtribut_Dishes() {
        return atribut_DishDAO.findAll();
    }
    @Override
    public Optional<Atribut_Dish> getOneAtribut_Dish(Long id) {
        return atribut_DishDAO.findById(id);
    }

    @Override
    public Atribut_Dish createAtribut_Dish(Atribut_Dish entity) {
        return atribut_DishDAO.save(entity);
    }

    @Override
    public Atribut_Dish updateAtribut_Dish(Atribut_Dish entity) {
        return atribut_DishDAO.save(entity);
    }

    @Override
    public void deleteAtribut_Dish(Long id) {
    	atribut_DishDAO.deleteById(id);
    }
}
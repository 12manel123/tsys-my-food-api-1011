package com.myfood.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.myfood.dao.IAtribut_DishDAO;
import com.myfood.dto.Atribut_Dish;
import com.myfood.dto.Dish;

@Service
public class Atribut_DishServiceImpl implements IAtribut_DishService {

	@Autowired
	private IAtribut_DishDAO atribut_DishDAO;

	@Override
	public Page<Atribut_Dish> getAllAtribut_Dishes(Pageable pageable) {
		return atribut_DishDAO.findAll(pageable);
	}

	@Override
	public Optional<Atribut_Dish> getOneAtribut_Dish(Long id) {
		return atribut_DishDAO.findById(id);
	}

	@Override
	public List<Dish> getDishesByAtribut(String atribut) {  
		return atribut_DishDAO.findByAttributes(atribut)
				.stream()
				.flatMap(atributDish -> atributDish.getDishes().stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Atribut_Dish> getAtributsByAttributes(String attributes) {
		return atribut_DishDAO.findByAttributes(attributes);
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
	@Override
	public Page<Atribut_Dish> findAll(Pageable pageable) {		
		return atribut_DishDAO.findAll(pageable);
	}



}
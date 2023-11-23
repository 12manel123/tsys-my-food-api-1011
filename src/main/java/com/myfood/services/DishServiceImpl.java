package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfood.dao.IDishDAO;
import com.myfood.dto.Dish;

@Service
public class DishServiceImpl implements IDishService {
	
	 @Autowired
	    private IDishDAO dishDAO;

	    @Override
	    public List<Dish> getAllDishes() {
	        return dishDAO.findAll();
	    }
	    @Override
	    public Optional<Dish> getOneDish(Long id) {
	        return dishDAO.findById(id);
	    }

	    @Override
	    public Dish createDish(Dish entity) {
	        return dishDAO.save(entity);
	    }

	    @Override
	    public Dish updateDish(Dish entity) {
	        return dishDAO.save(entity);
	    }

	    @Override
	    public void deleteDish(Long id) {
	    	dishDAO.deleteById(id);
	    }
}

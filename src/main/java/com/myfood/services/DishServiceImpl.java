package com.myfood.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	    public Optional<Dish> getDishByName(String name) {
	        return dishDAO.findByName(name);
	    }
	    
	    @Override
	    public List<Dish> getDishesByCategory(String category) {
	        return dishDAO.findByCategory(category);
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
	    
	    @Override
		public Page<Dish> getAllDishesWithPagination(Pageable pageable) {
		    return dishDAO.findAll(pageable);
		}
}
package com.myfood.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Atribut_Dish;
import com.myfood.dto.Dish;
import com.myfood.services.IAtribut_DishService;
import com.myfood.services.IDishService;



@RestController
@RequestMapping("api/v1")
public class Atribut_DishController {

	@Autowired
	private IAtribut_DishService atribut_DishService;
	@Autowired
	private IDishService dishService;

	@GetMapping("/atributs")
	public ResponseEntity<List<Atribut_Dish>> getAllAtribut_Dishes() {
		return ResponseEntity.ok(atribut_DishService.getAllAtribut_Dishes());
	}

	@GetMapping("/atribut/{id}")
	public ResponseEntity<Atribut_Dish> getOneAtribut_Dish(@PathVariable(name = "id") Long id) {
		Optional<Atribut_Dish> entity = atribut_DishService.getOneAtribut_Dish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}        
	}


	@PostMapping("/atribut/{dish_id}")
	public ResponseEntity<?> saveAtribut_Dish(@PathVariable(name = "dish_id") Long id, @RequestBody Atribut_Dish entity) {
		Map<String, Object> rest = new HashMap<>();
		Optional<Dish> dish = dishService.getOneDish(id);

		if(dish.isPresent()) {
			entity.setDish(dish.get());
		}

		if (isValidAtribut(entity.getAtributes())) {
			return ResponseEntity.ok(atribut_DishService.createAtribut_Dish(entity));
		}
		else {   
			rest.put("Error", "The 'Category' field only accepts the strings 'CELIAC,' 'LACTOSE,' 'VEGAN,' 'VEGETARIAN,' and 'NUTS'");
			return ResponseEntity.status(400).body(rest);
		}
	}

	@PutMapping("/atribut/{dish_id}")
	public ResponseEntity<?> updateAtribut_Dish(@PathVariable(name = "dish_id") Long id, @RequestBody Atribut_Dish entity) {
		
		Map<String, Object> rest = new HashMap<>();
		Optional<Dish> dish = dishService.getOneDish(id);
				
		if (dish.isPresent()) {

			entity.setDish(dish.get());					
		}
		if (isValidAtribut(entity.getAtributes())) {

			return ResponseEntity.status(201).body(atribut_DishService.updateAtribut_Dish(entity));
		}
		else {   
			rest.put("Error", "The 'Category' field only accepts the strings 'CELIAC,' 'LACTOSE,' 'VEGAN,' 'VEGETARIAN,' and 'NUTS'");
			return ResponseEntity.status(400).body(rest);
		}
	}
	

	@DeleteMapping("/atribut/{id}")
	public ResponseEntity<Void> deleteAtribut_Dish(@PathVariable(name = "id") Long id) {
		Optional<Atribut_Dish> entity = atribut_DishService.getOneAtribut_Dish(id);
		if (entity.isPresent()) {
			atribut_DishService.deleteAtribut_Dish(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean isValidAtribut(String atribut) {
		if ("CELIAC".equals(atribut) || "LACTOSE".equals(atribut) || "VEGAN".equals(atribut) || "VEGETARIAN".equals(atribut) || "NUTS".equals(atribut)) {
			return true;
		}
		return false;
	}


}

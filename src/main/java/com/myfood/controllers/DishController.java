package com.myfood.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Dish;
import com.myfood.services.IDishService;

@RestController
@RequestMapping("api/v1")
public class DishController {

	@Autowired
	private IDishService dishService;

	@GetMapping("/dishes")
	public ResponseEntity<List<Dish>> getAllDishes() {
		return ResponseEntity.ok(dishService.getAllDishes());
	}

	@GetMapping("/dish/{id}")
	public ResponseEntity<Dish> getOneDish(@PathVariable(name = "id") Long id) {
		Optional<Dish> entity = dishService.getOneDish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/dish")
	public ResponseEntity<?> saveDish(@RequestBody Dish entity) {

		System.out.println(entity);

		Map<String, Object> rest = new HashMap <String, Object>();

		if(isValidCategory(entity.getCategory())) {
			return ResponseEntity.ok(dishService.createDish(entity));
		}

		rest.put("Error", "The 'Category' field only accepts the strings 'APPETIZER,' 'FIRST,' 'SECOND,' and 'DESSERT'");
		return ResponseEntity.status(400).body(rest);

	}

	@PutMapping("/dish/{id}")
	public ResponseEntity<?> updateDish(@PathVariable(name = "id") Long id, @RequestBody Dish entity) {

		Map<String, Object> rest = new HashMap <String, Object>();
		Optional<Dish> existDish = dishService.getOneDish(id);

		if (existDish.isPresent()) {
			entity.getId();
		}
		if (isValidCategory(entity.getCategory())) {
			
	        return ResponseEntity.ok(dishService.updateDish(entity));
		}
		else {
			rest.put("Error", "The 'Category' field only accepts the strings 'APPETIZER,' 'FIRST,' 'SECOND,' and 'DESSERT'");
			return ResponseEntity.status(400).body(rest);
		}
	}

	@DeleteMapping("/dish/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable(name = "id") Long id) {
		Optional<Dish> entity = dishService.getOneDish(id);
		if (entity.isPresent()) {
			dishService.deleteDish(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	private boolean isValidCategory(String category) {
		if ("APPETIZER".equals(category) || "FIRST".equals(category) || "SECOND".equals(category) || "DESSERT".equals(category)) {
			return true;
		}
		return false;
	}

}

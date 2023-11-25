package com.myfood.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Dish;
import com.myfood.services.DishServiceImpl;

/**
 * Controller class for handling dish-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on dishes.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class DishController {

	@Autowired
	private DishServiceImpl dishService;

	/**
	 * Retrieve all dishes.
	 *
	 * @return ResponseEntity containing a list of all dishes.
	 */
	@GetMapping("/dishes")
	public ResponseEntity<List<Dish>> getAllDishes() {
		return ResponseEntity.ok(dishService.getAllDishes());
	}

	@GetMapping("/dishes/visible")
	public ResponseEntity<List<Dish>> getVisibleDishes() {
		List<Dish> visibleDishes = dishService.getAllDishes()
				.stream()
				.filter(Dish::isVisible) // Filtra los platos cuyo estado visible es true
				.collect(Collectors.toList());

		if (!visibleDishes.isEmpty()) {
			return ResponseEntity.ok(visibleDishes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Retrieve a specific dish by its ID.
	 *
	 * @param id The ID of the dish to retrieve.
	 * @return ResponseEntity containing the requested dish or a 404 response if not found.
	 */
	@GetMapping("/dish/{id}")
	public ResponseEntity<Dish> getOneDish(@PathVariable(name = "id") Long id) {
		Optional<Dish> entity = dishService.getOneDish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/dish/byName/{name}")
	public ResponseEntity<Dish> getDishByName(@PathVariable(name = "name") String name) {
		Optional<Dish> entity = dishService.getDishByName(name);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/dishes/visibleByName/{name}")
	public ResponseEntity<List<Dish>> getVisibleDishesByName(@PathVariable(name = "name") String name) {
		List<Dish> visibleDishes = dishService.getDishByName(name)
				.stream()
				.filter(Dish::isVisible) // Filtra los platos cuyo estado visible es true
				.collect(Collectors.toList());

		if (!visibleDishes.isEmpty()) {
			return ResponseEntity.ok(visibleDishes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/dishes/byCategory/{category}")
	public ResponseEntity<List<Dish>> getDishesByCategory(@PathVariable(name = "category") String category) {
		List<Dish> dishes = dishService.getDishesByCategory(category);
		if (!dishes.isEmpty()) {
			return ResponseEntity.ok(dishes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/dishes/visibleByCategory/{category}")
	public ResponseEntity<List<Dish>> getVisibleDishesByCategory(@PathVariable(name = "category") String category) {
		List<Dish> visibleDishes = dishService.getDishesByCategory(category)
				.stream()
				.filter(Dish::isVisible) // Filtra los platos cuyo estado visible es true
				.collect(Collectors.toList());

		if (!visibleDishes.isEmpty()) {
			return ResponseEntity.ok(visibleDishes);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Create a new dish.
	 *
	 * @param entity The dish to be created.
	 * @return ResponseEntity containing the created dish or an error response.
	 */
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

	/**
	 * Update an existing dish.
	 *
	 * @param id The ID of the dish to update.
	 * @param entity The updated dish.
	 * @return ResponseEntity containing the updated dish or an error response.
	 */
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

	@PutMapping("/dish/changeVisibility/{id}")
	public ResponseEntity<?> changeDishVisibility(@PathVariable(name = "id") Long id) {
		Map<String, Object> rest = new HashMap<>();

		Optional<Dish> existingDish = dishService.getOneDish(id);
		if (existingDish.isPresent()) {
			Dish dishToUpdate = existingDish.get();
			dishToUpdate.setVisible(!dishToUpdate.isVisible());  // Cambia el estado visible a su opuesto
			dishService.updateDish(dishToUpdate);
			String visibilityStatus = dishToUpdate.isVisible() ? "visible" : "not visible";
			return ResponseEntity.ok("Dish with ID " + id + " changed as " + visibilityStatus);
		} else {
			rest.put("Error", "Dish with ID " + id + " not found");
			return ResponseEntity.status(404).body(rest);
		}
	}

	/**
	 * Delete a dish.
	 *
	 * @param id The ID of the dish to delete.
	 * @return ResponseEntity indicating success or a 404 response if the dish is not found.
	 */
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

	/**
	 * Check if the provided category is valid.
	 *
	 * @param category The category to validate.
	 * @return true if the category is valid, false otherwise.
	 */
	private boolean isValidCategory(String category) {
		String[] categoriesValid = {"APPETIZER", "FIRST", "SECOND", "DESSERT"};
		return Arrays.asList(categoriesValid).contains(category);
	}
}
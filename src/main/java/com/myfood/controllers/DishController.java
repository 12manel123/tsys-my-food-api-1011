package com.myfood.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/dishes")
    public ResponseEntity<Page<Dish>> getAllDishes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Dish> dishPage = dishService.getAllDishesWithPagination(pageable);
        return ResponseEntity.ok(dishPage);
    }
	
	
	@GetMapping("/dishes/visible")
    public ResponseEntity<Page<Dish>> getVisibleDishes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Dish> allDishes = dishService.getAllDishes();
        List<Dish> visibleDishes = allDishes.stream()
                .filter(Dish::isVisible)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), visibleDishes.size());
        List<Dish> paginatedDishes = visibleDishes.subList(start, end);

        Page<Dish> dishPage = new PageImpl<>(paginatedDishes, pageable, visibleDishes.size());

        if (!paginatedDishes.isEmpty()) {
            return ResponseEntity.ok(dishPage);
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
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/dish/{id}")
	public ResponseEntity<Dish> getOneDish(@PathVariable(name = "id") Long id) {
		Optional<Dish> entity = dishService.getOneDish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Retrieve dishes by name, considering case-insensitive matching.
	 *
	 * @param name The name of the dishes to retrieve.
	 * @return ResponseEntity containing a list of dishes matching the provided name or a 404 response if none are found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/dish/byName/{name}")
	public ResponseEntity<List<Dish>> getDishByName(@PathVariable(name = "name") String name) {
		List<Dish> allDishes = dishService.getAllDishes();
		List<Dish> filteredDishes = new ArrayList<>();
		for (Dish dish : allDishes) {
			if (dish.getName().toLowerCase().contains(name.toLowerCase())) {
				filteredDishes.add(dish);
	        }
		}
		return ResponseEntity.ok(filteredDishes);
	}
	
	/**
	 * Retrieve visible dishes by name, considering case-insensitive matching.
	 *
	 * @param name The name of the dishes to retrieve.
	 * @return ResponseEntity containing a list of visible dishes matching the provided name or a 404 response if none are found.
	 */
	@GetMapping("/dish/visibleByName/{name}")
	public ResponseEntity<List<Dish>> getDishByNameVisible(@PathVariable(name = "name") String name) {
		List<Dish> allDishes = dishService.getAllDishes()
				.stream()
				.filter(Dish::isVisible)
				.collect(Collectors.toList());;
		List<Dish> filteredDishes = new ArrayList<>();
		for (Dish dish : allDishes) {
			if (dish.getName().toLowerCase().contains(name.toLowerCase())) {
				filteredDishes.add(dish);
	        }
		}
		return ResponseEntity.ok(filteredDishes);
	}

	/**
	 * Retrieve dishes by category.
	 *
	 * @param category The category of dishes to retrieve.
	 * @return ResponseEntity containing a list of dishes in the specified category or a 404 response if none are found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/dishes/byCategory/{category}")
	public ResponseEntity<Page<Dish>> getDishesByCategory(
	        @PathVariable(name = "category") String category,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Pageable pageable = PageRequest.of(page, size);
	    List<Dish> dishes = dishService.getDishesByCategory(category);

	    int start = (int) pageable.getOffset();
	    int end = Math.min((start + pageable.getPageSize()), dishes.size());
	    List<Dish> paginatedDishes = dishes.subList(start, end);

	    Page<Dish> dishPage = new PageImpl<>(paginatedDishes, pageable, dishes.size());

	    return ResponseEntity.ok(dishPage);
	}


	/**
	 * Retrieve visible dishes by category.
	 *
	 * @param category The category of dishes to retrieve.
	 * @return ResponseEntity containing a list of visible dishes in the specified category or a 404 response if none are found.
	 */
	@GetMapping("/dishes/visibleByCategory/{category}")
	public ResponseEntity<Page<Dish>> getVisibleDishesByCategory(
	        @PathVariable(name = "category") String category,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    List<Dish> allDishes = dishService.getDishesByCategory(category);

	    List<Dish> visibleDishes = allDishes.stream()
	            .filter(Dish::isVisible)
	            .collect(Collectors.toList());

	    Pageable pageable = PageRequest.of(page, size);
	    int start = (int) pageable.getOffset();
	    int end = Math.min((start + pageable.getPageSize()), visibleDishes.size());
	    List<Dish> paginatedDishes = visibleDishes.subList(start, end);

	    Page<Dish> dishPage = new PageImpl<>(paginatedDishes, pageable, visibleDishes.size());

	    return paginatedDishes.isEmpty()
	            ? ResponseEntity.notFound().build()
	            : ResponseEntity.ok(dishPage);
	}



	/**
	 * Create a new dish.
	 *
	 * @param entity The dish to be created.
	 * @return ResponseEntity containing the created dish or an error response.
	 */
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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

	/**
	 * Change the visibility status of a dish.
	 *
	 * @param id The ID of the dish to update.
	 * @return ResponseEntity indicating the success of the visibility change or an error response if the dish is not found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
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
package com.myfood.controllers;

import java.util.Arrays;
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


/**
 * Controller class for handling attribute-dish-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on attribute-dish relationships.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class Atribut_DishController {

	@Autowired
	private IAtribut_DishService atribut_DishService;
	@Autowired
	private IDishService dishService;
	
	/**
     * Retrieve all attribute-dish relationships.
     *
     * @return ResponseEntity containing a list of all attribute-dish relationships.
     */
	@GetMapping("/atributs")
	public ResponseEntity<List<Atribut_Dish>> getAllAtribut_Dishes() {
		return ResponseEntity.ok(atribut_DishService.getAllAtribut_Dishes());
	}
	
	/**
     * Retrieve a specific attribute-dish relationship by its ID.
     *
     * @param id The ID of the attribute-dish relationship to retrieve.
     * @return ResponseEntity containing the requested attribute-dish relationship or a 404 response if not found.
     */
	@GetMapping("/atribut/{id}")
	public ResponseEntity<Atribut_Dish> getOneAtribut_Dish(@PathVariable(name = "id") Long id) {
		Optional<Atribut_Dish> entity = atribut_DishService.getOneAtribut_Dish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}        
	}

	/**
     * Create a new attribute-dish relationship.
     *
     * @param id The ID of the dish to associate with the attribute.
     * @param entity The attribute-dish relationship to be created.
     * @return ResponseEntity containing the created attribute-dish relationship or an error response.
     */
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
	
	/**
     * Update an existing attribute-dish relationship.
     *
     * @param id The ID of the dish associated with the attribute.
     * @param entity The updated attribute-dish relationship.
     * @return ResponseEntity containing the updated attribute-dish relationship or an error response.
     */
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
	
	/**
     * Delete an attribute-dish relationship.
     *
     * @param id The ID of the attribute-dish relationship to delete.
     * @return ResponseEntity indicating success or a 404 response if the relationship is not found.
     */
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
	
	/**
     * Check if the provided attribute is valid.
     *
     * @param atribut The attribute to validate.
     * @return true if the attribute is valid, false otherwise.
     */
	private boolean isValidAtribut(String atribut) {
		 String[] atributesValid = {"CELIAC", "LACTOSE", "VEGAN", "VEGETARIAN", "NUTS"};
		 return Arrays.asList(atributesValid).contains(atribut);
	}

}

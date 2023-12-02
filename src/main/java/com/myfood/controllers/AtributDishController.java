package com.myfood.controllers;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Atribut_Dish;
import com.myfood.dto.Dish;
import com.myfood.services.Atribut_DishServiceImpl;
import com.myfood.services.DishServiceImpl;


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
public class AtributDishController {

	@Autowired
	private Atribut_DishServiceImpl atribut_DishService;
	
	@Autowired
	private DishServiceImpl dishService;
	
	/**
     * Retrieve all attribute-dish relationships.
     *
     * @return ResponseEntity containing a list of all attribute-dish relationships.
     */
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')") 
	@GetMapping("/atribut/{id}")
	public ResponseEntity<Atribut_Dish> getOneAtribut_Dish(@PathVariable(name = "id") Long id) {
		Optional<Atribut_Dish> entity = atribut_DishService.getOneAtribut_Dish(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}        
	}
	

    @PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/atribut/ByAtribute/{atributes}")
	public ResponseEntity<Page<Atribut_Dish>> getAllAttributes(
	        @PathVariable(name = "atributes") String attribute,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    PageRequest pageable = PageRequest.of(page, size);
	    List<Atribut_Dish> attributes = atribut_DishService.getAtributByAtributes(attribute);

	    int start = (int) pageable.getOffset();
	    int end = Math.min((start + pageable.getPageSize()), attributes.size());
	    List<Atribut_Dish> paginatedAttributes = attributes.subList(start, end);

	    Page<Atribut_Dish> atributDishPage = new PageImpl<>(paginatedAttributes, pageable, attributes.size());

	    return ResponseEntity.ok(atributDishPage);
	}


	
	@GetMapping("/atribut/visibleByAtribute/{atributes}")
	public ResponseEntity<Page<Dish>> getAllVisibleAttributes(
	        @PathVariable(name = "atributes") String atribute,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    List<Atribut_Dish> atributDishes = atribut_DishService.getAtributByAtributes(atribute);

	    List<Dish> visibleDishes = atributDishes
	            .stream()
	            .map(Atribut_Dish::getDish)
	            .filter(Dish::isVisible)
	            .collect(Collectors.toList());

	    PageRequest pageable = PageRequest.of(page, size);
	    int start = (int) pageable.getOffset();
	    int end = Math.min((start + pageable.getPageSize()), visibleDishes.size());
	    List<Dish> paginatedDishes = visibleDishes.subList(start, end);

	    Page<Dish> dishPage = new PageImpl<>(paginatedDishes, pageable, visibleDishes.size());

	    return paginatedDishes.isEmpty()
	            ? ResponseEntity.notFound().build()
	            : ResponseEntity.ok(dishPage);
	}

	/**
     * Create a new attribute-dish relationship.
     *
     * @param id The ID of the dish to associate with the attribute.
     * @param entity The attribute-dish relationship to be created.
     * @return ResponseEntity containing the created attribute-dish relationship or an error response.
     */
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/atribut/{id}")
    // OK
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



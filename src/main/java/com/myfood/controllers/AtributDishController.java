package com.myfood.controllers;

import java.util.Arrays;
import java.util.Collections;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.transaction.Transactional;


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
	 * Retrieve a paginated list of all attribute-dish relationships.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param page The page number for pagination (default is 0).
	 * @param size The size of each page for pagination (default is 10).
	 * @return ResponseEntity containing a paginated list of attribute-dish relationships or a not found response if no relationships are found.
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/atributs")
	public ResponseEntity<Page<Atribut_Dish>> getAllAtribut_Dishes(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	    PageRequest pageable = PageRequest.of(page, size);
	    Page<Atribut_Dish> atributDishes = atribut_DishService.getAllAtribut_Dishes(pageable);

	    if (atributDishes.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    return ResponseEntity.ok(atributDishes);
	}

	/**
	 * Retrieve a single attribute-dish relationship by ID.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param id The ID of the attribute-dish relationship to retrieve.
	 * @param page The page number for pagination (default is 0).
	 * @param size The size of each page for pagination (default is 1).
	 * @return ResponseEntity containing the requested attribute-dish relationship or a not found response if the relationship is not found.
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/atribut/{id}")
	public ResponseEntity<Page<Atribut_Dish>> getOneAtribut_Dish(
	        @PathVariable(name = "id") Long id,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "1") int size) {
		PageRequest pageable = PageRequest.of(page, size);

	    Optional<Atribut_Dish> entity = atribut_DishService.getOneAtribut_Dish(id);
	    
	    if (entity.isPresent()) {
	        List<Atribut_Dish> list = Collections.singletonList(entity.get());
	        Page<Atribut_Dish> pageResult = new PageImpl<>(list, pageable, list.size());
	        return ResponseEntity.ok(pageResult);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	/**
	 * Retrieve attribute-dish relationships by the specified attributes.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param attributes The attributes to filter by. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS.
	 * @param page The page number for pagination (default is 0).
	 * @param size The size of each page for pagination (default is 10).
	 * @return ResponseEntity containing a paginated list of attribute-dish relationships filtered by the specified attributes, or an error response if the attributes are invalid.
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/atribut/ByName/{attributes}")
    public ResponseEntity<?> getAllAttributes(
            @PathVariable(name = "attributes") String attributes,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> rest = new HashMap<>();
        if (!isValidAtribut(attributes)) {
            rest.put("Error","Invalid attribute. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS");
            return ResponseEntity.status(400).body(rest);
        }

        PageRequest pageable = PageRequest.of(page, size);
        List<Atribut_Dish> atributDishes = atribut_DishService.getAtributsByAttributes(attributes);

        if (atributDishes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), atributDishes.size());

        List<Atribut_Dish> paginatedAttributes = atributDishes.subList(start, end);
        Page<Atribut_Dish> atributDishPage = new PageImpl<>(paginatedAttributes, pageable, atributDishes.size());

        return ResponseEntity.ok(atributDishPage);
    }

	/**
	 * Retrieve dishes filtered by the specified attribute, considering only visible dishes.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param atribut The attribute to filter dishes by. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS.
	 * @param page The page number for pagination (default is 0).
	 * @param size The size of each page for pagination (default is 10).
	 * @return ResponseEntity containing a paginated list of visible dishes filtered by the specified attribute, or an error response if the attribute is invalid.
	 */
	@Operation(summary = "Endpoint USER", security = @SecurityRequirement(name = "bearerAuth"))
	@GetMapping("/atribut/visible/{atribut}/dishes")
	public ResponseEntity<?> getDishesByAtributVisible(
	        @PathVariable(name = "atribut") String atribut,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    if (isValidAtribut(atribut)) {
	        PageRequest pageable = PageRequest.of(page, size);

	        List<Dish> relatedDishes = atribut_DishService.getDishesByAtribut(atribut);

	        if (!relatedDishes.isEmpty()) {
	            int start = (int) pageable.getOffset();
	            int end = Math.min((start + pageable.getPageSize()), relatedDishes.size());

	            List<Dish> paginatedDishes = relatedDishes.subList(start, end);
	            Page<Dish> dishPage = new PageImpl<>(paginatedDishes, pageable, relatedDishes.size());

	            return ResponseEntity.ok(dishPage);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    } else {
	        return ResponseEntity.badRequest().body("Error,Invalid attribute. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS");
	    }
	}
	
	/**
	 * Add an existing attribute-dish relationship to a dish.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param dishId The ID of the dish to associate with the attribute-dish relationship.
	 * @param atributDishId The ID of the attribute-dish relationship to associate with the dish.
	 * @return ResponseEntity indicating success or a not found response if either the dish or attribute-dish relationship is not found.
	 */
	  @Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @PostMapping("/atribut/{atributDishId}/dish/{dishId}")
    public ResponseEntity<?> addAtributDishToDish(
            @PathVariable(name = "dishId") Long dishId,
            @PathVariable(name = "atributDishId") Long atributDishId) {

        Optional<Dish> dish = dishService.getOneDish(dishId);
        Optional<Atribut_Dish> atributDish = atribut_DishService.getOneAtribut_Dish(atributDishId);

        if (dish.isPresent() && atributDish.isPresent()) {
           
            dish.get().getAtribut_dish().add(atributDish.get());
            dishService.updateDish(dish.get());

            return ResponseEntity.ok("The Atribut_Dish added to Dish successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
    /**
		 * Update an existing attribute-dish relationship associated with a dish.
		 *
		 * This endpoint requires ADMIN role for access.
		 *
		 * @param id The ID of the dish associated with the attribute.
		 * @param entity The updated attribute-dish relationship.
		 * @return ResponseEntity containing the updated attribute-dish relationship or an error response if the attribute is invalid.
		 */
		@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	  @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/atribut/{dish_id}")
    public ResponseEntity<?> updateAtribut_Dish(@PathVariable(name = "dish_id") Long id, @RequestBody Atribut_Dish entity) {
        Map<String, Object> rest = new HashMap<>();
        Optional<Dish> dish = dishService.getOneDish(id);

        if (dish.isPresent()) {
            entity.setDishes(Collections.singletonList(dish.get()));
        }
        if (isValidAtribut(entity.getAttributes())) {
            return ResponseEntity.status(201).body(atribut_DishService.updateAtribut_Dish(entity));
        } else {
            rest.put("Error", "Invalid attribute. Accepted values are: CELIAC, LACTOSE, VEGAN, VEGETARIAN, NUTS");
            return ResponseEntity.status(400).body(rest);
        }
    }
	
	/**
	 * Delete the relationship between an attribute and a dish.
	 *
	 * This endpoint requires ADMIN role for access.
	 *
	 * @param atributId The ID of the attribute-dish relationship to delete.
	 * @param dishId The ID of the dish associated with the attribute-dish relationship.
	 * @return ResponseEntity indicating success or a not found response if either the attribute-dish relationship or the dish is not found.
	 */
	@Operation(summary = "Endpoint ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/atribut/{atributId}/dish/{dishId}")
    public ResponseEntity<?> deleteAtributDishRelation(
            @PathVariable(name = "atributId") Long atributId,
            @PathVariable(name = "dishId") Long dishId) {

        Optional<Atribut_Dish> atributDish = atribut_DishService.getOneAtribut_Dish(atributId);
        Optional<Dish> dish = dishService.getOneDish(dishId);

        if (atributDish.isPresent() && dish.isPresent()) {

            dish.get().getAtribut_dish().remove(atributDish.get());
            dishService.updateDish(dish.get());

            return ResponseEntity.ok("Relationship between Attribute and Dish deleted successfully.");
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }
	
	private boolean isValidAtribut(String atribut) {
		String[] atributesValid = {"CELIAC", "LACTOSE", "VEGAN", "VEGETARIAN", "NUTS"};
		return Arrays.asList(atributesValid).contains(atribut);
	}
}



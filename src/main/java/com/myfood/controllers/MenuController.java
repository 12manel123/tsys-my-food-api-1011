package com.myfood.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Dish;
import com.myfood.dto.Menu;
import com.myfood.services.MenuServiceImpl;

/**
 * Controller class for handling menu-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on menus.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 *                 @RequestMapping("/api/v1") Base mapping for all endpoints in
 *                 this controller.
 */
@RestController
@RequestMapping("api/v1")
public class MenuController {

	@Autowired
	private MenuServiceImpl menuService;

	/**
	 * Retrieve all menus.
	 *
	 * @return ResponseEntity containing a list of all menus.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/menus")
	public ResponseEntity<Page<Menu>> getAllMenus(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Menu> menuPage = menuService.getAllMenuWithPagination(pageable);
		return ResponseEntity.ok(menuPage);
	}

	@GetMapping("/allVisibleMenus")
	public ResponseEntity<List<Menu>> getAllVisibleMenus() {
		List<Menu> visibleMenus = menuService.getAllMenus()
				.stream()
				.filter(this::isMenuVisible)
				.collect(Collectors.toList());

		return visibleMenus.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(visibleMenus);
	}

	//TODO test in postman
	private boolean isMenuVisible(Menu menu) {
	    boolean isMenuVisible = menu.isVisible();
	    boolean areAllDishesVisible = areAllDishesVisible(Arrays.asList(menu.getAppetizer(), menu.getFirst(), menu.getSecond(), menu.getDessert()));

	    return isMenuVisible && areAllDishesVisible;
	}
	//TODO test in postman
	private boolean areAllDishesVisible(List<Dish> dishes) {
	    for (Dish dish : dishes) {
	        if (dish == null || !dish.isVisible()) {
	            return false;
	        }
	    }
	    return true;
	}

	/**
	 * Retrieve a specific menu by its ID.
	 *
	 * @param id The ID of the menu to retrieve.
	 * @return ResponseEntity containing the requested menu or a 404 response if not
	 *         found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/menu/{id}")
	public ResponseEntity<Menu> getOneMenu(@PathVariable(name = "id") Long id) {
		Optional<Menu> entity = menuService.getOneMenu(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Create a new menu.
	 *
	 * @param entity The menu to be created.
	 * @return ResponseEntity containing the created menu.
	 */
	@PostMapping("/menu")
	public ResponseEntity<Menu> saveMenu(@RequestBody Menu entity) {
		return ResponseEntity.ok(menuService.createMenu(entity));
	}

	/**
	 * Update an existing menu.
	 *
	 * @param id     The ID of the menu to update.
	 * @param entity The updated menu.
	 * @return ResponseEntity containing the updated menu or a 404 response if not
	 *         found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/menu/{id}")
	public ResponseEntity<Menu> updateMenu(@PathVariable(name = "id") Long id, @RequestBody Menu entity) {
		
		Optional<Menu> entityOld = menuService.getOneMenu(id);
	
		if (entityOld.isPresent()) {	
			entityOld.get().setId(id);
			entityOld.get().setAppetizer(entity.getAppetizer());
			entityOld.get().setFirst(entity.getFirst());
			entityOld.get().setSecond(entity.getSecond());
			entityOld.get().setDessert(entity.getDessert());
			entityOld.get().setVisible(entity.isVisible());		
			return ResponseEntity.ok(menuService.updateMenu(entityOld.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Change the visibility status of a menu.
	 *
	 * @param id The ID of the menu to update.
	 * @return ResponseEntity indicating success or a 404 response if the menu is
	 *         not found.
	 */
	@PutMapping("/menu/changeVisibility/{id}")
	public ResponseEntity<?> toggleMenuVisibility(@PathVariable(name = "id") Long id) {
		Optional<Menu> existingMenu = menuService.getOneMenu(id);

		if (existingMenu.isPresent()) {

			Menu menuToUpdate = existingMenu.get();
			menuToUpdate.setVisible(!menuToUpdate.isVisible());
			menuService.updateMenu(menuToUpdate);
			String visibilityStatus = menuToUpdate.isVisible() ? "visible" : "not visible";
			return ResponseEntity.ok("Menu visibility status with ID " + id + " changed to " + visibilityStatus);

		} else {

			Map<String, Object> rest = new HashMap<>();
			rest.put("Error", "Menu with id " + id + " not found");
			return ResponseEntity.status(404).body(rest);
		}
	}

	/**
	 * Delete a menu.
	 *
	 * @param id The ID of the menu to delete.
	 * @return ResponseEntity indicating success or a 404 response if the menu is
	 *         not found.
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<?> deleteMenu(@PathVariable(name = "id") Long id) {
		Optional<Menu> entity = menuService.getOneMenu(id);
		if (entity.isPresent()) {
			menuService.deleteMenu(id);
			return ResponseEntity.ok("The menu with "+id+", is deleted");		
			} 
		else {
			return ResponseEntity.notFound().build();
		}
	}
}

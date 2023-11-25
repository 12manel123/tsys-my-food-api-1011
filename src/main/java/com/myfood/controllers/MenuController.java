package com.myfood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Menu;
import com.myfood.services.MenuServiceImpl;

/**
 * Controller class for handling menu-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on menus.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
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
	@GetMapping("/menus")
	public ResponseEntity<List<Menu>> getAllMenus() {
		return ResponseEntity.ok(menuService.getAllMenus());
	}

	/**
     * Retrieve a specific menu by its ID.
     *
     * @param id The ID of the menu to retrieve.
     * @return ResponseEntity containing the requested menu or a 404 response if not found.
     */
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
     * @param id The ID of the menu to update.
     * @param entity The updated menu.
     * @return ResponseEntity containing the updated menu or a 404 response if not found.
     */
	@PutMapping("/menu/{id}")
	public ResponseEntity<Menu> updateMenu(@PathVariable(name = "id") Long id, @RequestBody Menu entity) {
		Optional<Menu> entityOld = menuService.getOneMenu(id);
		if (entityOld.isPresent()) {
			entity.setId(id);
			return ResponseEntity.ok(menuService.updateMenu(entity));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
     * Delete a menu.
     *
     * @param id The ID of the menu to delete.
     * @return ResponseEntity indicating success or a 404 response if the menu is not found.
     */
	@DeleteMapping("/menu/{id}")
	public ResponseEntity<Void> deleteMenu(@PathVariable(name = "id") Long id) {
		Optional<Menu> entity = menuService.getOneMenu(id);
		if (entity.isPresent()) {
			menuService.deleteMenu(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}

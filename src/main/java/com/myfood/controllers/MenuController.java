package com.myfood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myfood.dto.Menu;
import com.myfood.services.IMenuService;


@RestController
@RequestMapping("api/v1")
public class MenuController {

	@Autowired
	private IMenuService menuService;

	@GetMapping("/menus")
	public ResponseEntity<List<Menu>> getAllMenus() {
		return ResponseEntity.ok(menuService.getAllMenus());
	}

	@GetMapping("/menu/{id}")
	public ResponseEntity<Menu> getOneMenu(@PathVariable(name = "id") Long id) {
		Optional<Menu> entity = menuService.getOneMenu(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/menu")
	public ResponseEntity<Menu> saveMenu(@RequestBody Menu entity) {
		return ResponseEntity.ok(menuService.createMenu(entity));
	}

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

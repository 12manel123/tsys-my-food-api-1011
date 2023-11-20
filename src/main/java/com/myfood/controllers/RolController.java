package com.myfood.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfood.dto.Role;
import com.myfood.services.IRolService;

@RestController
@RequestMapping("api/v1")
public class RolController {

	@Autowired
	private IRolService roleServ;
	
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRole() {
		return ResponseEntity.ok(roleServ.getAllRoles());
	}
	
	@GetMapping("/role/{id}")
	public ResponseEntity<Role> getOneRole(@PathVariable(name = "id") Long id) { 
		Optional<Role> entity = roleServ.getOneRole(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/role")
	public ResponseEntity<Role> saveRole(@RequestBody Role entity) {
		return ResponseEntity.ok(roleServ.createRole(entity));
	}
	
	@PutMapping("/role/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable(name = "id") Long id, @RequestBody Role entity) {
		Optional<Role> entityOld = roleServ.getOneRole(id);
		if (entityOld.isPresent()) {
			entity.setId(id);
			return ResponseEntity.ok(roleServ.updateRole(entity));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/role/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable(name = "id") Long id) { 
		Optional<Role> entity = roleServ.getOneRole(id);
		if (entity.isPresent()) {
			roleServ.deleteRole(id);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}

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
	
	/**
	* Handles HTTP GET requests to retrieve a list of all roles.
	*
	* @return ResponseEntity with a list of roles and an HTTP status code.
	*/
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRole() {
		return ResponseEntity.ok(roleServ.getAllRoles());
	}
	
	/**
    * Handles HTTP GET requests to retrieve a single role by ID.
    *
    * @param id The ID of the role to retrieve.
    * @return ResponseEntity with a role or an error message along with an HTTP status code.
    */
	@GetMapping("/role/{id}")
	public ResponseEntity<Role> getOneRole(@PathVariable(name = "id") Long id) { 
		Optional<Role> entity = roleServ.getOneRole(id);
		if (entity.isPresent()) {
			return ResponseEntity.ok(entity.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
    * Handles HTTP POST requests to create a new role.
    *
    * @param entity The Role object to be created.
    * @return ResponseEntity with a created role or an error message along with an HTTP status code.
    */
	@PostMapping("/role")
	public ResponseEntity<Role> saveRole(@RequestBody Role entity) {
		return ResponseEntity.ok(roleServ.createRole(entity));
	}
	
	/**
    * Handles HTTP PUT requests to update an existing role.
    *
    * @param id     The ID of the role to update.
    * @param entity The updated Role object.
    * @return ResponseEntity with an updated role or an error message along with an HTTP status code.
    */
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
	
	/**
    * Handles HTTP DELETE requests to delete a role by ID.
    *
    * @param id The ID of the role to delete.
    * @return ResponseEntity with no content or an error message along with an HTTP status code.
    */
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

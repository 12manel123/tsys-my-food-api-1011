package com.myfood.controllers;
/**
 * @author David Maza
 *
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myfood.dto.Role;
import com.myfood.services.RolServiceImpl;

/**
 * Controller class for handling role-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on roles.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
 */
@RestController
@RequestMapping("api/v1")
public class RolController {

	@Autowired
	private RolServiceImpl roleServ;
	
	/**
	* Handles HTTP GET requests to retrieve a list of all roles.
	*
	* @return ResponseEntity with a list of roles and an HTTP status code.
	*/
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/roles")
	public ResponseEntity<List<Role>> getAllRole() {
		return ResponseEntity.ok(roleServ.getAllRoles());
	}
	
	/**
     * Retrieve a specific role by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return ResponseEntity containing the requested role or a 404 response if not found.
     */
	@PreAuthorize("hasRole('ADMIN')")
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
     * Create a new role.
     *
     * @param entity The role to be created.
     * @return ResponseEntity containing the created role.
     */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/role")
	public ResponseEntity<?> saveRole(@RequestBody Role entity) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		
		return roleServ.isValidRole(entity.getName())
		 ? ResponseEntity.ok(roleServ.createRole(entity))
		 : ResponseEntity.status(400).body(responseData.put("Error", "The 'Role' field only accepts the strings 'USER' , 'CHEF' or 'ADMIN'"));
		
	}
	
	/**
     * Update an existing role.
     *
     * @param id The ID of the role to update.
     * @param entity The updated role.
     * @return ResponseEntity containing the updated role or a 404 response if not found.
     */
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/role/{id}")
	public ResponseEntity<Role> updateRole(@PathVariable(name = "id") Long id, @RequestBody Role entity) {
		Optional<Role> entityOld = roleServ.getOneRole(id);
		if (entityOld.isPresent()&& roleServ.isValidRole(entity.getName())) {
			entity.setId(id);
			return ResponseEntity.ok(roleServ.updateRole(entity));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
     * Delete a role.
     *
     * @param id The ID of the role to delete.
     * @return ResponseEntity indicating success or a 404 response if the role is not found.
     */
	@PreAuthorize("hasRole('ADMIN')")
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

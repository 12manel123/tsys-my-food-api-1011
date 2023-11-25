package com.myfood.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.myfood.dto.User;
import com.myfood.dto.UserDTO;
import com.myfood.services.RolServiceImpl;
import com.myfood.services.UserServiceImpl;

/**
 * Controller class for handling user-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on users.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 *                 @RequestMapping("/api/v1") Base mapping for all endpoints in
 *                 this controller.
 */
@RestController
@RequestMapping("api/v1")
public class UserController {

	@Autowired
	private UserServiceImpl userServ;
	
	@Autowired 
	private RolServiceImpl roleService;

	/**
	 * Retrieve all users with simplified DTO representation.
	 *
	 * @return ResponseEntity containing a list of UserDTO representing all users.
	 */
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUser() {
		List<User> userList = userServ.getAllUser();

		List<UserDTO> userListDTO = userList.stream().map(user -> new UserDTO.Builder().setId(user.getId())
				.setEmail(user.getEmail()).setUsername(user.getUsername()).setRole(user.getRole()).build()).toList();

		return ResponseEntity.ok(userListDTO);
	}

	/**
	 * Retrieve a specific user by its ID with simplified DTO representation.
	 *
	 * @param id The ID of the user to retrieve.
	 * @return ResponseEntity containing the requested UserDTO or a 404 response if
	 *         not found.
	 */
	@GetMapping("/user/{id}")
	public ResponseEntity<UserDTO> getOneUser(@PathVariable(name = "id") Long id) {

		Optional<User> entity = userServ.getOneUser(id);
		if (entity.isPresent()) {

			UserDTO userListDTO = new UserDTO.Builder().setId(entity.get().getId()).setEmail(entity.get().getEmail())
					.setUsername(entity.get().getUsername()).setRole(entity.get().getRole()).build();

			return ResponseEntity.ok(userListDTO);

		} else {

			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Create a new user. Assign the default role 'USER' if available.
	 *
	 * @param entity The user to be created.
	 * @return ResponseEntity indicating success or an error response.
	 */
	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody User entity) {
		Map<String, Object> responseData = new HashMap<String, Object>();

		Role defaultRole = roleService.findByName("USER");

		if (defaultRole != null) {
			entity.setRole(defaultRole);
			userServ.createUser(entity);

			responseData.put("created user", entity.getUsername());

			return ResponseEntity.ok(responseData);
		}
		responseData.put("message", "you must first create the USER role before adding a user");
		return ResponseEntity.status(400).body(responseData);
	}

	/**
	 * Update an existing user.
	 *
	 * @param id     The ID of the user to update.
	 * @param entity The updated user.
	 * @return ResponseEntity indicating success or a 404 response if not found.
	 */
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id, @RequestBody User entity) {
		Optional<User> entityOld = userServ.getOneUser(id);

		if (entityOld.isPresent()) {
			entity.setId(id);
			userServ.updateUser(entity);
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("updated user", entity.getUsername());
			return ResponseEntity.ok(responseData);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Delete a user.
	 *
	 * @param id The ID of the user to delete.
	 * @return ResponseEntity indicating success or a 404 response if the user is
	 *         not found.
	 */
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
    	Map<String, Object> responseData = new HashMap<String, Object>();
    	Optional<User> entity = userServ.getOneUser(id);
        if (entity.isPresent()) {
        	userServ.deleteUser(id);  
        	 responseData.put("Message", "User deleted");
			return ResponseEntity.status(204).body(responseData);
        } else {
        	responseData.put("Message", "The User not exists");
        	return ResponseEntity.badRequest().body(responseData);
        }
    }

}

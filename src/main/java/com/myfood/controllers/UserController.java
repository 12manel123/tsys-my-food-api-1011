package com.myfood.controllers;
/**
 * @author David Maza
 *
 */
import java.time.LocalDateTime;
import java.time.ZoneId;
/**
 * @author Davi Maza
 *
 */
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller class for handling user-related operations.
 *
 * This controller provides endpoints for basic CRUD operations on users.
 *
 * @RestController Indicates that this class is a Spring MVC Controller.
 * @RequestMapping("/api/v1") Base mapping for all endpoints in this controller.
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
	@Operation(summary = "Endpoint for ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/users")
	public ResponseEntity<Page<UserDTO>> getAllUser(
			@PageableDefault(size = 20, page = 0, sort = "id")
			Pageable pageable) {
			
		Page<User> userListPage = userServ.findAllWithPagination(pageable);
		Page<UserDTO> userDTOListPage = userListPage
				.map(user -> new UserDTO.Builder()
						.setId(user.getId()).setEmail(user.getEmail())
						.setUsername(user.getUsername())
						.setRole(user.getRole())
						.setCreatedAt(user.getCreatedAt())
						.setUpdatedAt(user.getUpdatedAt())
						.build());

		return ResponseEntity.ok(userDTOListPage);
	}

	/**
	 * Retrieve a specific user by its ID with simplified DTO representation.
	 *
	 * @param id The ID of the user to retrieve.
	 * @return ResponseEntity containing the requested UserDTO or a 404 response if not found.
	 */
	@Operation(summary = "Endpoint for ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getOneUser(@PathVariable(name = "id") Long id ,  Authentication authentication) {
		   Optional<User> entity = userServ.getOneUser(id);
		
		   UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		    
			 
		    if (!userDetails.getUsername().equals(entity.get().getUsername()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to edit this profile.");
		    }
			

		
		if (entity.isPresent()) {

			UserDTO userListDTO = new UserDTO.Builder()
					.setId(entity.get().getId())
					.setEmail(entity.get().getEmail())
					.setUsername(entity.get().getUsername())
					.setRole(entity.get().getRole())
					.setCreatedAt(entity.get().getCreatedAt())
					.setUpdatedAt(entity.get().getUpdatedAt())
					.build();

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
	@Operation(summary = "Endpoint for ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user")
	public ResponseEntity<?> saveUser(@RequestBody User entity) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		Role defaultRole = roleService.findByName("USER").get();
		if (defaultRole != null) {
			entity.setRole(defaultRole);
			ZoneId madridZone = ZoneId.of("Europe/Madrid");
			entity.setCreatedAt(LocalDateTime.now(madridZone));
			userServ.createUser(entity);
            
			responseData.put("created user", entity.getUsername());

			return ResponseEntity.ok(responseData);
		}
		responseData.put("Error", "you must first create the USER role before adding a user");
		return ResponseEntity.status(400).body(responseData);
	}

	/**
	 * Update an existing user.
	 *
	 * @param id     The ID of the user to update.
	 * @param entity The updated user.
	 * @return ResponseEntity indicating success or a 404 response if not found.
	 */
	@Operation(summary = "Endpoint for ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@PutMapping("/user/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(name = "id") Long id ,@RequestBody User entity , Authentication authentication) {	
		
		Optional<User> entityOld = userServ.getOneUser(id);
		
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    
	 
	    if (!userDetails.getUsername().equals(entityOld.get().getUsername()) && !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to edit this profile.");
	    }
		
		
		
		
		
			
		if (entityOld.isPresent()) {
			
			if(entity.getRole() == null) {
				entity.setRole(entityOld.get().getRole());
			}else {
				String rolName = entity.getRole().getName();
				entity.setRole(roleService.findByName(rolName).get());
			}
			
	
			entity.setPassword(entityOld.get().getPassword());
			entity.setId(id);
			ZoneId madridZone = ZoneId.of("Europe/Madrid");
			entity.setUpdatedAt(LocalDateTime.now(madridZone));
			userServ.updateUser(entity);
	
			// Required attributes
			entity.setUsername(entityOld.get().getUsername());
			entity.setUsername(entityOld.get().getEmail());
			
					
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
	@Operation(summary = "Endpoint for ADMIN", security = @SecurityRequirement(name = "bearerAuth"))
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
		Map<String, Object> responseData = new HashMap<String, Object>();
		Optional<User> entity = userServ.getOneUser(id);
		if (entity.isPresent()) {
			userServ.deleteUser(id);
			responseData.put("Message", "User deleted");
			return ResponseEntity.status(204).body(responseData);
		} else {
			responseData.put("Error", "The User not exists");
			return ResponseEntity.badRequest().body(responseData);
		}
	}

}

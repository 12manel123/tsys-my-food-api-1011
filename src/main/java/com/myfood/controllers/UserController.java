package com.myfood.controllers;

import java.util.*;


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
import com.myfood.services.IRolService;
import com.myfood.services.IUserService;

@RestController
@RequestMapping("api/v1")
public class UserController {

	@Autowired
	private IUserService userServ;
	
	@Autowired 
	private IRolService roleService;

	@GetMapping("/users")

	public ResponseEntity<List<UserDTO>> getAllUser() {
		List<User> userList = userServ.getAllUser();

		List<UserDTO> userListDTO = userList.stream().map(user -> new UserDTO.Builder().setId(user.getId())
				.setEmail(user.getEmail()).setUsername(user.getUsername()).setRole(user.getRole()).build()).toList();

		return ResponseEntity.ok(userListDTO);
	}

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

	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {
		Optional<User> entity = userServ.getOneUser(id);
		if (entity.isPresent()) {
			userServ.deleteUser(id);
			Map<String, Object> responseData = new HashMap<String, Object>();
			responseData.put("delete user:", id);
			return ResponseEntity.status(204).body(responseData);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}

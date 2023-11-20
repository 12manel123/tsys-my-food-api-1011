package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.User;

public interface IUserService {
 
	//CRUD 

	//Read All
	public List<User> getAllUser();

	//Read One
	public Optional<User> getOneUser(Long id); 
	
	//Create
	public User createUser(User entity);

	//Update
	public User updateUser(User entity);

	//Delete
	public void deleteUser(Long id); 
}

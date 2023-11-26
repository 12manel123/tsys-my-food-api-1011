package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.User;

public interface IUserService {
 
	public List<User> getAllUser() ;

	public Optional<User> getOneUser(Long id); 

	public User createUser(User entity);

	public User updateUser(User entity);

	public void deleteUser(Long id); 
	
	

}

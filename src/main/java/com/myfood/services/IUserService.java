package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.User;

public interface IUserService {

	public List<User> getAllUser() throws Exception;
	public Optional<User> getOneUser(Long id) throws Exception;
	public User createUser(User entity) throws Exception;
	public User updateUser(User entity) throws Exception;
	public void deleteUser(Long id)throws Exception;
	public User getUserByEmail(String name)throws Exception;
}

package com.myfood.services;
/**
 * @author David Maza
 *
 */
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myfood.dto.User;

public interface IUserService {
	
	List<User>getAllUsers();
 
	Optional<User> getOneUser(Long id); 

	User createUser(User entity);

	User updateUser(User entity);

	void deleteUser(Long id); 
	
	 Page<User> findAllWithPagination(Pageable pageable);
	
}

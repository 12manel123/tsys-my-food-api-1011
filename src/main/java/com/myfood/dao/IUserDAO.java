package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.User;
@Repository
public interface IUserDAO extends JpaRepository<User, Long>{	
	
	/**
	* Retrieves a user from the system based on the provided email address.
	* This method performs a lookup based on the unique email address associated with each user.
	*
	* @param email The email address of the user to retrieve.
	* @return The {@code User} object corresponding to the provided email address,
	*         or {@code null} if no user is found with the given email.
	*/
	public User getUserByEmail(String name);		
}


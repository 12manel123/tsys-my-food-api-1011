package com.myfood.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.myfood.dto.User;



@Repository
public interface IUserDAO extends JpaRepository<User,Long>{
	   /**
     * Find a user by username.
     *
     * @param username The username of the user.
     * @return The user with the specified username, or null if not found.
     */
    User findByUsername(String username);
	 
}

package com.myfood.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/**
 * @author Davi Maza
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Order;
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
     
    /**
    * Retrieves a page of users based on the provided parameters.
    *
    * @param pageable Object defining pagination and sorting information.
    * @return A page of users based on the specified pagination and sorting criteria.
    */
    Page<User> findAll(Pageable pageable);

	 
}

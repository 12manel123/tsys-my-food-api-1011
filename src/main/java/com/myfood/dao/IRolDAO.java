package com.myfood.dao;
/**
 * @author Davi Maza
 *
 */
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Role;

@Repository
public interface IRolDAO extends JpaRepository<Role,Long> {	
	
     Optional<Role> findByName(String User);
	
}

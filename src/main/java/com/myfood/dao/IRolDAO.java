package com.myfood.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myfood.dto.Role;

@Repository
public interface IRolDAO extends JpaRepository<Role,Long> {
	
	public Role findByName(String User);

}

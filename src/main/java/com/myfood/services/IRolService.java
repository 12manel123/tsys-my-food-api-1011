package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Role;

public interface IRolService {

	//CRUD 

	//Read All
	public List<Role> getAllRoles();
	
	//Read One
	public Optional<Role> getOneRole(Long id);
	
	//Create
	public Role createRole(Role entity);
	
	//Update
	public Role updateRole(Role entity);
	
	//Delete
	public void deleteRole(Long id);
}

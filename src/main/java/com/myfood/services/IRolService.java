package com.myfood.services;

import java.util.List;
import java.util.Optional;

import com.myfood.dto.Role;

public interface IRolService {

	public List<Role> getAllRoles();
	public Optional<Role> getOneRole(Long id);
	public Role createRole(Role entity);
	public Role updateRole(Role entity);
	public void deleteRole(Long id);
}

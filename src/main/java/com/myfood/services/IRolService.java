package com.myfood.services;
/**
 * @author Davi Maza
 *
 */
import java.util.List;
import java.util.Optional;

import com.myfood.dto.Role;

public interface IRolService {

	 List<Role> getAllRoles();
		
	 Optional<Role> getOneRole(Long id);
	
	 Role createRole(Role entity);
	
	 Role updateRole(Role entity);
	
	 void deleteRole(Long id);
	
     Optional<Role> findByName(String User);
}

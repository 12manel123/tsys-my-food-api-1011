package com.myfood.services;
/**
 * @author Davi Maza
 *
 */
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myfood.dao.IRolDAO;
import com.myfood.dto.Role;

@Service
public class RolServiceImpl implements IRolService {

	@Autowired
	private IRolDAO rolDao;

	@Override
	public List<Role> getAllRoles()  {
		return rolDao.findAll();
	}

	@Override
	public Optional<Role> getOneRole(Long id) {
		return rolDao.findById(id);
	}

	@Override
	public Role createRole(Role entity) {
		return rolDao.save(entity);
	}

	@Override
	public Role updateRole(Role entity) {
		return rolDao.save(entity);
	}

	@Override
	public void deleteRole(Long id) {
		rolDao.deleteById(id);
	}

	/**
     * Check if the provided Role is valid.
     *
     * @param category The Role to validate.
     * @return true if the Role is valid, false otherwise.
     */
	public boolean isValidRole(String role) {
		String[] roleValid = {"USER", "CHEF", "ADMIN"};
		 return Arrays.asList(roleValid).contains(role);
	}

	@Override
	public Optional<Role> findByName(String User) {
		return rolDao.findByName(User);
	}
	
}

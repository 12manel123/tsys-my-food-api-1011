package com.myfood.services;

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
	public List<Role> getAllRoles() {
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

}

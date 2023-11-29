package com.myfood.security.service;
/**
 * @author David Maza
 *
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myfood.dao.IUserDAO;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private IUserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		var userEntity = this.userDAO.findByUsername(name);
		if (userEntity ==null) {
			throw new UsernameNotFoundException("User Not Found with user: " + name);
		}
		return UserDetailsImpl.build(userEntity);
	}

}
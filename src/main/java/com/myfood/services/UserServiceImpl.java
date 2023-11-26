package com.myfood.services;
/**
 * @author Davi Maza
 *
 */
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.myfood.dao.IUserDAO;
import com.myfood.dto.User;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDAO userDao;

	@Override
	public List<User> getAllUser() {
		return userDao.findAll();
	}

	@Override
	public Optional<User> getOneUser(Long id){
		return userDao.findById(id);
	}

	@Override
	public User createUser(User entity) {
		return userDao.save(entity);
	}

	@Override
	public User updateUser(User entity) {
		return userDao.save(entity);
	}

	@Override
	public void deleteUser(Long id) {
		userDao.deleteById(id);;
	}

    /**
     * Get a user by username.
     *
     * @param username The username of the user.
     * @return The user with the specified username, or null if not found.
     */
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

	

}

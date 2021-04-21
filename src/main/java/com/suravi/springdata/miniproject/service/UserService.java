package com.suravi.springdata.miniproject.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suravi.springdata.miniproject.errorhandling.ResourceNotFound;
import com.suravi.springdata.miniproject.model.User;
import com.suravi.springdata.miniproject.repos.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public UserRepository getUserRepository() {
		return userRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	/**
	 * This service gets the user by given id.
	 * @param id
	 * @return User Object
	 * @throws ResourceNotFound
	 */
	public User getUserById(int id) throws ResourceNotFound{
		
		 Optional<User> user = Optional.ofNullable(getUserRepository().findById(id));
		 if(user.isPresent()) {
			 return user.get();
		 }
		 else
		 {
			 throw new ResourceNotFound("No such user present");
		 }
	}
	/**
	 * This service gets the user by given username and password.
	 * @param name
	 * @param password
	 * @return User 
	 */
	public User getUserByNameAndPassword(String name, String password) {
		User user = getUserRepository().findByUserNameAndUserPassword(name, password);
		return user;
	}
	/**
	 * This service gets the user by given username.
	 * @param name
	 * @param password
	 * @return User 
	 */
	public User getUserByName(String name) {
		User user = getUserRepository().findByUserName(name);
		return user;
	}
	/**
	 * This service creates the user.
	 * @param name
	 * @param password
	 * @return newUser 
	 */
	public User createUser(User newUser) {
		newUser = getUserRepository().save(newUser);
		return newUser;
	}
	/**
	 * This service updates an existing user.
	 * @param updatedUser
	 * @return newUser
	 */
	public User updateUser(User updatedUser) {
		User newUser = new User();
		newUser.setUserId(updatedUser.getUserId());
		newUser.setUserName(updatedUser.getUserName());
		newUser.setUserPassword(updatedUser.getUserPassword());
		newUser.setUserRole(updatedUser.getUserRole());
		newUser = getUserRepository().save(newUser);
		return newUser;
	}
	/**
	 * This service deletes a user by given id.
	 * @param id
	 * @throws ResourceNotFound
	 */
	public void deleteUser(int id) throws ResourceNotFound {
		Optional<User> user = Optional.ofNullable(getUserRepository().findById(id));
		if(user.isPresent()) {
			getUserRepository().deleteById(id);
		}
		else {
			throw new ResourceNotFound("No such user item to delete.");
		}		
	}

}

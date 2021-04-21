package com.suravi.springdata.miniproject.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.suravi.springdata.miniproject.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findById(int id);
	User findByUserNameAndUserPassword(String userName, String userPassword);
	User findByUserName(String userName);

}

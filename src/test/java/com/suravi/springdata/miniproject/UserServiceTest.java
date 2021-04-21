package com.suravi.springdata.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.suravi.springdata.miniproject.errorhandling.ResourceNotFound;
import com.suravi.springdata.miniproject.model.User;
import com.suravi.springdata.miniproject.repos.UserRepository;
import com.suravi.springdata.miniproject.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserService.class)
public class UserServiceTest {
	
	@InjectMocks
	UserService userService;
	
	@MockBean
	UserRepository userRepository;
	
	@BeforeEach
    public void init() {
        userService.setUserRepository(userRepository);
    }
	
	@Test
	public void testGetUserById() throws ResourceNotFound
	{
		User user = new User();
		user.setUserId(1);
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		
		when(userRepository.findById(Mockito.anyInt())).thenReturn(user);
		
		User returnedUser = userService.getUserById(1);
		assertEquals(1, returnedUser.getUserId());
		assertEquals("name", returnedUser.getUserName());
		assertEquals("pass", returnedUser.getUserPassword());
		assertEquals("role", returnedUser.getUserRole());
	}
	
	@Test
	public void testGetUserByIdNull() throws ResourceNotFound
	{		
		when(userRepository.findById(Mockito.anyInt())).thenReturn(null);		
		User thisUser;
		try {
			// call to the service method
			thisUser = userService.getUserById(1);
		} catch (ResourceNotFound e) {
			assertEquals("No such user present", e.getMessage());
		}
	}
	
	@Test
	public void testGetUserByNameAndPassword() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		
		when(userRepository.findByUserNameAndUserPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(user);
		
		User returnedUser = userService.getUserByNameAndPassword("name", "pass");
		assertEquals(1, returnedUser.getUserId());
		assertEquals("name", returnedUser.getUserName());
		assertEquals("pass", returnedUser.getUserPassword());
		assertEquals("role", returnedUser.getUserRole());
	}	
	
	@Test
	public void testGetUserByName() {
		User user1 = new User();
		user1.setUserId(1);
		user1.setUserName("name");
		user1.setUserPassword("pass");
		user1.setUserRole("role");
		
		when(userRepository.findByUserName(Mockito.anyString())).thenReturn(user1);
		
		User returnedUser = userService.getUserByName("name");
		assertEquals(1, returnedUser.getUserId());
		assertEquals("name", returnedUser.getUserName());
		assertEquals("pass", returnedUser.getUserPassword());
		assertEquals("role", returnedUser.getUserRole());
	}	
	
	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
		User createdUser = userService.createUser(user);
		assertEquals(user.getUserName(), createdUser.getUserName());
	}
	
	@Test
	public void testUpdateUser() throws ResourceNotFound {
		User user = new User();
		user.setUserId(1);
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		
		User anotherUser = new User();
		anotherUser.setUserId(1);
		anotherUser.setUserName("newname");
		anotherUser.setUserPassword("newpass");
		anotherUser.setUserRole("role");
		
		// mocking save method
		when(userRepository.save(Mockito.any(User.class))).thenReturn(anotherUser);
		// calling service update() method
		User updatedUser  = userService.updateUser(user);		
		assertEquals(anotherUser.getUserName(), updatedUser.getUserName());		
	}
	
	@Test
	public void testDeleteDoctor() throws ResourceNotFound{
		// mocking delete method which returns nothing
		doNothing().when(userRepository).deleteById(Mockito.anyInt());
		// call to service method
		userRepository.deleteById(1);
		// verify if the deleteById(integer) was called at least once.
	    verify(userRepository, times(1)).deleteById(1);	    
	}
	
   
}

package com.suravi.springdata.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.suravi.springdata.miniproject.model.User;

@RunWith(SpringRunner.class)
@WebMvcTest(value = User.class)
public class UserModelTest {
	
	@Test
	public void testUser() {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		assertEquals(1, user.getUserId());
		assertEquals("name", user.getUserName());
		assertEquals("pass", user.getUserPassword());
		assertEquals("role", user.getUserRole());
	}
}

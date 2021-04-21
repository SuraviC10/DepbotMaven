package com.suravi.springdata.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suravi.springdata.miniproject.controller.DoctorController;
import com.suravi.springdata.miniproject.controller.UserController;
import com.suravi.springdata.miniproject.model.Doctor;
import com.suravi.springdata.miniproject.model.User;
import com.suravi.springdata.miniproject.repos.UserRepository;
import com.suravi.springdata.miniproject.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@WithMockUser
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private UserRepository userRepository;
	
	@InjectMocks
	UserController userController;
	
	@MockBean
	UserService userService;
	
	String mockedInputJson;
	
	@BeforeEach
	public void setUp()
	{
		userController.setUserService(userService);
	}
	
	@Test
	public void testGetUserById() throws Exception {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		// mocking the service method
		Mockito.when(userService.getUserById(1)).thenReturn(user);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/user/id/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testGetUserByNameAndPassword() throws Exception {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name1");
		user.setUserPassword("pass1");
		user.setUserRole("role");
		// mocking the service method
		Mockito.when(userService.getUserByNameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(user);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/user/name1/pass1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	@Test
	public void testGetUserByNameAndPasswordNull() throws Exception {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name1");
		user.setUserPassword("pass1");
		user.setUserRole("role");
		// mocking the service method
		Mockito.when(userService.getUserByNameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(null);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/user/name1/pass1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();		
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testGetUserByName() throws Exception {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		// mocking the service method
		Mockito.when(userService.getUserByName(Mockito.anyString())).thenReturn(user);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/user/name/name")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	@Test
	public void testCreateUser() throws Exception {
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		Mockito.when(userService.createUser(Mockito.any(User.class))).thenReturn(user);	
		mockedInputJson = this.mapToJson(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/spring-rest-api/user/add").accept(MediaType.APPLICATION_JSON)
				.content(mockedInputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}	
	
	@Test
	public void testUpdateUser() throws Exception{	
		User user = new User();
		user.setUserId(1);	
		user.setUserName("name");
		user.setUserPassword("pass");
		user.setUserRole("role");
		mockedInputJson = this.mapToJson(user);
		Mockito.when(userService.updateUser(Mockito.any(User.class))).thenReturn(user);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/spring-rest-api/user/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mockedInputJson))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteUser() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/spring-rest-api/user/1"))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	
}

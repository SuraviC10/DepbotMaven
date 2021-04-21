package com.suravi.springdata.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import com.suravi.springdata.miniproject.errorhandling.ErrorDetails;
import com.suravi.springdata.miniproject.model.Doctor;
import com.suravi.springdata.miniproject.repos.DoctorRepository;
import com.suravi.springdata.miniproject.service.DoctorService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = DoctorController.class)
@WithMockUser
public class DoctorControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@InjectMocks
	DoctorController doctorController;
	
	@MockBean
    private DoctorRepository doctorRepository;
	
	@MockBean
	DoctorService doctorService;
	
	String mockedInputJson;
	private List<Doctor> doctorsList;
	
	@BeforeEach
	public void setUp()
	{
		doctorController.setDoctorService(doctorService);
	}
	@Test
	public void testGetDoctorById() throws Exception {
		setMockOutput();
		// mocking the service method
		Mockito.when(doctorService.getDoctorById(1)).thenReturn(doctorsList.get(0));
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/id/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}	
	
	@Test
	public void testGetDoctorByIdNull() throws Exception {
		setMockOutput();
		// mocking the service method
		Mockito.when(doctorService.getDoctorById(1)).thenReturn(null);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/id/1")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
	}	
	
	@Test
	public void testCreateDoctor() throws Exception {
		setMockOutput();
		Mockito.when(doctorService.createDoctor(Mockito.any(Doctor.class))).thenReturn(doctorsList.get(0));	
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/spring-rest-api/doctors/add").accept(MediaType.APPLICATION_JSON)
				.content(mockedInputJson).contentType(MediaType.APPLICATION_JSON);
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = mvcResult.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}	
	
	
	@Test
	public void testGetAllDoctors() throws Exception{
		setMockOutput();
		Mockito.when(doctorService.getAllDoctors()).thenReturn(doctorsList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/all")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testGetDoctorBySpec() throws Exception{
		setMockOutput();
		Mockito.when(doctorService.getDoctorBySpecialization("AnySpec")).thenReturn(doctorsList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/spec/ENT")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testGetDoctorBySpecNull() throws Exception {
		setMockOutput();
		// mocking the service method
		Mockito.when(doctorService.getDoctorBySpecialization("ENT")).thenReturn(null);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/spec/ENT")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		//assertEquals("{\"data\":null,\"error\":\"Doctor not found\"}", result.getResponse().getContentAsString());
		assertEquals("", result.getResponse().getContentAsString());
	}	
	
	@Test
	public void testGetDoctorByName() throws Exception{
		setMockOutput();
		Mockito.when(doctorService.getDoctorByName(Mockito.anyString())).thenReturn(doctorsList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/name/suravi")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	@Test
	public void testGetDoctorByNameNull() throws Exception {
		setMockOutput();
		// mocking the service method
		Mockito.when(doctorService.getDoctorByName(Mockito.anyString())).thenReturn(null);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/name/name")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals("", result.getResponse().getContentAsString());
	}	
	@Test
	public void testGetDoctorByClinic() throws Exception{
		setMockOutput();
		Mockito.when(doctorService.getDoctorByClinic(Mockito.anyString())).thenReturn(doctorsList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/clinic/jsr")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	@Test
	public void testGetDoctorByClinicNull() throws Exception {
		setMockOutput();
		// mocking the service method
		Mockito.when(doctorService.getDoctorByName(Mockito.anyString())).thenReturn(null);
		// calling the end points  using mock
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/spring-rest-api/doctors/name/name")
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();		
		assertEquals("", result.getResponse().getContentAsString());
	}	
	@Test
	public void testUpdateDoctor() throws Exception{
		setMockOutput();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/spring-rest-api/doctors/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(mockedInputJson))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testDeleteDoctor() throws Exception{
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/spring-rest-api/doctors/1"))
				.andReturn();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	@Test
	public void testErrorDetails() {
		
		ErrorDetails errorDetails = new ErrorDetails(Date.valueOf("2021-03-24"), "404", "Not found");
		assertEquals("2021-03-24", errorDetails.getTimestamp().toString());
		assertEquals("404", errorDetails.getMessage());
		assertEquals("Not found", errorDetails.getDetails());				      
	}
	private void setMockOutput() throws JsonProcessingException {
		doctorsList = new ArrayList<>();
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(2);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("anotherdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("Cardiology");
		anotherDoctor.setPhone("7890");
		anotherDoctor.setDocFees(500f);
		anotherDoctor.setDocClinicName("Kolkata");
		
		doctorsList.add(doctor);
		doctorsList.add(anotherDoctor);
		mockedInputJson = this.mapToJson(doctor);
    }
	// converts an entity to a json string
	private String mapToJson(Object object) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
	

}

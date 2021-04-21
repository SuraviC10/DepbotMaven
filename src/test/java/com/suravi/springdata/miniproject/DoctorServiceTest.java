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
import com.suravi.springdata.miniproject.model.Doctor;
import com.suravi.springdata.miniproject.repos.DoctorRepository;
import com.suravi.springdata.miniproject.service.DoctorService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = DoctorServiceTest.class)
public class DoctorServiceTest {
	
	@InjectMocks
	DoctorService doctorService;
	
	@MockBean
	DoctorRepository doctorRepository;
	
	@BeforeEach
    public void init() {
        doctorService.setDoctorRepository(doctorRepository);
    }
	@Test
	public void testGetDoctorById() throws ResourceNotFound
	{
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		// mocking findById(integer) method
		when(doctorRepository.findById(1)).thenReturn(doctor);        
		
		// call to the service method
		Doctor returnedDoctor = doctorService.getDoctorById(1);
		
		assertEquals(1, returnedDoctor.getDocID());
		assertEquals("New Doctor", returnedDoctor.getDocName());
		assertEquals("newdoc@yahoo.com", returnedDoctor.getDocMail());
		assertEquals("ENT", returnedDoctor.getDocSpecialization());
		assertEquals("1234", returnedDoctor.getPhone());
		assertEquals(200f, returnedDoctor.getDocFees());
		assertEquals("JSR", returnedDoctor.getDocClinicName());
	}
	
	@Test
	public void testGetDoctorByIdNull()
	{
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		when(doctorRepository.findById(1)).thenReturn(null);        
		
		Doctor thisDoctor;
		try {
			// call to the service method
			thisDoctor = doctorService.getDoctorById(1);
		} catch (ResourceNotFound e) {
			assertEquals("No such Doctor present", e.getMessage());
		}
	}
	
	@Test
	public void testGetDoctorBySpecialization() {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("Skin");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(1);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("newdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("Skin");
		anotherDoctor.setPhone("1234");
		anotherDoctor.setDocFees(200f);
		anotherDoctor.setDocClinicName("JSR");	
		
		List<Doctor> docs = new ArrayList<Doctor>();
		docs.add(doctor);
		docs.add(anotherDoctor);
		
		when(doctorRepository.findByDocSpecializationStartsWith("Skin")).thenReturn(docs);
		List<Doctor> doctors = doctorService.getDoctorBySpecialization("Skin");
		assertEquals(doctor.getDocName(), doctors.get(0).getDocName());
		assertEquals(anotherDoctor.getDocName(), doctors.get(1).getDocName());
	}
	
	@Test
	public void testGetDoctorByName() {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("Skin");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(1);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("newdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("Skin");
		anotherDoctor.setPhone("1234");
		anotherDoctor.setDocFees(200f);
		anotherDoctor.setDocClinicName("JSR");	
		
		List<Doctor> docs = new ArrayList<Doctor>();
		docs.add(doctor);
		docs.add(anotherDoctor);
		
		when(doctorRepository.findByDocNameStartsWith(Mockito.anyString())).thenReturn(docs);
		List<Doctor> doctors = doctorService.getDoctorByName("new");
		assertEquals(doctor.getDocName(), doctors.get(0).getDocName());
		assertEquals(anotherDoctor.getDocName(), doctors.get(1).getDocName());
	}
	
	@Test
	public void testGetDoctorByClinic() {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("Skin");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(1);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("newdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("Skin");
		anotherDoctor.setPhone("1234");
		anotherDoctor.setDocFees(200f);
		anotherDoctor.setDocClinicName("JSR");	
		
		List<Doctor> docs = new ArrayList<Doctor>();
		docs.add(doctor);
		docs.add(anotherDoctor);
		
		when(doctorRepository.findByDocClinicNameStartsWith(Mockito.anyString())).thenReturn(docs);
		List<Doctor> doctors = doctorService.getDoctorByClinic("JSR");
		assertEquals(doctor.getDocName(), doctors.get(0).getDocName());
		assertEquals(anotherDoctor.getDocName(), doctors.get(1).getDocName());
	}
	
	
	@Test
	public void testGetAllDoctors() throws ResourceNotFound
	{
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");	
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(1);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("newdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("ENT");
		anotherDoctor.setPhone("1234");
		anotherDoctor.setDocFees(200f);
		anotherDoctor.setDocClinicName("JSR");	
		
		List<Doctor> docs = new ArrayList<Doctor>();
		docs.add(doctor);
		docs.add(anotherDoctor);
		
		// mocking findAll(integer) method
		when(doctorRepository.findAll()).thenReturn(docs);      
		
		// call to the method service method
		Iterable<Doctor> doctorList = doctorService.getAllDoctors();
		
		long size = doctorList.spliterator().getExactSizeIfKnown();
		assertEquals(docs.size(), size);
	}
	
	@Test
	public void testCreateDoctor() throws ResourceNotFound {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");
		
		// mocking save method
		when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(doctor);
		
		// call the service method
		Doctor createdDoctor = doctorService.createDoctor(doctor);		
		assertEquals(doctor.getDocName(), createdDoctor.getDocName());		
	}
	
	@Test
	public void testUpdateDoctor() throws ResourceNotFound {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");
		
		Doctor anotherDoctor = new Doctor();
		anotherDoctor.setDocID(1);
		anotherDoctor.setDocName("Another Doctor");
		anotherDoctor.setDocMail("newdoc@yahoo.com");
		anotherDoctor.setDocSpecialization("ENT");
		anotherDoctor.setPhone("1234");
		anotherDoctor.setDocFees(200f);
		anotherDoctor.setDocClinicName("JSR");
		
		// mocking save method
		when(doctorRepository.save(Mockito.any(Doctor.class))).thenReturn(anotherDoctor);
		// calling service update() method
		Doctor updatedDoctor = doctorService.updateDoctor(doctor);		
		assertEquals(anotherDoctor.getDocName(), updatedDoctor.getDocName());		
	}
	
	@Test
	public void testDeleteDoctor() throws ResourceNotFound{
		// mocking delete method which returns nothing
		doNothing().when(doctorRepository).deleteById(Mockito.anyInt());
		// call to service method
		doctorRepository.deleteById(1);
		// verify if the deleteById(integer) was called at least once.
	    verify(doctorRepository, times(1)).deleteById(1);	    
	}
	
	
}

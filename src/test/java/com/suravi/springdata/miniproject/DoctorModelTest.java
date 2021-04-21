package com.suravi.springdata.miniproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.suravi.springdata.miniproject.model.Doctor;

@RunWith(SpringRunner.class)
@WebMvcTest(value = Doctor.class)
public class DoctorModelTest {
	
	@Test
	public void testDoctor() {
		Doctor doctor = new Doctor();
		doctor.setDocID(1);
		doctor.setDocName("New Doctor");
		doctor.setDocMail("newdoc@yahoo.com");
		doctor.setDocSpecialization("ENT");
		doctor.setPhone("1234");
		doctor.setDocFees(200f);
		doctor.setDocClinicName("JSR");		
		assertEquals("New Doctor", doctor.getDocName());
		assertEquals("newdoc@yahoo.com", doctor.getDocMail());
		assertEquals("ENT", doctor.getDocSpecialization());
		assertEquals("1234", doctor.getPhone());
		assertEquals(200f, doctor.getDocFees());
		assertEquals("JSR", doctor.getDocClinicName());
	}

}

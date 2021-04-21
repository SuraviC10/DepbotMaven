package com.suravi.springdata.miniproject.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suravi.springdata.miniproject.controller.DoctorController;
import com.suravi.springdata.miniproject.errorhandling.ResourceNotFound;
import com.suravi.springdata.miniproject.loggers.MiniProjectLogger;
import com.suravi.springdata.miniproject.model.Doctor;
import com.suravi.springdata.miniproject.repos.DoctorRepository;

@Service
public class DoctorService {
	
	private Logger logger = MiniProjectLogger.getLogger(DoctorService.class);	
	
	@Autowired
	DoctorRepository doctorRepository;
	
	public DoctorRepository getDoctorRepository() {
		logger.info("Inside Doctor Service.");
		return doctorRepository;
	}
	public void setDoctorRepository(DoctorRepository doctorRepository) {
		this.doctorRepository = doctorRepository;
	}
	/**
	 * This service finds a doctor by given id.
	 * @param id Integer
	 * @return Doctor Object
	 * @throws ResourceNotFound Exception
	 */
	public Doctor getDoctorById(int id) throws ResourceNotFound{
		 Optional<Doctor> doctor = Optional.ofNullable(getDoctorRepository().findById(id));
		 if(doctor.isPresent()) {
			 return doctor.get();
		 }
		 else
		 {
			 throw new ResourceNotFound("No such Doctor present");
		 }
	}
	/**
	 * This service finds all doctors present in the database.
	 * @return List<Doctor>
	 */
	public Iterable<Doctor> getAllDoctors(){
		Iterable<Doctor> doctorList = getDoctorRepository().findAll();
		return doctorList;
	}	
	
	/**
	 * This service creates a new doctor.
	 * @param inDoctor
	 * @return inDoctor , new doctor
	 * @throws ResourceNotFound
	 */
	public Doctor createDoctor(Doctor inDoctor) throws ResourceNotFound
    {
        	inDoctor = getDoctorRepository().save(inDoctor);             
            return inDoctor;
    } 
	/**
	 * This service updates an existing doctor. 
	 * @param inDoctor
	 * @return newDoctor , updated doctor
	 * @throws ResourceNotFound
	 */
	public Doctor updateDoctor(Doctor inDoctor) throws ResourceNotFound{         
        Doctor newDoctor = new Doctor();
        newDoctor.setDocID(inDoctor.getDocID());
        newDoctor.setDocClinicName(inDoctor.getDocClinicName());
        newDoctor.setDocFees(inDoctor.getDocFees());
        newDoctor.setDocMail(inDoctor.getDocMail());
        newDoctor.setDocName(inDoctor.getDocName());
        newDoctor.setDocSpecialization(inDoctor.getDocSpecialization().toUpperCase());
        newDoctor.setPhone(inDoctor.getPhone());
        newDoctor = getDoctorRepository().save(newDoctor);
        return newDoctor; 
	}
	/**
	 * This service deletes a doctor by given id.
	 * @param id
	 * @throws ResourceNotFound
	 */	
	public void deleteDoctor(int id) throws ResourceNotFound{
		Optional<Doctor> doctor = Optional.ofNullable(getDoctorRepository().findById(id));
		if(doctor.isPresent()) {
			getDoctorRepository().deleteById(id);
		}
		else {
			throw new ResourceNotFound("No such doctor item to delete.");
		}		
	}
	/**
	 * This service finds doctors by given specialization.
	 * @param spec
	 * @return List<Doctor>
	 */
	public List<Doctor> getDoctorBySpecialization(String spec) {
		List<Doctor> doctors = getDoctorRepository().findByDocSpecializationStartsWith(spec);
		return doctors;
	}
	/**
	 * This service finds doctors by given name.
	 * @param spec
	 * @return List<Doctor>
	 */
	public List<Doctor> getDoctorByName(String spec) {
		List<Doctor> doctors = getDoctorRepository().findByDocNameStartsWith(spec);
		return doctors;
	}
	/**
	 * This service finds doctors by given clinic.
	 * @param spec
	 * @return List<Doctor>
	 */
	public List<Doctor> getDoctorByClinic(String spec) {
		List<Doctor> doctors = getDoctorRepository().findByDocClinicNameStartsWith(spec);
		return doctors;
	}
}

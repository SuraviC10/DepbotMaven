package com.suravi.springdata.miniproject.repos;


import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.suravi.springdata.miniproject.model.Doctor;


@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Integer> {
	
	Doctor findById(int id);	
	List<Doctor> findByDocClinicNameStartsWith(String clinic);
	List<Doctor> findByDocNameStartsWith(String name);
	List<Doctor> findByDocSpecializationStartsWith(String spec);
}


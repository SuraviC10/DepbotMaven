package com.suravi.springdata.miniproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.suravi.springdata.miniproject.service.DoctorService;
import com.suravi.springdata.miniproject.errorhandling.ResourceNotFound;
import com.suravi.springdata.miniproject.errorhandling.ResponseObject;
import com.suravi.springdata.miniproject.loggers.MiniProjectLogger;
import com.suravi.springdata.miniproject.model.Doctor;

@RestController 
@CrossOrigin(origins = "http://ipkcsdsjaas01.northamerica.cerner.net:4200")
@RequestMapping("/spring-rest-api/doctors")
public class DoctorController {
	private Logger logger = MiniProjectLogger.getLogger(DoctorController.class);
	
	@Autowired
	private DoctorService doctorService;	

	public DoctorService getDoctorService() {
		return doctorService;
	}

	public void setDoctorService(DoctorService doctorService) {
		this.doctorService = doctorService;
	}
	//http://localhost:8080/miniproject-crud/spring-rest-api/doctors/all
	@GetMapping("/all")
    public Iterable<Doctor> getAllDoctors() {
		logger.info("In spring-rest-api/doctors/all");
        return doctorService.getAllDoctors();
    }	
	
	@GetMapping("/id/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable(value = "id") int docId)
    		throws ResourceNotFound{		
		logger.info("In spring-rest-api/doctors/id/{id}");
        Doctor doctor = getDoctorService().getDoctorById(docId);
        if(doctor == null)
        {
        	throw new ResourceNotFound("Doctor id could not be found :: " + docId);
        }
        return ResponseEntity.ok().body(doctor);
    }
	
	@GetMapping("/spec/{spec}")	
	public ResponseEntity<List<Doctor>> getDoctorBySpec(@PathVariable(value = "spec") String spec)
    		throws ResourceNotFound{	
		logger.info("In spring-rest-api/doctors/spec/{spec}");
        List<Doctor> doctors = getDoctorService().getDoctorBySpecialization(spec);
        if(doctors == null)
        {        	       	
        	return ResponseEntity.ok().body(null);
        }

        return ResponseEntity.ok().body(doctors);
    }
	@GetMapping("/name/{name}")	
	public ResponseEntity<List<Doctor>> getDoctorByName(@PathVariable(value = "name") String name)
    		throws ResourceNotFound{	
		logger.info("In spring-rest-api/doctors/name/{name}");
        List<Doctor> doctors = getDoctorService().getDoctorByName(name);
        if(doctors == null)
        {        	       	
        	return ResponseEntity.ok().body(null);
        }

        return ResponseEntity.ok().body(doctors);
    }
	@GetMapping("/clinic/{clinic}")	
	public ResponseEntity<List<Doctor>> getDoctorByClinic(@PathVariable(value = "clinic") String clinic)
    		throws ResourceNotFound{	
		logger.info("In spring-rest-api/doctors/clinic/{clinic}");
        List<Doctor> doctors = getDoctorService().getDoctorByClinic(clinic);
        if(doctors == null)
        {        	       	
        	return ResponseEntity.ok().body(null);
        }

        return ResponseEntity.ok().body(doctors);
    }
	
	@PostMapping("/add")
    public Doctor createDoctor(@Valid @RequestBody Doctor doctor) throws ResourceNotFound {
		logger.info("In spring-rest-api/doctors/add");
        return doctorService.createDoctor(doctor);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<Doctor> updateDoctor(@PathVariable(value="id")int docId,@RequestBody Doctor doctorDetails ) throws ResourceNotFound
	{   
		logger.info("In UPDATE spring-rest-api/doctors/{id}");
		Doctor updatedDoctor = doctorService.updateDoctor(doctorDetails);		
		return ResponseEntity.ok(updatedDoctor);
	}
	
	@DeleteMapping("/{id}")
    public Map<String, Boolean> deleteDoctor(@PathVariable(value = "id") int docId)
         throws ResourceNotFound {
		logger.info("In DELETE spring-rest-api/doctors/{id}");
        doctorService.deleteDoctor(docId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}

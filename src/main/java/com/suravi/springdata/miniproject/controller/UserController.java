package com.suravi.springdata.miniproject.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.suravi.springdata.miniproject.errorhandling.ErrorDetails;
import com.suravi.springdata.miniproject.errorhandling.ResourceNotFound;
import com.suravi.springdata.miniproject.errorhandling.ResponseObject;
import com.suravi.springdata.miniproject.loggers.MiniProjectLogger;
import com.suravi.springdata.miniproject.model.User;
import com.suravi.springdata.miniproject.service.UserService;

@RestController 
@CrossOrigin(origins = "http://ipkcsdsjaas01.northamerica.cerner.net:4200")
@RequestMapping("/spring-rest-api/user")
public class UserController {

	private Logger logger = MiniProjectLogger.getLogger(UserController.class);
	@Autowired
	public UserService userService;

	public UserService getUserService() {
		logger.info("Inside UserController");
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int uid)
    		throws ResourceNotFound{	
		logger.info("In spring-rest-api/user/{id}");
        User user = getUserService().getUserById(uid);
        if(user == null)
        {
        	throw new ResourceNotFound("user id could not be found :: " + uid);
        }
        return ResponseEntity.ok().body(user);
    }
	
	@GetMapping("/name/{name}")
    public ResponseEntity<ResponseObject> getUserByName(@PathVariable(value = "name") String uname)
    		throws ResourceNotFound{	
		logger.info("In spring-rest-api/user/name/{name}");
        User user = getUserService().getUserByName(uname);
        if(user == null)
        {
        	ResponseObject object = new ResponseObject(null,"User not found");
        	return ResponseEntity.ok().body(object);
        }
        ResponseObject object = new ResponseObject(user,"");
        return ResponseEntity.ok().body(object);
    }
	
	@GetMapping("/{uname}/{upass}")
    public ResponseEntity<ResponseObject> getUserByNameAndPassword(@PathVariable(value = "uname") String username,
    		@PathVariable(value = "upass") String password) throws ResourceNotFound{
		logger.info("In spring-rest-api/user/{uname}/{upass}");
		User user = getUserService().getUserByNameAndPassword(username,password);
        if(user == null)
        {
        	ResponseObject object = new ResponseObject(null,"User not found");
        	//ErrorDetails error = new ErrorDetails(new Date(),"User not found","Username and Password is incorrect");
        	return ResponseEntity.ok().body(object);
        	//throw new ResourceNotFound("This user is not present");
        }
        ResponseObject object = new ResponseObject(user,"");
        return ResponseEntity.ok().body(object);
    }
	
	@PostMapping("/add")
    public User createUser(@Valid @RequestBody User user) throws ResourceNotFound {
		logger.info("In CREATE spring-rest-api/user/add");		
        return getUserService().createUser(user);
    }
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value="id")int uid,@RequestBody User userDetails ) throws ResourceNotFound
	{   
		logger.info("In UPDATE spring-rest-api/user/{id}");	
		User updatedUser = getUserService().updateUser(userDetails);		
		return ResponseEntity.ok(updatedUser);
	}
	
	@DeleteMapping("/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int uid)
         throws ResourceNotFound {
		logger.info("In DELETE spring-rest-api/user/{id}");
		getUserService().deleteUser(uid);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Deleted", Boolean.TRUE);
        return response;
    }
}

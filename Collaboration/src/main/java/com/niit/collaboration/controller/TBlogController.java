package com.niit.collaboration.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.TUserDAO;
import com.niit.collaboration.model.User;

@RestController
public class TBlogController {

	@Autowired
	private TUserDAO tUserDAO;

	@Autowired
	private User user;
	
	@Autowired
	private HttpSession session;
	

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello from Collaboration Back End server";
	}

	// Mapping
	// GetMapping -> Fetch the data by sending few parameters
	// PostMapping -> create a record OR Save
	// PutMapping -> Update the record
	// DeleteMapping -> delete the record

	// define a simple service and test
	// Call the methods of DAO
	// List<User> -> need to convert into JSON Objects
	// So that we can use in our front end project

	// Hot to test??
	// 1) Postman
	// 2)RestClient

	// http://localhost:8080/Collaboration/getAllUsers
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		List users = tUserDAO.list();
		if (users.isEmpty()) {
			user.setErrorCode("100");
			user.setErrorMessage("Not users are available");
			users.add(user);
			return new ResponseEntity<List<User>>(users, HttpStatus.OK);
		}
		user.setErrorCode("200");
		user.setErrorMessage("Successfully fetched the user");

		return new ResponseEntity<List<User>>(users, HttpStatus.OK);

	}

	@GetMapping("/getUser/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		user = tUserDAO.get(id);

		if (user == null) {
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("No user found with this id :" + id);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@PostMapping("/tValidate/")
	public ResponseEntity<User> validateCredentials(@RequestBody User user) {

		user = tUserDAO.validate(user.getId(), user.getPassword());

		if (user == null) { // NLP NullPointerException...what is the solution
			user = new User();
			user.setErrorCode("404");
			user.setErrorMessage("Invalid Credentials...Please try again.");
		} else {
			user.setErrorCode("200");
			user.setErrorMessage("You are successfully logged in....");
			
			session.setAttribute("loggedInUserID", user.getId());
			session.setAttribute("loggedInUserRole", user.getRole());
			
			
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@PostMapping("/tCreateUser/")
	public ResponseEntity<User> createUser(@RequestBody User user)

	{

		if (tUserDAO.get(user.getId()) == null) {
			tUserDAO.save(user);
			user.setErrorCode("200");
			user.setErrorMessage("You have successfully registered...");
		} else {
			user.setErrorCode("404");
			user.setErrorMessage("User exist with this id : " + user.getId());

		}

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

}








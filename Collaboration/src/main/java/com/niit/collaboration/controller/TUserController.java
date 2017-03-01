package com.niit.collaboration.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collaboration.dao.TUserDAO;
import com.niit.collaboration.model.User;

@RestController
public class TUserController{
	
	@Autowired
	private TUserDAO tUserDAO;
	
	@Autowired
	private User user;
	
	
@GetMapping("/getAllUsers")
public ResponseEntity<List<User>> getAllUsers()
{
	List users= tUserDAO.list();
	if(users.isEmpty())
	{
		user.setErrorCode("100");
		user.setErrorMessage("No users are available");
		users.add(user);
		return new  ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	user.setErrorCode("200");
	user.setErrorMessage("Successfully fetched the user");
	return new  ResponseEntity<List<User>>(users,HttpStatus.OK);
}





@GetMapping("/getUser/{id}")
public ResponseEntity<User> getUser(@PathVariable("id")String id)
{
	user= tUserDAO.get(id);
	
	if(user==null)
	{ 
		user = new User();
		user.setErrorCode("404");
		user.setErrorMessage("no user found with this id:"+id);
		
	}
	return new ResponseEntity<User>(user,HttpStatus.OK);
}


@GetMapping("/tValidate/{id}/{password}")
 public ResponseEntity<User>validateCredentials(@PathVariable("id")String id, @PathVariable("password")String pwd)
{
	user= tUserDAO.validate(id,pwd);
	if(user==null)
	{
		user=new User();
		user.setErrorCode("404");
		user.setErrorMessage("Invalid Credentials...Pleasetry again");
	}
	else
	{
		user.setErrorCode("200");
		user.setErrorMessage("You are successfully logged in...");
	}
	return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}

@PostMapping("/tCreateUser/")
public ResponseEntity<User> createUser(@RequestBody User user)
{
	if(tUserDAO.get(user.getId())==null)
	{ 
		tUserDAO.save(user);
		user.setErrorCode("200");
		user.setErrorMessage("You are successfully registered...");
	}
	else
	{
		user.setErrorCode("404");
		user.setErrorMessage("User exist with this id:"+ user.getId());
		
	}
	return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
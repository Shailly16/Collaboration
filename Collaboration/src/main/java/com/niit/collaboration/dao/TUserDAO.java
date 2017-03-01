package com.niit.collaboration.dao;

import java.util.List;

import com.niit.collaboration.model.User;

public interface TUserDAO {
	
	public User get(String id);
	

	
	public List<User> list();
	
	
	public User validate(String id, String password);
	
	
	public boolean save(User user);
	
	public boolean update(User user);
	
	

}

package com.niit.collaboration.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.niit.collaboration.model.User;

@Repository("userDAO")
public interface UserDAO {

	public List<User> list();

	public User get(String id, String password);

	public User get(String id);

	// public void saveOrUpdate(UserDetails UserDetails);

	public boolean save(User userDetailsDetails);

	public boolean update(User userDetailsDetails);

	public void delete(String id);

	public User authenticate(String id, String name);

	public void setOnline(String userID);

	public void setOffLine(String userID);

}

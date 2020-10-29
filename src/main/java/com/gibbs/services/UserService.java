package com.gibbs.services;

import java.util.Set;

import com.gibbs.data.UserDAO;
import com.gibbs.models.User;

public class UserService {
	private UserDAO userDao;
	
	public UserService(UserDAO ud) {
		userDao = ud;
	}
	
	public Integer createUser(User u) {
		return userDao.createUser(u);
		
	}
	
	public User logIn(String username, String password) {
		User u = userDao.getUserByUsernameAndPassword(username, password);
		return u;
	}
	
	public Set<User> findAllUsers(){
		return userDao.getUsers();
	}
	
	
	
	public void deleteUser(User u) {
		userDao.deleteUser(u);
	}

}

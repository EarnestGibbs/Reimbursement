package com.gibbs.data;

import java.util.Set;

import com.gibbs.models.User;
import com.gibbs.models.UserRole;

public interface UserDAO {
	
	public Integer createUser(User u);//create
	public Set<User> getUsers();//read
	public User getUserById(Integer id);//read
	public Set<User> getUserByRole(UserRole r);//read
	public User getUserByName(String name);//read
	public User getUserByUsername(String username);//read
	public User getUserByUsernameAndPassword(String username, String password);//read
	public void updateUser(User u);//update
	public void deleteUser(User u);//delete

}

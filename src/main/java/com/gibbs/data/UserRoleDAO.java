package com.gibbs.data;

import com.gibbs.models.UserRole;

public interface UserRoleDAO {
	public Integer createRole(UserRole r);//create
	public UserRole getRoleById(Integer id);//read
	public void updateRole(UserRole r);//update
	public void deleteRole(UserRole r);//Delete

}

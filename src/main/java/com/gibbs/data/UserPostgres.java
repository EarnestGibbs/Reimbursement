package com.gibbs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.gibbs.models.User;
import com.gibbs.models.UserRole;
import com.gibbs.utils.ConnectionUtil;

public class UserPostgres implements UserDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	public Integer createUser(User u) {
		Integer id = 0;
		try(Connection conn = cu.getConnection())
		{
			conn.setAutoCommit(false);
			String sql = "insert into ers_users values (default, ?,?,?,?,?,?)";
			String[]keys = {"ers_users_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getUserRoleId());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) 
			{
				id = rs.getInt(1);
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public Set<User> getUsers() {
		Set<User> users = null;
		try(Connection conn = cu.getConnection())
		{
			String sql = "select ers_users_id, ers_username, ers_password ,user_first_name , user_last_name ,user_email, user_role "
					+ "from ers_users join ers_user_roles on ers_users.user_role_id = ers_user_roles.user_role_id";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			users = new HashSet<>();
			while(rs.next()) 
			{
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				UserRole role = new UserRole();
				role.setUserRole(rs.getString(7));
				
				users.add(user);
			}
		}catch(Exception e)
		{e.printStackTrace();}
		return users;
	}

	public User getUserById(Integer id) {
		User user = null;
		try (Connection conn = cu.getConnection()){
			String sql = "select ers_users_id, ers_username, ers_password ,user_first_name , user_last_name ,user_email, user_role "
					+ "from ers_users join ers_user_roles on ers_users.user_role_id = ers_user_roles.user_role_id where ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				UserRole role = new UserRole();
				role.setUserRole(rs.getString(7));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public Set<User> getUserByRole(UserRole r) {
		try (Connection conn = cu.getConnection()){
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByName(String name) {
		User user = null;
		try (Connection conn = cu.getConnection()){
			String sql = "select ers_users_id, ers_username, ers_password ,user_first_name , user_last_name ,user_email, user_role "
					+ "from ers_users join ers_user_roles on ers_users.user_role_id = ers_user_roles.user_role_id where user_first_name = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, name);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) 
			{
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
				UserRole role = new UserRole();
				role.setUserRole(rs.getString(7));
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public User getUserByUsername(String username) {
		try (Connection conn = cu.getConnection()){
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public User getUserByUsernameAndPassword(String username, String password) {
		User user = null;
		try (Connection conn = cu.getConnection()){
			String sql =  "select ers_users_id, ers_username, ers_password ,user_first_name , user_last_name ,user_email, user_role "
					+ "from ers_users join ers_user_roles on ers_users.user_role_id = ers_user_roles.user_role_id where ers_username = ? and ers_password = md5(? || ? || 'bears')";
			PreparedStatement ptsmt = conn.prepareStatement(sql);
			
			ptsmt.setString(1, username);
			ptsmt.setString(2, username);
			ptsmt.setString(3,  password);
			
			ResultSet rs = ptsmt.executeQuery();
			
			if(rs.next()) 
			{
				user = new User();
				user.setUserId(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setFirstName(rs.getString(4));
				user.setLastName(rs.getString(5));
				user.setEmail(rs.getString(6));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	public void updateUser(User u) {
		try (Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "update ers_users set ers_username = ?, ers_password = ?,  user_first_name = ?, user_last_name = ?, user_email = ?, user_role_id  "
					+ "where ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, u.getUsername());
			pstmt.setString(2, u.getPassword());
			pstmt.setString(3, u.getFirstName());
			pstmt.setString(4, u.getLastName());
			pstmt.setString(5, u.getEmail());
			pstmt.setInt(6, u.getRole().getUserRoleId());
			pstmt.setInt(7, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
					conn.commit();
			}else {
			conn.rollback();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteUser(User u) {
		try (Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "delete from ers_users where ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUserId());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected>0) {
				conn.commit();
			}else {
				conn.rollback();
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

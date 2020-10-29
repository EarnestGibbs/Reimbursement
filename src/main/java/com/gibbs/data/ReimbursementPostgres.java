package com.gibbs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.gibbs.models.Reimbursement;
import com.gibbs.models.ReimbursementStatus;
import com.gibbs.models.ReimbursementType;
import com.gibbs.models.User;
import com.gibbs.utils.ConnectionUtil;

public class ReimbursementPostgres implements ReimbursementDAO {
	
	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();

	@Override
	public Integer createReimbursement(Reimbursement r) {
		Integer id = 0;
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "insert into ers_reimbursement values\r\n" + 
					"(default, ?, ?, default,?, ?, ?, null, ?, ? )";
			String[]keys = {"reimb_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setDouble(1, r.getReimbAmount());
			pstmt.setTimestamp(2, r.getReimbSubmitted());
			pstmt.setString(3, r.getReimbDescription());
			pstmt.setString(4, r.getReimbReceipt());
			pstmt.setInt(5, r.getAuthorUser().getUserId());
			pstmt.setInt(6, r.getStatus().getStatusId());
			pstmt.setInt(7, r.getType().getTypeId());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return id;
	}

	@Override
	public Reimbursement getReimbursementById(Integer id) {
		Reimbursement r = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status , ers_reimbursement_type.reimb_type from ers_reimbursement\r\n " + 
					"join ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id \r\n" + 
					"join ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id where reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				r = new Reimbursement();
				r.setReimbID(rs.getInt(1));
				r.setReimbAmount(rs.getDouble(2));
				r.setReimbSubmitted(rs.getTimestamp(3));
				r.setReimbResolved(rs.getTimestamp(4));
				r.setReimbDescription(rs.getString(5));
				r.setReimbReceipt(rs.getString(6));
				User user = new User();
				user.setUserId(rs.getInt(7));
				r.setAuthorUser(user);
				User user2 = new User();
				user2.setUserId(rs.getInt(8));
				r.setResolverUser(user2);
				ReimbursementType t = new ReimbursementType();
				t.setType(rs.getString(9));
				r.setType(t);
				ReimbursementStatus s = new ReimbursementStatus();
				s.setStatus(rs.getString(10));
				r.setStatus(s);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return r;
	}


	@Override
	public Set<Reimbursement> getReimbursements() {
		Set<Reimbursement> reimbs = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status , ers_reimbursement_type.reimb_type from ers_reimbursement\r\n " + 
					"join ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id \r\n" + 
					"join ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			reimbs = new HashSet<>();
			while(rs.next()) 
			{
				Reimbursement r = new Reimbursement();
				r.setReimbID(rs.getInt(1));
				r.setReimbAmount(rs.getDouble(2));
				r.setReimbSubmitted(rs.getTimestamp(3));
				r.setReimbResolved(rs.getTimestamp(4));
				r.setReimbDescription(rs.getString(5));
				r.setReimbReceipt(rs.getString(6));
				User user = new User();
				user.setUserId(rs.getInt(7));
				r.setAuthorUser(user);
				User user2 = new User();
				user2.setUserId(rs.getInt(8));
				r.setResolverUser(user2);
				ReimbursementType t = new ReimbursementType();
				t.setType(rs.getString(9));
				r.setType(t);
				ReimbursementStatus s = new ReimbursementStatus();
				s.setStatus(rs.getString(10));
				r.setStatus(s);
				
				reimbs.add(r);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reimbs;
	}

	@Override
	public Set<Reimbursement> getReimbursementsByStatus(ReimbursementStatus status) {
		Set<Reimbursement> reimbs = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status , ers_reimbursement_type.reimb_type from ers_reimbursement\r\n " + 
					"join ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id \r\n" + 
					"join ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id where ers_reimbursement.reimb_status_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, status.getStatusId());
			
			ResultSet rs = pstmt.executeQuery();
			
			reimbs = new HashSet<>();
			while(rs.next()) 
			{
				Reimbursement r = new Reimbursement();
				r.setReimbID(rs.getInt(1));
				r.setReimbAmount(rs.getDouble(2));
				r.setReimbSubmitted(rs.getTimestamp(3));
				r.setReimbResolved(rs.getTimestamp(4));
				r.setReimbDescription(rs.getString(5));
				r.setReimbReceipt(rs.getString(6));
				User user = new User();
				user.setUserId(rs.getInt(7));
				r.setAuthorUser(user);
				User user2 = new User();
				user2.setUserId(rs.getInt(8));
				r.setResolverUser(user2);
				ReimbursementType t = new ReimbursementType();
				t.setType(rs.getString(9));
				r.setType(t);
				ReimbursementStatus s = new ReimbursementStatus();
				s.setStatus(rs.getString(10));
				r.setStatus(s);
				
				reimbs.add(r);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reimbs;
		}

	@Override
	public Set<Reimbursement> getReimbursementsByType(ReimbursementType type) {
		Set<Reimbursement> reimbs = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status , ers_reimbursement_type.reimb_type from ers_reimbursement\r\n " + 
					"join ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id \r\n" + 
					"join ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id where ers_reimbursement.reimb_type_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, type.getTypeId());
			
			ResultSet rs = pstmt.executeQuery();
			
			reimbs = new HashSet<>();
			while(rs.next()) 
			{
				Reimbursement r = new Reimbursement();
				r.setReimbID(rs.getInt(1));
				r.setReimbAmount(rs.getDouble(2));
				r.setReimbSubmitted(rs.getTimestamp(3));
				r.setReimbResolved(rs.getTimestamp(4));
				r.setReimbDescription(rs.getString(5));
				r.setReimbReceipt(rs.getString(6));
				User user = new User();
				user.setUserId(rs.getInt(7));
				r.setAuthorUser(user);
				User user2 = new User();
				user2.setUserId(rs.getInt(8));
				r.setResolverUser(user2);
				ReimbursementType t = new ReimbursementType();
				t.setType(rs.getString(9));
				r.setType(t);
				ReimbursementStatus s = new ReimbursementStatus();
				s.setStatus(rs.getString(10));
				r.setStatus(s);
				
				reimbs.add(r);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reimbs;
		}

	@Override
	public Set<Reimbursement> getReimbursementsByUser(User u) {
		Set<Reimbursement> reimbs = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description,reimb_receipt, reimb_author, reimb_resolver, ers_reimbursement_status.reimb_status , ers_reimbursement_type.reimb_type from ers_reimbursement\r\n " + 
					"join ers_reimbursement_status on ers_reimbursement.reimb_status_id = ers_reimbursement_status.reimb_status_id \r\n" + 
					"join ers_reimbursement_type on ers_reimbursement.reimb_type_id = ers_reimbursement_type.reimb_type_id where reimb_author = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, u.getUserId());
			
			ResultSet rs = pstmt.executeQuery();
			
			reimbs = new HashSet<>();
			while(rs.next()) 
			{
				Reimbursement r = new Reimbursement();
				r.setReimbID(rs.getInt(1));
				r.setReimbAmount(rs.getDouble(2));
				r.setReimbSubmitted(rs.getTimestamp(3));
				r.setReimbResolved(rs.getTimestamp(4));
				r.setReimbDescription(rs.getString(5));
				r.setReimbReceipt(rs.getString(6));
				User user = new User();
				user.setUserId(rs.getInt(7));
				r.setAuthorUser(user);
				User user2 = new User();
				user2.setUserId(rs.getInt(8));
				r.setResolverUser(user2);
				ReimbursementType t = new ReimbursementType();
				t.setType(rs.getString(9));
				r.setType(t);
				ReimbursementStatus s = new ReimbursementStatus();
				s.setStatus(rs.getString(10));
				r.setStatus(s);
				
				reimbs.add(r);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return reimbs;
		}

	@Override
	public void updateReimbursmentType(Reimbursement r) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "update ers_reimbursement set reimb_amount = ?, reimb_submitted = ?, reimb_resolver = ?, reimb_description = ?, "
					+ "reimb_receipt = ?, reimb_author = ?, reimb_resover = ?, reimb_status_id = ?, reimb_type_id = ? where reimb_id = ? ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, r.getReimbAmount());
			pstmt.setTimestamp(2, r.getReimbSubmitted());
			pstmt.setTimestamp(3, r.getReimbResolved());
			pstmt.setString(4, r.getReimbDescription());
			pstmt.setString(5, r.getReimbReceipt());
			pstmt.setInt(6, r.getAuthorUser().getUserId());
			pstmt.setInt(7, r.getResolverUser().getUserId());
			pstmt.setInt(8, r.getStatus().getStatusId());
			pstmt.setInt(9, r.getType().getTypeId());
			pstmt.setInt(10, r.getReimbID());
			
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteReimbursmentType(Reimbursement r) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "delete from ers_reimbursement where reimb_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, r.getReimbID());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}

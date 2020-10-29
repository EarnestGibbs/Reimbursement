package com.gibbs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gibbs.models.ReimbursementStatus;
import com.gibbs.utils.ConnectionUtil;

public class ReimbursementStatusPostgres implements ReimbursementStatusDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	@Override
	public Integer createReimbursementType(ReimbursementStatus s) {
		
		Integer id = 0;
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "insert into ers_reimbursement_type values (default, ?)";
			String[]keys = {"reimb_status_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, s.getStatus());
			
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rs.next()) {
				id = rs.getInt(1);
				conn.commit();
			}else {
				conn.rollback();
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		return id;
	}

	@Override
	public ReimbursementStatus getTypeById(Integer id) {
		ReimbursementStatus s = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_status_id , reimb_status from ers_reimbursement_status where reimb_status_id = ? ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				s = new ReimbursementStatus();
				s.setStatusId(rs.getInt(1));
				s.setStatus(rs.getString(2));
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return s;
	}

	@Override
	public void updateReimbursmentType(ReimbursementStatus s) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "update ers_reimbursement_status set reimb_status = ? where reimb_status_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, s.getStatus());
			pstmt.setInt(2, s.getStatusId());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) 
			{
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch(Exception e) {
			
		}
	}

	@Override
	public void deleteReimbursmentType(ReimbursementStatus s) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "delete from ers_reimbursement_status where reimb_status_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, s.getStatusId());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected>0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}

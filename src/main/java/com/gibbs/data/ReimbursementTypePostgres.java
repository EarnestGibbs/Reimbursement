package com.gibbs.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gibbs.models.ReimbursementType;
import com.gibbs.utils.ConnectionUtil;

public class ReimbursementTypePostgres implements ReimbursementTypeDAO {

	private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
	
	@Override
	public Integer createReimbursementType(ReimbursementType t) {
		Integer id = 0;
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql = "insert into ers_reimbursement_type values (default, ?)";
			String[]keys = {"reimb_type_id"};
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, t.getType());
			
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
	public ReimbursementType getTypeById(Integer id) {
		ReimbursementType t = null;
		try(Connection conn = cu.getConnection()){
			String sql = "select reimb_type_id , reimb_type from ers_reimbursement_type where reimb_type_id = ? ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				t = new ReimbursementType();
				t.setTypeId(rs.getInt(1));
				t.setType(rs.getString(2));
				}
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		return t;
	}

	@Override
	public void updateReimbursmentType(ReimbursementType t) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			String sql  = "update ers_reimbursement_type set reimb_type = ? where reimb_type_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, t.getType());
			pstmt.setInt(2, t.getTypeId());
			
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0) {
				conn.commit();
			}else {
				conn.rollback();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void deleteReimbursmentType(ReimbursementType t) {
		try(Connection conn = cu.getConnection()){
			conn.setAutoCommit(false);
			
			String sql = "delete from ers_reimbursement_type where reimb_type_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t.getTypeId());
			
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

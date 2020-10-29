package com.gibbs.services;

import java.util.Set;

import com.gibbs.data.ReimbursementDAO;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.User;

public class EmployeeService {
	private ReimbursementDAO reimbursementDao;
	
	public EmployeeService(ReimbursementDAO rd) {
		reimbursementDao = rd;
	}
	
	public Integer createReimbursementRequest(Reimbursement r) {
		return reimbursementDao.createReimbursement(r);
	}
	
	public Reimbursement viewReimbursementRequestById(Integer id) {
		return reimbursementDao.getReimbursementById(id);
	}
	
	public Set<Reimbursement> viewReimbursementRequestByUser(User u) {
		return reimbursementDao.getReimbursementsByUser(u);
	}

}

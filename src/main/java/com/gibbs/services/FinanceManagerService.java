package com.gibbs.services;

import java.util.Set;

import com.gibbs.data.ReimbursementDAO;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.User;

public class FinanceManagerService {
	private ReimbursementDAO reimbursementDao;
	
	public FinanceManagerService(ReimbursementDAO rd) {
		reimbursementDao = rd;	
	}
	
	public Reimbursement viewReimbursementRequestById(Integer id) {
		return reimbursementDao.getReimbursementById(id);
	}
	
	public Set<Reimbursement> findAllReimbursements(){
		return reimbursementDao.getReimbursements();
	}
	
	public Set<Reimbursement> viewReimbursementRequestByUser(User u) {
		return reimbursementDao.getReimbursementsByUser(u);
	}
	
	public void updateReimbursementRequest(Reimbursement r) {
		reimbursementDao.updateReimbursmentType(r);
		
	}

}

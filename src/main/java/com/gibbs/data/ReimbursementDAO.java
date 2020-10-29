package com.gibbs.data;

import java.util.Set;

import com.gibbs.models.Reimbursement;
import com.gibbs.models.ReimbursementStatus;
import com.gibbs.models.ReimbursementType;
import com.gibbs.models.User;

public interface ReimbursementDAO {
	public Integer createReimbursement(Reimbursement r);//create
	public Reimbursement getReimbursementById(Integer id);//read
	public Set<Reimbursement> getReimbursements();//read
	public Set<Reimbursement> getReimbursementsByStatus(ReimbursementStatus s);//read
	public Set<Reimbursement> getReimbursementsByType(ReimbursementType t);//read
	public Set<Reimbursement> getReimbursementsByUser(User u);//read
	public void updateReimbursmentType (Reimbursement r);//update
	public void deleteReimbursmentType (Reimbursement r);//delete

}
            
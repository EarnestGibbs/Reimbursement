package com.gibbs.data;

import com.gibbs.models.ReimbursementStatus;

public interface ReimbursementStatusDAO {
	public Integer createReimbursementType(ReimbursementStatus s);//create
	public ReimbursementStatus getTypeById(Integer id);//read
	public void updateReimbursmentType (ReimbursementStatus s);//update
	public void deleteReimbursmentType (ReimbursementStatus s);//delete

}

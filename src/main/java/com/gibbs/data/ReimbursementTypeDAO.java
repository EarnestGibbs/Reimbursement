package com.gibbs.data;

import com.gibbs.models.ReimbursementType;

public interface ReimbursementTypeDAO {
	public Integer createReimbursementType(ReimbursementType t);//create
	public ReimbursementType getTypeById(Integer id);//read
	public void updateReimbursmentType (ReimbursementType t);//update
	public void deleteReimbursmentType (ReimbursementType t);//delete

}

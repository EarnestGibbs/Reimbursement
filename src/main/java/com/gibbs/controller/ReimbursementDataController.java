package com.gibbs.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibbs.data.ReimbursementPostgres;
import com.gibbs.data.UserPostgres;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.User;
import com.gibbs.services.FinanceManagerService;
import com.gibbs.services.UserService;

public class ReimbursementDataController {
	
	private UserService us;
	private FinanceManagerService fs;
	
	public ReimbursementDataController(UserService us, FinanceManagerService fs) {
		super();
		this.us = us;
		this.fs = fs;
	}
	
	public ReimbursementDataController() {
		us = new UserService(new UserPostgres());
		fs = new FinanceManagerService(new ReimbursementPostgres());
	}


	public void sendAllData(HttpServletResponse res) {
		res.setContentType("text/json");
		Set<Reimbursement> reimbs = fs.findAllReimbursements();
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimbs));
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void sendReimb(HttpServletRequest req ,HttpServletResponse res) {
		res.setContentType("text/json");
		System.out.println(req.getParameter("reimb_id"));
		int id = Integer.valueOf(req.getParameter("reimb_id"));
		Reimbursement reimb = fs.viewReimbursementRequestById(id);
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimb));
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	

}

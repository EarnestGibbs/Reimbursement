package com.gibbs.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibbs.data.ReimbursementPostgres;
import com.gibbs.data.UserPostgres;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.ReimbursementStatus;
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
	
	public void sendReimb(HttpServletRequest req ,HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {
		res.setContentType("text/json");
		ObjectMapper om = new ObjectMapper();
		JsonNode r = om.readTree(req.getInputStream());
		int id = Integer.valueOf(om.convertValue(r.get("reimbId"),Integer.class));
		Reimbursement reimb = fs.viewReimbursementRequestById(id);
		ReimbursementStatus rs= new ReimbursementStatus();
		rs.setStatusId(Integer.valueOf(om.convertValue(r.get("status").get("statusId"),Integer.class)));
		System.out.println(rs.toString());
		reimb.setStatus(rs);
		reimb.setReimbResolved(Timestamp.valueOf(LocalDateTime.now()));
		reimb.setResolverUser((User) req.getSession().getAttribute("user"));
		System.out.println(reimb.toString());
		fs.updateReimbursementRequest(reimb);
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimb));
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public void sendReimbToEmployes(HttpServletRequest req ,HttpServletResponse res) throws JsonParseException, JsonMappingException, IOException {		
		Set<Reimbursement> reimbs = fs.viewReimbursementRequestByUser((User) req.getSession().getAttribute("user"));
		try {
			res.getWriter().println(new ObjectMapper().writeValueAsString(reimbs));
			}catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	

}

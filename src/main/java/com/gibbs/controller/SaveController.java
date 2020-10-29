package com.gibbs.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gibbs.data.ReimbursementPostgres;
import com.gibbs.data.UserPostgres;
import com.gibbs.models.Reimbursement;
import com.gibbs.models.User;
import com.gibbs.services.EmployeeService;
import com.gibbs.services.UserService;

public class SaveController {
	
	private UserService us;
	private EmployeeService es;
	
	public SaveController(UserService us,EmployeeService es) {
		super();
		this.us = us;
		this.es = es;
	} 
	
	public SaveController() {
		us = new UserService(new UserPostgres());
		es = new EmployeeService(new ReimbursementPostgres());
		
	}
	
	public void saveUser(HttpServletRequest req, HttpServletResponse res) throws IOException{
		try {
			User u = new ObjectMapper().readValue(req.getInputStream(), User.class);
			System.out.println(u);
			res.getWriter().println("User was add to the database");
		} catch (IOException e) {
			e.printStackTrace();
			res.getWriter().println("something went wrong");
		}
	}
	
	public void saveReimbursement(HttpServletRequest req, HttpServletResponse res) throws IOException{
		try {
			Reimbursement r = new ObjectMapper().readValue(req.getInputStream(), Reimbursement.class);
			r.setAuthorUser((User) req.getSession().getAttribute("user"));
			System.out.println(r);
			int reimbResult =es.createReimbursementRequest(r);
			res.getWriter().println("Reimbursement was add to the database");
		} catch (IOException e) {
			e.printStackTrace();
			res.getWriter().println("something went wrong");
		}
	}

	
}

package com.gibbs.controller;

import javax.servlet.http.HttpServletRequest;

import com.gibbs.data.UserPostgres;
import com.gibbs.models.User;
import com.gibbs.services.UserService;

public class ReimbursementController {
	
	private UserService us;
	
	public ReimbursementController(UserService us) {
		super();
		this.us = us;	
	}
	
	public ReimbursementController() {
		us = new UserService(new UserPostgres());
	}
	
	
	public String login(HttpServletRequest req) {
		String userName = req.getParameter("name");
		String passWord = req.getParameter("pass");
		User u = us.logIn(userName, passWord);
		if(u != null) {
			req.getSession().setAttribute("user", u);
			return "html/reimbursement.html";
		} else {
			return ".page";
		}
	}
	
	public String view() {
		return "html/view.html";
	} 
}

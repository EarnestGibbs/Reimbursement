package com.gibbs.delegates;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gibbs.controller.ReimbursementController;
import com.gibbs.controller.ReimbursementDataController;
import com.gibbs.controller.SaveController;

public class RequestForwarder extends HttpServlet {

    
    public String routes(HttpServletRequest req) {
    	switch(req.getRequestURI()) {
    	case "/ReimbursementSystem/login.page":
    		System.out.println(req.getParameter("name"));
    		return new ReimbursementController().login(req);
    	case"/ReimbursementSystem/view.page":
    		return new ReimbursementController().view();
    	default:
    		return "html/login.html";
    	}
    }
    
    
	public void data(HttpServletRequest req, HttpServletResponse res) throws IOException {
		switch(req.getRequestURI()) {
		case"/ReimbursementSystem/all.json":
			new ReimbursementDataController().sendReimbToEmployes(req, res);
			break;
		case"/ReimbursementSystem/reimbursement.json":
			new SaveController().saveReimbursement(req, res);
			break;
		case"/ReimbursementSystem/update.json":
			new ReimbursementDataController().sendReimb(req, res);
			break;
		case"/ReimbursementSystem/allreimb.json":
			new ReimbursementDataController().sendAllData(res);
		
		}
	}

} 	

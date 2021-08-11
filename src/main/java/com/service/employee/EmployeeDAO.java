package com.service.employee;

import java.util.List;

import org.hibernate.Session;

import com.service.rest.ReimbursementController;

public interface EmployeeDAO {

	
	public String checkLogin(String name, String password);
	public Class<ReimbursementController> submitRequest();
	public Class<ReimbursementController> viewRequests();
	
	
}

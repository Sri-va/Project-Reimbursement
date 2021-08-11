package com.service.manager;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import com.fasterxml.jackson.core.JsonProcessingException;



public interface ManagerDAO {

	
	public String acceptRequests() throws JsonProcessingException;
	public List rejectRequests();
}

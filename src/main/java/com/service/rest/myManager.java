package com.service.rest;

public class myManager {

	private int id;
	private double salary;
	
	public myManager() {}
	
	public myManager(int id, double salary) {
		
		this.id = id;
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	
}

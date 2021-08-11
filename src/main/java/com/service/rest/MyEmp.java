package com.service.rest;

public class MyEmp {

	private String email,firstname,lastname,password,phonenum,username;
	private double salary;
	
	public MyEmp() {
		
	}
	

	public MyEmp(String email, String firstname, String lastname, String password, String phonenum, String username,
			double salary) {
		this.email = email;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.phonenum = phonenum;
		this.username = username;
		this.salary = salary;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenum() {
		return phonenum;
	}

	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}

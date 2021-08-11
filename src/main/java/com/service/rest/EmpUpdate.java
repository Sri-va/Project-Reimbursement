package com.service.rest;

public class EmpUpdate {

	private String fname,lname,uname,phone,email,password;
	int id;
	
	public EmpUpdate() {
	}

	
	public EmpUpdate(String fname, String lname, String uname, String phone, String email, String password, int id) {
		
		this.fname = fname;
		this.lname = lname;
		this.uname = uname;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.id=id;
	}


	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}

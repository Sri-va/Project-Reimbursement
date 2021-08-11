package com.service.rest;

import java.util.Date;

class Support{
	private String paymentMethod,purpose;
	private Date date;
	private int id;
	private double amount;
	
	public Support() {
		
	}
	
	public Support(String paymentMethod, Double amount, String purpose, Date date,int id) {
		
		this.paymentMethod = paymentMethod;
		this.purpose = purpose;
		this.amount=amount;
		this.date=date;
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

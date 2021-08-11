package com.service.employee;


import java.util.Date;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.service.manager.Manager;

@Entity
public class SupportCase {

	
	
	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "support_sequence"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
        }
    )
	private int sid;
	

	@Column(name="date")
	private Date date;
	
	@Column(name="date_of_expense")
	private Date date_of_expense;
	
	@Column(name="method")
	private String payment_method;
	
	@Column(name="purpose")
	private String purpose;
	
	@Column(name="amount")
	private double amount;
	
	@Column(name="status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee emp;
	
	
	@ManyToOne
	@JoinColumn(name = "m_id")
	private Manager manager;
	
	public SupportCase(){
		
	}
	
	

	public SupportCase(Date date,Date date_of_expense, String payment_method, String purpose, Double amount, String status) {
		
		this.date=date;
		this.date_of_expense = date_of_expense;
		this.payment_method = payment_method;
		this.purpose = purpose;
		this.amount=amount;
		this.status = status;
	}


	



	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	
	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Date getDate_of_expense() {
		return date_of_expense;
	}

	public void setDate_of_expense(Date date_of_expense) {
		this.date_of_expense = date_of_expense;
	}

	public String getPayment_method() {
		return payment_method;
	}

	public void setPayment_method(String payment_method) {
		this.payment_method = payment_method;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public Employee getEmp() {
		return emp;
	}



	public void setEmp(Employee emp) {
		this.emp = emp;
	}



	public Manager getManager() {
		return manager;
	}



	public void setManager(Manager manager) {
		this.manager = manager;
	}

	
}

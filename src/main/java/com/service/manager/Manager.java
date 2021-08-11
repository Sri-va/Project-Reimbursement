package com.service.manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import javax.persistence.Query;


import org.hibernate.Session;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.service.employee.Employee;
import com.service.employee.SupportCase;


@Entity

public class Manager extends Employee{

	@Id
	@GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
      name = "sequence-generator",
      strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
      parameters = {
        @Parameter(name = "sequence_name", value = "manager_sequence"),
        @Parameter(name = "initial_value", value = "0"),
        @Parameter(name = "increment_size", value = "1")
        }
    )
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@OneToMany(cascade=CascadeType.ALL, mappedBy = "manager")
	private List<SupportCase> scases=new ArrayList<>();

	
	public List<SupportCase> getCases() {
		return scases;
	}

	public void setCases(List<SupportCase> cases) {
		this.scases = cases;
	}





	
}

package com.service.rest;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.hibernate.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.employee.Employee;
import com.service.employee.EmployeeDAO;

class Emp{
	private String name,password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Emp() {}
	public Emp(String name, String password) {
		
		this.name = name;
		this.password = password;
	}
	
}

class EmpSupport{
	private String paymentMethod,purpose;
	private Date date;
	private int id;
	private double amount;
	
	public EmpSupport() {
		
	}
	
	public EmpSupport(String paymentMethod, Double amount, String purpose, Date date, int id) {
		
		this.paymentMethod = paymentMethod;
		this.purpose = purpose;
		this.amount=amount;
		this.date=date;
		this.id = id;
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
@Path("employee")
public class EmployeeController implements EmployeeDAO {
	
	private static final Logger logger=LogManager.getLogger(EmployeeController.class);

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String signUp(String data) throws JsonProcessingException {
			
		ObjectMapper mapper=new ObjectMapper();
		try{
			Transaction transaction=null;
			Session session=HibernateUtil.getSessionFactory().openSession();
			logger.info("Connected to db");
			MyEmp emp=mapper.readValue(data, MyEmp.class);	
			transaction=session.beginTransaction();
			Employee e=new Employee(emp.getFirstname(), emp.getLastname(), emp.getUsername(),emp.getPassword(),emp.getSalary(), emp.getEmail(), emp.getPhonenum());
			session.save(e);
			transaction.commit();
			session.close();
		}catch(SessionException e) {
			logger.fatal("Failed at: ", e);
		}
		logger.trace("Signup Success");
		return mapper.writeValueAsString("Done");
		}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String data) throws JsonProcessingException {
			
		ObjectMapper mapper=new ObjectMapper();
		try {
			Transaction transaction=null;
			Session session=HibernateUtil.getSessionFactory().openSession();
			
				Emp e=mapper.readValue(data, Emp.class);
				System.out.println("JSON"+e.getName());
				
			

			System.out.println(e.getPassword());
			
			CriteriaBuilder criteria=session.getCriteriaBuilder();
			CriteriaQuery<Employee> criteriaquery=criteria.createQuery(Employee.class);
			Root<Employee> root=criteriaquery.from(Employee.class);			
			criteriaquery.select(root).where(criteria.equal(root.get("username"), e.getName()));
			Query<Employee> query=session.createQuery(criteriaquery); 
			List<Employee> list=query.list();
			session.close();
			if(list.size()>0) {		
				logger.info("Login success");
				return mapper.writeValueAsString(list);
			}
		}catch(SessionException e) {
			logger.fatal("Failed at: ", e);
		}catch(TransactionException t) {
			logger.trace("Failed at inserting: ",t);
		}
		logger.trace("Failed login");
			return mapper.writeValueAsString("Failed");
		}
	
		@GET
		@Path("/view/{user}")
		@Produces(MediaType.APPLICATION_JSON)
		public String viewDetails(@PathParam("user") int userid) throws JsonProcessingException {
			
			ObjectMapper mapper=new ObjectMapper();
			try {
				Transaction transaction=null;
				Session session=HibernateUtil.getSessionFactory().openSession();
				CriteriaBuilder criteria=session.getCriteriaBuilder();
				CriteriaQuery<Employee> criteriaquery=criteria.createQuery(Employee.class);
				Root<Employee> root=criteriaquery.from(Employee.class);			
				criteriaquery.select(root).where(criteria.equal(root.get("id"), userid));
				Query<Employee> query=session.createQuery(criteriaquery); 
				List<Employee> list=query.getResultList();
				session.close();
				if(list.size()!=0) {
					logger.info("Loaded successfully");
					return mapper.writeValueAsString(list);
				}
				
			}catch(SessionException e) {
				logger.fatal("Failed at: ", e);
			}catch(TransactionException t) {
				logger.trace("Failed at inserting: ",t);
			}
			return mapper.writeValueAsString("Failed to load");
		}
		
		@PUT
		@Path("/update")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String updateDetails(String data) throws JsonProcessingException {
			
			ObjectMapper mapper=new ObjectMapper();
			try {
				Transaction transaction=null;
				Session session=HibernateUtil.getSessionFactory().openSession();
				
				EmpUpdate e=mapper.readValue(data, EmpUpdate.class);
				
				transaction=session.beginTransaction();
				Query<Employee> query=session.createQuery("UPDATE Employee SET username =:user,first_name =:first, last_name =:last,password =:pass,phonenum =:ph, email =:mail WHERE id = :i ");
				query.setParameter("user", e.getUname());
				query.setParameter("first", e.getFname());
				query.setParameter("last", e.getLname());
				query.setParameter("pass", e.getPassword());
				query.setParameter("ph", e.getPhone());
				query.setParameter("mail", e.getEmail());
				query.setParameter("i", e.getId());
				int status=query.executeUpdate();
				transaction.commit();
				session.close();
				if(status!=0)
					return mapper.writeValueAsString("Updated");
				
			}catch(SessionException e) {
				logger.fatal("Failed at: ", e);
			}catch(TransactionException t) {
				logger.trace("Failed at inserting: ",t);
			}
			logger.trace("Failed updating");
			return mapper.writeValueAsString("Failed");
		}
		
		@Path("view/support")
		@Override
		public Class<ReimbursementController> viewRequests() {
			// TODO Auto-generated method stub
			return ReimbursementController.class;
		}

		@Override
		public String checkLogin(String name, String password) {
			// TODO Auto-generated method stub
			return null;
		}


		@Path("new/support")
		@Override
		public Class<ReimbursementController> submitRequest() {
			// TODO Auto-generated method stub
			
			return ReimbursementController.class;
		}
}

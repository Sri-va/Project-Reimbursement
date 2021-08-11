package com.service.rest;

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

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.employee.Employee;
import com.service.employee.SupportCase;
import com.service.manager.Manager;

import com.service.rest.CorsFilter;


@Path("manager")
public class ManagerController{


	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String signUp(String data) throws JsonProcessingException {
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		ObjectMapper mapper=new ObjectMapper();
		
		myManager mdata=mapper.readValue(data, myManager.class);
		transaction=session.beginTransaction();
//		CriteriaBuilder criteria=session.getCriteriaBuilder();
//		CriteriaQuery<Employee> criteriaquery=criteria.createQuery(Employee.class);
//		Root<Employee> root=criteriaquery.from(Employee.class);			
//		criteriaquery.select(root).where(criteria.equal(root.get("id"), mdata.getId()));
//		Query<Employee> empquery=session.createQuery(criteriaquery); 
//		List<Employee> list=empquery.getResultList();
		Query<Manager> query=session.createQuery("INSERT INTO Manager (email,first_name,last_name,password,phonenum,salary, username) SELECT email,first_name,last_name,password,phonenum,:s, username FROM Employee WHERE id=:i");
		query.setParameter("i", mdata.getId());
		query.setParameter("s", mdata.getSalary());
		int status=query.executeUpdate();
//		session.close();
		transaction.commit();
		if(status==0)
			return mapper.writeValueAsString("Failed");
		
		return mapper.writeValueAsString("Updated");
		}
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String data) throws JsonProcessingException {
			
			Transaction transaction=null;
			Session session=HibernateUtil.getSessionFactory().openSession();
			ObjectMapper mapper=new ObjectMapper();
			
				Emp e=mapper.readValue(data, Emp.class);
				System.out.println("JSON"+e.getName());
				
			

			System.out.println(e.getPassword());
			
			CriteriaBuilder criteria=session.getCriteriaBuilder();
			CriteriaQuery<Manager> criteriaquery=criteria.createQuery(Manager.class);
			Root<Manager> root=criteriaquery.from(Manager.class);			
			criteriaquery.select(root).where(criteria.equal(root.get("username"), e.getName()));
			Query<Manager> query=session.createQuery(criteriaquery); 
			List<Manager> list=query.list();
			session.close();
			if(list.size()>0) {				
				return mapper.writeValueAsString(list);
			}
			
			return mapper.writeValueAsString("Failed");
		}


	@GET
	@Path("/view/{user}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewDetails(@PathParam("user") int userid) throws JsonProcessingException {
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<Manager> criteriaquery=criteria.createQuery(Manager.class);
		Root<Manager> root=criteriaquery.from(Manager.class);			
		criteriaquery.select(root).where(criteria.equal(root.get("id"), userid));
		Query<Manager> query=session.createQuery(criteriaquery); 
		List<Manager> list=query.getResultList();
		ObjectMapper mapper=new ObjectMapper();
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDetails(String data) throws JsonProcessingException {
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		ObjectMapper mapper=new ObjectMapper();
		
		EmpUpdate e=mapper.readValue(data, EmpUpdate.class);
		System.out.println("JSON"+e.getFname());
		System.out.println(e.getLname());
		System.out.println(e.getUname());
		System.out.println(e.getPassword());
		System.out.println(e.getPhone());
		System.out.println(e.getEmail());
		transaction=session.beginTransaction();
		Query<Manager> query=session.createQuery("UPDATE Manager SET username =:user,first_name =:first, last_name =:last,password =:pass,phonenum =:ph, email =:mail WHERE id = :i ");
		query.setParameter("user", e.getUname());
		query.setParameter("first", e.getFname());
		query.setParameter("last", e.getLname());
		query.setParameter("pass", e.getPassword());
		query.setParameter("ph", e.getPhone());
		query.setParameter("mail", e.getEmail());
		query.setParameter("i", e.getId());
		int status=query.executeUpdate();	
		transaction.commit();
		if(status==0)
			return mapper.writeValueAsString("Failed");
		
		return mapper.writeValueAsString("Updated");
	}
	
	
	@Path("/update/support")
	public Class<ReimbursementController> accept() throws JsonProcessingException {
		// TODO Auto-generated method stub
		return ReimbursementController.class;
		
	}
	@GET
	@Path("/view/employees")
	public String viewEmployees() throws JsonProcessingException {
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		CriteriaBuilder criteria=session.getCriteriaBuilder();
		CriteriaQuery<Employee> criteriaquery=criteria.createQuery(Employee.class);
		Root<Employee> root=criteriaquery.from(Employee.class);			
		criteriaquery.select(root);
		Query<Employee> query=session.createQuery(criteriaquery); 
		List<Employee> list=query.getResultList();
		ObjectMapper mapper=new ObjectMapper();
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}

	@Path("view/support")
	
	public Class<ReimbursementController> viewRequests() {
		// TODO Auto-generated method stub
		return ReimbursementController.class;
	}


}

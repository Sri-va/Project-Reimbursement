package com.service.rest;

import java.util.ArrayList;
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

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.employee.Employee;
import com.service.employee.SupportCase;
import com.service.manager.Manager;

class UpdateSupport{
	private String status;
	private int id,mid;
	
	public UpdateSupport() {}
	
	public UpdateSupport(String status, int id,int mid) {
		
		this.status = status;
		this.id = id;
		this.mid=mid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getMid() {
		return mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}

@Path("/support")
@Produces(MediaType.APPLICATION_JSON)
public class ReimbursementController {

	@POST
	@Path("/request")
	@Consumes(MediaType.APPLICATION_JSON)
	public String submitRequest(String body) throws JsonProcessingException {
		// TODO Auto-generated method stub
		ObjectMapper mapper=new ObjectMapper();
		try {
			System.out.println("data " + body);
			Transaction transaction=null;
			Session session=HibernateUtil.getSessionFactory().openSession();
			EmpSupport emp=mapper.readValue(body, EmpSupport.class);
			transaction=session.beginTransaction();
			Employee e=new Employee();
			e.setId(emp.getId());
			System.out.println(e.getId());
			SupportCase s=new SupportCase(new Date(), emp.getDate(), emp.getPaymentMethod(), emp.getPurpose(), emp.getAmount(),"pending");
			s.setEmp(e);
			session.save(s);
			transaction.commit();
			session.close();
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return mapper.writeValueAsString("Submitted sucessfully");
	}
	
	@PUT
	@Path("/case")
	public String accept(String body) throws JsonProcessingException {
		// TODO Auto-generated method stub
		ObjectMapper mapper=new ObjectMapper();
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		UpdateSupport approve=mapper.readValue(body, UpdateSupport.class);
		Query<SupportCase> query=session.createQuery("UPDATE SupportCase SET status=:s,m_id=:m WHERE sid=:id");
		System.out.println(approve.getStatus());
		System.out.println(approve.getMid());
		System.out.println(approve.getId());
		query.setParameter("s",approve.getStatus());
		query.setParameter("m", approve.getMid());
		query.setParameter("id",approve.getId());

		System.out.println(query.executeUpdate());
		transaction.commit();
		session.close();
		return mapper.writeValueAsString("Updated");
	}
	
	@GET
	@Path("/all/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewDetails(@PathParam("id") int userid) throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s WHERE emp_id=:id ");
		query.setParameter("id",userid);
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/resolved/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewResolved(@PathParam("id") int userid) throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s WHERE emp_id=:id AND status=:s");
		query.setParameter("id",userid);
		query.setParameter("s", "accepted");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/pending/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewPending(@PathParam("id") int userid) throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s WHERE emp_id=:id AND status=:s");
		query.setParameter("id",userid);
		query.setParameter("s", "pending");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view pending list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/rejected/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewRejected(@PathParam("id") int userid) throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase WHERE emp_id=:id AND status=:s");
		query.setParameter("id",userid);
		query.setParameter("s", "rejected");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewAllDetails() throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s");
		
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/resolved")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewAllResolved() throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase WHERE status=:s");
		
		query.setParameter("s", "accepted");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/pending")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewAllPending() throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s WHERE status=:s");
		
		query.setParameter("s", "pending");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view pending list "+list.size());
		session.close();
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
	
	@GET
	@Path("/rejected")
	@Produces(MediaType.APPLICATION_JSON)
	public String viewAllRejected() throws JsonProcessingException {
		
		
		Transaction transaction=null;
		Session session=HibernateUtil.getSessionFactory().openSession();
		transaction=session.beginTransaction();
		Query<SupportCase> query=session.createQuery("FROM SupportCase s WHERE status=:s");
		
		query.setParameter("s", "rejected");
		List<SupportCase> list=query.list();
		ObjectMapper mapper=new ObjectMapper();
		System.out.println("view list "+list.size());
		session.close();
		
		if(list.size()!=0)
			return mapper.writeValueAsString(list);
		return mapper.writeValueAsString("Failed to load");
	}
}
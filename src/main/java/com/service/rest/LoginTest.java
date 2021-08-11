package com.service.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

@DisplayName("Testing Login")
public class LoginTest {

	
	EmployeeController ec;
	@BeforeEach
	public void beforeEachTest() {
		this.ec = new EmployeeController();
	}
	@Test
	public void testLogin() throws Exception{
		
		Assertions.assertEquals("Failed",this.ec.login("{\"name\":\"Test\", \"password\":\"pass\"}"));
	}
}


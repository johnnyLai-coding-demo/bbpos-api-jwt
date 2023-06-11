package com.bbpos.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.springframework.test.web.servlet.MvcResult;

@ActiveProfiles("test")
@EnableAutoConfiguration
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class AllAPITests {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;	
	
	@BeforeEach
	public void setUp() {
		 mockMvc = webAppContextSetup(webApplicationContext).build();		 
	}		

	@Test
	public void testGetExample() throws Exception {
		
		MvcResult requestResult = mockMvc.perform(get("/api/test/all").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.accept(MediaType.TEXT_PLAIN_VALUE)).andExpect(status().isOk()).andReturn();

		String result = requestResult.getResponse().getContentAsString();
		
		//one more as we just sign up a new one
		assertEquals("Hello World", result);		
		
	}
   
	
}
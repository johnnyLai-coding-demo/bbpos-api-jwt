package com.bbpos.api.test;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;

import org.springframework.security.test.context.support.WithMockUser;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@EnableAutoConfiguration
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class UserAPITests {
	
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext webApplicationContext;			
	
	@BeforeEach
	public void setUp() throws Exception {
		 mockMvc = webAppContextSetup(webApplicationContext).build();	
	}		

	@Test
	@WithMockUser(username = "bbposTestLogin", password = "pwd", roles = "USER")
	public void testPostExample() throws Exception {
				
		MvcResult getRequestResult = mockMvc.perform(get("/api/test/user")
				.header("Authorization", "Bearer " ) .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		

		assertEquals("Hey bbposTestLogin", getRequestResult.getResponse().getContentAsString()+"");
		
	}
   
	
}
package com.bbpos.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import com.bbpos.request.JwtRequest;
import com.bbpos.security.UserAuthentication;
import com.bbpos.store.UserStore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@EnableAutoConfiguration
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class LoginAPITests {
	
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext webApplicationContext;	
		
	@Autowired
	private UserAuthentication userAuthentication;	
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@BeforeEach
	public void setUp() {
		 mockMvc = webAppContextSetup(webApplicationContext).build();		 
	}		

	@Test
	public void testPostExample() throws Exception {
		
		//sample data
		String usernameTestData = "bbposTestLogin";
		String passwordTestData = "000000000";
		
		//user data store in application context
		UserStore userStore = webApplicationContext.getBean(UserStore.class);
		
		//manually insert user to user data store
		JwtRequest userHashedPwd = new JwtRequest();
		
		userHashedPwd.setUsername(usernameTestData);
		
		userHashedPwd.setPassword(bcryptEncoder.encode(passwordTestData));
		
		userStore.getUserlist().add(userHashedPwd);
		
		//set up user obj to simulate normal login
		JwtRequest userNormalLogin = new JwtRequest();
		
		userNormalLogin.setUsername(usernameTestData);
		
		userNormalLogin.setPassword(passwordTestData);
		
		//test login and get token
		String json = mapper.writeValueAsString(userNormalLogin);
		MvcResult requestResult = mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON)
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(requestResult.getResponse().getContentAsString());
		
		//check any token
		String token = jsonNode.get("token").asText();
		
		assertEquals(token.length() > 0, true);
		
		
	}
   
	
}
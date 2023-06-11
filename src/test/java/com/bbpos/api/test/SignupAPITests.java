package com.bbpos.api.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


import java.util.List;


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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.bbpos.request.JwtRequest;
import com.bbpos.security.UserAuthentication;
import com.bbpos.store.UserStore;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@EnableAutoConfiguration
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
public class SignupAPITests {
	
	private MockMvc mockMvc;

	private static ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private WebApplicationContext webApplicationContext;	
		
	@Autowired
	private UserAuthentication userAuthentication;	
	
	@BeforeEach
	public void setUp() {
		 mockMvc = webAppContextSetup(webApplicationContext).build();		 
	}		

	@Test
	public void testPostExample() throws Exception {
		
		//sample data
		String usernameTestData = "bbposTest";
		String passwordTestData = "000000000";
		
		//user data store in application context
		Object userStore = webApplicationContext.getBean(UserStore.class);
		
		
		//any existing user before test?
		List userList = ((UserStore) userStore).getUserlist();	
		
		//use list size before test
		int userListSize = userList.size();
		
		JwtRequest user = new JwtRequest();
		
		user.setUsername(usernameTestData);
		user.setPassword(passwordTestData);
		
		String json = mapper.writeValueAsString(user);
		mockMvc.perform(post("/api/auth/signup").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		//one more as we just sign up a new one
		assertEquals(userList.size(), userListSize+1);		
		
		//get last user, the one just sign up
		JwtRequest userFromStore = (JwtRequest) userList.get(userList.size()-1);
		
		//check username
		assertEquals(userFromStore.getUsername(), usernameTestData);
		
		//try to login
		boolean testLoginResult = userAuthentication.authenticate(usernameTestData, passwordTestData);
		
		assertEquals(testLoginResult, true);
	}
   
	
}
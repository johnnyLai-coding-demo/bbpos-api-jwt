package com.bbpos.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbpos.request.JwtRequest;
import com.bbpos.response.JwtResponse;
import com.bbpos.security.UserAuthentication;
import com.bbpos.service.GetUserDetailsService;
import com.bbpos.store.UserStore;
import com.bbpos.util.JwtTokenUtil;

@RestController
public class ApiController {

	@Autowired
	private GetUserDetailsService userDetailsService;

	@Autowired
	private UserAuthentication userAuthentication;	
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
    @Autowired
    ConfigurableApplicationContext applicationContext;
	
	@RequestMapping({ "/api/test/all" })
	public String firstPage() {
		return "Hello World";
	}

	@RequestMapping({ "/api/test/user" })
	public String secondPage(@RequestHeader("Authorization") String token) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "Hey " + userDetails.getUsername();
	}	
	
	@RequestMapping(value = "/api/auth/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody JwtRequest authenticationRequest) throws Exception {

		userAuthentication.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
//		final UserDetails userDetails2 = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}	

	@RequestMapping(value = "/api/auth/signup", method = RequestMethod.POST)
	public ResponseEntity<?> signup(@RequestBody JwtRequest signupRequest) throws Exception {
		
		
		signupRequest.setPassword(bcryptEncoder.encode(signupRequest.getPassword()));
		
		System.out.println("Encrypted " + signupRequest.getPassword());
		
		Object userStore = applicationContext.getBean(UserStore.class);

		
		
		List userList = ((UserStore) userStore).getUserlist();
		
		
		userList.add(signupRequest);
		System.out.print("number of users " + userList.size());
		
//		//userAuthentication.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//
//		final UserDetails userDetails = userDetailsService
//				.loadUserByUsername(authenticationRequest.getUsername());
//
//		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok("User signup OK");
	}	
	
}



/*
@Component
public class HelloWorldService {

	@Value("${name:World}")
	private String name;

	public String getHelloMessage() {
		return "Hello " + this.name;
	}

}
*/
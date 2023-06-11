package com.bbpos.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bbpos.request.JwtRequest;
import com.bbpos.store.UserStore;

@Service
public class GetUserDetailsService implements UserDetailsService {

    @Autowired
    ConfigurableApplicationContext applicationContext;
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Object userStore = applicationContext.getBean(UserStore.class);
		
		List userList = ((UserStore) userStore).getUserlist();
		
		System.out.println("user size from getdetail " + userList.size());
		
		JwtRequest user;
		
		for (int i=0; i<userList.size(); i++) {
			user = (JwtRequest) userList.get(i);
			if (user.getUsername().equals(username))	{
				return new User(user.getUsername(), user.getPassword(),
						new ArrayList<>());
			}
		}
		
		
//		if ("bbpos".equals(username)) {
//			return new User("bbpos", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
//					new ArrayList<>());
//		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
	}
}
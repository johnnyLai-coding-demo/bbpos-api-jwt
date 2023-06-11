package com.bbpos.store;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserStore {

	List list = new ArrayList();
	public List getUserlist() {
		return list;
	}
	
}

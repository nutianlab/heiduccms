package com.heiduc.rest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;



public class RestManager extends Application {
	
	
	private Set<Object> singletons = new HashSet<Object>();  
    private Set<Class<?>> classes = new HashSet<Class<?>>();

    
    
	public RestManager() {
		
	}

	@Override
	public Set<Class<?>> getClasses() {
		return classes;
	}

	@Override
	public Map<String, Object> getProperties() {
		return super.getProperties();
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	
}

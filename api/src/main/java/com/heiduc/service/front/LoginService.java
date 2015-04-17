

package com.heiduc.service.front;

import java.util.Map;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface LoginService extends AbstractService {

	ServiceResponse login(final String email, final String password);
	
	ServiceResponse logout();
	
	ServiceResponse forgotPassword(String email);
	
	Map<String, String> getSystemProperties();
	
	ServiceResponse setLanguage(String language);
}

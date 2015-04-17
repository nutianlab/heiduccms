

package com.heiduc.service.front;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;


public interface FormService extends AbstractService {
	
	ServiceResponse send(final String name, Map<String, String> params);
	
	/**
	 * Send entered form. Protected by reCaptcha service.
	 * @param name - form name
	 * @param params-form parameters
	 * @param challenge - recaptcha challenge
	 * @param response - recaptcha response
	 * @return - service response.
	 */
	ServiceResponse send(final String name, Map<String, String> params,
			final String challenge, final String response, 
			HttpServletRequest request);
}



package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.rest.DummyRest;
import com.heiduc.rest.RestManager;

/**
 * @author Alexander Oleynik
 */
public class RestFilter extends AbstractFilter implements Filter {

	private static final Log logger = LogFactory.getLog(RestFilter.class);
	
	private RestManager manager;
	
	public RestFilter() {
		super();
		manager = new RestManager();
		manager.addService("dummy", new DummyRest());
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setCharacterEncoding("UTF-8");
		String result;
		try {
			result = manager.execute(httpRequest);
			logger.info("REST result: " + result);
			if (result != null) {
				httpResponse.setContentType("application/json");
				httpResponse.setStatus(200);
				httpResponse.getWriter().write(result);
			}
			else {
				httpResponse.setStatus(501);
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			httpResponse.setContentType("text/plain");
			httpResponse.setStatus(500);
			httpResponse.getWriter().write(ExceptionUtils.getStackTrace(e));
		}
	}		
	
}

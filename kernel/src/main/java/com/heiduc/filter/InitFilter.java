

package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.SetupBean;

/**
 * Application initial database creation filter.
 * @author Alexander Oleynik
 *
 */
public class InitFilter extends AbstractFilter implements Filter {
    
    private static final Log logger = LogFactory.getLog(SiteFilter.class);

    private static final String INIT_URL = "/init";
    
    public InitFilter() {
    	super();
    }
  
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getServletPath();
        if (url.equals(INIT_URL)) {
        	SetupBean setupBean = getBusiness().getSetupBean();
        	setupBean.init();
        	logger.info("Init was successfully completed.");
        	httpResponse.sendRedirect("/cms/");
        	return;
        }
        chain.doFilter(request, response);
        getBusiness().getSystemService().getCache().resetLocalCache();
    }
    
}

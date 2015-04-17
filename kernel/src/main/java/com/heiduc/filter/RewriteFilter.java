

package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.common.HeiducContext;

/**
 * @author Alexander Oleynik
 */
public class RewriteFilter extends AbstractFilter implements Filter {
    
    private static final Log logger = LogFactory.getLog(SiteFilter.class);

    public static class RewrittenRequestWrapper extends HttpServletRequestWrapper {

    	private String newURI;
    	private String newServletPath;
    	
        public RewrittenRequestWrapper(HttpServletRequest request) {
            super(request);
            newURI = HeiducContext.getInstance().getBusiness()
            		.getRewriteUrlBusiness().rewrite(request.getRequestURI());
            newServletPath = HeiducContext.getInstance().getBusiness()
    				.getRewriteUrlBusiness().rewrite(request.getServletPath());
        }
        
        @Override
        public String getRequestURI() {
            return newURI;
        }

        @Override
        public String getServletPath() {
            return newServletPath;
        }
        
    }
    
    public RewriteFilter() {
    	super();
    }
  
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	if (isSkipUrl(httpRequest.getServletPath())) {
            chain.doFilter(request, response);
    	}
    	else {
    		HttpServletRequest newRequest = new RewrittenRequestWrapper(httpRequest);
    	    chain.doFilter(newRequest, response);
    	}
    }

    private boolean isSkipUrl(String url) {
    	for (String u : SiteFilter.SKIP_URLS) {
    		if (url.startsWith(u)) return true;
    	}
    	return false;
    }
    
}

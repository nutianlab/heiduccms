

package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter extends AbstractFilter implements Filter {
	
	private String encoding = "UTF-8";
    
    public EncodingFilter() {
    	super();
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(this.encoding);
		response.setCharacterEncoding(this.encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		String encoding = config.getInitParameter("encoding");
		if (encoding != null && encoding.trim().length() > 0) {
			this.encoding = encoding;
		}
	}
}



package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.heiduc.business.impl.BusinessImpl;
import com.heiduc.business.impl.mq.MessageQueueImpl;
import com.heiduc.common.HeiducContext;
import com.heiduc.common.Session;
import com.heiduc.service.impl.BackServiceImpl;
import com.heiduc.service.impl.FrontServiceImpl;

/**
 * Context creation and request injection.
 * 
 * @author Alexander Oleynik
 *
 */
public class ContextFilter extends AbstractFilter implements Filter {
    
    public ContextFilter() {
    	super();
    }
    
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
//    	String path = ((HttpServletRequest)request).getServletPath(); 
    	HeiducContext ctx = HeiducContext.getInstance();
    	ctx.setRequest((HttpServletRequest)request);
    	ctx.setResponse((HttpServletResponse)response);
    	ctx.setConfig(null);
    	if (ctx.getMessageQueue() == null) {
        	ctx.setMessageQueue(new MessageQueueImpl());
    	}
    	if (ctx.getBusiness() == null) {
        	ctx.setBusiness(new BusinessImpl());
    	}
    	if (ctx.getFrontService() == null) {
        	ctx.setFrontService(new FrontServiceImpl());
    	}
    	if (ctx.getBackService() == null) {
        	ctx.setBackService(new BackServiceImpl());
    	}
    	ctx.getPageRenderingContext().clear();
    	ctx.setSession(new Session((HttpServletRequest)request));
    	chain.doFilter(request, response);
    	ctx.getPageRenderingContext().clear();
    	ctx.getSession().save((HttpServletResponse)response);
    }
}



package com.heiduc.filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.heiduc.common.HeiducContext;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class LanguageFilter extends AbstractFilter implements Filter {
    
    public LanguageFilter() {
    	super();
    }
  
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
    	HeiducContext ctx = HeiducContext.getInstance();
    	if (ctx.getSession().getLocale() == null) {
    		ctx.setLocale(request.getLocale());
    		ctx.getSession().setLocale(request.getLocale());
    	}
    	else {
    		ctx.setLocale(ctx.getSession().getLocale());
    	}
    	if (httpRequest.getParameter("language") != null) {
    		String languageCode = httpRequest.getParameter("language");
   			Locale locale = getLocale(languageCode);
   			LOGGER.info("Locale " + locale.getDisplayName());
   			ctx.setLocale(locale);
       		ctx.getSession().setLocale(locale);
    	}
        chain.doFilter(request, response);
    }
    
    public static Locale getLocale(String language) {
    	int undescore = language.indexOf("_");
    	if (undescore != -1) {
    		String[] codes = language.split("_");
    		if (codes.length > 1) {
        		return new Locale(codes[0], codes[1]);
    		}
    	}
    	return new Locale(language);
    }
    
}

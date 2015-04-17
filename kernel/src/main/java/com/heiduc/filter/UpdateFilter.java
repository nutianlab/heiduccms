

package com.heiduc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.SetupBean;
import com.heiduc.update.UpdateException;
import com.heiduc.update.UpdateManager;

/**
 * @author Alexander Oleynik
 */
public class UpdateFilter extends AbstractFilter implements Filter {
    
    private static final Log logger = LogFactory.getLog(UpdateFilter.class);

    private static final String UPDATE_URL = "/update";
    
    public UpdateFilter() {
    	super();
    }
  
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getServletPath();
        if (url.equals(UPDATE_URL)) {
        	String msg = "<h2>Heiduc CMS " + SetupBean.FULLVERSION + " Update</h2>";
        	try {
        		UpdateManager updateManager = new UpdateManager(getBusiness());
        		String updateMsg = updateManager.update();
        		if (StringUtils.isEmpty(updateMsg)) {
        			updateMsg = "Database is already updated.";
        		}
        		writeContent(httpResponse, msg + updateMsg);
            	return;
        	}
        	catch (UpdateException e) {
        		writeContent(httpResponse, msg 
        				+ "<p class=\"color:red;\">Errors during update! " 
        				+ e.getMessage() + "</p>");
        		return;
        	}
        }
        chain.doFilter(request, response);
    }
    
    private void writeContent(HttpServletResponse response, String content)
    		throws IOException {
    	response.setContentType("text/html");
    	response.setCharacterEncoding("UTF-8");
    	response.getWriter().append("<html><body>" + content + "</body></html>");
    }
    
}



package com.heiduc.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.SimpleMessage;

/**
 * Execute plugin cron tasks.
 * @author Alexander Oleynik
 *
 */
public class PluginCronFilter extends AbstractFilter implements Filter {
    
    private static final Log logger = LogFactory.getLog(PluginCronFilter.class);

    private static final String PLUGIN_CRON_URL = "/_ah/cron/plugin";
    private static final String PAGE_PUBLISH_CRON_URL = "/_ah/cron/page_publish";
    
    public PluginCronFilter() {
    	super();
    }
  
    public void doFilter(ServletRequest request, ServletResponse response, 
    		FilterChain chain) throws IOException, ServletException {
    	Date now = new Date();
    	HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String url = httpRequest.getServletPath();
        boolean processed = false;
        if (url.equals(PLUGIN_CRON_URL)) {
        	getBusiness().getPluginBusiness().cronSchedule(now);
    		processed = true;
        }
        if (url.equals(PAGE_PUBLISH_CRON_URL)) {
    		getMessageQueue().publish(new SimpleMessage(
    				Topic.PAGE_PUBLISH_CRON));
    		logger.info("Added new page publish task");
    		processed = true;
        }
        if (processed) {
        	writeContent(httpResponse, "<h4>OK</h4>");
        	return;
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

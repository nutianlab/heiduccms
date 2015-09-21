package org.heiduc.oauth2.filter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import com.heiduc.filter.AbstractFilter;

public class Oauth2Filter extends AbstractFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException
	    {
	        HttpServletResponse res = (HttpServletResponse)response;
	        try
	        {
	            System.out.println("========Oauth2Filter========");
	            String header = ((HttpServletRequest)request).getHeader("access_token");
	            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest((HttpServletRequest)request, new ParameterStyle[] {
	                ParameterStyle.HEADER
	            });
	            String accessToken = oauthRequest.getAccessToken();
	            System.out.println((new StringBuilder()).append("accessToken:").append(accessToken).toString());
	        }
	        catch(OAuthProblemException e)
	        {
	            e.printStackTrace();
	        }
	        catch(OAuthSystemException e)
	        {
	            e.printStackTrace();
	            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "error trying to access oauth server", e);
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	        chain.doFilter(request, response);
	    }


}

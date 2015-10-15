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

import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import com.heiduc.common.HeiducContext;
import com.heiduc.entity.TokenEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.filter.AbstractFilter;

public class Oauth2Filter extends AbstractFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	        throws IOException, ServletException
	    {
	        HttpServletResponse res = (HttpServletResponse)response;
	        try
	        {
	            OAuthAccessResourceRequest oauthRequest = new OAuthAccessResourceRequest((HttpServletRequest)request, new ParameterStyle[] {
	                ParameterStyle.HEADER
	            });
	            String accessToken = oauthRequest.getAccessToken();
	            if (getBusiness().getOauth2Business().checkAccessToken(accessToken)) {//accessToken 
	            	//根据accessToken获取Token
		            TokenEntity token = getDao().getTokenDao().getByAccessToken(accessToken);
		            //根据用户名获取用户
		            UserEntity user = getDao().getUserDao().getByName(token.getUserName());
		            HeiducContext.getInstance().setUser(user);
		            chain.doFilter(request, response);
	  		    	return;
				}
	            
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
	        OAuthResponse oauthResponse = null;
	    	try {
				oauthResponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
						 		  .setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						 		  .setErrorDescription("accessToken INVALID").setParam("code", "20101") // 认证失败
						 		  .buildJSONMessage();
			} catch (OAuthSystemException e) {
			}
	    	
	    	res.setStatus(oauthResponse.getResponseStatus());
	    	res.getOutputStream().write(oauthResponse.getBody().getBytes());
	    	res.getOutputStream().flush();
	        return;
	    }


}

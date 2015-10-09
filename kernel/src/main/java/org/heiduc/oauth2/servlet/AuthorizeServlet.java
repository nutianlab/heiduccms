package org.heiduc.oauth2.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import com.heiduc.servlet.AbstractServlet;

public class AuthorizeServlet extends AbstractServlet {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String AUTHORIZE_URL = "/";

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException
	    {
	        doPost(request, response);
	    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException
		  {
		    try
		    {
		      OAuthAuthzRequest oauthRequest = new OAuthAuthzRequest(request);

		      if (!getBusiness().getOauth2Business().checkClientId(oauthRequest.getClientId())) {
		        OAuthResponse oresponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		.setError(OAuthError.TokenResponse.INVALID_CLIENT)
		        		.setErrorDescription("client_id INVALID")
		        		.buildJSONMessage();

		        response.setStatus(oresponse.getResponseStatus());
		        response.getOutputStream().write(oresponse.getBody().getBytes());
		        response.getOutputStream().flush();
		        return;
		      }

		      if (request.getMethod().equalsIgnoreCase("get")) {
		        response.sendRedirect(AUTHORIZE_URL);
		        return;
		      }

		      String username = request.getParameter("username");
		      String password = request.getParameter("password");
		      OAuthResponse oresponse = null;

		      if (!getBusiness().getOauth2Business().login(username, password)) {
		        oresponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		.setError(OAuthError.TokenResponse.INVALID_CLIENT)
		        		.setErrorDescription("username or password INVALID")
		        		.buildJSONMessage();

		        response.sendRedirect(AUTHORIZE_URL+"?error_code=4001");
		        return;
		      }

		      String authorizationCode = null;
		      String accessToken = null;

		      String responseType = oauthRequest.getParam(OAuth.OAUTH_RESPONSE_TYPE);
		      OAuthIssuerImpl oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		      //授权类型 code
		      if (responseType.equals(ResponseType.CODE.toString()))
		      {
		        String redirectURI = oauthRequest.getParam(OAuth.OAUTH_REDIRECT_URI);

		        authorizationCode = oauthIssuerImpl.authorizationCode();
		        getBusiness().getOauth2Business().addAuthCode(authorizationCode, username);

		        oresponse = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND)
		        		.setCode(authorizationCode)
		        		.location(redirectURI)
		        		.buildQueryMessage();

		        response.addHeader("Location", oresponse.getLocationUri());
		        response.setStatus(oresponse.getResponseStatus());
		        return;
		      }
		      //授权类型 token
		      else if (responseType.equals(ResponseType.TOKEN.toString())) {
		        accessToken = oauthIssuerImpl.accessToken();
		        getBusiness().getOauth2Business().addAccessToken(accessToken, username);

		        oresponse = OAuthASResponse.authorizationResponse(request, HttpServletResponse.SC_FOUND)
		        		.setAccessToken(accessToken)
		        		.setExpiresIn(String.valueOf(getBusiness().getOauth2Business().getExpireIn()))
		        		//权限范围
		        		.setScope("basic")
		        		.buildJSONMessage();

		        response.setStatus(oresponse.getResponseStatus());
		        response.getOutputStream().write(oresponse.getBody().getBytes());
		        response.getOutputStream().flush();
		        return;
		      }

		      //如授权类型非code或token，不支持
		      oresponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		    		  .setErrorDescription("UNSUPPORTED")
		    		  .buildJSONMessage();

		      response.setStatus(oresponse.getResponseStatus());
		      response.getOutputStream().write(oresponse.getBody().getBytes());
		      response.getOutputStream().flush();
		    }
		    catch (OAuthProblemException e)
		    {
		      e.printStackTrace();
		    } catch (OAuthSystemException e) {
		      e.printStackTrace();
		    }
		  }

	@Override
	public void init(ServletConfig config) throws ServletException {
		String authorizeURL = config.getInitParameter("authorizeURL");
		if (!StringUtils.isEmpty(authorizeURL)) {
			AUTHORIZE_URL = authorizeURL;
		}
	}
	
}

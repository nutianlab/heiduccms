package org.heiduc.oauth2.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.TokenType;

import com.heiduc.servlet.AbstractServlet;

public class AccessTokenServlet extends AbstractServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	        throws ServletException, IOException
	    {
	        doPost(req, resp);
	    }

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		    throws ServletException, IOException
		  {
		    try
		    {
		      OAuthTokenRequest oauthRequest = new OAuthTokenRequest(req);

		      //验证client_id
		      if (!getBusiness().getOauth2Business().checkClientId(oauthRequest.getClientId())) {
		        OAuthResponse oresponse = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		.setError(OAuthError.TokenResponse.UNAUTHORIZED_CLIENT)
		        		.setErrorDescription("client_id INVALID")
		        		.buildJSONMessage();

		        resp.setStatus(oresponse.getResponseStatus());
		        resp.getOutputStream().write(oresponse.getBody().getBytes());
		        resp.getOutputStream().flush();
		        return;
		      }

		      OAuthResponse response = null;
		      String username = null;
		      //授权模式  authorization_code
		      if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.AUTHORIZATION_CODE.toString())) {
		        String authCode = oauthRequest.getParam(OAuth.OAUTH_CODE);
		        if (!getBusiness().getOauth2Business().checkAuthCode(authCode)) {
		          response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		  .setError(OAuthError.TokenResponse.INVALID_GRANT)
		        		  .setErrorDescription("clientId INVALID")
		        		  .buildJSONMessage();

		          resp.setStatus(response.getResponseStatus());
		          resp.getOutputStream().write(response.getBody().getBytes());
		          resp.getOutputStream().flush();
		          return;
		        }
		        username = getBusiness().getOauth2Business().getUsernameByAuthCode(authCode);
		      } 
		      //password
		      else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.PASSWORD.toString()))
		      {
		        if (!getBusiness().getOauth2Business().login(oauthRequest.getUsername(), oauthRequest.getPassword())) {
		          response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		  .setError(OAuthError.TokenResponse.INVALID_GRANT)
		        		  .setErrorDescription("username or password INVALID")
		        		  .buildJSONMessage();

		          resp.setStatus(response.getResponseStatus());
		          resp.getOutputStream().write(response.getBody().getBytes());
		          resp.getOutputStream().flush();
		          return;
		        }
		        username = oauthRequest.getUsername();
		      }
		      //client_credentials
		      else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.CLIENT_CREDENTIALS.toString())) {
		          response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		  .setError(OAuthError.TokenResponse.INVALID_GRANT)
		        		  .setErrorDescription("client_credentials UNSUPPORTED")
		        		  .buildJSONMessage();

		          resp.setStatus(response.getResponseStatus());
		          resp.getWriter().write(response.getBody());
		          return;
		      }
		      //refresh_token
		      else if (oauthRequest.getParam(OAuth.OAUTH_GRANT_TYPE).equals(GrantType.REFRESH_TOKEN.toString())) {
		          String refreshToken = oauthRequest.getRefreshToken();
		          if (!getBusiness().getOauth2Business().checkRefreshToken(refreshToken)) {
		            response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		            		.setError(OAuthError.TokenResponse.INVALID_GRANT)
		            		.setErrorDescription("refresh_token INVALID").buildJSONMessage();

		            resp.setStatus(response.getResponseStatus());
		            resp.getOutputStream().write(response.getBody().getBytes());
		            resp.getOutputStream().flush();
		            return;
		          }
		          username = getBusiness().getOauth2Business().getUsernameByRefreshToken(refreshToken);
		      }else{//不支持
		    	  response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
		        		  .setError(OAuthError.TokenResponse.INVALID_GRANT)
		        		  .setErrorDescription("grant_type UNSUPPORTED")
		        		  .buildJSONMessage();

		          resp.setStatus(response.getResponseStatus());
		          resp.getWriter().write(response.getBody());
		          return;
		      }

		      OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
		      String accessToken = oauthIssuerImpl.accessToken();
		      String refreshToken = oauthIssuerImpl.refreshToken();

		      getBusiness().getOauth2Business().addAccessToken(accessToken, username);
		      getBusiness().getOauth2Business().addRefreshToken(refreshToken, username);

		      response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
		    		  .setRefreshToken(refreshToken)
		    		  .setAccessToken(accessToken)
		    		  .setTokenType(TokenType.BEARER.name())
		    		  .setExpiresIn(String.valueOf(getBusiness().getOauth2Business().getExpireIn()))
		    		  .setScope("basic")
		    		  .buildJSONMessage();

		      resp.setStatus(response.getResponseStatus());
		      resp.getOutputStream().write(response.getBody().getBytes());
		      resp.getOutputStream().flush();
		    }
		    catch (OAuthProblemException e) {
		      OAuthResponse res = null;
		      try {
		        res = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST).error(e).buildJSONMessage();
		      }
		      catch (OAuthSystemException e1) {
		        e1.printStackTrace();
		      }
		      resp.setStatus(res.getResponseStatus());
		      resp.getOutputStream().write(res.getBody().getBytes());
		      resp.getOutputStream().flush();
		    }
		    catch (OAuthSystemException e) {
		      e.printStackTrace();
		    }
		  }


}

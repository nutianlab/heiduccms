package org.heiduc.oauth2.handler;


public interface AuthenticationHandler {

	// 登录认证
	boolean authenticate(Credentials credentials);
	
}

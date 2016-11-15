package org.heiduc.oauth2.handler;


public class AbstractAuthenticationHandler implements AuthenticationHandler {

	@Override
	public boolean authenticate(Credentials credentials) {
		
		return false;
	}
	
	/*public void before(){
		
	}
	
	
	public void after(){
		
	}*/
	
	public void onSuccess(Credentials credentials){
		
	}
	
	public void onFailue(Credentials credentials){
		
	}

}

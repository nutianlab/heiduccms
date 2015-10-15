package com.heiduc.business;

import com.heiduc.entity.TokenEntity;

public interface Oauth2Business {
	
	public abstract boolean checkClientId(String clientId);

    public abstract void addAuthCode(String authCode, String username);

    public abstract boolean checkAuthCode(String authCode);

    public abstract boolean checkRefreshToken(String refreshToken);

    public abstract String getUsernameByAuthCode(String authCode);

    public abstract boolean login(String username, String password);

    public abstract long getExpireIn();

    public abstract void addAccessToken(TokenEntity token);
    
    public boolean checkAccessToken(String accessToken);
    //public abstract void addRefreshToken(TokenEntity token);

    public abstract String getUsernameByRefreshToken(String refreshToken);


}

package com.heiduc.business;

public interface Oauth2Business {
	
	public abstract boolean checkClientId(String clientId);

    public abstract void addAuthCode(String authCode, String username);

    public abstract boolean checkAuthCode(String authCode);

    public abstract boolean checkRefreshToken(String refreshToken);

    public abstract String getUsernameByAuthCode(String authCode);

    public abstract void addAccessToken(String accessToken, String username);

    public abstract boolean login(String username, String password);

    public abstract long getExpireIn();

    public abstract void addRefreshToken(String refreshToken, String username);

    public abstract String getUsernameByRefreshToken(String refreshToken);


}

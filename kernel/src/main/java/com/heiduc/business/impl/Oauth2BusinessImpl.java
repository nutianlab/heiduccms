package com.heiduc.business.impl;

import org.apache.commons.lang.StringUtils;



import com.heiduc.business.Oauth2Business;
import com.heiduc.common.BCrypt;
import com.heiduc.entity.Oauth2ClientEntity;
import com.heiduc.entity.TokenEntity;
import com.heiduc.entity.UserEntity;

public class Oauth2BusinessImpl extends AbstractBusinessImpl 
implements Oauth2Business {

	@Override
	public boolean checkClientId(String clientId) {
		Oauth2ClientEntity client = getDao().getOauth2Dao().getByClientId(clientId);
        return client != null && !client.isDisabled();

	}

	@Override
	public void addAuthCode(String authorizationCode, String username) {
		getSystemService().getCache().put(authorizationCode, username);
	}

	@Override
	public boolean checkAuthCode(String authCode) {
		return getSystemService().getCache().get(authCode) != null;

	}

	@Override
	public boolean checkRefreshToken(String refreshToken) {
		return getSystemService().getCache().get(refreshToken) != null;

	}
	
	@Override
	public boolean checkAccessToken(String accessToken) {
		
		return getDao().getTokenDao().checkAccessToken(accessToken);
	}

	@Override
	public String getUsernameByAuthCode(String authCode) {
		return (String)getSystemService().getCache().getAndRemove(authCode);

	}

	@Override
	public void addAccessToken(TokenEntity token) {
		TokenEntity entity = getDao().getTokenDao().get(token);
		if (entity == null) {
			entity = token;
		}
		entity.setAccessToken(token.getAccessToken());
		entity.setRefreshToken(token.getRefreshToken());
		getDao().getTokenDao().save(entity);
		getSystemService().getCache().put(token.getAccessToken()+token.getClientId(), token.getUserName());
	}

	/*@Override
	public void addRefreshToken(TokenEntity token) {
		getSystemService().getCache().put(token.getRefreshToken()+token.getClientId(), token.getUserName());
	}*/
	
	@Override
	public String getUsernameByRefreshToken(String refreshToken) {
		return (String)getSystemService().getCache().getAndRemove(refreshToken);
	}
	
	@Override
	public boolean login(String username, String password) {
		UserEntity user = getDao().getUserDao().getByName(username);
		if (user == null || user.isDisabled()) {
			return false;
		}
		
		if (user.getPassword() == null) {
			if (!StringUtils.isEmpty(password)) {
				return false;
			}
		}
		else {		
			try {
				if (!BCrypt.checkpw(password, user.getPassword())) {
					return false;
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public long getExpireIn() {
		 return 3600L;
	}
	

}

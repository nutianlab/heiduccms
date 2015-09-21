package com.heiduc.dao;

import com.heiduc.entity.Oauth2ClientEntity;

public interface Oauth2Dao extends BaseDao<Oauth2ClientEntity> {
	
	
	 Oauth2ClientEntity getByName(String name);

	 Oauth2ClientEntity getByClientId(String clientId);
	

}

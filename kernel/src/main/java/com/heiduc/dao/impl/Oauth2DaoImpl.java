package com.heiduc.dao.impl;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.Oauth2Dao;
import com.heiduc.entity.Oauth2ClientEntity;

public class Oauth2DaoImpl extends BaseDaoImpl<Oauth2ClientEntity> 
implements Oauth2Dao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Oauth2DaoImpl() {
		super(Oauth2ClientEntity.class);
	}
	
	@Override
	public Oauth2ClientEntity getByName(String name) {
		Query q = newQuery();
        q.addFilter("clientName", FilterOperator.EQUAL, name);
        return (Oauth2ClientEntity)selectOne(q, "getByName", params(name));
	}

	@Override
	public Oauth2ClientEntity getByClientId(String clientId) {
		Query q = newQuery();
        q.addFilter("clientId", org.heiduc.api.datastore.Query.FilterOperator.EQUAL, clientId);
        return (Oauth2ClientEntity)selectOne(q, "getByName", params(clientId));

	}

}



package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import org.heiduc.api.datastore.Query;

import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.TokenDao;
import com.heiduc.entity.TokenEntity;

public class TokenDaoImpl extends BaseDaoImpl<TokenEntity> 
		implements TokenDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenDaoImpl() {
		super(TokenEntity.class);
	}

	@Override
	public TokenEntity get(TokenEntity entity) {
		Query q = newQuery();
		q.addFilter("userName", EQUAL, entity.getUserName());
		q.addFilter("clientId", EQUAL, entity.getClientId());
		return selectOne(q, "get", params(entity.getUserName(),entity.getClientId()));
	}

	@Override
	public boolean checkAccessToken(String accessToken) {
		Query q = newQuery();
		q.addFilter("accessToken", EQUAL, accessToken);
		return selectOne(q, "checkAccessToken", params(accessToken)) == null ? false : true;
	}
	
	@Override
	public TokenEntity getByAccessToken(String accessToken) {
		Query q = newQuery();
		q.addFilter("accessToken", EQUAL, accessToken);
		return selectOne(q, "getByAccessToken", params(accessToken));
	}

}

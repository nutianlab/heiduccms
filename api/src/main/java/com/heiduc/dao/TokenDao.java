

package com.heiduc.dao;

import com.heiduc.entity.TokenEntity;

/**
 * @author zyj
 */
public interface TokenDao extends BaseDao<TokenEntity> {

	TokenEntity get(TokenEntity entity);
	
	TokenEntity getByAccessToken(String accessToken);
	
	boolean checkAccessToken(String accessToken);
}



package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.getLongProperty;
import static com.heiduc.utils.EntityUtil.getStringProperty;
import static com.heiduc.utils.EntityUtil.setProperty;

import org.heiduc.api.datastore.Entity;


/**
 * @author zyj
 */
public class TokenEntity extends BaseEntityImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String userName;
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private Long expiresIn;
	private String scope;
	private String clientId;
	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public TokenEntity() {
		super();
		this.expiresIn = -1l;
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		userName = getStringProperty(entity, "userName");
		accessToken = getStringProperty(entity, "accessToken");
		refreshToken = getStringProperty(entity, "refreshToken");
		tokenType = getStringProperty(entity, "tokenType");
		expiresIn = getLongProperty(entity, "tokenType",-1l);
		scope = getStringProperty(entity, "scope");
		clientId = getStringProperty(entity, "clientId");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "userName", userName, true);
		setProperty(entity, "accessToken", accessToken, true);
		setProperty(entity, "refreshToken", refreshToken, true);
		setProperty(entity, "tokenType", tokenType, false);
		setProperty(entity, "expiresIn", expiresIn, false);
		setProperty(entity, "scope", scope, false);
		setProperty(entity, "clientId", clientId, true);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public Long getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}

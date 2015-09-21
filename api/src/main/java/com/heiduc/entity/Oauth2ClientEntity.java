package com.heiduc.entity;

import org.heiduc.api.datastore.Entity;
import static com.heiduc.utils.EntityUtil.*;

public class Oauth2ClientEntity extends BaseEntityImpl {
	
	
	private static final long serialVersionUID = 2L;

	private String clientId;
	private String clientName;
    private String clientSecret;
    private boolean disabled;

	public Oauth2ClientEntity() {
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		clientId = getStringProperty(entity, "clientId");
        clientName = getStringProperty(entity, "clientName");
        clientSecret = getStringProperty(entity, "clientSecret");
        disabled = getBooleanProperty(entity, "disabled", false);

	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "clientId", clientId, true);
        setProperty(entity, "clientName", clientName, true);
        setProperty(entity, "clientSecret", clientSecret, true);
        setProperty(entity, "disabled", disabled, false);

	}

	public Oauth2ClientEntity(final String clientId, final String clientName, 
			final String clientSecret) {
		this.clientId = clientId;
		this.clientName = clientName;
		this.clientSecret = clientSecret;
	}
	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}

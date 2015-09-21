package org.heiduc.oauth2;

import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.Oauth2ClientEntity;
import com.heiduc.utils.UUIDUtil;

public class Oauth2ClientTest extends AbstractOauth2Test {
	
	
	private Oauth2ClientEntity addClient(final String clientId, final String clientName,final String clientSecret) {
		
		Oauth2ClientEntity client = new Oauth2ClientEntity(clientId,clientName,clientSecret);
		getDao().getOauth2Dao().save(client);
		return client;
	}
	
	

	public void testAddClient(){
		
		String clientId = UUIDUtil.next();
		String clientName = "test-client";
		String clientSecret = UUIDUtil.next();
		
		
		Oauth2ClientEntity client = addClient(clientId,clientName,clientSecret);
		
		assertTrue(getBusiness().getOauth2Business().checkClientId(clientId));
		
		
		
	}
}

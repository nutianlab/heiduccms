package org.heiduc.api.datastore;

import org.heiduc.api.datastore.dialect.MongoDBDatastoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatastoreServiceFactory {
	
	private  static final Logger logger = LoggerFactory.getLogger(DatastoreServiceFactory.class);
	
	private DatastoreServiceFactory(){
		
	}

	public static DatastoreService getDatastoreService() {
		try {
			return new MongoDBDatastoreServiceImpl();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}

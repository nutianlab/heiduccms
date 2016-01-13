package org.heiduc.api.datastore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DatastoreServiceFactory {
	
	private  static final Logger logger = LoggerFactory.getLogger(DatastoreServiceFactory.class);
	
	private DatastoreServiceFactory(){
		
	}

	public static DatastoreService getDatastoreService() {
		try {
			return new DatastoreServiceImpl();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

}

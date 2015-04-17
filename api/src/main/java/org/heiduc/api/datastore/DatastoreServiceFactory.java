package org.heiduc.api.datastore;


public class DatastoreServiceFactory {
	

	public static DatastoreService getDatastoreService() {
		try {
			return new DatastoreServiceImpl();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}

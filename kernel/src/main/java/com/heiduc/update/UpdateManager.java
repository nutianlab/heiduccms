

package com.heiduc.update;

import java.util.ArrayList;
import java.util.List;

import org.heiduc.api.datastore.DatastoreService;
import org.heiduc.api.datastore.DatastoreServiceFactory;
import org.heiduc.api.datastore.Entity;
import org.heiduc.api.datastore.Query;


import com.heiduc.business.Business;
import com.heiduc.dao.Dao;

/**
 * @author Alexander Oleynik
 */
public class UpdateManager {

	private List<UpdateTask> tasks;
	private DatastoreService datastore;
	private Business business;
	
	public UpdateManager(Business aBusiness) {
		business = aBusiness;
		datastore = DatastoreServiceFactory.getDatastoreService(); 
		tasks = new ArrayList<UpdateTask>();
		tasks.add(new UpdateTask04(business));
		tasks.add(new UpdateTask05(business));
		tasks.add(new UpdateTask06(business));
		tasks.add(new UpdateTask07(business));
		tasks.add(new UpdateTask08(business));
		tasks.add(new UpdateTask09(business));
	}
	
	public String update() throws UpdateException {
		if (getConfig().getProperty("version") == null) {
			addConfigVersion();
		}
		StringBuffer result = new StringBuffer();
		for (UpdateTask task : tasks) {
			if (getConfig().getProperty("version").equals(task.getFromVersion())) {
				result.append("<p>").append(task.update()).append("</p>");
				Entity config = getConfig();
				config.setProperty("version", task.getToVersion());
				datastore.put(config);
			}
		}
		business.getSystemService().getCache().clear();
		return result.toString();
	}
	
	private Entity getConfig() {
		Query query = new Query("ConfigEntity");
		return datastore.prepare(query).asIterator().next();
	}
	
	private void addConfigVersion() {
		Entity config = getConfig();
		config.setProperty("version", "0.0.2");
		config.setProperty("enableRecaptcha", false);
		datastore.put(config);
	}
	
	private Dao getDao() {
		return business.getDao();
	}
	
}

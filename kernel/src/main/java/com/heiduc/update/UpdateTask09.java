

package com.heiduc.update;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;

/**
 * @author Alexander Oleynik
 */
public class UpdateTask09 implements UpdateTask {

	private Business business;
	
	public UpdateTask09(Business aBusiness) {
		business = aBusiness;
	}
	
	private Dao getDao() {
		return business.getDao();
	}
	
	private Business getBusiness() {
		return business;
	}

	@Override
	public String getFromVersion() {
		return "0.8";
	}

	@Override
	public String getToVersion() {
		return "0.9";
	}

	@Override
	public String update() throws UpdateException {
		updatePlugins();
		return "Successfully updated to 0.9 version.";
	}

	private void updatePlugins() {
		for (PluginEntity plugin : getDao().getPluginDao().select()) {
			plugin.setDisabled(true);
			getDao().getPluginDao().save(plugin);
		}
	}

}

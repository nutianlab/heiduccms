

package com.heiduc.update;


import com.heiduc.business.Business;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.SimpleMessage;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;
import com.heiduc.update.verion_0_7.PageTitleUpdate;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class UpdateTask07 implements UpdateTask {

	private Business business;
	
	public UpdateTask07(Business aBusiness) {
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
		return "0.6";
	}

	@Override
	public String getToVersion() {
		return "0.7";
	}

	@Override
	public String update() throws UpdateException {
		updatePlugins();
		updatePages();
		return "Successfully updated to 0.7 version.";
	}

	private void updatePlugins() {
		for (PluginEntity plugin : getDao().getPluginDao().select()) {
			plugin.setDisabled(true);
			getDao().getPluginDao().save(plugin);
		}
	}

	private void updatePages() {
		Message msg = new SimpleMessage(PageTitleUpdate.class);
		getBusiness().getMessageQueue().publish(msg);
	}
	
}

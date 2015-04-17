

package com.heiduc.update;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;
import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.entity.UserEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class UpdateTask05 implements UpdateTask {

	private Business business;
	
	public UpdateTask05(Business aBusiness) {
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
		return "0.4";
	}

	@Override
	public String getToVersion() {
		return "0.5";
	}

	@Override
	public String update() throws UpdateException {
		updateStructureTemplates();
		updateUsers();
		updatePlugins();
		return "Successfully updated to 0.5 version.";
	}

	private void updateStructureTemplates() {
		for (StructureTemplateEntity template : getDao()
				.getStructureTemplateDao().select()) {
			template.setName(template.getTitle());
			getDao().getStructureTemplateDao().save(template);
		}
	}
	
	private void updateUsers() {
		for (UserEntity user : getDao().getUserDao().select()) {
			user.setDisabled(false);
			getDao().getUserDao().save(user);
		}
	}
	
	private void updatePlugins() {
		for (PluginEntity plugin : getDao().getPluginDao().select()) {
			plugin.setDisabled(true);
			getDao().getPluginDao().save(plugin);
		}
	}

}

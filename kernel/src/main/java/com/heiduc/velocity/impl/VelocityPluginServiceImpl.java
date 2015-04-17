

package com.heiduc.velocity.impl;

import java.util.HashMap;
import java.util.Map;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;
import com.heiduc.global.SystemService;
import com.heiduc.velocity.FormVelocityService;
import com.heiduc.velocity.VelocityPluginService;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class VelocityPluginServiceImpl implements VelocityPluginService {

	private FormVelocityService form;
	private Business business;
	
	public VelocityPluginServiceImpl(Business aBusiness) {
		business = aBusiness;
		form = new FormVelocityServiceImpl(getBusiness());
	}
	
	@Override
	public Map<String, Object> getPlugins() {
		Map<String, Object> services = new HashMap<String, Object>();
		services.put("form", form);
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			try {
				Object velocityPlugin = getBusiness().getPluginBusiness()
					.getVelocityPlugin(plugin);
				if (velocityPlugin != null) {
					services.put(plugin.getName(), velocityPlugin);
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return services;
	}

	public Business getBusiness() {
		return business;
	}

	public Dao getDao() {
		return getBusiness().getDao();
	}
	
	public SystemService getSystemService() {
		return getBusiness().getSystemService();
	}
	
}

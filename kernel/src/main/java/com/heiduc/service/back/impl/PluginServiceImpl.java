

package com.heiduc.service.back.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.vo.PluginPropertyVO;
import com.heiduc.entity.PluginEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.PluginService;
import com.heiduc.service.impl.AbstractServiceImpl;

/**
 * @author Alexander Oleynik
 */
public class PluginServiceImpl extends AbstractServiceImpl 
		implements PluginService {

	@Override
	public List<PluginEntity> select() {
		return getDao().getPluginDao().select();
	}

	@Override
	public ServiceResponse remove(Long id) {
		try {
			PluginEntity plugin = getDao().getPluginDao().getById(id);
			if (plugin != null) {
				getBusiness().getPluginBusiness().uninstall(plugin);
				return ServiceResponse.createSuccessResponse(
						Messages.get("plugin.success_uninstall"));
			}
			else {
				return ServiceResponse.createErrorResponse(
						Messages.get("plugin.not_found"));
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage());
			return ServiceResponse.createErrorResponse(e.getMessage());
		}
	}

	@Override
	public List<PluginPropertyVO> getProperties(Long pluginId) {
		PluginEntity plugin = getDao().getPluginDao().getById(pluginId);
		if (plugin == null) {
			return Collections.EMPTY_LIST;
		}
		return getBusiness().getPluginBusiness().getProperties(plugin);	
	}

	@Override
	public PluginEntity getById(Long pluginId) {
		return getDao().getPluginDao().getById(pluginId);
	}

	@Override
	public PluginEntity getByName(String pluginName) {
		return getDao().getPluginDao().getByName(pluginName);
	}

	@Override
	public ServiceResponse savePluginConfig(Long pluginId, String xml) {
		PluginEntity plugin = getById(pluginId);
		if (plugin != null) {
			plugin.setConfigData(xml);
			getDao().getPluginDao().save(plugin);
			return ServiceResponse.createSuccessResponse(
					Messages.get("config.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("plugin.not_found"));
		}
	}

}

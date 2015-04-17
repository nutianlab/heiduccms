

package com.heiduc.service.back;

import java.util.List;

import com.heiduc.business.vo.PluginPropertyVO;
import com.heiduc.entity.PluginEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface PluginService extends AbstractService {
	
	List<PluginEntity> select();

	ServiceResponse remove(Long id);
	
	List<PluginPropertyVO> getProperties(Long pluginId);
	
	PluginEntity getById(Long pluginId);
	
	ServiceResponse savePluginConfig(Long pluginId, String xml);

	PluginEntity getByName(String pluginName);
}

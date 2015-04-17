

package com.heiduc.business.impl;

import com.heiduc.business.PluginResourceBusiness;
import com.heiduc.entity.PluginResourceEntity;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PluginResourceBusinessImpl extends AbstractBusinessImpl 
	implements PluginResourceBusiness {

	@Override
	public void updateResourceCache(PluginResourceEntity resource) {
		String key = "plugin:" + resource.getUrl();
		getSystemService().getCache().put(key, resource.getContent());
	}

	
}

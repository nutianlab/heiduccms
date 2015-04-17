

package com.heiduc.business.impl.pagefilter.fragments;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.ContentFragment;
import com.heiduc.business.plugin.PluginEntryPoint;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.PluginEntity;

public class PluginHeadFragment	implements ContentFragment {

	@Override
	public String get(Business business, PageEntity page) {
		StringBuffer code = new StringBuffer(); 
		for (PluginEntity plugin : business.getDao().getPluginDao()
				.selectEnabled()) {
			PluginEntryPoint entryPoint = business.getPluginBusiness()
					.getEntryPoint(plugin);
			if (entryPoint == null) continue;
			if (entryPoint.isHeadInclude()) {
				entryPoint.setHeadInclude(false);
				if (!StringUtils.isEmpty(plugin.getPageHeader())) {
					code.append(plugin.getPageHeader());
				}
			}
			code.append(entryPoint.getHeadBeginInclude());
		}
		return code.toString();
	}

}



package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.ConfigBusiness;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.filter.SiteFilter;
import com.heiduc.i18n.Messages;

public class ConfigBusinessImpl extends AbstractBusinessImpl 
	implements ConfigBusiness {

	@Override
	public ConfigEntity getConfig() {
		return HeiducContext.getInstance().getConfig();
	}

	@Override
	public boolean isTextFileExt(String ext) {
		ConfigEntity config = getConfig();
		String[] exts = config.getEditExt().split(",");
		for (String textExt : exts) {
			if (ext.equals(textExt)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isImageFileExt(String ext) {
		String[] exts = {"jpg","jpeg","png","gif","ico"};
		for (String imageExt : exts) {
			if (ext.equals(imageExt)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<String> validateBeforeUpdate(ConfigEntity entity) {
		List<String> errors = new ArrayList<String>();
		if (StringUtils.isEmpty(entity.getSiteDomain())) {
			errors.add(Messages.get("config.site_domain_is_empty"));
		}
		if (StringUtils.isEmpty(entity.getSiteEmail())) {
			errors.add(Messages.get("config.site_email_is_empty"));
		}
		if (HeiducContext.getInstance().isSkipUrl(entity.getSiteUserLoginUrl())) {
			errors.add(entity.getSiteUserLoginUrl() 
					+ Messages.get("config.url_reserved"));
		}
		return errors;
	}
	
}



package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.PageAttributeBusiness;
import com.heiduc.business.page.impl.PageSetAttributeMessage;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageAttributeEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.FolderUtil;

/**
 * @author Alexander Oleynik
 */
public class PageAttributeBusinessImpl extends AbstractBusinessImpl 
	implements PageAttributeBusiness {

	@Override
	public List<PageAttributeEntity> getByPage(String pageUrl) {
		if (pageUrl.equals("/")) {
			return getDao().getPageAttributeDao().getByPage(pageUrl);
		}
		List<PageAttributeEntity> result = getDao().getPageAttributeDao()
				.getByPageInherited("/");
		String[] paths = FolderUtil.getPathChain(pageUrl);
		String url = "";
		for (String path : paths) {
			url = url + "/" + path;
			if (url.equals(pageUrl)) {
				result.addAll(getDao().getPageAttributeDao()
						.getByPage(url));
			}
			else {
				result.addAll(getDao().getPageAttributeDao()
						.getByPageInherited(url));
			}
		}
		return result;
	}

	@Override
	public PageAttributeEntity getByPage(String pageUrl, String name) {
		PageAttributeEntity result = getDao().getPageAttributeDao()
				.getByPageName("/", name);
		if (result != null) {
			return result;
		}
		String[] paths = FolderUtil.getPathChain(pageUrl);
		String url = "";
		for (String path : paths) {
			url = url + "/" + path;
			result = getDao().getPageAttributeDao().getByPageName(url, name);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public void setAttribute(PageEntity page, String name, String language,
			String value, boolean applyToChildren) {
		PageAttributeEntity attribute = getByPage(page.getFriendlyURL(), name);
		if (attribute == null) {
			LOGGER.error("Attribute definition: " + name
					+ " not found for page " + page.getFriendlyURL());
			return;
		}
		page.setAttribute(name, language, value);
		getDao().getPageDao().save(page);
		if (applyToChildren && attribute.isInherited()) {
			getBusiness().getMessageQueue().publish(
					new PageSetAttributeMessage(page.getFriendlyURL(), name, 
							language, value));
		}
	}

	@Override
	public List<String> validateBeforeUpdate(PageAttributeEntity entity) {
		List<String> errors = new ArrayList<String>();
		if (StringUtils.isEmpty(entity.getName())) {
			errors.add(Messages.get("name_is_empty"));
		}
		if (StringUtils.isEmpty(entity.getTitle())) {
			errors.add(Messages.get("title_is_empty"));
		}
		if (entity.isNew()) {
			PageAttributeEntity found = getDao().getPageAttributeDao()
					.getByPageName(entity.getPageUrl(), entity.getName());
			if (found != null ) {
				errors.add(Messages.get("attribute_already_exists"));
			}
		}
		return errors;
	}

	
}

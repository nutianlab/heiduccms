

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.PageAttributeEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.PageAttributeService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class PageAttributeServiceImpl extends AbstractServiceImpl 
		implements PageAttributeService {

	@Override
	public List<PageAttributeEntity> getByPage(String pageUrl) {
		return getBusiness().getPageAttributeBusiness().getByPage(pageUrl);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		PageAttributeEntity attr;
		if (StringUtils.isEmpty(vo.get("id"))) {
			attr = new PageAttributeEntity();
		}
		else {
			attr = getDao().getPageAttributeDao().getById(Long.valueOf(
					vo.get("id")));
		}
		attr.setPageUrl(vo.get("url"));
		attr.setName(vo.get("name"));
		attr.setTitle(vo.get("title"));
		attr.setDefaultValue(vo.get("defaultValue"));
		attr.setInherited(Boolean.valueOf(vo.get("inherited")));
		// validating
		List<String> errors = getBusiness().getPageAttributeBusiness()
				.validateBeforeUpdate(attr);
		if (!errors.isEmpty()) {
			return ServiceResponse.createErrorResponse(Messages.get("error"),
					errors);
		}
		getDao().getPageAttributeDao().save(attr);
		// set default value to all children
		if (attr.isInherited() 
				&& StringUtils.isNotEmpty(attr.getDefaultValue())) {
			
			PageEntity page = getDao().getPageDao().getByUrl(attr.getPageUrl());
			ConfigEntity config = HeiducContext.getInstance().getConfig();

			getBusiness().getPageAttributeBusiness().setAttribute(page, 
					attr.getName(), config.getDefaultLanguage(), 
					attr.getDefaultValue(), true);
		}
		return ServiceResponse.createSuccessResponse(Messages.get("success"));
	}

	@Override
	public ServiceResponse remove(List<String> ids, Long pageId) {
		PageEntity page = getDao().getPageDao().getById(pageId);
		List<PageAttributeEntity> attrs = getDao().getPageAttributeDao()
				.getById(StrUtil.toLong(ids));
		List<Long> removeIds = new ArrayList<Long>();
		for (PageAttributeEntity attr : attrs) {
            if (attr.getPageUrl().equals(page.getFriendlyURL())) {
				removeIds.add(attr.getId());
			}
		}
		getDao().getPageAttributeDao().remove(removeIds);		
		return ServiceResponse.createSuccessResponse(Messages.get("success"));
	}

}

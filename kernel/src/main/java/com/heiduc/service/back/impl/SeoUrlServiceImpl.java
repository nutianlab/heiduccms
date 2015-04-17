

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.SeoUrlEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.SeoUrlService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class SeoUrlServiceImpl extends AbstractServiceImpl 
		implements SeoUrlService {

	@Override
	public List<SeoUrlEntity> select() {
		return getDao().getSeoUrlDao().select();
	}

	@Override
	public ServiceResponse remove(List<String> ids) {
		getDao().getSeoUrlDao().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("seo_urls.success_delete"));
	}

	@Override
	public SeoUrlEntity getById(Long id) {
		return getDao().getSeoUrlDao().getById(id);
	}

	private List<String> validate(SeoUrlEntity entity) {
		List<String> errors = new ArrayList<String>();
		SeoUrlEntity found = getDao().getSeoUrlDao().getByFrom(
				entity.getFromLink());
		if (found != null && !found.getId().equals(entity.getId())) {
				errors.add(Messages.get("seo_urls.already_exists"));
		}
		return errors;
	}
	
	@Override
	public ServiceResponse save(Map<String, String> vo) {
		SeoUrlEntity seoUrl = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			seoUrl = getDao().getSeoUrlDao().getById(Long.valueOf(vo.get("id")));
		}
		if (seoUrl == null) {
			seoUrl = new SeoUrlEntity();
		}
		seoUrl.setFromLink(vo.get("fromLink"));
		seoUrl.setToLink(vo.get("toLink"));
		List<String> errors = validate(seoUrl);
		if (errors.isEmpty()) {
			getDao().getSeoUrlDao().save(seoUrl);
			return ServiceResponse.createSuccessResponse(
					Messages.get("seo_urls.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

}

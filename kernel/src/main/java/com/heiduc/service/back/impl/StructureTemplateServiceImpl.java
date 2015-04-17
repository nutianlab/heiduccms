

package com.heiduc.service.back.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.enums.StructureTemplateType;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.StructureTemplateService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class StructureTemplateServiceImpl extends AbstractServiceImpl 
		implements StructureTemplateService {

	@Override
	public ServiceResponse remove(List<String> ids) {
		List<String> errors = getBusiness().getStructureTemplateBusiness()
				.remove(StrUtil.toLong(ids));
		if (errors.isEmpty()) {
			return ServiceResponse.createSuccessResponse(
				Messages.get("structureTemplate.success_delete"));
		}
		else {
			return ServiceResponse.createErrorResponse(
				Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public StructureTemplateEntity getById(Long id) {
		return getDao().getStructureTemplateDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		StructureTemplateEntity entity = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			entity = getDao().getStructureTemplateDao().getById(
					Long.valueOf(vo.get("id")));
		}
		if (entity == null) {
			entity = new StructureTemplateEntity();
		}
		entity.setName(vo.get("name"));
		entity.setTitle(vo.get("title"));
		entity.setContent(vo.get("content"));
		entity.setHeadContent(vo.get("headContent"));
		entity.setStructureId(Long.valueOf(vo.get("structureId")));
		entity.setType(StructureTemplateType.valueOf(vo.get("type")));
		List<String> errors = getBusiness().getStructureTemplateBusiness()
				.validateBeforeUpdate(entity);
		if (errors.isEmpty()) {
			getBusiness().getStructureTemplateBusiness().save(entity);
			return ServiceResponse.createSuccessResponse(entity.getId()
					.toString());
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public List<StructureTemplateEntity> selectByStructure(Long structureId) {
		return getDao().getStructureTemplateDao().selectByStructure(structureId);
	}

}

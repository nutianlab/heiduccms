

package com.heiduc.service.back.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.vo.StructureFieldVO;
import com.heiduc.entity.StructureEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.StructureService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class StructureServiceImpl extends AbstractServiceImpl 
		implements StructureService {

	@Override
	public List<StructureEntity> select() {
		return getDao().getStructureDao().select();
	}

	@Override
	public ServiceResponse remove(List<String> ids) {
		List<String> errors = getBusiness().getStructureBusiness().remove(
				StrUtil.toLong(ids));
		if (errors.isEmpty()) {
			return ServiceResponse.createSuccessResponse(
				Messages.get("structures.success_delete"));
		}
		else {
			return ServiceResponse.createErrorResponse(
				Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public StructureEntity getById(Long id) {
		return getDao().getStructureDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		StructureEntity entity = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			entity = getDao().getStructureDao().getById(Long.valueOf(
					vo.get("id")));
		}
		if (entity == null) {
			entity = new StructureEntity();
		}
		entity.setTitle(vo.get("title"));
		entity.setContent(vo.get("content"));
		List<String> errors = getBusiness().getStructureBusiness()
				.validateBeforeUpdate(entity);
		if (errors.isEmpty()) {
			getDao().getStructureDao().save(entity);
			return ServiceResponse.createSuccessResponse(entity.getId()
					.toString());
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public List<StructureFieldVO> getFields(Long structureId) {
		StructureEntity entity = getById(structureId);
		if (entity == null) {
			return Collections.EMPTY_LIST;
		}
		return entity.getFields();
	}

}



package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.business.vo.StructureFieldVO;
import com.heiduc.entity.StructureEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface StructureService extends AbstractService {
	
	List<StructureEntity> select();

	ServiceResponse remove(final List<String> ids);

	StructureEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
	List<StructureFieldVO> getFields(final Long structureId);
}

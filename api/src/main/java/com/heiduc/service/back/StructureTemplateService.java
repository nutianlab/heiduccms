

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface StructureTemplateService extends AbstractService {
	
	List<StructureTemplateEntity> selectByStructure(final Long structureId);
	
	ServiceResponse remove(final List<String> ids);

	StructureTemplateEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
}

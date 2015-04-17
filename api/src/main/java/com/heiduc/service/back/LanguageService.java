

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.LanguageEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface LanguageService extends AbstractService {
	
	List<LanguageEntity> select();

	ServiceResponse remove(final List<String> ids);

	LanguageEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
}

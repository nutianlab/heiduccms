

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.SeoUrlEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface SeoUrlService extends AbstractService {
	
	List<SeoUrlEntity> select();

	ServiceResponse remove(final List<String> ids);

	SeoUrlEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
}

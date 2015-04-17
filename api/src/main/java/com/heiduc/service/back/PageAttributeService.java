

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.PageAttributeEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;

/**
 * @author Alexander Oleynik
 */
public interface PageAttributeService extends AbstractService {
	
	List<PageAttributeEntity> getByPage(final String pageUrl);

	ServiceResponse save(Map<String, String> vo);
	
	ServiceResponse remove(List<String> ids, Long pageId);
}

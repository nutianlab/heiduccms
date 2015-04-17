

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.ContentPermissionVO;

/**
 * @author Alexander Oleynik
 */
public interface ContentPermissionService extends AbstractService {
	
	ServiceResponse remove(final List<String> ids);

	ContentPermissionEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
	List<ContentPermissionVO> selectByUrl(final String pageUrl);

	ContentPermissionEntity getPermission(final String url);
	
}

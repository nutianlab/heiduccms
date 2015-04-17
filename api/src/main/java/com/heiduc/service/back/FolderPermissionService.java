

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.FolderPermissionVO;

/**
 * @author Alexander Oleynik
 */
public interface FolderPermissionService extends AbstractService {

	ServiceResponse remove(final List<String> ids);

	FolderPermissionEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
	List<FolderPermissionVO> selectByFolder(final Long folderId);

	FolderPermissionEntity getPermission(final Long folderId);
	
}

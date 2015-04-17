

package com.heiduc.service.back;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.FolderEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.FolderRequestVO;


public interface FolderService extends AbstractService {
	
	TreeItemDecorator<FolderEntity> getTree();
	
	TreeItemDecorator<FolderEntity> getFolderByPath(String path);

	FolderEntity createFolderByPath(String path) 
			throws UnsupportedEncodingException;

	String getFolderPath(final Long folderId);
	
	FolderEntity getFolder(final Long id);
	
	FolderRequestVO getFolderRequest(final Long folderId,
			final Long folderParentId);

	List<FolderEntity> getByParent(final Long id);
	
	ServiceResponse saveFolder(final Map<String, String> vo);
	
	ServiceResponse deleteFolder(final List<String> ids);
	
}

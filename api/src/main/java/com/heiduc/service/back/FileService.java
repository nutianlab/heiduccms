

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.FileEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.FileVO;


public interface FileService extends AbstractService {
	
	List<FileEntity> getByFolder(final Long folderId);

	ServiceResponse deleteFiles(final List<String> fileIds);
	
	String getFilePath(final Long fileId);

	ServiceResponse updateContent(final Long fileId, final String content);

	FileVO getFile(final Long id);
	
	FileVO getFile(final Long id, String encoding);

	ServiceResponse saveFile(final Map<String, String> vo);
}



package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FileEntity;

public interface FileDao extends BaseDao<FileEntity> {

	List<FileEntity> getByFolder(final Long folderId);

	FileEntity getByName(final Long folderId, final String name);

	void save(final FileEntity file, byte[] content);
	
	byte[] getFileContent(final FileEntity file);
	
	void removeByFolder(final Long folderId);

}

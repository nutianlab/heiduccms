

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.FileChunkEntity;
import com.heiduc.entity.FileEntity;

public interface FileChunkDao extends BaseDao<FileChunkEntity> {

	void save(final FileEntity file, byte[] content);
	
	byte[] getFileContent(final FileEntity file);
	
	void removeByFile(final Long fileId);
	
	List<FileChunkEntity> createChunks(FileEntity file, byte[] content);

}



package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.Date;
import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.FileChunkDao;
import com.heiduc.dao.FileDao;
import com.heiduc.entity.FileEntity;

public class FileDaoImpl extends BaseDaoImpl<FileEntity> 
		implements FileDao {

	public FileDaoImpl() {
		super(FileEntity.class);
	}

	@Override
	public void remove(final Long fileId) {
		if (fileId == null) {
			return;
		}
		getFileChunkDao().removeByFile(fileId);
		super.remove(fileId);
	}

	@Override
	public void remove(final List<Long> ids) {
		for (Long fileId : ids) {
			remove(fileId);
		}
	}

	@Override
	public List<FileEntity> getByFolder(Long folderId) {
		Query q = newQuery();
		q.addFilter("folderId", EQUAL, folderId);
		return select(q, "getByFolder", params(folderId));
	}

	@Override
	public FileEntity getByName(Long folderId, String name) {
		Query q = newQuery();
		q.addFilter("folderId", EQUAL, folderId);
		q.addFilter("filename", EQUAL, name);
		return selectOne(q, "getByName", params(folderId, name));
	}

	@Override
	public void save(FileEntity file, byte[] content) {
		file.setLastModifiedTime(new Date());
		file.setSize(content.length);
		save(file);
		getFileChunkDao().save(file, content);
	}

	@Override
	public void removeByFolder(Long folderId) {
		List<FileEntity> files = getByFolder(folderId);
		for (FileEntity file : files) {
			remove(file.getId());
		}
	}

	@Override
	public void removeAll() {
		super.removeAll();
		getFileChunkDao().removeAll();
	}

	public FileChunkDao getFileChunkDao() {
		return getDao().getFileChunkDao();
	}

	@Override
	public byte[] getFileContent(FileEntity file) {
		return getFileChunkDao().getFileContent(file);
	}

}

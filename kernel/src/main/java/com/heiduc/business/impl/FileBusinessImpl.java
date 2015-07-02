

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.FileBusiness;
import com.heiduc.business.FolderBusiness;
import com.heiduc.business.FolderPermissionBusiness;
import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.FolderUtil;
import com.heiduc.utils.MimeType;

public class FileBusinessImpl extends AbstractBusinessImpl 
	implements FileBusiness {

	@Override
	public List<String> validateBeforeUpdate(final FileEntity entity) {
		List<String> errors = new ArrayList<String>();
		if (StringUtils.isEmpty(entity.getFilename())) {
			errors.add(Messages.get("filename_is_empty"));
		}
		else {
			FileEntity file = getDao().getFileDao().getByName(
					entity.getFolderId(), entity.getFilename());
			if (file != null && 
				(entity.isNew() 
				||
				(!entity.isNew() && !file.getId().equals(entity.getId())))) {
				errors.add(Messages.get("file_already_exists"));
			}
		}
		if (StringUtils.isEmpty(entity.getTitle())) {
			errors.add(Messages.get("title_is_empty"));
		}
		return errors;
	}

	@Override
	public byte[] readFile(String filename) {
		try {
			String path = FolderUtil.getFilePath(filename);
			String name = FolderUtil.getFileName(filename);
			FolderEntity folder = getFolderBusiness().findFolderByPath(
					getFolderBusiness().getTree(), path).getEntity();
			if (folder == null) {
				return null;
			}
			FileEntity file = getDao().getFileDao().getByName(folder.getId(), name);
			if (file != null) {
				return getDao().getFileDao().getFileContent(file);
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public FileEntity saveFile(String filename, byte[] data) {
		try {
			String path = FolderUtil.getFilePath(filename);
			String name = FolderUtil.getFileName(filename);
			FolderEntity folder = getFolderBusiness().createFolder(path);
			FileEntity file = getDao().getFileDao().getByName(folder.getId(), name);
			if (file == null) {
				file = new FileEntity(name, name, folder.getId(), 
						MimeType.getContentTypeByExt(FolderUtil.getFileExt(filename)), 
						new Date(), data.length);
			}
			getDao().getFileDao().save(file, data);
			getSystemService().getFileCache().remove(filename);
			return file;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private FolderBusiness getFolderBusiness() {
		return getBusiness().getFolderBusiness();
	}

	private FolderPermissionBusiness getFolderPermissionBusiness() {
		return getBusiness().getFolderPermissionBusiness();
	}

	@Override
	public FileEntity findFile(String filename) {
		try {
			String path = FolderUtil.getFilePath(filename);
			String name = FolderUtil.getFileName(filename);
			TreeItemDecorator<FolderEntity> folder = getFolderBusiness()
					.findFolderByPath(getFolderBusiness().getTree(), path);
			if (folder == null) {
				return null;
			}
			return getDao().getFileDao().getByName(folder.getEntity().getId(), 
					name);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void remove(String filename) {
		String path = FolderUtil.getFilePath(filename);
		String name = FolderUtil.getFileName(filename);
		TreeItemDecorator<FolderEntity> folder = getFolderBusiness()
				.findFolderByPath(getFolderBusiness().getTree(), path);
		if (folder == null) {
			LOGGER.error("Folder not found. " + path);
			return;
		}
		FileEntity file = getDao().getFileDao().getByName(folder.getEntity()
				.getId(), name);
		if (file == null) {
			LOGGER.error("File not found. " + filename);
			return;
		}
		FolderPermissionEntity perm = getFolderPermissionBusiness()
				.getPermission(folder.getEntity(), 
						HeiducContext.getInstance().getUser());
		if (perm.isChangeGranted()) {
			getDao().getFileDao().remove(file.getId());
			getSystemService().getFileCache().remove(filename);
		}
	}

	@Override
	public void remove(List<Long> ids) {
		for (Long id : ids) {
			FileEntity file = getDao().getFileDao().getById(id);
			if (file != null) {
				getSystemService().getFileCache().remove(getFilePath(file));
			}
		}
		getDao().getFileDao().remove(ids);
	}

	@Override
	public String getFilePath(FileEntity file) {
		return getDao().getFolderDao().getFolderPath(file.getFolderId())
				+ "/" +file.getFilename();
	}
	
}

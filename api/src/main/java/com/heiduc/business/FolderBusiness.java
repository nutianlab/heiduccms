

package com.heiduc.business;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.FolderEntity;

/**
 * @author Alexander Oleynik
 */
public interface FolderBusiness {

	/**
	 * Security filtered dao version.
	 * @return found folder.
	 */
	FolderEntity getById(final Long id);
	
	/**
	 * Security filtered dao version.
	 * @return found folders.
	 */
	List<FolderEntity> getByParent(final Long id);

	FolderEntity getByPath(final String path);
	
	TreeItemDecorator<FolderEntity> getTree(final List<FolderEntity> list);

	TreeItemDecorator<FolderEntity> getTree();
	
	TreeItemDecorator<FolderEntity> findFolderByPath(
			final TreeItemDecorator<FolderEntity> root, final String path);
	
	List<String> validateBeforeUpdate(final FolderEntity folder);
	
	/**
	 * Create all directories in the path.
	 * @param path - directories path.
	 * @return last folder in the path.
	 * @throws UnsupportedEncodingException
	 */
	FolderEntity createFolder(final String path); 
	
	String getFolderPath(final FolderEntity folder);
	String getFolderPath(final FolderEntity folder, 
			TreeItemDecorator<FolderEntity> root);
	
	void recursiveRemove(final List<Long> folderIds);

	void recursiveRemove(final String path);
}

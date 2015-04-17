

package com.heiduc.service.vo;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;

/**
 * Value object to be returned from services.
 * 
 * @author Alexander Oleynik
 */
public class SiteStatVO {

	public SiteStatVO() {
	}

	private Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}
	
	public int getPages() {
		return getDao().getPageDao().count();
	}

	public int getPagePermissions() {
		return getDao().getContentPermissionDao().count();
	}

	public int getStructures() {
		return getDao().getStructureDao().count();
	}

	public int getStructureTemplates() {
		return getDao().getStructureTemplateDao().count();
	}

	public int getTemplates() {
		return getDao().getTemplateDao().count();
	}

	public int getFolders() {
		return getDao().getFolderDao().count();
	}

	public int getFolderPermissions() {
		return getDao().getFolderPermissionDao().count();
	}

	public int getFiles() {
		return getDao().getFileDao().count();
	}

	public int getLanguages() {
		return getDao().getLanguageDao().count();
	}

	public int getMessages() {
		return getDao().getMessageDao().count();
	}

	public int getUsers() {
		return getDao().getUserDao().count();
	}

	public int getGroups() {
		return getDao().getGroupDao().count();
	}

	public int getTags() {
		return getDao().getTagDao().count();
	}

	public int getTotal() {
		return getFiles() + getFolderPermissions() + getFolders()
			+ getGroups() + getLanguages() + getMessages() +getPagePermissions()
			+ getPages() + getStructures() + getStructureTemplates() + getTags()
			+ getTemplates() + getUsers();
	}
}

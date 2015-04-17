

package com.heiduc.business;

import java.util.TimeZone;

import com.heiduc.business.mq.MessageQueue;
import com.heiduc.dao.Dao;
import com.heiduc.entity.UserEntity;
import com.heiduc.global.SystemService;
import com.heiduc.search.SearchEngine;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface Business {
	
	SystemService getSystemService();
	void setSystemService(final SystemService bean);

	Dao getDao();
	void setDao(Dao bean);
	
	SearchEngine getSearchEngine();
	void setSearchEngine(SearchEngine bean);

	UserEntity getUser();
	TimeZone getTimeZone();
	
	/**
	 * Get current langauge for rendering page's content. It can be different 
	 * from current locale language as site languages set can not include current
	 * locale language. 
	 * @return language code.
	 */
	String getLanguage();
	
	String getDefaultLanguage();
	
	MessageQueue getMessageQueue();

	PageBusiness getPageBusiness();
	void setPageBusiness(final PageBusiness bean);

	FolderBusiness getFolderBusiness();
	void setFolderBusiness(final FolderBusiness bean);
	
	TemplateBusiness getTemplateBusiness();
	void setTemplateBusiness(final TemplateBusiness bean);

	ConfigBusiness getConfigBusiness();
	void setConfigBusiness(final ConfigBusiness bean);

	FormBusiness getFormBusiness();
	void setFormBusiness(final FormBusiness bean);

	FileBusiness getFileBusiness();
	void setFileBusiness(final FileBusiness bean);

	CommentBusiness getCommentBusiness();
	void setCommentBusiness(final CommentBusiness bean);

	FieldBusiness getFieldBusiness();
	void setFieldBusiness(final FieldBusiness bean);

	MessageBusiness getMessageBusiness();
	void setMessageBusiness(final MessageBusiness bean);

	UserBusiness getUserBusiness();
	void setUserBusiness(final UserBusiness bean);

	ContentPermissionBusiness getContentPermissionBusiness();
	void setContentPermissionBusiness(final ContentPermissionBusiness bean);

	GroupBusiness getGroupBusiness();
	void setGroupBusiness(final GroupBusiness bean);

	FolderPermissionBusiness getFolderPermissionBusiness();
	void setFolderPermissionBusiness(final FolderPermissionBusiness bean);
	
	StructureBusiness getStructureBusiness();
	void setStructureBusiness(final StructureBusiness bean);

	StructureTemplateBusiness getStructureTemplateBusiness();
	void setStructureTemplateBusiness(final StructureTemplateBusiness bean);

	PluginBusiness getPluginBusiness();
	void setPluginBusiness(final PluginBusiness bean);

	PluginResourceBusiness getPluginResourceBusiness();
	void setPluginResourceBusiness(final PluginResourceBusiness bean);
	
	ImportExportBusiness getImportExportBusiness();
	void setImportExportBusiness(final ImportExportBusiness bean);

	TagBusiness getTagBusiness();
	void setTagBusiness(final TagBusiness bean);

	PicasaBusiness getPicasaBusiness();
	void setPicasaBusiness(final PicasaBusiness bean);

	SetupBean getSetupBean();
	void setSetupBean(final SetupBean bean);

	FormDataBusiness getFormDataBusiness();
	void setFormDataBusiness(final FormDataBusiness bean);

	PageAttributeBusiness getPageAttributeBusiness();
	void setPageAttributeBusiness(final PageAttributeBusiness bean);
	
	RewriteUrlBusiness getRewriteUrlBusiness();
	void setRewriteUrlBusiness(RewriteUrlBusiness bean);
}

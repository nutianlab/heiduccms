

package com.heiduc.business.imex.task;

import com.heiduc.dao.DaoTaskException;
import com.heiduc.entity.CommentEntity;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.FieldEntity;
import com.heiduc.entity.FileEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FormConfigEntity;
import com.heiduc.entity.FormEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.MessageEntity;
import com.heiduc.entity.PageDependencyEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.PageTagEntity;
import com.heiduc.entity.PluginEntity;
import com.heiduc.entity.SeoUrlEntity;
import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.entity.TagEntity;
import com.heiduc.entity.TemplateEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;

/**
 * @author Alexander Oleynik
 */
public interface DaoTaskAdapter {

	int getStart();

	void setStart(int value);

	int getEnd();
	
	void resetCounters();

	String getCurrentFile();

	void setCurrentFile(String file);
	
	int getFileCounter();
	
	void setFileCounter(int value);
	
	void nextFile();

	void configSave(ConfigEntity entity) throws DaoTaskException;
	
	void languageSave(LanguageEntity entity) throws DaoTaskException;
	
	void folderSave(FolderEntity entity) throws DaoTaskException;

	void formSave(FormEntity entity) throws DaoTaskException;

	void fieldSave(FieldEntity entity) throws DaoTaskException;

	void formConfigSave(FormConfigEntity entity) throws DaoTaskException;

	void groupSave(GroupEntity entity) throws DaoTaskException;

	void userGroupSave(UserGroupEntity entity) throws DaoTaskException;

	void messageSave(MessageEntity entity) throws DaoTaskException;
	
	void pageSave(PageEntity entity) throws DaoTaskException;

	void setPageContent(Long pageId, String languageCode, String content) 
			throws DaoTaskException;

	void commentSave(CommentEntity entity) throws DaoTaskException;

	void fileSave(FileEntity entity, byte[] data) throws DaoTaskException;

	void structureSave(StructureEntity entity) throws DaoTaskException;

	void structureTemplateSave(StructureTemplateEntity entity) 	
			throws DaoTaskException;
	
	void templateSave(TemplateEntity entity) throws DaoTaskException;

	void userSave(UserEntity entity) throws DaoTaskException;

	void pluginSave(PluginEntity entity) throws DaoTaskException;

	void seoUrlSave(SeoUrlEntity entity) throws DaoTaskException;

	void tagSave(TagEntity entity) throws DaoTaskException;

	void pageDependencySave(PageDependencyEntity entity) throws DaoTaskException;

	void pageTagSave(PageTagEntity entity) throws DaoTaskException;
}
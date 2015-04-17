

package com.heiduc.business.impl.imex.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.imex.task.DaoTaskAdapter;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
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
public class DaoTaskAdapterImpl implements DaoTaskAdapter {

	private static final Log logger = LogFactory.getLog(DaoTaskAdapterImpl.class);

	private static final int TASK_DURATION = 60 * 9;
	
	private int current;
	private int start;
	private String currentFile;
	private int fileCounter;
	
	public Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}

	public int getStart() {
		return start;
	}

	public void setStart(int aStart) {
		this.start = aStart;
		current = 0;
	}

	public int getEnd() {
		return current;
	}
	
	public void resetCounters() {
		current = 0;
		start = 0;
	}

	private boolean isSkip() throws DaoTaskException {
		current++;
		if (getDao().getSystemService().getRequestCPUTimeSeconds() > TASK_DURATION) {
			throw new DaoTaskTimeoutException();
		}
		if (current < start) {
			return true;
		}
		return false;
	}
	
	@Override
	public void configSave(ConfigEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				ConfigEntity config = HeiducContext.getInstance().getConfig();
				entity.setId(config.getId());
			}
		}
		else {
			getDao().getConfigDao().save(entity);
		}
	}

	@Override
	public void languageSave(LanguageEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				LanguageEntity found = getDao().getLanguageDao().getByCode(
						entity.getCode());
				if (found == null) {
					throw new DaoTaskException("Language not found while " 
						+ "skipping save operation. code=" + entity.getCode());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getLanguageDao().save(entity);
		}
	}
	
	@Override
	public void folderSave(FolderEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				FolderEntity found = getDao().getFolderDao().getByParentName(
						entity.getParent(), entity.getName());
				if (found == null) {
					throw new DaoTaskException("Folder not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getFolderDao().save(entity);
		}
	}

	@Override
	public void formSave(FormEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				FormEntity found = getDao().getFormDao().getByName(
						entity.getName());
				if (found == null) {
					throw new DaoTaskException("Form not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getFormDao().save(entity);
		}
	}

	@Override
	public void fieldSave(FieldEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				FormEntity form = getDao().getFormDao().getById(
						entity.getFormId());
				FieldEntity found = getDao().getFieldDao().getByName(form, 
						entity.getName());
				if (found == null) {
					throw new DaoTaskException("Field not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getFieldDao().save(entity);
		}
	}
	
	@Override
	public void formConfigSave(FormConfigEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				FormConfigEntity found = getDao().getFormConfigDao().getConfig();
				if (found == null) {
					throw new DaoTaskException("Form config not found while " 
						+ "skipping save operation. ");
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getFormConfigDao().save(entity);
		}
	}

	@Override
	public void groupSave(GroupEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				GroupEntity found = getDao().getGroupDao().getByName(
						entity.getName());
				if (found == null) {
					throw new DaoTaskException("Group not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getGroupDao().save(entity);
		}
	}
	
	@Override
	public void userGroupSave(UserGroupEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				UserGroupEntity found = getDao().getUserGroupDao()
					.getByUserGroup(entity.getGroupId(), entity.getUserId());
				if (found == null) {
					throw new DaoTaskException("UserGroup not found while " 
						+ "skipping save operation. group=" + entity.getGroupId()
						+ " user=" + entity.getUserId());
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getUserGroupDao().saveNoAudit(entity);
		}
	}

	@Override
	public void messageSave(MessageEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				MessageEntity found = getDao().getMessageDao().getByCode(
						entity.getCode(), entity.getLanguageCode());
				if (found == null) {
					throw new DaoTaskException("Message not found while " 
						+ "skipping save operation. code=" + entity.getCode()
						+ " language=" + entity.getLanguageCode());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getMessageDao().saveNoAudit(entity);
		}
	}

	@Override
	public void pageSave(PageEntity entity) throws DaoTaskException {
		if (isSkip()) {
			//logger.info("skip " + entity.getFriendlyURL()	+ " version=" 
			//		+ entity.getVersion() + " current " + current);
			if (entity.getId() == null) {
				PageEntity found = getDao().getPageDao().getByUrlVersion(
						entity.getFriendlyURL(), entity.getVersion());
				if (found == null) {
					throw new DaoTaskException("Page not found while " 
						+ "skipping save operation. " + entity.getFriendlyURL()
						+ " version=" + entity.getVersion());
				}
				entity.setId(found.getId());
			}
		}
		else {
			//logger.info("import " + entity.getFriendlyURL()	+ " version=" 
			//		+ entity.getVersion() + " current " + current);
			getDao().getPageDao().saveNoAudit(entity);
		}
	}

	@Override
	public void commentSave(CommentEntity entity) throws DaoTaskException {
		if (!isSkip()) {
			getDao().getCommentDao().saveNoAudit(entity);
		}
	}

	@Override
	public void setPageContent(Long pageId, String languageCode,
			String content) throws DaoTaskException {
		if (!isSkip()) {
			getDao().getPageDao().setContent(pageId, languageCode, content);
		}
	}

	@Override
	public void fileSave(FileEntity entity, byte[] data) throws DaoTaskException {
		if (!isSkip()) {
			getDao().getFileDao().save(entity, data);
		}
	}
	
	@Override
	public void structureSave(StructureEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				StructureEntity found = getDao().getStructureDao().getByTitle(
						entity.getTitle());
				if (found == null) {
					throw new DaoTaskException("Structure not found while " 
						+ "skipping save operation. " + entity.getTitle());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getStructureDao().saveNoAudit(entity);
		}
	}

	@Override
	public void structureTemplateSave(StructureTemplateEntity entity) 
			throws DaoTaskException {
		if (!isSkip()) {
			getDao().getStructureTemplateDao().saveNoAudit(entity);
		}
	}

	@Override
	public void templateSave(TemplateEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				TemplateEntity found = getDao().getTemplateDao().getByUrl(
						entity.getUrl());
				if (found == null) {
					throw new DaoTaskException("Template not found while " 
						+ "skipping save operation. " + entity.getTitle());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getTemplateDao().saveNoAudit(entity);
		}
	}
	
	@Override
	public void userSave(UserEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				UserEntity found = getDao().getUserDao().getByEmail(
						entity.getEmail());
				if (found == null) {
					throw new DaoTaskException("User not found while " 
						+ "skipping save operation. " + entity.getEmail());
				}
				entity.setKey(found.getKey());
			}
		}
		else {
			getDao().getUserDao().saveNoAudit(entity);
		}
	}

	@Override
	public void pluginSave(PluginEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				PluginEntity found = getDao().getPluginDao().getByName(
						entity.getName());
				if (found == null) {
					throw new DaoTaskException("Plugin not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getPluginDao().saveNoAudit(entity);
		}
	}

	@Override
	public void seoUrlSave(SeoUrlEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				SeoUrlEntity found = getDao().getSeoUrlDao().getByFrom(
						entity.getFromLink());
				if (found == null) {
					throw new DaoTaskException("SeoUrl not found while " 
						+ "skipping save operation. " + entity.getFromLink());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getSeoUrlDao().saveNoAudit(entity);
		}
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}

	@Override
	public void tagSave(TagEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				TagEntity found = getDao().getTagDao().getByName(
						entity.getParent(), entity.getName());
				if (found == null) {
					throw new DaoTaskException("Tag not found while " 
						+ "skipping save operation. " + entity.getName());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getTagDao().saveNoAudit(entity);
		}
	}

	@Override
	public int getFileCounter() {
		return fileCounter;
	}

	@Override
	public void setFileCounter(int value) {
		fileCounter = value;
	}

	@Override
	public void nextFile() {
		fileCounter++;
	}

	@Override
	public void pageDependencySave(PageDependencyEntity entity)
			throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				PageDependencyEntity found = getDao().getPageDependencyDao()
					.getByPageAndDependency(entity.getPage(), 
							entity.getDependency());
				
				if (found == null) {
					throw new DaoTaskException("PageDependency not found while " 
						+ "skipping save operation. " + entity.getPage());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getPageDependencyDao().saveNoAudit(entity);
		}
	}

	@Override
	public void pageTagSave(PageTagEntity entity) throws DaoTaskException {
		if (isSkip()) {
			if (entity.getId() == null) {
				PageTagEntity found = getDao().getPageTagDao().getByURL(
						entity.getPageURL());
				if (found == null) {
					throw new DaoTaskException("Page Tag not found while "
							+ "skipping save operation. " + entity.getPageURL());
				}
				entity.setId(found.getId());
			}
		}
		else {
			getDao().getPageTagDao().saveNoAudit(entity);
		}
	}
}

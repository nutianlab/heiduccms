

package com.heiduc.dao.impl;

import java.io.Serializable;



import com.heiduc.common.HeiducContext;
import com.heiduc.dao.CommentDao;
import com.heiduc.dao.ConfigDao;
import com.heiduc.dao.ContentDao;
import com.heiduc.dao.ContentPermissionDao;
import com.heiduc.dao.Dao;
import com.heiduc.dao.DaoStat;
import com.heiduc.dao.FieldDao;
import com.heiduc.dao.FileChunkDao;
import com.heiduc.dao.FileDao;
import com.heiduc.dao.FolderDao;
import com.heiduc.dao.FolderPermissionDao;
import com.heiduc.dao.FormConfigDao;
import com.heiduc.dao.FormDao;
import com.heiduc.dao.FormDataDao;
import com.heiduc.dao.GroupDao;
import com.heiduc.dao.LanguageDao;
import com.heiduc.dao.MessageDao;
import com.heiduc.dao.Oauth2Dao;
import com.heiduc.dao.PageAttributeDao;
import com.heiduc.dao.PageDao;
import com.heiduc.dao.PageDependencyDao;
import com.heiduc.dao.PageTagDao;
import com.heiduc.dao.PluginDao;
import com.heiduc.dao.PluginResourceDao;
import com.heiduc.dao.SeoUrlDao;
import com.heiduc.dao.StructureDao;
import com.heiduc.dao.StructureTemplateDao;
import com.heiduc.dao.TagDao;
import com.heiduc.dao.TemplateDao;
import com.heiduc.dao.UserDao;
import com.heiduc.dao.UserGroupDao;
import com.heiduc.dao.cache.EntityCache;
import com.heiduc.dao.cache.QueryCache;
import com.heiduc.dao.cache.impl.EntityCacheImpl;
import com.heiduc.dao.cache.impl.QueryCacheImpl;
import com.heiduc.global.SystemService;

public class DaoImpl implements Dao, Serializable {

	private EntityCache entityCache;
	private QueryCache queryCache;
	
	private PageDao pageDao;
	private FileDao fileDao;
	private FileChunkDao fileChunkDao;
	private FolderDao folderDao;
	private UserDao userDao;
	private TemplateDao templateDao;
	private ConfigDao configDao;
	private FormDao formDao;
	private FormConfigDao formConfigDao;
	private CommentDao commentDao;
	private FieldDao fieldDao;
	private SeoUrlDao seoUrlDao;
	private LanguageDao languageDao;
	private ContentDao contentDao;
	private MessageDao messageDao;
	private GroupDao groupDao;
	private UserGroupDao userGroupDao;
	private ContentPermissionDao contentPermissionDao;
	private FolderPermissionDao folderPermissionDao;
	private StructureDao structureDao;
	private StructureTemplateDao structureTemplateDao;
	private PluginDao pluginDao;
	private PluginResourceDao pluginResourceDao;
	private TagDao tagDao;
	private PageTagDao pageTagDao;
	private FormDataDao formDataDao;
	private PageDependencyDao pageDependencyDao;
	private PageAttributeDao pageAttributeDao;
	private DaoStat daoStat;
	private Oauth2Dao oauth2Dao;

	@Override
	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	public PageDao getPageDao() {
		if (pageDao == null) {
			pageDao = new PageDaoImpl();
		}
		return pageDao; 
	}
	public void setPageDao(PageDao aPageDao) {
		pageDao = aPageDao;		
	}

	public FileDao getFileDao() {
		if (fileDao == null) {
			fileDao = new FileDaoImpl();
		}
		return fileDao;
	}
	public void setFileDao(FileDao aFileDao) {
		fileDao = aFileDao;		
	}

	public UserDao getUserDao() {
		if (userDao == null) {
			userDao = new UserDaoImpl();
		}
		return userDao;
	}
	public void setUserDao(UserDao aUserDao) {
		userDao = aUserDao;		
	}
	
	public FolderDao getFolderDao() {
		if (folderDao == null) {
			folderDao = new FolderDaoImpl();
		}
		return folderDao;
	}
	
	public void setFolderDao(FolderDao folderDao) {
		this.folderDao = folderDao;
	}
	
	public TemplateDao getTemplateDao() {
		if (templateDao == null) {
			templateDao = new TemplateDaoImpl();
		}
		return templateDao;
	}
	
	public void setTemplateDao(TemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public ConfigDao getConfigDao() {
		if (configDao == null) {
			configDao = new ConfigDaoImpl();
		}
		return configDao;
	}
	
	public void setConfigDao(ConfigDao configDao) {
		this.configDao = configDao;
	}

	public FormDao getFormDao() {
		if (formDao == null) {
			formDao = new FormDaoImpl();
		}
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}

	public FormConfigDao getFormConfigDao() {
		if (formConfigDao == null) {
			formConfigDao = new FormConfigDaoImpl();
		}
		return formConfigDao;
	}

	public void setFormConfigDao(FormConfigDao formConfigDao) {
		this.formConfigDao = formConfigDao;
	}

	public CommentDao getCommentDao() {
		if (commentDao == null) {
			commentDao = new CommentDaoImpl();
		}
		return commentDao;
	}
	
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public FieldDao getFieldDao() {
		if (fieldDao == null) {
			fieldDao = new FieldDaoImpl();
		}
		return fieldDao;
	}

	public void setFieldDao(FieldDao bean) {
		this.fieldDao = bean;
	}
	
	public SeoUrlDao getSeoUrlDao() {
		if (seoUrlDao == null) {
			seoUrlDao = new SeoUrlDaoImpl();
		}
		return seoUrlDao;
	}
	
	public void setSeoUrlDao(SeoUrlDao seoUrlDao) {
		this.seoUrlDao = seoUrlDao;
	}

	public LanguageDao getLanguageDao() {
		if (languageDao == null) {
			languageDao = new LanguageDaoImpl();
		}
		return languageDao;
	}
	
	public void setLanguageDao(LanguageDao bean) {
		this.languageDao = bean;
	}

	public ContentDao getContentDao() {
		if (contentDao == null) {
			contentDao = new ContentDaoImpl();
		}
		return contentDao;
	}
	
	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	public MessageDao getMessageDao() {
		if (messageDao == null) {
			messageDao = new MessageDaoImpl();
		}
		return messageDao;
	}
	
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public GroupDao getGroupDao() {
		if (groupDao == null) {
			groupDao = new GroupDaoImpl();
		}
		return groupDao;
	}
	
	public void setGroupDao(GroupDao bean) {
		this.groupDao = bean;
	}

	public UserGroupDao getUserGroupDao() {
		if (userGroupDao == null) {
			userGroupDao = new UserGroupDaoImpl();
		}
		return userGroupDao;
	}
	
	public void setUserGroupDao(UserGroupDao bean) {
		this.userGroupDao = bean;
	}

	public ContentPermissionDao getContentPermissionDao() {
		if (contentPermissionDao == null) {
			contentPermissionDao = new ContentPermissionDaoImpl();
		}
		return contentPermissionDao;
	}
	
	public void setContentPermissionDao(ContentPermissionDao bean) {
		this.contentPermissionDao = bean;
	}

	public FolderPermissionDao getFolderPermissionDao() {
		if (folderPermissionDao == null) {
			folderPermissionDao = new FolderPermissionDaoImpl();
		}
		return folderPermissionDao;
	}
	
	public void setFolderPermissionDao(FolderPermissionDao bean) {
		this.folderPermissionDao = bean;
	}
	
	public StructureDao getStructureDao() {
		if (structureDao == null) {
			structureDao = new StructureDaoImpl();
		}
		return structureDao;
	}
	
	public void setStructureDao(StructureDao structureDao) {
		this.structureDao = structureDao;
	}
	
	public StructureTemplateDao getStructureTemplateDao() {
		if (structureTemplateDao == null) {
			structureTemplateDao = new StructureTemplateDaoImpl();
		}
		return structureTemplateDao;
	}
	
	public void setStructureTemplateDao(StructureTemplateDao structureTemplateDao) {
		this.structureTemplateDao = structureTemplateDao;
	}
	
	public EntityCache getEntityCache() {
		if (entityCache == null) {
			entityCache = new EntityCacheImpl();
		}
		return entityCache;
	}
	
	public void setEntityCache(EntityCache entityCache) {
		this.entityCache = entityCache;
	}
	
	public QueryCache getQueryCache() {
		if (queryCache == null) {
			queryCache = new QueryCacheImpl(getEntityCache());
		}
		return queryCache;
	}
	
	public void setQueryCache(QueryCache queryCache) {
		this.queryCache = queryCache;
	}

	public PluginDao getPluginDao() {
		if (pluginDao == null) {
			pluginDao = new PluginDaoImpl();
		}
		return pluginDao;
	}
	
	public void setPluginDao(PluginDao value) {
		this.pluginDao = value;
	}

	public PluginResourceDao getPluginResourceDao() {
		if (pluginResourceDao == null) {
			pluginResourceDao = new PluginResourceDaoImpl();
		}
		return pluginResourceDao;
	}
	
	public void setPluginResourceDao(PluginResourceDao value) {
		this.pluginResourceDao = value;
	}

	@Override
	public FileChunkDao getFileChunkDao() {
		if (fileChunkDao == null) {
			fileChunkDao = new FileChunkDaoImpl();
		}
		return fileChunkDao;
	}

	@Override
	public void setFileChunkDao(FileChunkDao bean) {
		fileChunkDao = bean;
	}
	
	@Override
	public TagDao getTagDao() {
		if (tagDao == null) {
			tagDao = new TagDaoImpl();
		}
		return tagDao;
	}

	@Override
	public void setTagDao(TagDao bean) {
		tagDao = bean;
	}

	@Override
	public PageTagDao getPageTagDao() {
		if (pageTagDao == null) {
			pageTagDao = new PageTagDaoImpl();
		}
		return pageTagDao;
	}

	@Override
	public void setPageTagDao(PageTagDao bean) {
		pageTagDao = bean;
	}

	@Override
	public FormDataDao getFormDataDao() {
		if (formDataDao == null) {
			formDataDao = new FormDataDaoImpl();
		}
		return formDataDao;
	}

	@Override
	public void setFormDataDao(FormDataDao bean) {
		formDataDao = bean;
	}

	@Override
	public PageDependencyDao getPageDependencyDao() {
		if (pageDependencyDao == null) {
			pageDependencyDao = new PageDependencyDaoImpl();
		}
		return pageDependencyDao;
	}

	@Override
	public void setPageDependencyDao(PageDependencyDao bean) {
		pageDependencyDao = bean;
	}

	@Override
	public PageAttributeDao getPageAttributeDao() {
		if (pageAttributeDao == null) {
			pageAttributeDao = new PageAttributeDaoImpl();
		}
		return pageAttributeDao;
	}

	@Override
	public void setPageAttributeDao(PageAttributeDao bean) {
		pageAttributeDao = bean;
	}

	@Override
	public DaoStat getDaoStat() {
		if (daoStat == null) {
			daoStat = new DaoStat();
		}
		return daoStat;
	}

	@Override
	public Oauth2Dao getOauth2Dao() {
		if(oauth2Dao == null){
			oauth2Dao = new Oauth2DaoImpl();
		}
		return oauth2Dao;
	}

	@Override
	public void setOauth2Dao(Oauth2Dao bean) {
		oauth2Dao = bean;
	}

}

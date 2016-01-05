

package com.heiduc.business.impl;

import java.io.Serializable;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.Business;
import com.heiduc.business.CommentBusiness;
import com.heiduc.business.ConfigBusiness;
import com.heiduc.business.ContentPermissionBusiness;
import com.heiduc.business.FieldBusiness;
import com.heiduc.business.FileBusiness;
import com.heiduc.business.FolderBusiness;
import com.heiduc.business.FolderPermissionBusiness;
import com.heiduc.business.FormBusiness;
import com.heiduc.business.FormDataBusiness;
import com.heiduc.business.GroupBusiness;
import com.heiduc.business.ImportExportBusiness;
import com.heiduc.business.MessageBusiness;
import com.heiduc.business.Oauth2Business;
import com.heiduc.business.PageAttributeBusiness;
import com.heiduc.business.PageBusiness;
import com.heiduc.business.PicasaBusiness;
import com.heiduc.business.PluginBusiness;
import com.heiduc.business.PluginResourceBusiness;
import com.heiduc.business.RewriteUrlBusiness;
import com.heiduc.business.SetupBean;
import com.heiduc.business.StructureBusiness;
import com.heiduc.business.StructureTemplateBusiness;
import com.heiduc.business.TagBusiness;
import com.heiduc.business.TemplateBusiness;
import com.heiduc.business.UserBusiness;
import com.heiduc.business.mq.MessageQueue;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.dao.impl.DaoImpl;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.global.SystemService;
import com.heiduc.global.impl.SystemServiceImpl;
import com.heiduc.search.SearchEngine;
import com.heiduc.search.impl.SearchEngineImpl;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class BusinessImpl implements Business, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SystemService systemService;
	private Dao dao;
	private SearchEngine searchEngine;
	
	private PageBusiness pageBusiness;
	private FolderBusiness folderBusiness;
	private TemplateBusiness templateBusiness;
	private ConfigBusiness configBusiness;
	private FormBusiness formBusiness;
	private FileBusiness fileBusiness;
	private CommentBusiness commentBusiness;
	private FieldBusiness fieldBusiness;
	private MessageBusiness messageBusiness;
	private UserBusiness userBusiness;
	private ContentPermissionBusiness contentPermissionBusiness;
	private GroupBusiness groupBusiness;
	private FolderPermissionBusiness folderPermissionBusiness;
	private StructureBusiness structureBusiness;
	private StructureTemplateBusiness structureTemplateBusiness;
	private PluginBusiness pluginBusiness;
	private PluginResourceBusiness pluginResourceBusiness;
	private ImportExportBusiness importExportBusiness;
	private TagBusiness tagBusiness;
	private PicasaBusiness picasaBusiness;
	private FormDataBusiness formDataBusiness;
	private PageAttributeBusiness pageAttributeBusiness;
	private RewriteUrlBusiness rewriteUrlBusiness;
	private Oauth2Business oauth2Business;

	private SetupBean setupBean;
	
	public BusinessImpl(){
		
	}

	@Override
	public UserEntity getUser() {
		return HeiducContext.getInstance().getUser();
	}
	
	@Override
	public MessageQueue getMessageQueue() {
		return HeiducContext.getInstance().getMessageQueue();
	}

	@Override
	public PageBusiness getPageBusiness() {
		if (pageBusiness == null) {
			pageBusiness = new PageBusinessImpl();
		}
		return pageBusiness;
	}

	@Override
	public void setPageBusiness(PageBusiness bean) {
		pageBusiness = bean;
	}
	
	@Override
	public FolderBusiness getFolderBusiness() {
		if (folderBusiness == null) {
			folderBusiness = new FolderBusinessImpl();
		}
		return folderBusiness;
	}

	@Override
	public void setFolderBusiness(FolderBusiness bean) {
		folderBusiness = bean;
	}

	@Override
	public TemplateBusiness getTemplateBusiness() {
		if (templateBusiness == null) {
			templateBusiness = new TemplateBusinessImpl();
		}
		return templateBusiness;
	}

	@Override
	public void setTemplateBusiness(TemplateBusiness bean) {
		templateBusiness = bean;
	}

	@Override
	public ConfigBusiness getConfigBusiness() {
		if (configBusiness == null) {
			configBusiness = new ConfigBusinessImpl();
		}
		return configBusiness;
	}
	@Override
	public void setConfigBusiness(ConfigBusiness bean) {
		configBusiness = bean;
	}

	@Override
	public FormBusiness getFormBusiness() {
		if (formBusiness == null) {
			formBusiness = new FormBusinessImpl();
		}
		return formBusiness;
	}

	@Override
	public void setFormBusiness(FormBusiness formBusiness) {
		this.formBusiness = formBusiness;
	}

	@Override
	public FileBusiness getFileBusiness() {
		if (fileBusiness == null) {
			fileBusiness = new FileBusinessImpl();
		}
		return fileBusiness;
	}

	@Override
	public void setFileBusiness(FileBusiness bean) {
		fileBusiness = bean;		
	}

	@Override
	public CommentBusiness getCommentBusiness() {
		if (commentBusiness == null) {
			commentBusiness = new CommentBusinessImpl();
		}
		return commentBusiness;
	}

	@Override
	public void setCommentBusiness(CommentBusiness bean) {
		commentBusiness = bean;
	}

	@Override
	public FieldBusiness getFieldBusiness() {
		if (fieldBusiness == null) {
			fieldBusiness = new FieldBusinessImpl();
		}
		return fieldBusiness;
	}

	@Override
	public void setFieldBusiness(FieldBusiness bean) {
		fieldBusiness = bean;
	}

	@Override
	public SystemService getSystemService() {
		if (systemService == null) {
			systemService = new SystemServiceImpl();
		}
		return systemService;
	}

	@Override
	public void setSystemService(SystemService bean) {
		systemService = bean;
	}

	@Override
	public MessageBusiness getMessageBusiness() {
		if (messageBusiness == null) {
			messageBusiness = new MessageBusinessImpl();
		}
		return messageBusiness;
	}

	@Override
	public void setMessageBusiness(MessageBusiness messageBusiness) {
		this.messageBusiness = messageBusiness;
	}
	
	@Override
	public UserBusiness getUserBusiness() {
		if (userBusiness == null) {
			userBusiness = new UserBusinessImpl();
		}
		return userBusiness;
	}

	@Override
	public void setUserBusiness(UserBusiness bean) {
		this.userBusiness = bean;
	}

	@Override
	public ContentPermissionBusiness getContentPermissionBusiness() {
		if (contentPermissionBusiness == null) {
			contentPermissionBusiness = new ContentPermissionBusinessImpl();
		}
		return contentPermissionBusiness;
	}

	@Override
	public void setContentPermissionBusiness(ContentPermissionBusiness bean) {
		contentPermissionBusiness = bean;
	}

	@Override
	public GroupBusiness getGroupBusiness() {
		if (groupBusiness == null) {
			groupBusiness = new GroupBusinessImpl();
		}
		return groupBusiness;
	}

	@Override
	public void setGroupBusiness(GroupBusiness bean) {
		groupBusiness = bean;
	}

	@Override
	public FolderPermissionBusiness getFolderPermissionBusiness() {
		if (folderPermissionBusiness == null) {
			folderPermissionBusiness = new FolderPermissionBusinessImpl();
		}
		return folderPermissionBusiness;
	}

	@Override
	public void setFolderPermissionBusiness(FolderPermissionBusiness bean) {
		folderPermissionBusiness = bean;
	}

	@Override
	public StructureBusiness getStructureBusiness() {
		if (structureBusiness == null) {
			structureBusiness = new StructureBusinessImpl();
		}
		return structureBusiness;
	}

	@Override
	public void setStructureBusiness(StructureBusiness structureBusiness) {
		this.structureBusiness = structureBusiness;
	}

	@Override
	public StructureTemplateBusiness getStructureTemplateBusiness() {
		if (structureTemplateBusiness == null) {
			structureTemplateBusiness = new StructureTemplateBusinessImpl();
		}
		return structureTemplateBusiness;
	}

	@Override
	public void setStructureTemplateBusiness(
			StructureTemplateBusiness structureTemplateBusiness) {
		this.structureTemplateBusiness = structureTemplateBusiness;
	}
	
	@Override
	public PluginBusiness getPluginBusiness() {
		if (pluginBusiness == null) {
			pluginBusiness = new PluginBusinessImpl();
		}
		return pluginBusiness;
	}

	@Override
	public void setPluginBusiness(PluginBusiness bean) {
		this.pluginBusiness = bean;
	}
	
	@Override
	public PluginResourceBusiness getPluginResourceBusiness() {
		if (pluginResourceBusiness == null) {
			pluginResourceBusiness = new PluginResourceBusinessImpl();
		}
		return pluginResourceBusiness;
	}

	@Override
	public void setPluginResourceBusiness(PluginResourceBusiness bean) {
		this.pluginResourceBusiness = bean;
	}
	
	@Override
	public Dao getDao() {
		if (dao == null) {
			dao = new DaoImpl();
		}
		return dao;
	}

	@Override
	public void setDao(Dao bean) {
		this.dao = bean;
	}

	public SearchEngine getSearchEngine() {
		if (searchEngine == null) {
			searchEngine = new SearchEngineImpl();
		}
		return searchEngine;
	}

	public void setSearchEngine(SearchEngine searchEngine) {
		this.searchEngine = searchEngine;
	}

	@Override
	public ImportExportBusiness getImportExportBusiness() {
		if (importExportBusiness == null) {
			importExportBusiness = new ImportExportBusinessImpl();
		}
		return importExportBusiness;
	}

	@Override
	public void setImportExportBusiness(ImportExportBusiness bean) {
		importExportBusiness = bean;
	}

	@Override
	public TagBusiness getTagBusiness() {
		if (tagBusiness == null) {
			tagBusiness = new TagBusinessImpl();
		}
		return tagBusiness;
	}

	@Override
	public void setTagBusiness(TagBusiness bean) {
		tagBusiness = bean;
	}

	@Override
	public PicasaBusiness getPicasaBusiness() {
		if (picasaBusiness == null) {
			picasaBusiness = new PicasaBusinessImpl();
		}
		return picasaBusiness;
	}

	@Override
	public void setPicasaBusiness(PicasaBusiness bean) {
		picasaBusiness = bean;
	}

	@Override
	public SetupBean getSetupBean() {
		if (setupBean == null) {
			setupBean = new SetupBeanImpl();
		}
		return setupBean;
	}

	@Override
	public void setSetupBean(SetupBean bean) {
		setupBean = bean;
	}

	@Override
	public FormDataBusiness getFormDataBusiness() {
		if (formDataBusiness == null) {
			formDataBusiness = new FormDataBusinessImpl();
		}
		return formDataBusiness;
	}

	@Override
	public void setFormDataBusiness(FormDataBusiness bean) {
		formDataBusiness = bean;
	}

	@Override
	public TimeZone getTimeZone() {
		if (getUser() != null 
				&& !StringUtils.isEmpty(getUser().getTimezone())) {
			return TimeZone.getTimeZone(getUser().getTimezone());
		}
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		if (!StringUtils.isEmpty(config.getDefaultTimezone())) {
			return TimeZone.getTimeZone(config.getDefaultTimezone());
		}
		return TimeZone.getDefault();
	}

	@Override
	public PageAttributeBusiness getPageAttributeBusiness() {
		if (pageAttributeBusiness == null) {
			pageAttributeBusiness = new PageAttributeBusinessImpl();
		}
		return pageAttributeBusiness;
	}

	@Override
	public void setPageAttributeBusiness(PageAttributeBusiness bean) {
		pageAttributeBusiness = bean;
	}

	@Override
	public String getLanguage() {
    	String localeLanguage = HeiducContext.getInstance().getLanguage();
    	LanguageEntity language = getDao().getLanguageDao().getByCode(
    			localeLanguage);
    	if (language == null) {
    		localeLanguage = getDefaultLanguage();
    	}
		return localeLanguage; 
	}

	@Override
	public String getDefaultLanguage() {
   		LanguageEntity language = getDao().getLanguageDao().getByCode(
    			HeiducContext.getInstance().getConfig().getDefaultLanguage());
   		if (language == null) {
   			List<LanguageEntity> langs = getDao().getLanguageDao().select();
   			if (langs.isEmpty()) {
   				return LanguageEntity.ENGLISH_CODE;
   			}
   			language = langs.get(0);
   		}
		return language.getCode();
	}

	@Override
	public RewriteUrlBusiness getRewriteUrlBusiness() {
		if (rewriteUrlBusiness == null) {
			rewriteUrlBusiness = new RewriteUrlBusinessImpl();
		}
		return rewriteUrlBusiness;
	}

	@Override
	public void setRewriteUrlBusiness(RewriteUrlBusiness bean) {
		rewriteUrlBusiness = bean;
	}

	@Override
	public Oauth2Business getOauth2Business() {
		if (oauth2Business == null) {
			oauth2Business = new Oauth2BusinessImpl();
		}
		return oauth2Business;
	}

	@Override
	public void setOauth2Business(Oauth2Business bean) {
		oauth2Business = bean;
	}

}

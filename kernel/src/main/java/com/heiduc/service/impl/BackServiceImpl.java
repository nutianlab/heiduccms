

package com.heiduc.service.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jabsorb.JSONRPCBridge;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PluginEntity;
import com.heiduc.service.BackService;
import com.heiduc.service.back.CommentService;
import com.heiduc.service.back.ConfigService;
import com.heiduc.service.back.ContentPermissionService;
import com.heiduc.service.back.FieldService;
import com.heiduc.service.back.FileService;
import com.heiduc.service.back.FolderPermissionService;
import com.heiduc.service.back.FolderService;
import com.heiduc.service.back.FormService;
import com.heiduc.service.back.GroupService;
import com.heiduc.service.back.LanguageService;
import com.heiduc.service.back.MessageService;
import com.heiduc.service.back.PageAttributeService;
import com.heiduc.service.back.PageService;
import com.heiduc.service.back.PicasaService;
import com.heiduc.service.back.PluginService;
import com.heiduc.service.back.SeoUrlService;
import com.heiduc.service.back.StructureService;
import com.heiduc.service.back.StructureTemplateService;
import com.heiduc.service.back.TagService;
import com.heiduc.service.back.TemplateService;
import com.heiduc.service.back.UserService;
import com.heiduc.service.back.impl.CommentServiceImpl;
import com.heiduc.service.back.impl.ConfigServiceImpl;
import com.heiduc.service.back.impl.ContentPermissionServiceImpl;
import com.heiduc.service.back.impl.FieldServiceImpl;
import com.heiduc.service.back.impl.FileServiceImpl;
import com.heiduc.service.back.impl.FolderPermissionServiceImpl;
import com.heiduc.service.back.impl.FolderServiceImpl;
import com.heiduc.service.back.impl.FormServiceImpl;
import com.heiduc.service.back.impl.GroupServiceImpl;
import com.heiduc.service.back.impl.LanguageServiceImpl;
import com.heiduc.service.back.impl.MessageServiceImpl;
import com.heiduc.service.back.impl.PageAttributeServiceImpl;
import com.heiduc.service.back.impl.PageServiceImpl;
import com.heiduc.service.back.impl.PicasaServiceImpl;
import com.heiduc.service.back.impl.PluginServiceImpl;
import com.heiduc.service.back.impl.SeoUrlServiceImpl;
import com.heiduc.service.back.impl.StructureServiceImpl;
import com.heiduc.service.back.impl.StructureTemplateServiceImpl;
import com.heiduc.service.back.impl.TagServiceImpl;
import com.heiduc.service.back.impl.TemplateServiceImpl;
import com.heiduc.service.back.impl.UserServiceImpl;
import com.heiduc.service.plugin.PluginServiceManager;

public class BackServiceImpl implements BackService, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(BackServiceImpl.class);

	private FileService fileService;
	private FolderService folderService;
	private CommentService commentService;
	private PageService pageService;
	private TemplateService templateService;
	private FormService formService;
	private FieldService fieldService;
	private ConfigService configService;
	private SeoUrlService seoUrlService;
	private UserService userService;
	private LanguageService languageService;
	private MessageService messageService;
	private GroupService groupService;
	private ContentPermissionService contentPermissionService;
	private FolderPermissionService folderPermissionService;
	private StructureService structureService;
	private StructureTemplateService structureTemplateService;
	private PluginService pluginService;
	private TagService tagService;
	private PicasaService picasaService;
	private PageAttributeService pageAttributeService;
	
	@Override
	public void register(JSONRPCBridge bridge) {
		bridge.registerObject("fileService", getFileService());
		bridge.registerObject("folderService", getFolderService());
		bridge.registerObject("commentService", getCommentService());
		bridge.registerObject("pageService", getPageService());
		bridge.registerObject("templateService", getTemplateService());
		bridge.registerObject("fieldService", getFieldService());
		bridge.registerObject("formService", getFormService());
		bridge.registerObject("configService", getConfigService());
		bridge.registerObject("seoUrlService", getSeoUrlService());
		bridge.registerObject("userService", getUserService());
		bridge.registerObject("languageService", getLanguageService());
		bridge.registerObject("messageService", getMessageService());
		bridge.registerObject("groupService", getGroupService());
		bridge.registerObject("contentPermissionService", 
				getContentPermissionService());
		bridge.registerObject("folderPermissionService", 
				getFolderPermissionService());
		bridge.registerObject("structureService", getStructureService());
		bridge.registerObject("structureTemplateService", 
				getStructureTemplateService());
		bridge.registerObject("pluginService", getPluginService());
		bridge.registerObject("tagService", getTagService());
		bridge.registerObject("picasaService", getPicasaService());
		bridge.registerObject("pageAttributeService", getPageAttributeService());
		registerPluginServices(bridge);
	}
	
	@Override
	public void unregister(JSONRPCBridge bridge) {
		bridge.unregisterObject("fileService");
		bridge.unregisterObject("folderService");
		bridge.unregisterObject("commentService");
		bridge.unregisterObject("pageService");
		bridge.unregisterObject("templateService");
		bridge.unregisterObject("fieldService");
		bridge.unregisterObject("formService");
		bridge.unregisterObject("configService");
		bridge.unregisterObject("seoUrlService");
		bridge.unregisterObject("userService");
		bridge.unregisterObject("languageService");
		bridge.unregisterObject("messageService");
		bridge.unregisterObject("groupService");
		bridge.unregisterObject("contentPermissionService");
		bridge.unregisterObject("folderPermissionService");
		bridge.unregisterObject("structureService");
		bridge.unregisterObject("structureTemplateService");
		bridge.unregisterObject("pluginService");
		bridge.unregisterObject("tagService");
		bridge.unregisterObject("picasaService");
		bridge.unregisterObject("pageAttributeService");
		unregisterPluginServices(bridge);
	}

	@Override
	public FileService getFileService() {
		if (fileService == null) {
			fileService = new FileServiceImpl();
		}
		return fileService;
	}

	@Override
	public void setFileService(FileService bean) {
		fileService = bean;
	}

	@Override
	public FolderService getFolderService() {
		if (folderService == null) {
			folderService = new FolderServiceImpl();
		}
		return folderService;
	}

	@Override
	public void setFolderService(FolderService bean) {
		folderService = bean;
	}

	@Override
	public CommentService getCommentService() {
		if (commentService == null) {
			commentService = new CommentServiceImpl();
		}
		return commentService;
	}

	@Override
	public void setCommentService(CommentService bean) {
		commentService = bean;		
	}

	@Override
	public PageService getPageService() {
		if (pageService == null) {
			pageService = new PageServiceImpl();
		}
		return pageService;
	}

	@Override
	public void setPageService(PageService bean) {
		pageService = bean;		
	}

	@Override
	public TemplateService getTemplateService() {
		if (templateService == null) {
			templateService = new TemplateServiceImpl();
		}
		return templateService;
	}

	@Override
	public void setTemplateService(TemplateService templateService) {
		this.templateService = templateService;
	}

	@Override
	public FieldService getFieldService() {
		if (fieldService == null) {
			fieldService = new FieldServiceImpl();
		}
		return fieldService;
	}

	@Override
	public void setFieldService(FieldService bean) {
		fieldService = bean;		
	}

	@Override
	public ConfigService getConfigService() {
		if (configService == null) {
			configService = new ConfigServiceImpl();
		}
		return configService;
	}

	@Override
	public void setConfigService(ConfigService bean) {
		configService = bean;		
	}

	@Override
	public FormService getFormService() {
		if (formService == null) {
			formService = new FormServiceImpl();
		}
		return formService;
	}

	@Override
	public void setFormService(FormService bean) {
		formService = bean;		
	}

	@Override
	public SeoUrlService getSeoUrlService() {
		if (seoUrlService == null) {
			seoUrlService = new SeoUrlServiceImpl();
		}
		return seoUrlService;
	}

	@Override
	public void setSeoUrlService(SeoUrlService bean) {
		seoUrlService = bean;		
	}

	@Override
	public UserService getUserService() {
		if (userService == null) {
			userService = new UserServiceImpl();
		}
		return userService;
	}

	@Override
	public void setUserService(UserService bean) {
		userService = bean;		
	}

	@Override
	public LanguageService getLanguageService() {
		if (languageService == null) {
			languageService = new LanguageServiceImpl();
		}
		return languageService;
	}

	@Override
	public void setLanguageService(LanguageService bean) {
		languageService = bean;		
	}

	@Override
	public MessageService getMessageService() {
		if (messageService == null) {
			messageService = new MessageServiceImpl();
		}
		return messageService;
	}

	@Override
	public void setMessageService(MessageService bean) {
		messageService = bean;		
	}

	@Override
	public GroupService getGroupService() {
		if (groupService == null) {
			groupService = new GroupServiceImpl();
		}
		return groupService;
	}

	@Override
	public void setGroupService(GroupService bean) {
		groupService = bean;		
	}

	@Override
	public ContentPermissionService getContentPermissionService() {
		if (contentPermissionService == null) {
			contentPermissionService = new ContentPermissionServiceImpl();
		}
		return contentPermissionService;
	}

	@Override
	public void setContentPermissionService(ContentPermissionService bean) {
		contentPermissionService = bean;		
	}

	@Override
	public FolderPermissionService getFolderPermissionService() {
		if (folderPermissionService == null) {
			folderPermissionService = new FolderPermissionServiceImpl();
		}
		return folderPermissionService;
	}

	@Override
	public void setFolderPermissionService(FolderPermissionService bean) {
		folderPermissionService = bean;		
	}

	@Override
	public StructureService getStructureService() {
		if (structureService == null) {
			structureService = new StructureServiceImpl();
		}
		return structureService;
	}

	@Override
	public void setStructureService(StructureService structureService) {
		this.structureService = structureService;
	}

	@Override
	public StructureTemplateService getStructureTemplateService() {
		if (structureTemplateService == null) {
			structureTemplateService = new StructureTemplateServiceImpl();
		}
		return structureTemplateService;
	}

	@Override
	public void setStructureTemplateService(
			StructureTemplateService structureTemplateService) {
		this.structureTemplateService = structureTemplateService;
	}
	
	@Override
	public PluginService getPluginService() {
		if (pluginService == null) {
			pluginService = new PluginServiceImpl();
		}
		return pluginService;
	}

	@Override
	public void setPluginService(PluginService bean) {
		this.pluginService = bean;
	}

	@Override
	public TagService getTagService() {
		if (tagService == null) {
			tagService = new TagServiceImpl();
		}
		return tagService;
	}

	@Override
	public void setTagService(TagService bean) {
		this.tagService = bean;
	}

	@Override
	public PicasaService getPicasaService() {
		if (picasaService == null) {
			picasaService = new PicasaServiceImpl();
		}
		return picasaService;
	}

	@Override
	public void setPicasaService(PicasaService bean) {
		this.picasaService = bean;
	}

	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	private void registerPluginServices(JSONRPCBridge bridge) {
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			try {
				PluginServiceManager manager = getBusiness()
						.getPluginBusiness().getBackServices(plugin);
				if (manager != null) {
					manager.register(bridge);
				}
			} catch (ClassNotFoundException e) {
				LOG.error(e.getStackTrace());
			} catch (InstantiationException e) {
				LOG.error(e.getStackTrace());
			} catch (IllegalAccessException e) {
				LOG.error(e.getStackTrace());
			}
		}
	}
	
	private void unregisterPluginServices(JSONRPCBridge bridge) {
		for (PluginEntity plugin : getDao().getPluginDao().selectEnabled()) {
			try {
				PluginServiceManager manager = getBusiness()
					.getPluginBusiness().getBackServices(plugin);
				if (manager != null) {
					manager.unregister(bridge);
				}				
			} catch (ClassNotFoundException e) {
				LOG.error(e.getStackTrace());
			} catch (InstantiationException e) {
				LOG.error(e.getStackTrace());
			} catch (IllegalAccessException e) {
				LOG.error(e.getStackTrace());
			}
		}
	}

	private Dao getDao() {
		return getBusiness().getDao();
	}

	@Override
	public PageAttributeService getPageAttributeService() {
		if (pageAttributeService == null) {
			pageAttributeService = new PageAttributeServiceImpl();
		}
		return pageAttributeService;
	}

	@Override
	public void setPageAttributeService(PageAttributeService bean) {
		pageAttributeService = bean;		
	}
	
}

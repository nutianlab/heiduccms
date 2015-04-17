

package com.heiduc.service;

import org.jabsorb.JSONRPCBridge;

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

public interface BackService {
	
	void register(JSONRPCBridge bridge);
	void unregister(JSONRPCBridge bridge);

	FileService getFileService();
	void setFileService(FileService bean);

	FolderService getFolderService();
	void setFolderService(FolderService bean);
	
	CommentService getCommentService();
	void setCommentService(CommentService bean);

	PageService getPageService();
	void setPageService(PageService bean);

	TemplateService getTemplateService();
	void setTemplateService(TemplateService bean);

	FormService getFormService();
	void setFormService(FormService bean);

	FieldService getFieldService();
	void setFieldService(FieldService bean);
	
	ConfigService getConfigService();
	void setConfigService(ConfigService bean);
	
	SeoUrlService getSeoUrlService();
	void setSeoUrlService(SeoUrlService bean);

	UserService getUserService();
	void setUserService(UserService bean);

	LanguageService getLanguageService();
	void setLanguageService(LanguageService bean);

	MessageService getMessageService();
	void setMessageService(MessageService bean);

	GroupService getGroupService();
	void setGroupService(GroupService bean);

	ContentPermissionService getContentPermissionService();
	void setContentPermissionService(ContentPermissionService bean);

	FolderPermissionService getFolderPermissionService();
	void setFolderPermissionService(FolderPermissionService bean);

	StructureService getStructureService();
	void setStructureService(StructureService bean);
	
	StructureTemplateService getStructureTemplateService();
	void setStructureTemplateService(StructureTemplateService bean);

	PluginService getPluginService();
	void setPluginService(PluginService bean);

	TagService getTagService();
	void setTagService(TagService bean);

	PicasaService getPicasaService();
	void setPicasaService(PicasaService bean);

	PageAttributeService getPageAttributeService();
	void setPageAttributeService(PageAttributeService bean);
}

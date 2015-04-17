

package com.heiduc.service.vo;

import java.util.Collections;
import java.util.List;

import com.heiduc.business.vo.StructureFieldVO;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.ContentEntity;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.TagEntity;
import com.heiduc.entity.TemplateEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class PageRequestVO {

    private PageEntity page;
    private ConfigEntity config;
    private List<PageVO> versions;
    private List<TemplateEntity> templates;
    private List<LanguageEntity> languages;
    private List<PageVO> children;
    private List<CommentVO> comments;
    private List<ContentEntity> contents;
    private List<ContentPermissionVO> permissions;
    private List<GroupVO> groups;
    private ContentPermissionEntity pagePermission;
    private List<StructureEntity> structures;
    private List<StructureFieldVO> structureFields;
    private List<TagEntity> tags;
    private String dependencies;
    private Long folderId;
	
    public PageRequestVO() {
    	versions = Collections.EMPTY_LIST;
    	templates = Collections.EMPTY_LIST;
    	languages = Collections.EMPTY_LIST;
    	children = Collections.EMPTY_LIST;
    	comments = Collections.EMPTY_LIST;
    	contents = Collections.EMPTY_LIST;
    	permissions = Collections.EMPTY_LIST;
    	groups = Collections.EMPTY_LIST;
    	structures = Collections.EMPTY_LIST;
    	structureFields = Collections.EMPTY_LIST;
    }
    
    public PageEntity getPage() {
		return page;
	}
	
    public void setPage(PageEntity page) {
		this.page = page;
	}
	
    public List<PageVO> getVersions() {
		return versions;
	}
	
    public void setVersions(List<PageVO> versions) {
		this.versions = versions;
	}
	
    public List<TemplateEntity> getTemplates() {
		return templates;
	}
	
    public void setTemplates(List<TemplateEntity> templates) {
		this.templates = templates;
	}
	
    public List<LanguageEntity> getLanguages() {
		return languages;
	}
	
    public void setLanguages(List<LanguageEntity> languages) {
		this.languages = languages;
	}
	
    public List<PageVO> getChildren() {
		return children;
	}
	
    public void setChildren(List<PageVO> children) {
		this.children = children;
	}
	
    public List<CommentVO> getComments() {
		return comments;
	}
	
    public void setComments(List<CommentVO> comments) {
		this.comments = comments;
	}
	
    public List<ContentEntity> getContents() {
		return contents;
	}
	
    public void setContents(List<ContentEntity> contents) {
		this.contents = contents;
	}
	
    public List<ContentPermissionVO> getPermissions() {
		return permissions;
	}
	
    public void setPermissions(List<ContentPermissionVO> permissions) {
		this.permissions = permissions;
	}
	
    public List<GroupVO> getGroups() {
		return groups;
	}
	
    public void setGroups(List<GroupVO> groups) {
		this.groups = groups;
	}
	
    public ContentPermissionEntity getPagePermission() {
		return pagePermission;
	}
	
    public void setPagePermission(ContentPermissionEntity pagePermission) {
		this.pagePermission = pagePermission;
	}

	public List<StructureEntity> getStructures() {
		return structures;
	}

	public void setStructures(List<StructureEntity> structures) {
		this.structures = structures;
	}

	public List<StructureFieldVO> getStructureFields() {
		return structureFields;
	}

	public void setStructureFields(List<StructureFieldVO> structureFields) {
		this.structureFields = structureFields;
	}

	public List<TagEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	public ConfigEntity getConfig() {
		return config;
	}

	public void setConfig(ConfigEntity config) {
		this.config = config;
	}

	public String getDependencies() {
		return dependencies;
	}

	public void setDependencies(String dependencies) {
		this.dependencies = dependencies;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

}

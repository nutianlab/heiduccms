

package com.heiduc.service.back.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.entity.FolderEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.FileService;
import com.heiduc.service.back.FolderPermissionService;
import com.heiduc.service.back.FolderService;
import com.heiduc.service.back.GroupService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.FolderRequestVO;
import com.heiduc.utils.ParamUtil;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class FolderServiceImpl extends AbstractServiceImpl 
		implements FolderService {

	@Override
	public TreeItemDecorator<FolderEntity> getTree() {
		return getBusiness().getFolderBusiness().getTree();
	}

	@Override
	public String getFolderPath(final Long folderId) {
		FolderEntity folder = getDao().getFolderDao().getById(folderId);
		if (folder != null) {
			return getBusiness().getFolderBusiness().getFolderPath(folder);
		}
		return null;
	}

	@Override
	public FolderEntity getFolder(Long id) {
		return getBusiness().getFolderBusiness().getById(id);
	}

	@Override
	public List<FolderEntity> getByParent(Long id) {
		return getBusiness().getFolderBusiness().getByParent(id);
	}

	@Override
	public ServiceResponse saveFolder(Map<String, String> vo) {
		FolderEntity folder = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			folder = getDao().getFolderDao().getById(Long.valueOf(vo.get("id")));
		}
		if (folder == null) {
			folder = new FolderEntity();
		}
		folder.setName(vo.get("name"));
		folder.setTitle(vo.get("title"));
		folder.setParent(ParamUtil.getLong(vo.get("parent"), null));
		List<String> errors = getBusiness().getFolderBusiness()
			.validateBeforeUpdate(folder);
		if (errors.isEmpty()) {
			getDao().getFolderDao().save(folder);
			return ServiceResponse.createSuccessResponse(folder.getId().toString());
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public ServiceResponse deleteFolder(List<String> ids) {
		getBusiness().getFolderBusiness().recursiveRemove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("folder.success_delete"));
	}

	@Override
	public FolderRequestVO getFolderRequest(Long folderId, Long folderParentId) {
		FolderRequestVO result = new FolderRequestVO();
		FolderEntity folder = getFolder(folderId);
		result.setFolder(folder);
		Long permFolderId = folderParentId;
		if (result.getFolder() != null) {
			result.setChildren(getByParent(folderId));
			result.setFiles(getFileService().getByFolder(folderId));
			result.setPermissions(getFolderPermissionService().selectByFolder(
					folderId));
			permFolderId = folderId;
			result.setAncestors(getDao().getFolderDao().getAncestors(folder));
			result.setParent(getFolder(folder.getParent()));
		}
		else {
			FolderEntity parent = getFolder(folderParentId);
			result.setAncestors(getDao().getFolderDao().getAncestors(parent));
			result.setParent(parent);
		}
		result.setGroups(getGroupService().select());
		result.setFolderPermission(getFolderPermissionService().getPermission(
				permFolderId));
		return result;
	}

	public FileService getFileService() {
		return getBackService().getFileService();
	}

	public FolderPermissionService getFolderPermissionService() {
		return getBackService().getFolderPermissionService();
	}
	
	public GroupService getGroupService() {
		return getBackService().getGroupService();
	}

	@Override
	public TreeItemDecorator<FolderEntity> getFolderByPath(
			String path) {
		return getBusiness().getFolderBusiness().findFolderByPath(
				getBusiness().getFolderBusiness().getTree(), path);
	}

	@Override
	public FolderEntity createFolderByPath(String path) 
			throws UnsupportedEncodingException {
		return getBusiness().getFolderBusiness().createFolder(path);
	}


}

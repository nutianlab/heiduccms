

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


import com.heiduc.common.HeiducContext;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.helper.GroupHelper;
import com.heiduc.enums.FolderPermissionType;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceException;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.FolderPermissionService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.FolderPermissionVO;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class FolderPermissionServiceImpl extends AbstractServiceImpl 
		implements FolderPermissionService {

	@Override
	public ServiceResponse remove(List<String> ids) {
		getDao().getFolderPermissionDao().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("folder.permissions_success_delete"));
	}

	@Override
	public FolderPermissionEntity getById(Long id) {
		return getDao().getFolderPermissionDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		try {
			GroupEntity group = getDao().getGroupDao().getById(Long.valueOf((
				vo.get("groupId"))));
			if (group == null) {
				throw new ServiceException(Messages.get("group_not_found"));
			}
			FolderEntity folder = getDao().getFolderDao().getById(
					Long.valueOf(vo.get("folderId")));
			if (folder == null) {
				throw new ServiceException(Messages.get("folder.not_found",
						vo.get("folderId")));
			}
			FolderPermissionType perm = FolderPermissionType.valueOf(
				vo.get("permission"));
			getBusiness().getFolderPermissionBusiness().setPermission(
					folder, group, perm);
			return ServiceResponse.createSuccessResponse(
				Messages.get("folder.permission_success_save"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.toString() + " " 
					+ e.getMessage());
		}
	}

	@Override
	public List<FolderPermissionVO> selectByFolder(Long folderId) {
		FolderEntity folder = getDao().getFolderDao().getById(folderId);
		if (folder == null) {
			return Collections.EMPTY_LIST;
		}
		List<FolderPermissionEntity> inherited = getBusiness()
				.getFolderPermissionBusiness().getInheritedPermissions(folder);
		//logger.info("inherited " + inherited.size());
		List<FolderPermissionEntity> direct = getDao()
				.getFolderPermissionDao().selectByFolder(folderId);
		//logger.info("direct " + direct.size());
		Map<Long, GroupEntity> groups = GroupHelper.createIdMap(getDao()
				.getGroupDao().select());
		List<FolderPermissionVO> result = new ArrayList<FolderPermissionVO>();
		for (FolderPermissionEntity perm : inherited) {
			if (!containsPermission(direct, perm)
				&& !containsPermissionVO(result, perm)) {
				FolderPermissionVO vo = new FolderPermissionVO(perm);
				vo.setInherited(true);
				vo.setGroup(groups.get(perm.getGroupId()));
				result.add(vo);
			}
		}
		for (FolderPermissionEntity perm : direct) {
			FolderPermissionVO vo = new FolderPermissionVO(perm);
			vo.setInherited(false);
			vo.setGroup(groups.get(perm.getGroupId()));
			result.add(vo);
		}
		return result;
	}

	private boolean containsPermission(List<FolderPermissionEntity> list,
			FolderPermissionEntity permission) {
		for (FolderPermissionEntity perm : list) {
			if (perm.getGroupId().equals(permission.getGroupId())) {
				return true;
			}
		}
		return false;
	}

	private boolean containsPermissionVO(List<FolderPermissionVO> list,
			FolderPermissionEntity permission) {
		for (FolderPermissionVO perm : list) {
			if (perm.getGroup().getId().equals(permission.getGroupId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public FolderPermissionEntity getPermission(Long folderId) {
		FolderEntity folder = getDao().getFolderDao().getById(folderId);
		if (folder != null) {
			return getBusiness().getFolderPermissionBusiness().getPermission(
				folder, HeiducContext.getInstance().getUser());
		}
		return null;
	}
	
}

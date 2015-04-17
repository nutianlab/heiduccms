

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.helper.GroupHelper;
import com.heiduc.enums.ContentPermissionType;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceException;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.ContentPermissionService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.ContentPermissionVO;
import com.heiduc.utils.UrlUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class ContentPermissionServiceImpl extends AbstractServiceImpl 
		implements ContentPermissionService {

	@Override
	public ServiceResponse remove(List<String> ids) {
		List<Long> idList = new ArrayList<Long>();
		for (String id : ids) {
			idList.add(Long.valueOf(id));
		}
		getDao().getContentPermissionDao().remove(idList);
		return ServiceResponse.createSuccessResponse(
				Messages.get("content_permissions_success_delete"));
	}

	@Override
	public ContentPermissionEntity getById(Long id) {
		return getDao().getContentPermissionDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		try {
			GroupEntity group = getDao().getGroupDao().getById(Long.valueOf((
				vo.get("groupId"))));
			if (group == null) {
				throw new ServiceException(Messages.get("group_not_found"));
			}
			String url = vo.get("url");
			ContentPermissionType perm = ContentPermissionType.valueOf(
				vo.get("permission"));
			String languages = StringUtils.isEmpty(vo.get("languages")) ? null : 
				vo.get("languages"); 
			getBusiness().getContentPermissionBusiness().setPermission(
					url, group, perm, languages);
			return ServiceResponse.createSuccessResponse(
					Messages.get("content_permissions_success_save"));
		}
		catch (Exception e) {
			return ServiceResponse.createErrorResponse(e.toString() + " " 
					+ e.getMessage());
		}
	}

	@Override
	public List<ContentPermissionVO> selectByUrl(String pageUrl) {
		List<ContentPermissionEntity> direct = getDao()
				.getContentPermissionDao().selectByUrl(pageUrl);
		List<ContentPermissionEntity> inherited = getBusiness()
				.getContentPermissionBusiness().getInheritedPermissions(
						UrlUtil.getParentFriendlyURL(pageUrl));
		Map<Long, GroupEntity> groups = GroupHelper.createIdMap(getDao()
				.getGroupDao().select());
		List<ContentPermissionVO> result = new ArrayList<ContentPermissionVO>();
		for (ContentPermissionEntity perm : inherited) {
			if (!containsPermission(direct, perm)
				&& !containsPermissionVO(result, perm)) {
				ContentPermissionVO vo = new ContentPermissionVO(perm);
				vo.setInherited(true);
				vo.setGroup(groups.get(perm.getGroupId()));
				result.add(vo);
			}
		}
		for (ContentPermissionEntity perm : direct) {
			ContentPermissionVO vo = new ContentPermissionVO(perm);
			vo.setInherited(false);
			vo.setGroup(groups.get(perm.getGroupId()));
			result.add(vo);
		}
		return result;
	}

	private boolean containsPermission(List<ContentPermissionEntity> list,
			ContentPermissionEntity permission) {
		for (ContentPermissionEntity perm : list) {
			if (perm.getGroupId().equals(permission.getGroupId())) {
				return true;
			}
		}
		return false;
	}

	private boolean containsPermissionVO(List<ContentPermissionVO> list,
			ContentPermissionEntity permission) {
		for (ContentPermissionVO perm : list) {
			if (perm.getGroup().getId().equals(permission.getGroupId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ContentPermissionEntity getPermission(String url) {
		return getBusiness().getContentPermissionBusiness().getPermission(
				url, HeiducContext.getInstance().getUser());
	}
	
}

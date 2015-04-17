

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.heiduc.business.ContentPermissionBusiness;
import com.heiduc.business.FolderBusiness;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.enums.ContentPermissionType;
import com.heiduc.i18n.Messages;
import com.heiduc.utils.UrlUtil;

/**
 * @author Alexander Oleynik
 */
public class ContentPermissionBusinessImpl extends AbstractBusinessImpl
		implements ContentPermissionBusiness {

	@Override
	public ContentPermissionEntity getGuestPermission(final String url) {
		GroupEntity guests = getDao().getGroupDao().getGuestsGroup();
		ContentPermissionEntity result = getGroupPermission(url, guests.getId());
		result.setUrl(url);
		return result;
	}

	@Override
	public ContentPermissionEntity getPermission(final String url,
			final UserEntity user) {
		if (user == null || getGuestPermission(url).isRead()) {
			return getGuestPermission(url);
		}
		if (user.isAdmin()) {
			return new ContentPermissionEntity(url, ContentPermissionType.ADMIN);
		}
		List<UserGroupEntity> userGroups = getDao().getUserGroupDao()
				.selectByUser(user.getId());
		userGroups.add(new UserGroupEntity(getDao().getGroupDao()
				.getGuestsGroup().getId(), user.getId()));
		List<ContentPermissionEntity> permissions = new ArrayList<ContentPermissionEntity>();
		for (UserGroupEntity userGroup : userGroups) {
			ContentPermissionEntity contentPermission = getGroupPermission(url,
					userGroup.getGroupId());
			if (contentPermission != null) {
				permissions.add(contentPermission);
			}
		}
		ContentPermissionEntity result = consolidatePermissions(permissions);
		result.setUrl(url);
		return result;
	}

	private ContentPermissionEntity consolidatePermissions(
			List<ContentPermissionEntity> permissions) {
		ContentPermissionEntity result = new ContentPermissionEntity();
		result.setPermission(ContentPermissionType.DENIED);
		result.setAllLanguages(false);
		Set<String> languages = new HashSet<String>();
		for (ContentPermissionEntity perm : permissions) {
			if (perm.isMyPermissionHigher(result)) {
				result.setPermission(perm.getPermission());
				if (perm.isAllLanguages()) {
					result.setAllLanguages(true);
				}
				languages.addAll(perm.getLanguagesList());
			}
		}
		String langs = "";
		for (String lang : languages) {
			langs += (langs.equals("") ? "" : ",") + lang;
		}
		result.setLanguages(langs);
		return result;
	}

	private ContentPermissionEntity getGroupPermission(String url, Long groupId) {
		String myUrl = url;
		while (myUrl != null) {
			ContentPermissionEntity perm = getDao().getContentPermissionDao()
					.getByUrlGroup(myUrl, groupId);
			if (perm != null) {
				return perm;
			}
			if (myUrl.equals("/")) {
				myUrl = null;
			} else {
				myUrl = UrlUtil.getParentFriendlyURL(myUrl);
			}
		}
		;
		return null;
	}

	@Override
	public void setPermission(String url, GroupEntity group,
			ContentPermissionType permission) {
		setPermission(url, group, permission, null);
	}

	@Override
	public void setPermission(String url, GroupEntity group,
			ContentPermissionType permission, String languages) {
		ContentPermissionEntity perm = getDao().getContentPermissionDao()
				.getByUrlGroup(url, group.getId());
		if (perm == null) {
			perm = new ContentPermissionEntity();
			perm.setUrl(url);
			perm.setGroupId(group.getId());
		}
		if (languages == null) {
			perm.setAllLanguages(true);
		} else {
			perm.setAllLanguages(false);
			perm.setLanguages(languages);
		}
		perm.setPermission(permission);
		getDao().getContentPermissionDao().save(perm);
		getFolderBusiness().createFolder("/page" + url);
	}

	@Override
	public List<String> validateBeforeUpdate(ContentPermissionEntity perm) {
		List<String> errors = new ArrayList<String>();
		if (perm.getGroupId() == null) {
			errors.add(Messages.get("group_is_empty"));
		}
		if (perm.getUrl() == null) {
			errors.add(Messages.get("url_is_empty"));
		}
		if (perm.getPermission() == null) {
			errors.add(Messages.get("permission_is_empty"));
		}
		return errors;
	}

	@Override
	public List<ContentPermissionEntity> getInheritedPermissions(
			final String url) {
		List<ContentPermissionEntity> result = new ArrayList<ContentPermissionEntity>();
		String myUrl = url;
		while (myUrl != null) {
			result.addAll(getDao().getContentPermissionDao().selectByUrl(myUrl));
			if (myUrl.equals("/")) {
				myUrl = null;
			} else {
				myUrl = UrlUtil.getParentFriendlyURL(myUrl);
			}
		}
		return result;
	}

	@Override
	public List<ContentPermissionEntity> selectByUrl(String pageUrl) {
		List<ContentPermissionEntity> direct = getDao()
				.getContentPermissionDao().selectByUrl(pageUrl);
		List<ContentPermissionEntity> inherited = 
				getInheritedPermissions(UrlUtil.getParentFriendlyURL(pageUrl));
		List<ContentPermissionEntity> result = 
				new ArrayList<ContentPermissionEntity>();
		for (ContentPermissionEntity perm : inherited) {
			if (!direct.contains(perm) && !result.contains(perm)) {
				result.add(perm);
			}
		}
		for (ContentPermissionEntity perm : direct) {
			result.add(perm);
		}
		return result;
	}

	private FolderBusiness getFolderBusiness() {
		return getBusiness().getFolderBusiness();
	}

}

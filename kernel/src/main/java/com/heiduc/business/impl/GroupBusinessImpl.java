

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.GroupBusiness;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.i18n.Messages;

/**
 * @author Alexander Oleynik
 */
public class GroupBusinessImpl extends AbstractBusinessImpl 
	implements GroupBusiness {

	@Override
	public List<String> validateBeforeUpdate(final GroupEntity group) {
		List<String> errors = new ArrayList<String>();
		GroupEntity foundGroup = getDao().getGroupDao().getByName(
				group.getName());
		if (group.getId() == null) {
			if (foundGroup != null) {
				errors.add(Messages.get("group_already_exists"));
			}
		}
		else {
			if (foundGroup != null && !foundGroup.getId().equals(group.getId())) {
				errors.add(Messages.get("group_already_exists"));
			}
		}
		if (StringUtils.isEmpty(group.getName())) {
			errors.add(Messages.get("name_is_empty"));
		}
		return errors;
	}

	@Override
	public void addUserToGroup(GroupEntity group, UserEntity user) {
		UserGroupEntity userGroup = getDao().getUserGroupDao().getByUserGroup(
				group.getId(), user.getId());
		if (userGroup == null) {
			getDao().getUserGroupDao().save(new UserGroupEntity(
					group.getId(), user.getId()));
		}
	}
	
	@Override
	public void remove(List<Long> ids) {
		if (HeiducContext.getInstance().getUser().isAdmin()) {
			getDao().getUserGroupDao().removeByGroup(ids);
			getDao().getContentPermissionDao().removeByGroup(ids);
			getDao().getFolderPermissionDao().removeByGroup(ids);
			getDao().getGroupDao().remove(ids);
		}
	}
	
}

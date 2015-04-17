

package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.heiduc.common.BCrypt;
import com.heiduc.entity.UserEntity;
import com.heiduc.enums.UserRole;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.UserService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.UserVO;
import com.heiduc.utils.ParamUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class UserServiceImpl extends AbstractServiceImpl 
		implements UserService {

	@Override
	public List<UserVO> select() {
		return UserVO.create(getDao().getUserDao().select());
	}

	@Override
	public ServiceResponse remove(List<String> ids) {
		List<Long> idList = new ArrayList<Long>();
		String msg = Messages.get("users.success_delete");
		for (String idString : ids) {
			Long id = Long.valueOf(idString);
			if (getBusiness().getUser().getId().equals(id)) {
				msg = Messages.get("users.cant_delete_myself");
			}
			else {
				idList.add(id);
			}
		}
		getBusiness().getUserBusiness().remove(idList);
		return ServiceResponse.createSuccessResponse(msg);
	}

	@Override
	public UserEntity getById(Long id) {
		return getDao().getUserDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		UserEntity user = null;
		if (!StringUtils.isEmpty(vo.get("id"))) {
			user = getDao().getUserDao().getById(Long.valueOf(vo.get("id")));
		}
		if (user == null) {
			user = new UserEntity();
		}
		user.setName(vo.get("name"));
		if (!StringUtils.isEmpty(vo.get("email"))) {
			user.setEmail(vo.get("email").toLowerCase());
		}
		if (!StringUtils.isEmpty(vo.get("password"))) {
			user.setPassword(BCrypt.hashpw(vo.get("password"), 
					BCrypt.gensalt()));
		}
		if (!StringUtils.isEmpty(vo.get("role"))) {
			user.setRole(UserRole.valueOf(vo.get("role")));
		}
		if (!StringUtils.isEmpty(vo.get("timezone"))) {
			user.setTimezone(vo.get("timezone"));
		}
		if (!StringUtils.isEmpty(vo.get("disabled"))) {
			user.setDisabled(ParamUtil.getBoolean(vo.get("disabled"), false));
		}
		List<String> errors = getBusiness().getUserBusiness()
				.validateBeforeUpdate(user);
		if (errors.isEmpty()) {
			getDao().getUserDao().save(user);
			return ServiceResponse.createSuccessResponse(
					Messages.get("user.success_save"));
		}
		else {
			return ServiceResponse.createErrorResponse(
					Messages.get("errors_occured"), errors);
		}
	}

	@Override
	public UserEntity getLoggedIn() {
		return getBusiness().getUser();
	}

	@Override
	public List<UserVO> selectByGroup(String groupId) {
		return UserVO.create(getDao().getUserDao().selectByGroup(
				Long.valueOf(groupId)));
	}

	@Override
	public ServiceResponse disable(Long userId, boolean disable) {
		UserEntity user = getDao().getUserDao().getById(userId);
		if (user == null) {
			return ServiceResponse.createErrorResponse(Messages.get(
					"user_not_found"));
		}
		user.setDisabled(disable);
		getDao().getUserDao().save(user);
		return ServiceResponse.createSuccessResponse(Messages.get("success"));
	}

	@Override
	public List<String> getTimezones() {
		List<String> result = new ArrayList<String>();
		String[] ids = TimeZone.getAvailableIDs();
		Arrays.sort(ids);
		for (String id : ids) {
			result.add(id);
		}
		return result;
	}


}

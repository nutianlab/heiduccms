

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.entity.UserEntity;

/**
 * Value object to be returned from services.
 * 
 * @author Alexander Oleynik
 */
public class UserVO {

    private UserEntity user;

	public UserVO(final UserEntity entity) {
		user = entity;
	}

	public static List<UserVO> create(List<UserEntity> list) {
		List<UserVO> result = new ArrayList<UserVO>();
		for (UserEntity User : list) {
			result.add(new UserVO(User));
		}
		return result;
	}

	public String getId() {
		return user.getId().toString();
	}

	public String getName() {
		return user.getName();
	}

	public String getEmail() {
		return user.getEmail();
	}
	
	public String getRole() {
		return user.getRole().name();
	}
	
	public boolean isDisabled() {
		return user.isDisabled();
	}
}

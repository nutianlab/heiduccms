

package com.heiduc.entity.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heiduc.entity.UserEntity;
import com.heiduc.enums.UserRole;

public class UserHelper {

	public static Map<Long, UserEntity> createIdMap(List<UserEntity> list) {
		Map<Long, UserEntity> map = new HashMap<Long, UserEntity>();
		for (UserEntity user : list) {
			map.put(user.getId(), user);
		}
		return map;
	}
	
	public static UserEntity ADMIN = new UserEntity("System", "", 
			"system@vosao.org", UserRole.ADMIN);
	
}

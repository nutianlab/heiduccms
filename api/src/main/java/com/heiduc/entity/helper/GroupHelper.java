

package com.heiduc.entity.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.heiduc.entity.GroupEntity;

public class GroupHelper {

	public static Map<Long, GroupEntity> createIdMap(
			final List<GroupEntity> list) {
		Map<Long, GroupEntity> result = new HashMap<Long, GroupEntity>();
		for (GroupEntity group : list) {
			result.put(group.getId(), group);
		}
		return result;
	}
	
}



package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.heiduc.entity.GroupEntity;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class GroupVO {

    private GroupEntity group;
    private List<UserVO> users;

	public GroupVO(final GroupEntity entity) {
		group = entity;
		users = new ArrayList<UserVO>();
	}

	public static List<GroupVO> create(List<GroupEntity> list) {
		List<GroupVO> result = new ArrayList<GroupVO>();
		for (GroupEntity Group : list) {
			result.add(new GroupVO(Group));
		}
		return result;
	}

	public String getId() {
		return group.getId().toString();
	}

	public String getName() {
		return group.getName();
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

}

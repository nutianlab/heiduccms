

package com.heiduc.entity;

import static com.heiduc.utils.EntityUtil.*;

import org.heiduc.api.datastore.Entity;


/**
 * @author Alexander Oleynik
 */
public class UserGroupEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 3L;

	private Long groupId;
	private Long userId;

	public UserGroupEntity() {
	}
	
	public UserGroupEntity(Long aGroupId, Long aUserId) {
		this();
		groupId = aGroupId;
		userId = aUserId;
	}

	@Override
	public void load(Entity entity) {
		super.load(entity);
		groupId = getLongProperty(entity, "groupId");
		userId = getLongProperty(entity, "userId");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "groupId", groupId, true);
		setProperty(entity, "userId", userId, true);
	}

	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}

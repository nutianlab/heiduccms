

package com.heiduc.entity;

import org.heiduc.api.datastore.Entity;

import static com.heiduc.utils.EntityUtil.*;

/**
 * @author Alexander Oleynik
 */
public class GroupEntity extends BaseEntityImpl {

	private static final long serialVersionUID = 3L;

	private String name;
	
	public GroupEntity() {
	}
	
	public GroupEntity(String aName) {
		this();
		name = aName;
	}
	
	@Override
	public void load(Entity entity) {
		super.load(entity);
		name = getStringProperty(entity, "name");
	}
	
	@Override
	public void save(Entity entity) {
		super.save(entity);
		setProperty(entity, "name", name, true);
	}
	
	/*static GroupEntity getByKey(long id){
		    return getByKey(GroupEntity.class, id);
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

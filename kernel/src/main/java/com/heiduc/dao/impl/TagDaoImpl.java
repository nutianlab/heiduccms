

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.List;

import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.TagDao;
import com.heiduc.entity.TagEntity;

public class TagDaoImpl extends BaseDaoImpl<TagEntity> 
		implements TagDao {

	public TagDaoImpl() {
		super(TagEntity.class);
	}

	@Override
	public TagEntity getByName(final Long parent, final String name) {
		Query q = newQuery();
		q.addFilter("parent", EQUAL, parent);
		q.addFilter("name", EQUAL, name);
		return selectOne(q, "getByName", params(parent, name));
	}

	@Override
	public List<TagEntity> selectByParent(final Long parent) {
		Query q = newQuery();
		q.addFilter("parent", EQUAL, parent);
		return select(q, "selectByParent", params(parent));
	}

}

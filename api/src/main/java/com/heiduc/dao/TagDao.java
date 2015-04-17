

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.TagEntity;

/**
 * @author Alexander Oleynik
 */
public interface TagDao extends BaseDao<TagEntity> {

	TagEntity getByName(final Long parent, final String name);

	List<TagEntity> selectByParent(final Long parent);

}

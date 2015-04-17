

package com.heiduc.dao;

import com.heiduc.entity.SeoUrlEntity;

/**
 * @author Alexander Oleynik
 */
public interface SeoUrlDao extends BaseDao<SeoUrlEntity> {

	SeoUrlEntity getByFrom(final String from);

}

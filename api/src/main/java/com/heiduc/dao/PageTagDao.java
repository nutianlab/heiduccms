

package com.heiduc.dao;

import com.heiduc.entity.PageTagEntity;

/**
 * @author Alexander Oleynik
 */
public interface PageTagDao extends BaseDao<PageTagEntity> {

	PageTagEntity getByURL(final String url);

}

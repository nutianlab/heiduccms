

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.PageAttributeEntity;

/**
 * @author Alexander Oleynik
 */
public interface PageAttributeDao extends BaseDao<PageAttributeEntity> {

	/**
	 * Get PageAttributes by page's friendlyURL.
	 * @param pageUrl - page friendlyURL.
	 * @return found PageAttributes.
	 */
	List<PageAttributeEntity> getByPage(final String pageUrl);

	List<PageAttributeEntity> getByPageInherited(final String pageUrl);

	void removeByPage(final String url);
	
	PageAttributeEntity getByPageName(final String pageUrl, final String name);

}

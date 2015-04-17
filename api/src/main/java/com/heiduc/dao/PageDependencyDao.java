

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.PageDependencyEntity;

/**
 * @author Alexander Oleynik
 */
public interface PageDependencyDao extends BaseDao<PageDependencyEntity> {

	/**
	 * Get dependencies by page URL.
	 * @param pageUrl - page friendlyURL.
	 * @return found PageDependencys.
	 */
	List<PageDependencyEntity> selectByPage(final String pageUrl);
	
	/**
	 * Get dependencies by dependency URL.
	 * @param pageUrl - dependency page friendlyURL.
	 * @return found PageDependencys.
	 */
	List<PageDependencyEntity> selectByDependency(final String pageUrl);

	PageDependencyEntity getByPageAndDependency(String pageUrl, 
			String dependency);
	
}

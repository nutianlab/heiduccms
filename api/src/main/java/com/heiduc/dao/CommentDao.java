

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.CommentEntity;

/**
 * @author Alexander Oleynik
 */
public interface CommentDao extends BaseDao<CommentEntity> {

	/**
	 * Get comments by page's friendlyURL.
	 * @param pageUrl - page friendlyURL.
	 * @return found comments ordered by publishDate desc.
	 */
	List<CommentEntity> getByPage(final String pageUrl);

	/**
	 * Get comments by page's friendlyURL and disabled flag value.
	 * @param pageUrl - page friendlyURL.
	 * @param disabled - disabled flag.
	 * @return found comments ordered by publishDate desc.
	 */
	List<CommentEntity> getByPage(final String pageUrl, boolean disabled);
	
	/**
	 * Get comments by page's friendlyURL and ordering by publishDate asc or desc.
	 * @param pageUrl - page friendlyURL.
	 * @param disabled - disabled flag.
	 * @param ascdesc - ordering
	 * @return found comments ordered by publishDate asc or desc.
	 */
	List<CommentEntity> getByPage(final String pageUrl, boolean disabled, String ascdesc);

	void enable(final List<Long> ids);
	
	void disable(final List<Long> ids);
	
	void removeByPage(final String url);
	
	List<CommentEntity> getRecent(int limit);
}

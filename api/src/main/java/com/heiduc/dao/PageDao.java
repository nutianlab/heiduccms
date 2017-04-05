

package com.heiduc.dao;

import java.util.Date;
import java.util.List;

import com.heiduc.entity.ContentEntity;
import com.heiduc.entity.PageEntity;

public interface PageDao extends BaseDao<PageEntity> {

	/**
	 * Select all children versions for parent page.
	 * @param parentUrl - parent page url.
	 * @return pages list.
	 */
	List<PageEntity> selectAllChildren(final String parentUrl);
	
	/**
	 * Select all children versions for parent page.
	 * @param parentUrl - parent page url.
	 * @param deps - include sub.
	 * @return pages list.
	 */
	List<PageEntity> selectAllChildren(final String parentUrl,final boolean deps);
	
	/**
	 * Select all children versions for parent page with publishDate in period
	 * between startDate and endDate.
	 * @param parentUrl - parent page url.
	 * @param startDate - period start date (inclusive).
	 * @param endDate - period end date (exclusive).
	 * @return pages list.
	 */
	List<PageEntity> selectAllChildren(final String parentUrl,
			final Date startDate, final Date endDate);

	/**
	 * Select pages latest versions by parent page.
	 * @param url
	 * @return
	 */
	List<PageEntity> getByParent(final String url);
	
	List<PageEntity> getByParent(final String url,boolean deps);

	/**
	 * Select pages approved latest versions by parent page.
	 * @param url
	 * @return
	 */
	List<PageEntity> getByParentApproved(final String url);

	/**
	 * Select pages approved latest versions by parent page.
	 * @param url
	 * @param startDate - period start date (inclusive).
	 * @param endDate - period end date (exclusive).
	 * @return
	 */
	List<PageEntity> getByParentApproved(final String url, Date startDate,
			Date endDate);

	/**
	 * Get latest version approved page by url.
	 * @param url
	 * @return page
	 */
	PageEntity getByUrl(final String url);

	PageEntity getByUrlVersion(final String url, final Integer version);

	String getContent(final Long pageId, final String languageCode);

	ContentEntity setContent(final Long pageId, final String languageCode, 
			final String content);
	
	List<ContentEntity> getContents(final Long pageId);
	
	/**
	 * Selects all page's versions ordered by version.
	 * @param url - friendly url.
	 * @return - list of pages.
	 */
	List<PageEntity> selectByUrl(final String url);
	
	List<PageEntity> selectByTemplate(Long templateId);
	
	List<PageEntity> selectByStructure(Long structureId);

	List<PageEntity> selectByStructureTemplate(Long structureTemplateId);
	
	void removeVersion(Long id);
	
	List<PageEntity> getCurrentHourPublishedPages();

	List<PageEntity> getCurrentHourUnpublishedPages();
	
	List<PageEntity> selectChildren(final String parentUrl,Date publishDate, int limit);
	
}

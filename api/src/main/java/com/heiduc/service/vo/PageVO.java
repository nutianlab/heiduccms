

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.heiduc.entity.PageEntity;
import com.heiduc.utils.DateUtil;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class PageVO {

    private PageEntity page;
    private boolean hasPublishedVersion;

	public PageVO(final PageEntity entity) {
		page = entity;
	}

	public static List<PageVO> create(Collection<PageEntity> pages) {
		List<PageVO> result = new ArrayList<PageVO>();
		for (PageEntity entity : pages) {
			result.add(new PageVO(entity));
		}
		return result;
	}

	public Long getId() {
		return page.getId();
	}

	public String getTitle() {
		return page.getTitle();
	}

	public Map<String, String> getTitles() {
		return page.getTitles();
	}

	public String getFriendlyURL() {
		return page.getFriendlyURL();
	}

	public String getPageFriendlyURL() {
		return page.getPageFriendlyURL();
	}

	public String getParentFriendlyURL() {
		return page.getParentFriendlyURL();
	}

	public String getParentUrl() {
		return page.getParentUrl();
	}

	public Long getTemplate() {
		return page.getTemplate();
	}

	public String getPublishDate() {
		return DateUtil.toString(page.getPublishDate());
	}
	
	public String getPublishDateString() {
		return page.getPublishDateString();
	}

	public String getPublishTimeString() {
		return page.getPublishTimeString();
	}

	public boolean isCommentEnabled() {
		return page.isCommentsEnabled();
	}
	
	public Integer getVersion() {
		return page.getVersion();
	}

	public String getVersionTitle() {
		return page.getVersionTitle();
	}

	public String getState() {
		return page.getState().name();
	}
	
	public String getCreateUserEmail() {
		return page.getCreateUserEmail();
	}
	
	public String getCreateDate() {
		return DateUtil.dateTimeToString(page.getCreateDate());
	}

	public String getModUserEmail() {
		return page.getModUserEmail();
	}
	
	public String getModDate() {
		return DateUtil.dateTimeToString(page.getModDate());
	}

	public String getKeywords() {
		return page.getKeywords();
	}

	public String getDescription() {
		return page.getDescription();
	}

	public boolean isHasPublishedVersion() {
		return hasPublishedVersion;
	}

	public void setHasPublishedVersion(boolean value) {
		this.hasPublishedVersion = value;
	}
	
	public boolean isSearchable() {
		return page.isSearchable();
	}
	
	public Integer getSortIndex() {
		return page.getSortIndex();
	}
	
	public boolean isVelocityProcessing() {
		return page.isVelocityProcessing();
	}

	public String getHeadHtml() {
		return page.getHeadHtml();
	}

	public boolean isSkipPostProcessing() {
		return page.isSkipPostProcessing();
	}
	
	public List<String> getAncestorsURL() {
		return page.getAncestorsURL();
	}
	
	public String getContentType() {
		return page.getContentType();
	}
	
	public boolean isWikiProcessing() {
		return page.isWikiProcessing();
	}

}

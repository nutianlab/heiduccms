

package com.heiduc.business.page;

import java.util.Date;
import java.util.List;

import com.heiduc.entity.PageEntity;
import com.heiduc.entity.field.PageAttributesField;

public interface PageRenderDecorator {

	PageEntity getPage();

	void setPage(PageEntity page);

	/**
	 * Get page id.
	 * @return page id.
	 */
	Long getId();

	/**
	 * Get page content processed by velocity. 
	 * @return page content.
	 */
	String getContent();

	/**
	 * Get comments block for page. 
	 * @return comments block when comments are enabled for page.
	 */
	String getComments();

	/**
	 * Get page title.
	 * @return page title.
	 */
	String getTitle();

	String getLocalTitle();

	String getLocalTitle(String language);
	
	/**
	 * Get page friendlyURL.
	 * @return page friendlyURL.
	 */
	String getFriendlyURL();

	/**
	 * Get parent friendlyURL.
	 * @return parent friendlyURL. For root page returns empty page.
	 */
	String getParentUrl();

	/**
	 * Get template id for page.
	 * @return template id.
	 */
	Long getTemplate();

	/**
	 * Get page publish date.
	 * @return publish date.
	 */
	Date getPublishDate();

	/**
	 * Comments enabled for page.
	 * @return comments enabled or page flag.
	 */
	boolean isCommentsEnabled();

	/**
	 * Get page version.
	 * @return page version.
	 */
	Integer getVersion();

	/**
	 * Get version title.
	 * @return version title.
	 */
	String getVersionTitle();

    /**
     * Get page state.
	 * @return page state.
     */
	String getState();

	/**
	 * Get page created user email.
	 * @return page created user email.
	 */
	String getCreateUserEmail();

	/**
	 * Get page created date.
	 * @return page created date.
	 */
	Date getCreateDate();

	/**
	 * Get modify user email.
	 * @return modify uer email.
	 */
	String getModUserEmail();

	/**
	 * Get page modification date.
	 * @return page modification date.
	 */
	Date getModDate();

	String getKeywords();
	
	String getDescription();
	
	boolean isVelocityProcessing();

	String getHeadHtml();

	boolean isSkipPostProcessing();
	
	List<String> getAncestorsURL();

	boolean isWikiProcessing();
	
	boolean isPHPProcessing();
	
	boolean isForInternalUse();
	
	Date getEndPublishDate();
	
	boolean isEnableCkeditor();
	
	PageAttributesField getAttribute();
	
	boolean isRestful();

	boolean isPublished();
	
	boolean isPublished(Date date);

}
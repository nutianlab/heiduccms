

package com.heiduc.business.page.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.heiduc.business.PageBusiness;
import com.heiduc.business.page.PageRenderDecorator;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.field.PageAttributesField;
import com.heiduc.global.SystemService;
import com.heiduc.i18n.Messages;

public abstract class AbstractPageRenderDecorator implements PageRenderDecorator {

	protected static final Log logger = LogFactory.getLog(
			AbstractPageRenderDecorator.class);

	private PageEntity page;
	private String languageCode;
	private String content;
	private Dao dao;
	private PageBusiness pageBusiness;
	private SystemService systemService;
	
	public PageEntity getPage() {
		return page;
	}
	
	public void setPage(PageEntity page) {
		this.page = page;
	}
	
	public Long getId() {
		return page.getId();
	}
	
	public String getLanguageCode() {
		return languageCode;
	}
	
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Dao getDao() {
		return dao;
	}
	
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	public PageBusiness getPageBusiness() {
		return pageBusiness;
	}
	
	public void setPageBusiness(PageBusiness pageBusiness) {
		this.pageBusiness = pageBusiness;
	}
	
	public SystemService getSystemService() {
		return systemService;
	}
	
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public String getTitle() {
		return page.getTitle();
	}

	public String getFriendlyURL() {
		return page.getFriendlyURL();
	}

	public String getParentUrl() {
		return page.getParentUrl();
	}
	
	public Long getTemplate() {
		return page.getTemplate();
	}
	
	public Date getPublishDate() {
		return page.getPublishDate();
	}
	
	public boolean isCommentsEnabled() {
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
	
	public Date getCreateDate() {
		return page.getCreateDate();
	}

	public String getModUserEmail() {
		return page.getModUserEmail();
	}
	
	public Date getModDate() {
		return page.getModDate();
	}

	public String getDescription() {
		return page.getDescription();
	}

	public String getKeywords() {
		return page.getKeywords();
	}

	public String getLocalTitle() {
		return page.getLocalTitle(languageCode);
	}

	public String getLocalTitle(String language) {
		return page.getLocalTitle(language);
	}
	
	public String getComments() {
		if (isCommentsEnabled()) {
			String commentsTemplate = HeiducContext.getInstance().getConfig()
				.getCommentsTemplate();
			if (StringUtils.isEmpty(commentsTemplate)) {
				logger.error(Messages.get("config.comments_template_is_empty"));
				return Messages.get("config.comments_template_is_empty");
			}
			VelocityContext context = getPageBusiness().createContext(
					getLanguageCode(), getPage());
			context.put("page", getPage());
			return getSystemService().render(commentsTemplate, context);
		}
		return "";
	}

	@Override
	public boolean isVelocityProcessing() {
		return page.isVelocityProcessing();
	}

	@Override
	public String getHeadHtml() {
		return page.getHeadHtml();
	}
	
	@Override
	public boolean isSkipPostProcessing() {
		return page.isSkipPostProcessing();
	}
	
	@Override
	public List<String> getAncestorsURL() {
		return page.getAncestorsURL();
	}
	
	@Override
	public boolean isWikiProcessing() {
		return page.isWikiProcessing();
	}
	
	@Override
	public boolean isForInternalUse() {
		return page.isForInternalUse();
	}
	
	@Override
	public boolean isRestful() {
		return page.isRestful();
	}

	@Override
	public Date getEndPublishDate() {
		return page.getEndPublishDate();
	}

	@Override
	public boolean isEnableCkeditor() {
		return page.isEnableCkeditor();
	}

	@Override
	public PageAttributesField getAttribute() {
		return page.getAttribute();
	}
	
	@Override
	public boolean isPublished() {
		return page.isPublished();
	}

	@Override
	public boolean isPublished(Date date) {
		return page.isPublished(date);
	}	
	
}

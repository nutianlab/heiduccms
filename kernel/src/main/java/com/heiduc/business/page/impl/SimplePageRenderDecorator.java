package com.heiduc.business.page.impl;

import org.apache.velocity.VelocityContext;

import com.heiduc.business.PageBusiness;
import com.heiduc.dao.Dao;
import com.heiduc.entity.ContentEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.global.SystemService;
import com.heiduc.i18n.Messages;

/**
 * 
 * @author Alexander Oleynik
 * 
 */
public class SimplePageRenderDecorator extends AbstractPageRenderDecorator {

	public SimplePageRenderDecorator(PageEntity page, String languageCode, Dao dao, PageBusiness pageBusiness, SystemService systemService) {
		super();
		setPage(page);
		setLanguageCode(languageCode);
		setDao(dao);
		setPageBusiness(pageBusiness);
		setSystemService(systemService);
		prepareContent();
	}

	private void prepareContent() {
		ContentEntity contentEntity = getPageBusiness().getPageContent(getPage(), getLanguageCode());
		if (contentEntity == null) {
			String msg = Messages.get("content_not_found") + " " + getFriendlyURL() + " " + getLanguageCode();
			logger.error(msg);
			setContent(msg);
			return;
		}
		String resultContent = contentEntity.getContent();
		if (isVelocityProcessing()) {
			VelocityContext context = getPageBusiness().createContext(getLanguageCode(), getPage());
			context.put("page", getPage());
			resultContent = getSystemService().render(resultContent, context);
		}
		if (isWikiProcessing()) {
			resultContent = getSystemService().renderWiki(resultContent, getPage());
		}

		if (isPHPProcessing()) {
			resultContent = getSystemService().renderPHP(resultContent, getPage());
		}

		setContent(resultContent);
	}

}

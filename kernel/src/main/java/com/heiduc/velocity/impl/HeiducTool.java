

package com.heiduc.velocity.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.SetupBean;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.utils.FolderUtil;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class HeiducTool {

	private static final Log logger = LogFactory.getLog(HeiducTool.class);
	
	private PageEntity page;
	
	public HeiducTool() {
	}
	
	public String getTextContent(String content, int start, int size) {
		String text = StrUtil.extractTextFromHTML(content);
		if (text.length() <= start) {
			return "";
		}
		int end = start + size;
		if (end > text.length()) {
			end = text.length();
		}
		return text.substring(start, end);
	}

	public List reverse(List list) {
		Collections.reverse(list);
		return list;
	}
	
	public boolean isLoggedIn() {
		return HeiducContext.getInstance().getUser() != null;
	}
	
	public UserEntity getUser() {
		return HeiducContext.getInstance().getUser();
	}
	
	public String getFullVersion() {
		return SetupBean.FULLVERSION;
	}
	
	public void setSessionAttribute(String name, Object value) {
		HeiducContext.getInstance().getRequest().getSession().setAttribute(
				name, value);
	}
	
	public String getRestParam(int index) {
		if (index < 1) {
			throw new IllegalArgumentException();
		}
		if (page != null) {
			String url = HeiducContext.getInstance().getRequest().getServletPath();
			String paramsUrl = url.substring(page.getFriendlyURL().length(), 
					url.length());
			String[] params = FolderUtil.getPathChain(paramsUrl);
			if (index <= params.length) {
				return params[index - 1];
			}
		}
		else {
			logger.info("Page is null");
		}
		return null;
	}

	public void setPage(PageEntity page) {
		this.page = page;
	}
}

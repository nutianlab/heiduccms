

package com.heiduc.business.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import com.heiduc.business.CommentBusiness;
import com.heiduc.business.ContentPermissionBusiness;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.CommentEntity;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.utils.EmailUtil;
import com.heiduc.utils.StrUtil;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class CommentBusinessImpl extends AbstractBusinessImpl 
	implements CommentBusiness {

	private static final String COMMENT_LETTER_SUBJECT = "New comment";
	
	@Override
	public CommentEntity addComment(String name, String content, 
			PageEntity page) {

		ConfigEntity config = HeiducContext.getInstance().getConfig();
		CommentEntity comment = new CommentEntity(name, content, 
				new Date(), page.getFriendlyURL());
		getDao().getCommentDao().save(comment);
		getBusiness().getSystemService().getPageCache().remove(
				page.getFriendlyURL());
		List<String> toAddresses = StrUtil.fromCSV(config.getCommentsEmail());
		if (toAddresses.size() == 0) {
			toAddresses.add(config.getSiteEmail());
		}
		for (String email : toAddresses) {
			EmailUtil.sendEmail(createCommentLetter(comment, page), 
				COMMENT_LETTER_SUBJECT, 
				config.getSiteEmail(), 
				config.getSiteDomain() + " admin", 
				StringUtils.strip(email));
			logger.debug("New comment letter was sent to " + email);
		}
		return comment;
	}
	
	private String createCommentLetter(CommentEntity comment, PageEntity page) {
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		StringBuffer b = new StringBuffer();
		b.append("<p>New comment was added to page ")
		    .append(config.getSiteDomain()).append(page.getFriendlyURL())
		    .append(" by ").append(comment.getName()).append("</p>")
		    .append(comment.getContent());
		return b.toString();
	}

	private boolean isChangeGranted(List<Long> ids) {
		if (ids.size() > 0) {
			CommentEntity comment = getDao().getCommentDao().getById(ids.get(0));
			ContentPermissionEntity permission = getContentPermissionBusiness()
					.getPermission(	comment.getPageUrl(), 
							HeiducContext.getInstance().getUser());
			if (permission != null) {
				return permission.isChangeGranted();
			}
		}		
		return false;
	}
	
	@Override
	public void disable(List<Long> ids) {
		clearPageCache(ids);
		if (isChangeGranted(ids)) {
			getDao().getCommentDao().disable(ids);
		}		
	}

	@Override
	public void enable(List<Long> ids) {
		clearPageCache(ids);
		if (isChangeGranted(ids)) {
			getDao().getCommentDao().enable(ids);
		}		
	}

	@Override
	public void remove(List<Long> ids) {
		clearPageCache(ids);
		if (isChangeGranted(ids)) {
			getDao().getCommentDao().remove(ids);
		}		
	}

	private void clearPageCache(List<Long> commentIds) {
		for(Long id : commentIds) {
			CommentEntity comment = getDao().getCommentDao().getById(id);
			if (comment != null) {
				getBusiness().getSystemService().getPageCache().remove(
						comment.getPageUrl());
			}
		}
	}

	private ContentPermissionBusiness getContentPermissionBusiness() {
		return getBusiness().getContentPermissionBusiness();
	}
	
}

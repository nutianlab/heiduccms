

package com.heiduc.dao.tool;

import java.util.Date;

import com.heiduc.dao.Dao;
import com.heiduc.entity.CommentEntity;
import com.heiduc.entity.PageEntity;

public class CommentTool {

	private Dao dao;
	
	public CommentTool(Dao aDao) {
		dao = aDao;
	}
	
	public CommentEntity addComment(final String name, final String content, 
			final PageEntity page) {
		CommentEntity comment = new CommentEntity(name, content, new Date(), 
				page.getFriendlyURL());
		dao.getCommentDao().save(comment);
		return comment;
	}
	
	public CommentEntity addComment(final String name, final String content, 
			final PageEntity page, final boolean disabled) {
		CommentEntity comment = new CommentEntity(name, content, new Date(), 
				page.getFriendlyURL());
		comment.setDisabled(disabled);
		dao.getCommentDao().save(comment);
		return comment;
	}
	
}

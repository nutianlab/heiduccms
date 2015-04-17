

package com.heiduc.business;

import java.util.List;

import com.heiduc.entity.CommentEntity;
import com.heiduc.entity.PageEntity;

public interface CommentBusiness {

	/**
	 * Security filtered dao version.
	 */
	void remove(List<Long> ids);
	
	/**
	 * Security filtered dao version.
	 */
	void enable(List<Long> ids);
	
	/**
	 * Security filtered dao version.
	 */
	void disable(List<Long> ids);

	CommentEntity addComment(final String name, final String content, 
			final PageEntity page);
	
}

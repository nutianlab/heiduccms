

package com.heiduc.service.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.heiduc.entity.CommentEntity;
import com.heiduc.utils.DateUtil;

/**
 * Value object to be returned from services.
 * @author Alexander Oleynik
 */
public class CommentVO {

    private CommentEntity comment;

	public CommentVO(final CommentEntity entity) {
		comment = entity;
	}

	public static List<CommentVO> create(List<CommentEntity> list) {
		List<CommentVO> result = new ArrayList<CommentVO>();
		for (CommentEntity comment : list) {
			result.add(new CommentVO(comment));
		}
		return result;
	}

	public Long getId() {
		return comment.getId();
	}

	public String getPageUrl() {
		return comment.getPageUrl();
	}

	public String getName() {
		return comment.getName();
	}

	public String getContent() {
		
		// keeping carriage return in HTML 
		return comment.getContent().replace("\n", "<br/>");
		
	}

	public String getPublishDate() {
		return DateUtil.toString(comment.getPublishDate());
	}

	public String getPublishDateTime() {
		return DateUtil.dateTimeToString(comment.getPublishDate());
	}

	public Date getDate() {
		return comment.getPublishDate();
	}

	public boolean isDisabled() {
		return comment.isDisabled();
	}
	
}

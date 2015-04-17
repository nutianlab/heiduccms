

package com.heiduc.service.back.impl;

import java.util.List;


import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.CommentService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.CommentVO;
import com.heiduc.utils.StrUtil;

/**
 * @author Alexander Oleynik
 */
public class CommentServiceImpl extends AbstractServiceImpl 
		implements CommentService {

	@Override
	public List<CommentVO> getByPage(String pageUrl) {
		return CommentVO.create(getDao().getCommentDao().getByPage(pageUrl));
	}

	@Override
	public ServiceResponse deleteComments(List<String> ids) {
		getBusiness().getCommentBusiness().remove(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("comments_success_delete"));
	}

	@Override
	public ServiceResponse disableComments(List<String> ids) {
		getBusiness().getCommentBusiness().disable(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("comments_success_disable"));
	}

	@Override
	public ServiceResponse enableComments(List<String> ids) {
		getBusiness().getCommentBusiness().enable(StrUtil.toLong(ids));
		return ServiceResponse.createSuccessResponse(
				Messages.get("comments_success_enable"));
	}

}

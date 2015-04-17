

package com.heiduc.service.front.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaResponse;


import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceException;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.front.CommentService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.CommentVO;
import com.heiduc.utils.ParamUtil;
import com.heiduc.utils.RecaptchaUtil;

/**
 * @author Alexander Oleynik
 */
public class CommentServiceImpl extends AbstractServiceImpl 
		implements CommentService {

	@Override
	public List<CommentVO> getByPage(String pageUrl) {
		return CommentVO.create(getDao().getCommentDao()
				.getByPage(pageUrl, false));
	}

	@Override
	public ServiceResponse addComment(String name, String comment,
			String pageUrl, String challenge, String response,
			HttpServletRequest request) {
		
		ConfigEntity config = getBusiness().getConfigBusiness().getConfig();
		boolean valid = true;
		ReCaptchaResponse recaptchaResponse = null;
		if (config.isEnableRecaptcha()) {
			recaptchaResponse = RecaptchaUtil.check(
					config.getRecaptchaPublicKey(), 
					config.getRecaptchaPrivateKey(), 
					challenge, response, request);
			valid = recaptchaResponse.isValid();
		}
		if (valid) {
        	try {
        		addComment(ParamUtil.filterXSS(name), 
        				ParamUtil.filterXSS(comment), pageUrl);
                return ServiceResponse.createSuccessResponse(
                		Messages.get("comment_success_create"));
        	}
        	catch (ServiceException e) {
                return ServiceResponse.createErrorResponse(e.getMessage());
        	}
        }
        else {
            return ServiceResponse.createErrorResponse(
            		recaptchaResponse.getErrorMessage());
        }
	}
	
	private void addComment(String name, String content, String pageUrl) 
			throws ServiceException {
		PageEntity page = getDao().getPageDao().getByUrl(pageUrl);
		if (page == null) {
			throw new ServiceException(Messages.get("page_not_found", pageUrl));
		}
		getBusiness().getCommentBusiness().addComment(name, content, page);
	}

}

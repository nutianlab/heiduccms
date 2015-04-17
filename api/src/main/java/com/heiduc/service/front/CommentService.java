

package com.heiduc.service.front;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.CommentVO;


public interface CommentService extends AbstractService {
	
	List<CommentVO> getByPage(final String pageUrl);

	/**
	 * Add comment to page. Protected by reCaptcha service.
	 * @param name - user name
	 * @param params - form parameters
	 * @param challenge - recaptcha challenge
	 * @param response - recaptcha response
	 * @return - service response.
	 */
	ServiceResponse addComment(final String name, 
			final String comment, 
			final String pageUrl,
			final String challenge, 
			final String response, 
			HttpServletRequest request);
	
}

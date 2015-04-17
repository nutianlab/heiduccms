

package com.heiduc.service.back;

import java.util.List;

import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.CommentVO;


public interface CommentService extends AbstractService {
	
	List<CommentVO> getByPage(final String pageUrl);

	ServiceResponse enableComments(final List<String> ids);

	ServiceResponse disableComments(final List<String> ids);
	
	ServiceResponse deleteComments(final List<String> ids);
	
}

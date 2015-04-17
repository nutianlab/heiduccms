

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.heiduc.entity.UserEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.CodeVO;
import com.heiduc.service.vo.UserVO;

/**
 * @author Alexander Oleynik
 */
public interface UserService extends AbstractService {
	
	List<UserVO> select();

	ServiceResponse remove(final List<String> ids);

	UserEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
	UserEntity getLoggedIn();
	
	List<UserVO> selectByGroup(final String groupId);
	
	ServiceResponse disable(final Long userId, boolean disable);
	
	List<String> getTimezones();
}

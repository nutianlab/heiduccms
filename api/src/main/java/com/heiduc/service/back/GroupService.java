

package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.GroupEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.GroupVO;

/**
 * @author Alexander Oleynik
 */
public interface GroupService extends AbstractService {
	
	List<GroupVO> select();

	ServiceResponse remove(final List<String> ids);

	GroupVO getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 

	ServiceResponse setGroupUsers(final String groupId, final List<String> ids);
	
}



package com.heiduc.service.back;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.MessageEntity;
import com.heiduc.service.AbstractService;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.vo.MessageVO;

/**
 * @author Alexander Oleynik
 */
public interface MessageService extends AbstractService {
	
	List<MessageEntity> selectByCode(final String code);

	List<MessageVO> select();

	ServiceResponse remove(final List<String> codes);

	MessageEntity getById(final Long id);
	
	ServiceResponse save(final Map<String, String> vo); 
	
}



package com.heiduc.service.back.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.MessageEntity;
import com.heiduc.i18n.Messages;
import com.heiduc.service.ServiceResponse;
import com.heiduc.service.back.MessageService;
import com.heiduc.service.impl.AbstractServiceImpl;
import com.heiduc.service.vo.MessageVO;

/**
 * @author Alexander Oleynik
 */
public class MessageServiceImpl extends AbstractServiceImpl 
		implements MessageService {

	@Override
	public List<MessageVO> select() {
		List<MessageVO> result = new ArrayList<MessageVO>();
		Map<String, Map<String, String>> messagesMap = 
				new HashMap<String, Map<String,String>>();
		List<MessageEntity> messages = getDao().getMessageDao().select();
		for (MessageEntity message : messages) {
			Map<String, String> langMap = messagesMap.get(message.getCode());
			if (langMap == null) {
				langMap = new HashMap<String, String>();
				messagesMap.put(message.getCode(), langMap);
			}
			langMap.put(message.getLanguageCode(), message.getValue());
		}
		List<String> keys = new ArrayList<String>();
		keys.addAll(messagesMap.keySet());
		Collections.sort(keys);
		for (String code : keys) {
			result.add(new MessageVO(code, messagesMap.get(code)));
		}
		return result;
	}

	@Override
	public ServiceResponse remove(List<String> codes) {
		List<Long> ids = new ArrayList<Long>();
		for (String code : codes) {
			List<MessageEntity> messages = getDao().getMessageDao()
					.selectByCode(code);
			for (MessageEntity message : messages) {
				ids.add(message.getId());
			}
		}
		getDao().getMessageDao().remove(ids);
		return ServiceResponse.createSuccessResponse(
				Messages.get("messages.success_deleted"));
	}

	@Override
	public MessageEntity getById(Long id) {
		return getDao().getMessageDao().getById(id);
	}

	@Override
	public ServiceResponse save(Map<String, String> vo) {
		List<String> errors = new ArrayList<String>();
		if (StringUtils.isEmpty(vo.get("code"))) {
			errors.add(Messages.get("code_is_empty"));
		}
		else {
			String code = StringUtils.strip(vo.get("code"));
			List<LanguageEntity> languages = getDao().getLanguageDao().select();
			for (LanguageEntity lang : languages) {
				if (!StringUtils.isEmpty(vo.get(lang.getCode()))) {
					MessageEntity message = getDao().getMessageDao().getByCode(
							code, lang.getCode());
					if (message == null) {
						message = new MessageEntity();
						message.setCode(code);
						message.setLanguageCode(lang.getCode());
					}
					message.setValue(vo.get(lang.getCode()));
					getDao().getMessageDao().save(message);
				}
			}
			return ServiceResponse.createSuccessResponse(
					Messages.get("message.success_save"));
		}
		return ServiceResponse.createErrorResponse(
				Messages.get("errors_occured"), errors);
	}

	@Override
	public List<MessageEntity> selectByCode(String code) {
		return getDao().getMessageDao().selectByCode(code);
	}

}

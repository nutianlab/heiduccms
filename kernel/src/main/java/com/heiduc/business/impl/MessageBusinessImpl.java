

package com.heiduc.business.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.MessageBusiness;
import com.heiduc.entity.MessageEntity;
import com.heiduc.i18n.Messages;

/**
 * @author Alexander Oleynik
 */
public class MessageBusinessImpl extends AbstractBusinessImpl 
	implements MessageBusiness {

	private static final String DEFAULT_BUNDLE_LANGUAGE = "en";
	
	@Override
	public Map<String, String> getBundle(String languageCode) {
		Map<String, String> result = new HashMap<String, String>();
		addMessages(result, DEFAULT_BUNDLE_LANGUAGE);
		addMessages(result, languageCode);
		return result;
	}

	private void addMessages(Map<String, String> bundle, String language) {
		List<MessageEntity> messages = getDao().getMessageDao().select(
				language);
		for (MessageEntity message : messages) {
			if (!StringUtils.isEmpty(message.getValue())) {
				bundle.put(message.getCode(), message.getValue());
			}
		}
	}
	
	@Override
	public List<String> validateBeforeUpdate(MessageEntity entity) {
		List<String> errors = new ArrayList<String>();
		if (StringUtils.isEmpty(entity.getCode())) {
			errors.add(Messages.get("code_is_empty"));
		}
		if (StringUtils.isEmpty(entity.getLanguageCode())) {
			errors.add(Messages.get("language_code_is_empty"));
		}
		return errors;
	}
	
}

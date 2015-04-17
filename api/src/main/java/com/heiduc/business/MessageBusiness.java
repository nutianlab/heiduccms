

package com.heiduc.business;

import java.util.List;
import java.util.Map;

import com.heiduc.entity.MessageEntity;

public interface MessageBusiness {
	
	Map<String, String> getBundle(final String languageCode);
	
	List<String> validateBeforeUpdate(final MessageEntity entity);
}



package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.MessageEntity;

/**
 * @author Alexander Oleynik
 */
public interface MessageDao extends BaseDao<MessageEntity> {

	List<MessageEntity> selectByCode(final String code);
	
	MessageEntity getByCode(final String code, final String languageCode);

	List<MessageEntity> select(final String languageCode);
	
}

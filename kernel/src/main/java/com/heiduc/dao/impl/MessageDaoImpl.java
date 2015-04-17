

package com.heiduc.dao.impl;

import java.util.List;

import org.heiduc.api.datastore.Query;
import org.heiduc.api.datastore.Query.FilterOperator;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.MessageDao;
import com.heiduc.entity.MessageEntity;

public class MessageDaoImpl extends BaseDaoImpl<MessageEntity> 
		implements MessageDao {

	public MessageDaoImpl() {
		super(MessageEntity.class);
	}

	@Override
	public List<MessageEntity> selectByCode(final String code) {
		Query q = newQuery();
		q.addFilter("code", FilterOperator.EQUAL, code);
		return select(q, "selectByCode", params(code));
	}
	
	@Override
	public MessageEntity getByCode(final String code, 
			final String languageCode) {
		Query q = newQuery();
		q.addFilter("code", FilterOperator.EQUAL, code);
		q.addFilter("languageCode", FilterOperator.EQUAL, languageCode);
		return selectOne(q, "getByCode", params(code, languageCode));
	}

	@Override
	public List<MessageEntity> select(final String languageCode) {
		Query q = newQuery();
		q.addFilter("languageCode", FilterOperator.EQUAL, languageCode);
		return select(q, "select", params(languageCode));
	}

}

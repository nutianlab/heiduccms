

package com.heiduc.dao.impl;

import static org.heiduc.api.datastore.Query.FilterOperator.EQUAL;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.heiduc.api.datastore.Query;


import com.heiduc.dao.BaseDaoImpl;
import com.heiduc.dao.CommentDao;
import com.heiduc.entity.CommentEntity;
import com.heiduc.entity.helper.CommentHelper;

/**
 * @author Alexander Oleynik
 */
public class CommentDaoImpl extends BaseDaoImpl<CommentEntity> 
		implements CommentDao {

	public CommentDaoImpl() {
		super(CommentEntity.class);
	}

	@Override
	public List<CommentEntity> getByPage(final String pageUrl) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, pageUrl);
		List<CommentEntity> result = select(q, "getByPage", params(pageUrl));
		//Collections.sort(result, new CommentHelper.PublishDateDesc());
		return result;
	}
	
	@Override
	public void disable(List<Long> ids) {
		getQueryCache().removeQueries(CommentEntity.class);
		for (Long id : ids) {
			CommentEntity comment = getById(id);
			if (comment != null) {
				comment.setDisabled(true);
				save(comment);
				getEntityCache().removeEntity(CommentEntity.class, id);
			}
		}
	}

	@Override
	public void enable(List<Long> ids) {
		getQueryCache().removeQueries(CommentEntity.class);
		for (Long id : ids) {
			CommentEntity comment = getById(id);
			if (comment != null) {
				comment.setDisabled(false);
				save(comment);
				getEntityCache().removeEntity(CommentEntity.class, id);
			}
		}
	}

	@Override
	public List<CommentEntity> getByPage(String pageUrl, boolean disabled) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, pageUrl);
		q.addFilter("disabled", EQUAL, disabled);
		List<CommentEntity> result = select(q, "getByPage", params(pageUrl, 
				disabled));
		logger.info("Sorting by publishDate in Asc mode");
		Collections.sort(result, new CommentHelper.PublishDateAsc());
		return result;
	}

	@Override
	public List<CommentEntity> getByPage(String pageUrl, boolean disabled, String ascdesc) {
		
		logger.info("into getBypage, ascdesc = " +  ascdesc);
		
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, pageUrl);
		q.addFilter("disabled", EQUAL, disabled);
		List<CommentEntity> result = select(q, "getByPage", params(pageUrl, 
				disabled));
		if (StringUtils.isNotEmpty(ascdesc) && ascdesc.equalsIgnoreCase("ASC")) {
			logger.info("ordering by publishing date " +  ascdesc);
			Collections.sort(result, new CommentHelper.PublishDateAsc());
		}
		else {
			logger.info("ordering by publishing date desc");
			Collections.sort(result, new CommentHelper.PublishDateDesc());
		}
		return result;
	}

	@Override
	public void removeByPage(String url) {
		Query q = newQuery();
		q.addFilter("pageUrl", EQUAL, url);
		removeSelected(q);
	}

	@Override
	public List<CommentEntity> getRecent(int limit) {
		Query q = newQuery();
		q.addFilter("disabled", EQUAL, false);
		List<CommentEntity> result = select(q, "getRecent", limit, params(false));
		Collections.sort(result, new CommentHelper.PublishDateDesc());
		return result;
	}
	
}

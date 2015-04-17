

package com.heiduc.dao;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.cache.EntityCache;
import com.heiduc.dao.cache.QueryCache;
import com.heiduc.global.SystemService;

public class AbstractDaoImpl implements AbstractDao, Serializable {

	protected static final Log logger = LogFactory.getLog(AbstractDaoImpl.class);

	public EntityCache getEntityCache() {
		return HeiducContext.getInstance().getBusiness().getDao().getEntityCache();
	}

	public QueryCache getQueryCache() {
		return HeiducContext.getInstance().getBusiness().getDao().getQueryCache();
	}

	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}

	protected Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}
	
}

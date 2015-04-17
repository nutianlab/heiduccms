

package com.heiduc.dao;

import com.heiduc.dao.cache.EntityCache;
import com.heiduc.dao.cache.QueryCache;
import com.heiduc.global.SystemService;

public interface AbstractDao {

	EntityCache getEntityCache();

	QueryCache getQueryCache(); 

	SystemService getSystemService();

}



package com.heiduc.common;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.global.SystemService;

public interface AbstractServiceBean {

	Business getBusiness();

	void setBusiness(Business bean);
	
	Dao getDao();
	
	SystemService getSystemService();
}

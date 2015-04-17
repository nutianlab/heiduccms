

package com.heiduc.service.plugin;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;
import com.heiduc.service.BackService;
import com.heiduc.service.FrontService;

public interface ServicePlugin {

	Dao getDao();
	
	Business getBusiness();
	void setBusiness(Business bean);

	BackService getBackService();
	void setBackService(BackService bean);
	
	FrontService getFrontService();
	void setFrontService(FrontService bean);
}

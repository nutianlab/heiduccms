

package com.heiduc.velocity.plugin;

import com.heiduc.business.Business;
import com.heiduc.dao.Dao;

public interface VelocityPlugin {

	Dao getDao();
	
	Business getBusiness();

	void setBusiness(Business bean);

}

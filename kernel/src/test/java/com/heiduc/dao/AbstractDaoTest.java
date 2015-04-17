

package com.heiduc.dao;

import com.heiduc.common.HeiducContext;
import com.heiduc.global.SystemService;
import com.heiduc.test.AbstractHeiducContextTest;

public abstract class AbstractDaoTest extends AbstractHeiducContextTest {
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
	}    
    
	public Dao getDao() {
		return HeiducContext.getInstance().getBusiness().getDao();
	}
	
	public SystemService getSystemService() {
		return HeiducContext.getInstance().getBusiness().getSystemService();
	}
}

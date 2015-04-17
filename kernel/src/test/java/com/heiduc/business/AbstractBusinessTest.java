

package com.heiduc.business;

import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.test.AbstractHeiducContextTest;

public abstract class AbstractBusinessTest extends AbstractHeiducContextTest {
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
	}    
    
	public Dao getDao() {
		return getBusiness().getDao();
	}

	public Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
}

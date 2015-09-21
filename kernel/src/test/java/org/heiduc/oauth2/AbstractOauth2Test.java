

package org.heiduc.oauth2;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.dao.Dao;
import com.heiduc.test.AbstractHeiducContextTest;

public abstract class AbstractOauth2Test extends AbstractHeiducContextTest {
	
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

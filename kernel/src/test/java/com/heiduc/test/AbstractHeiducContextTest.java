

package com.heiduc.test;

import com.heiduc.business.impl.BusinessImpl;
import com.heiduc.business.impl.mq.MessageQueueImpl;
import com.heiduc.common.HeiducContext;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public abstract class AbstractHeiducContextTest extends LocalDatastoreTestCase {
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
        initContext();
	}    
    
    private void initContext() {
        HeiducContext.getInstance().setMessageQueue(new MessageQueueImpl());
        HeiducContext.getInstance().setBusiness(new BusinessImpl());
    }
	
}

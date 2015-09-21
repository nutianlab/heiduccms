

package com.heiduc.test;

/*import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
*/
public abstract class LocalDatastoreTestCase extends LocalServiceTestCase {

   /* private final LocalServiceTestHelper helper =
        new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
	
    @Override
    public void setUp() throws Exception {
        super.setUp();
        helper.setUp();
    }

    @Override
    public void tearDown() throws Exception {
    	//helper.tearDown();
    	super.tearDown();
    }*/
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
    }

}


package com.heiduc.dao;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.dao.tool.ContentPermissionTool;
import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.enums.ContentPermissionType;

public class ContentPermissionDaoTest extends AbstractDaoTest {

	private ContentPermissionTool contentPermissionTool;
	
	@Override
    public void setUp() throws Exception {
        super.setUp();
        contentPermissionTool = new ContentPermissionTool(getDao());
	}    

	private void createTestCase() {
		contentPermissionTool.addPermission("/", ContentPermissionType.ADMIN, 
				1L);
		contentPermissionTool.addPermission("/", ContentPermissionType.WRITE, 
				2L);
		contentPermissionTool.addPermission("/", ContentPermissionType.READ, 
				3L);
		contentPermissionTool.addPermission("/man", ContentPermissionType.READ, 
				3L);
		contentPermissionTool.addPermission("/do/man", ContentPermissionType.READ, 
				3L);
		contentPermissionTool.addPermission("/test", ContentPermissionType.READ, 
				3L);
	}	

	public void testSelectByUrl() {
		createTestCase();
		List<ContentPermissionEntity> list = getDao().getContentPermissionDao()
				.selectByUrl("/");
		assertEquals(3, list.size());
	}	

	public void testRemoveByGroup() {
		createTestCase();
		List<Long> groupIds = new ArrayList<Long>();
		groupIds.add(3L);
		groupIds.add(2L);
		getDao().getContentPermissionDao().removeByGroup(groupIds);
		List<ContentPermissionEntity> list = getDao().getContentPermissionDao()
				.selectByUrl("/");
		assertEquals(1, list.size());
		list = getDao().getContentPermissionDao().select();
		assertEquals(1, list.size());
	}	
	
}

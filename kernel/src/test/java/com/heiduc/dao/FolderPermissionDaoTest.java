

package com.heiduc.dao;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.dao.tool.FolderPermissionTool;
import com.heiduc.dao.tool.FolderTool;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.enums.FolderPermissionType;

public class FolderPermissionDaoTest extends AbstractDaoTest {

	private FolderPermissionTool folderPermissionTool;
	private FolderTool folderTool;

	@Override
    public void setUp() throws Exception {
        super.setUp();
        folderTool = new FolderTool(getDao());
        folderPermissionTool = new FolderPermissionTool(getDao());
	}    
	
	public void testSelectByFolder() {
		FolderEntity folder = folderTool.addFolder("test", null);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.READ, 1L);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.WRITE, 2L);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.ADMIN, 3L);
		List<FolderPermissionEntity> list = getDao().getFolderPermissionDao()
				.selectByFolder(folder.getId());
		assertEquals(3, list.size());
	}	
	
	public void testSelectByGroup() {
		FolderEntity folder = folderTool.addFolder("test", null);
		FolderEntity folder2 = folderTool.addFolder("test2", null);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.READ, 1L);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.WRITE, 2L);
		folderPermissionTool.addPermission(folder.getId(), 
				FolderPermissionType.ADMIN, 3L);
		folderPermissionTool.addPermission(folder2.getId(), 
				FolderPermissionType.READ, 1L);
		folderPermissionTool.addPermission(folder2.getId(), 
				FolderPermissionType.WRITE, 2L);
		folderPermissionTool.addPermission(folder2.getId(), 
				FolderPermissionType.ADMIN, 3L);
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		getDao().getFolderPermissionDao().removeByGroup(ids);
		List<FolderPermissionEntity> list = getDao().getFolderPermissionDao()
				.selectByFolder(folder.getId());
		assertEquals(2, list.size());
		list = getDao().getFolderPermissionDao().selectByFolder(folder2.getId());
		assertEquals(2, list.size());
	}	
	
}

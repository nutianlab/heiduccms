

package com.heiduc.business;

import java.io.UnsupportedEncodingException;

import com.heiduc.business.decorators.TreeItemDecorator;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.enums.FolderPermissionType;
import com.heiduc.enums.UserRole;

public class FolderBusinessTest extends AbstractBusinessTest {

	private FolderEntity addFolder(final String name, final Long parent) {
		FolderEntity Folder = new FolderEntity(name, parent);
		getDao().getFolderDao().save(Folder);
		return Folder;
	}
	
	private UserEntity addUser(String name, String email, UserRole role) {
		UserEntity user = new UserEntity(name, name, email, role);
		getDao().getUserDao().save(user);
		return user;
	}
	
	private GroupEntity addGroup(String name) {
		GroupEntity group = new GroupEntity(name);
		getDao().getGroupDao().save(group);
		return group;
	}
	
	private void addUserGroup(UserEntity user, GroupEntity group) {
		UserGroupEntity userGroup = new UserGroupEntity(group.getId(), 
				user.getId());
		getDao().getUserGroupDao().save(userGroup);
	}
	
	private void addPermission(FolderEntity folder, GroupEntity group, 
			FolderPermissionType perm) {
		FolderPermissionEntity p = new FolderPermissionEntity(folder.getId(),
				perm, group.getId());
		getDao().getFolderPermissionDao().save(p);
	}

	private void addGuestPerission(FolderEntity root) {
		UserEntity tester = addUser("tester","kinyelo@gmail.com", UserRole.ADMIN);
		HeiducContext.getInstance().setUser(tester);
		GroupEntity guests = addGroup("guests");
		GroupEntity developers = addGroup("developers");
		addUserGroup(tester, developers);
		addPermission(root, guests, FolderPermissionType.READ);
	}
	
	/**
	 * /images/
	 *        /logos/
	 *              /heiduc 
	 *        
	 *        /photos
	 *        /test
	 */
	public void testFindFolderByPath() {
		FolderEntity root = addFolder("/", null);
		addGuestPerission(root);
		FolderEntity images = addFolder("images", root.getId());
		FolderEntity logos = addFolder("logos", images.getId());
		FolderEntity photos = addFolder("photos", images.getId());
		FolderEntity test = addFolder("test", images.getId());
		FolderEntity heiduc = addFolder("heiduc", logos.getId());
		TreeItemDecorator<FolderEntity> treeRoot = getBusiness().getFolderBusiness()
				.getTree();
		
		TreeItemDecorator<FolderEntity> result = getBusiness().getFolderBusiness()
				.findFolderByPath(treeRoot, "/images");
		assertNotNull(result);
		assertEquals(images.getId(), result.getEntity().getId());
		
		result = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
				"/images/logos");
		assertNotNull(result);
		assertEquals(logos.getId(), result.getEntity().getId());
		
		result = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
				"/images/photos");
		assertNotNull(result);
		assertEquals(photos.getId(), result.getEntity().getId());

		result = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
				"/images/test");
		assertNotNull(result);
		assertEquals(test.getId(), result.getEntity().getId());

		result = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
			"/images/logos/heiduc");
		assertNotNull(result);
		assertEquals(heiduc.getId(), result.getEntity().getId());

		result = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
			"/images/logos/heiduc1");
		assertNull(result);
	}	

	public void testCreateFolder() throws UnsupportedEncodingException {
		FolderEntity root = addFolder("/", null);
		addGuestPerission(root);
		getBusiness().getFolderBusiness().createFolder("/one/two/free/four");
		TreeItemDecorator<FolderEntity> treeRoot = getBusiness()
			.getFolderBusiness().getTree();
		TreeItemDecorator<FolderEntity> folder = getBusiness()
			.getFolderBusiness().findFolderByPath(treeRoot,	"/one");
		assertNotNull(folder);
		assertEquals("one", folder.getEntity().getName());

		folder = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
				"/one/two");
		assertNotNull(folder);
		assertEquals("two", folder.getEntity().getName());

		folder = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
			"/one/two/free");
		assertNotNull(folder);
		assertEquals("free", folder.getEntity().getName());

		folder = getBusiness().getFolderBusiness().findFolderByPath(treeRoot, 
			"/one/two/free/four");
		assertNotNull(folder);
		assertEquals("four", folder.getEntity().getName());
	}
	
	/**
	 * /images/
	 *        /logos/
	 *              /heiduc 
	 *        
	 *        /photos
	 *        /test
	 */
	public void testGetFolderPath() {
		FolderEntity root = addFolder("/", null);
		addGuestPerission(root);
		FolderEntity images = addFolder("images", root.getId());
		FolderEntity logos = addFolder("logos", images.getId());
		FolderEntity photos = addFolder("photos", images.getId());
		FolderEntity test = addFolder("test", images.getId());
		FolderEntity heiduc = addFolder("heiduc", logos.getId());
		assertEquals("/images", getBusiness().getFolderBusiness()
				.getFolderPath(images));
		assertEquals("/images/logos", getBusiness().getFolderBusiness()
				.getFolderPath(logos));
		assertEquals("/images/logos/heiduc", getBusiness().getFolderBusiness()
				.getFolderPath(heiduc));
		assertEquals("/images/photos", getBusiness().getFolderBusiness()
				.getFolderPath(photos));
	}
}

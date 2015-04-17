

package com.heiduc.business;

import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.FolderEntity;
import com.heiduc.entity.FolderPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.enums.ContentPermissionType;
import com.heiduc.enums.FolderPermissionType;
import com.heiduc.enums.UserRole;

/**
 * @author Alexander Oleynik
 */
public class FolderPermissionBusinessTest extends AbstractBusinessTest {

	private FolderEntity addFolder(String title, FolderEntity parent) {
		FolderEntity folder = new FolderEntity(title, 
				parent != null ? parent.getId() : null);
		getDao().getFolderDao().save(folder);
		return folder;
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
	
	public void testGetPermission() {
		UserEntity developer = addUser("alex","kinyelo@gmail.com", UserRole.USER); 
		UserEntity admin = addUser("admin","admin@gmail.com", UserRole.ADMIN); 
		UserEntity manager = addUser("test1","test1@gmail.com", UserRole.USER); 
		UserEntity dev2 = addUser("test2","test2@gmail.com", UserRole.USER);
		GroupEntity guests = addGroup("guests");
		GroupEntity managers = addGroup("managers");
		GroupEntity developers = addGroup("developers");
		addUserGroup(developer, developers);
		addUserGroup(manager, managers);
		addUserGroup(dev2, developers);
		addUserGroup(dev2, managers);
		FolderEntity home = addFolder("home", null);
		FolderEntity about = addFolder("about", home);
		FolderEntity company = addFolder("company", about);
		FolderEntity team = addFolder("team", about);
		FolderEntity services = addFolder("services", home);
		FolderEntity php = addFolder("php", services);
		addPermission(home, guests, FolderPermissionType.READ);
		addPermission(about, guests, FolderPermissionType.DENIED);
		addPermission(about, developers, FolderPermissionType.READ);
		addPermission(team, developers, FolderPermissionType.WRITE);
		FolderPermissionEntity p = getBusiness().getFolderPermissionBusiness()
				.getPermission(home, developer);
		assertEquals(FolderPermissionType.READ, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getPermission(
				about, developer);
		assertEquals(FolderPermissionType.READ, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getPermission(
				team, developer);
		assertEquals(FolderPermissionType.WRITE, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getGuestPermission(
				home);
		assertEquals(FolderPermissionType.READ, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getGuestPermission(
				about);
		assertEquals(FolderPermissionType.DENIED, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getGuestPermission(
				team);
		assertEquals(FolderPermissionType.DENIED, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getGuestPermission(
				services);
		assertEquals(FolderPermissionType.READ, p.getPermission());
		p = getBusiness().getFolderPermissionBusiness().getGuestPermission(
				php);
		assertEquals(FolderPermissionType.READ, p.getPermission());
	}
	
	
	
}

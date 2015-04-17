

package com.heiduc.business;

import com.heiduc.entity.ContentPermissionEntity;
import com.heiduc.entity.GroupEntity;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.UserEntity;
import com.heiduc.entity.UserGroupEntity;
import com.heiduc.enums.ContentPermissionType;
import com.heiduc.enums.UserRole;

/**
 * @author Alexander Oleynik
 */
public class ContentPermissionBusinessTest extends AbstractBusinessTest {

	private PageEntity addPage(String title, String url) {
		PageEntity page = new PageEntity(title, url);
		getDao().getPageDao().save(page);
		return page;
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
	
	private void addPermission(String url, GroupEntity group, 
			ContentPermissionType perm) {
		ContentPermissionEntity p = new ContentPermissionEntity(url,
				perm, group.getId());
		getDao().getContentPermissionDao().save(p);
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
		addPage("home", "/");
		addPage("about", "/about");
		addPage("company", "/about/company");
		addPage("team", "/about/team");
		addPage("services", "/services");
		addPage("php development", "/services/php");
		addPermission("/", guests, ContentPermissionType.READ);
		addPermission("/about", guests, ContentPermissionType.DENIED);
		addPermission("/about", developers, ContentPermissionType.READ);
		addPermission("/about/team", developers, ContentPermissionType.WRITE);
		addPermission("/", managers, ContentPermissionType.PUBLISH);
		ContentPermissionEntity p = getBusiness().getContentPermissionBusiness()
				.getPermission("/", developer);
		assertEquals(ContentPermissionType.READ, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getPermission(
				"/about", developer);
		assertEquals(ContentPermissionType.READ, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getPermission(
				"/about/team", developer);
		assertEquals(ContentPermissionType.WRITE, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getPermission(
				"/about/team", manager);
		assertEquals(ContentPermissionType.PUBLISH, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getPermission(
				"/about/team", manager);
		assertEquals(ContentPermissionType.PUBLISH, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getGuestPermission(
				"/");
		assertEquals(ContentPermissionType.READ, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getGuestPermission(
				"/about");
		assertEquals(ContentPermissionType.DENIED, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getGuestPermission(
				"/about/team");
		assertEquals(ContentPermissionType.DENIED, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getGuestPermission(
				"/services");
		assertEquals(ContentPermissionType.READ, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getGuestPermission(
				"/services/php");
		assertEquals(ContentPermissionType.READ, p.getPermission());
		p = getBusiness().getContentPermissionBusiness().getPermission(
				"/about/team", dev2);
		assertEquals(ContentPermissionType.PUBLISH, p.getPermission());
	}
	
	
	
}

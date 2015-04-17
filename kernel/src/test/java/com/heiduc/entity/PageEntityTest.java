

package com.heiduc.entity;

import junit.framework.TestCase;

public class PageEntityTest extends TestCase {

	public void testGetParentFriendlyURL() {
		PageEntity page = new PageEntity();
		page.setFriendlyURL("/");
		assertEquals("", page.getParentFriendlyURL());
		assertEquals("", page.getPageFriendlyURL());
		page.setFriendlyURL("/rent");
		assertEquals("/", page.getParentFriendlyURL());
		assertEquals("rent", page.getPageFriendlyURL());
		page.setFriendlyURL("/parent/page");
		assertEquals("/parent", page.getParentFriendlyURL());
		assertEquals("page", page.getPageFriendlyURL());
		page.setFriendlyURL("/parent/page/subpage");
		assertEquals("/parent/page", page.getParentFriendlyURL());
		assertEquals("subpage", page.getPageFriendlyURL());
	}

	public void testSetPageFriendlyURL() {
		PageEntity page = new PageEntity();
		page.setFriendlyURL("/");
		page.setPageFriendlyURL("test");
		assertEquals("/test", page.getFriendlyURL());
		page.setPageFriendlyURL("friend");
		assertEquals("/friend", page.getFriendlyURL());
		page.setFriendlyURL("/page/friend/first");
		page.setPageFriendlyURL("second");
		assertEquals("/page/friend/second", page.getFriendlyURL());
	}

	public void testGetAncestorsURL() {
		PageEntity page = new PageEntity("test", "/");
		assertEquals(1, page.getAncestorsURL().size());
		assertEquals("/", page.getAncestorsURL().get(0));
		page = new PageEntity("test2", "/test");
		assertEquals(1, page.getAncestorsURL().size());
		assertEquals("/test", page.getAncestorsURL().get(0));
		page = new PageEntity("test3", "/test/more/than/this");
		assertEquals(4, page.getAncestorsURL().size());
		assertEquals("/test", page.getAncestorsURL().get(0));
		assertEquals("/test/more", page.getAncestorsURL().get(1));
		assertEquals("/test/more/than", page.getAncestorsURL().get(2));
		assertEquals("/test/more/than/this", page.getAncestorsURL().get(3));
	}
	
}

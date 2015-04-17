

package com.heiduc.business;

import com.heiduc.entity.TagEntity;

public class TagBusinessTest extends AbstractBusinessTest {

	private TagEntity addTag(Long parent, String name) {
		return getDao().getTagDao().save(new TagEntity(parent, name, name));
	}
	
	public void testGetByPath() {
		TagEntity t1 = addTag(null, "t1");
		TagEntity t2 = addTag(null, "t2");
		TagEntity t3 = addTag(t1.getId(), "t3");
		TagEntity t4 = addTag(t3.getId(), "t4");
		TagEntity t = getBusiness().getTagBusiness().getByPath("/t1");
		assertNotNull(t);
		assertEquals("t1", t.getName());
		t = getBusiness().getTagBusiness().getByPath("/t2");
		assertNotNull(t);
		assertEquals("t2", t.getName());
		t = getBusiness().getTagBusiness().getByPath("/t1/t3");
		assertNotNull(t);
		assertEquals("t3", t.getName());
		t = getBusiness().getTagBusiness().getByPath("/t1/t3/t4");
		assertNotNull(t);
		assertEquals("t4", t.getName());
	}

	public void testGetPath() {
		TagEntity t1 = addTag(null, "t1");
		TagEntity t2 = addTag(null, "t2");
		TagEntity t3 = addTag(t1.getId(), "t3");
		TagEntity t4 = addTag(t3.getId(), "t4");
		String p = getBusiness().getTagBusiness().getPath(t4);
		assertEquals("/t1/t3/t4", p);
		p = getBusiness().getTagBusiness().getPath(t2);
		assertEquals("/t2", p);
		p = getBusiness().getTagBusiness().getPath(t3);
		assertEquals("/t1/t3", p);
	}
	
	
}

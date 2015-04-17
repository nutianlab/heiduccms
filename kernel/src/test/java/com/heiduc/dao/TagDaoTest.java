

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.TagEntity;

public class TagDaoTest extends AbstractDaoTest {

	private TagEntity addTag(Long parent, String name) {
		return getDao().getTagDao().save(new TagEntity(parent, name, name));
	}
	
	public void testGetByName() {
		addTag(null, "test1");
		addTag(1L, "test2");
		addTag(2L, "test3");
		TagEntity tag = getDao().getTagDao().getByName(null, "test1");
		assertNotNull(tag);
		assertEquals("test1", tag.getName());
	}	
	
	public void testSelectByParent() {
		addTag(null, "test1");
		addTag(1L, "test2");
		addTag(2L, "test3");
		List<TagEntity> tags = getDao().getTagDao().selectByParent(null);
		assertNotNull(tags);
		assertEquals(1, tags.size());
		assertEquals("test1", tags.get(0).getName());
	}	
	
}

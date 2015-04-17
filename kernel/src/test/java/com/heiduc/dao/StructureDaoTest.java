

package com.heiduc.dao;

import java.util.ArrayList;
import java.util.List;

import com.heiduc.entity.StructureEntity;
import com.heiduc.entity.StructureTemplateEntity;
import com.heiduc.enums.StructureTemplateType;

public class StructureDaoTest extends AbstractDaoTest {

	private StructureEntity addStructure(String title, String content) {
		StructureEntity s = getDao().getStructureDao().save(
				new StructureEntity(title, content));
		getDao().getStructureTemplateDao().save(new StructureTemplateEntity(
				title, title, s.getId(), StructureTemplateType.VELOCITY, content));
		return s;
	}
	
	public void testGetByTitle() {
		addStructure("sitemap", "file1");
		addStructure("black", "file2");
		addStructure("super", "file1");
		StructureEntity s = getDao().getStructureDao().getByTitle("sitemap");
		assertNotNull(s);
		assertEquals("file1", s.getContent());
		s = getDao().getStructureDao().getByTitle(null);
		assertNull(s);
		s = getDao().getStructureDao().getByTitle("megahit");
		assertNull(s);
		StructureTemplateEntity st = getDao().getStructureTemplateDao()
				.getByName("sitemap");
		assertNotNull(st);
	}	

	public void testRemove() {
		StructureEntity s1 = addStructure("sitemap", "file1");
		StructureEntity s2 = addStructure("black", "file2");
		StructureEntity s3 = addStructure("super", "file1");
		List<Long> ids = new ArrayList<Long>();
		ids.add(s1.getId());
		ids.add(s2.getId());
		ids.add(s3.getId());
		getDao().getStructureDao().remove(ids);
		assertEquals(0, getDao().getStructureDao().select().size());
		assertEquals(0, getDao().getStructureTemplateDao().select().size());
	}	
	
	
}

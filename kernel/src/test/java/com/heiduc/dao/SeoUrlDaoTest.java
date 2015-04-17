

package com.heiduc.dao;

import com.heiduc.entity.SeoUrlEntity;


public class SeoUrlDaoTest extends AbstractDaoTest {

	private SeoUrlEntity addSeoUrl(String from, String to) {
		return getDao().getSeoUrlDao().save(new SeoUrlEntity(from, to));
	}
	
	public void testGetByFrom() {
		addSeoUrl("/man", "/woman");
		addSeoUrl("/black", "/white");
		addSeoUrl("/super/man", "/ordinal/woman");
		SeoUrlEntity s = getDao().getSeoUrlDao().getByFrom("/man");
		assertNotNull(s);
		assertEquals("/woman", s.getToLink());
		s = getDao().getSeoUrlDao().getByFrom(null);
		assertNull(s);
		s = getDao().getSeoUrlDao().getByFrom("/megahit");
		assertNull(s);
	}	
	
}

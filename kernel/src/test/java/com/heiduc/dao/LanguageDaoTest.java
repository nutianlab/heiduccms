

package com.heiduc.dao;

import com.heiduc.entity.LanguageEntity;


public class LanguageDaoTest extends AbstractDaoTest {

	private LanguageEntity addLanguage(String code) {
		return getDao().getLanguageDao().save(new LanguageEntity(code, code));
	}
	
	public void testGetByCode() {
		addLanguage("en");
		addLanguage("ru");
		addLanguage("uk");
		addLanguage("fr");
		LanguageEntity l = getDao().getLanguageDao().getByCode("eng");
		assertNull(l);
		l = getDao().getLanguageDao().getByCode(null);
		assertNull(l);
		l = getDao().getLanguageDao().getByCode("en");
		assertNotNull(l);
		assertEquals("en", l.getCode());
	}	
	
}

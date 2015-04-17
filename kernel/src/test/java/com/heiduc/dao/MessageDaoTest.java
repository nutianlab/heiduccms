

package com.heiduc.dao;

import java.util.List;

import com.heiduc.entity.LanguageEntity;
import com.heiduc.entity.MessageEntity;


public class MessageDaoTest extends AbstractDaoTest {

	private MessageEntity addMessage(String code, String languageCode,
			String value) {
		return getDao().getMessageDao().save(new MessageEntity(code, 
				languageCode, value));
	}
	
	private void init() {
		addMessage("test", "en", "test_en");
		addMessage("test", "ru", "test_ru");
		addMessage("test", "uk", "test_uk");
		addMessage("test2", "en", "test2_en");
		addMessage("test2", "ru", "test2_ru");
		addMessage("test2", "uk", "test2_uk");
	}
	
	public void testSelectByCode() {
		init();
		List<MessageEntity> list = getDao().getMessageDao().selectByCode("test");
		assertEquals(3, list.size());
		list = getDao().getMessageDao().selectByCode(null);
		assertEquals(0, list.size());
		list = getDao().getMessageDao().selectByCode("null");
		assertEquals(0, list.size());
	}
	
	public void testGetByCode() {
		init();
		MessageEntity m = getDao().getMessageDao().getByCode("test", "en");
		assertNotNull(m);
		assertEquals("test_en", m.getValue());
		m = getDao().getMessageDao().getByCode("test3", "en");
		assertNull(m);
		m = getDao().getMessageDao().getByCode(null, null);
		assertNull(m);
		m = getDao().getMessageDao().getByCode("test", null);
		assertNull(m);
		m = getDao().getMessageDao().getByCode(null, "en");
		assertNull(m);
	}

	public void testSelect() {
		init();
		List<MessageEntity> list = getDao().getMessageDao().select("en");
		assertEquals(2, list.size());
		list = getDao().getMessageDao().select("ru");
		assertEquals(2, list.size());
		String lang = null;
		list = getDao().getMessageDao().select(lang);
		assertEquals(0, list.size());
		list = getDao().getMessageDao().select("fr");
		assertEquals(0, list.size());
	}
	
}

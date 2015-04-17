

package com.heiduc.utils;

import java.text.ParseException;
import java.util.Date;

import junit.framework.TestCase;

public class DateUtilTest extends TestCase {

	public void testToDate() {
		Date dt = new Date();
		String dtStr = DateUtil.toString(dt);
		try {
			Date dt2 = DateUtil.toDate(dtStr);
			assertEquals(dtStr, DateUtil.toString(dt2));
		}
		catch (ParseException e) {
		}
		assertEquals("01.01.2010", String.format("01.%02d.%d", 1, 2010));
	}
	
}

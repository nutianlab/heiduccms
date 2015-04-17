

package com.heiduc.velocity;

import java.util.TimeZone;

import org.apache.velocity.tools.generic.DateTool;

/**
 * @author Alexander Oleynik
 */
public class MyDateTool extends DateTool {

	public MyDateTool(TimeZone tz) {
		super();
		setTimeZone(tz);
	}
	
}



package com.heiduc.common;


/**
 * 
 * @author Alexander Oleynik
 *
 */
public class AppEngine {

	public static boolean isProduction() {
		String value = System.getProperty("com.google.appengine.runtime.environment");
		if (value != null && value.equals("Production")) {
			return true;
		}
		return false;
	}

}

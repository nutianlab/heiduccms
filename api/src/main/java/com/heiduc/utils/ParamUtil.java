

package com.heiduc.utils;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.josephoconnell.html.HTMLInputFilter;

/**
 * @author Alexander Oleynik
 */
public class ParamUtil {

	static public Integer getInteger(final String s, 
			final Integer defaultValue) {
		try {
			return Integer.valueOf(s);
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	static public Long getLong(final String s, 
			final Long defaultValue) {
		try {
			return Long.valueOf(s);
		}
		catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	static public Boolean getBoolean(final String s, 
			final Boolean defaultValue) {
		try {
			return Boolean.valueOf(s);
		}
		catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Convert string to date from format dd.mm.yyyy
	 * @param s
	 * @param defaultValue
	 * @return
	 */
	static public Date getDate(final String s, 
			final Date defaultValue) {
		try {
			return DateUtil.toDate(s);
		}
		catch (Exception e) {
			return defaultValue;
		}
	}

	private static HTMLInputFilter xssFilter = new HTMLInputFilter();
	
	static public String filterXSS(String value) {
		//return StringEscapeUtils.escapeHtml(xssFilter.filter(value));
		
//		return StringEscapeUtils.escapeHtml(value);
		return HtmlUtils.htmlEscape(value);
	}
	
}

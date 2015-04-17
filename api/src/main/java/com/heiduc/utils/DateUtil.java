

package com.heiduc.utils;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

import com.heiduc.common.HeiducContext;

public class DateUtil {

	private static final Format formatter = new SimpleDateFormat("dd.MM.yyyy");
	private static final DateFormat dateTimeFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	private static final DateFormat timeFormatter = new SimpleDateFormat("HH:mm");
	private static final Format headerFormatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");
	
	public static String toString(final Date date) {
		if (date == null) return "";
		return formatter.format(date);
	}
	
	public static TimeZone getTimeZone() {
		return HeiducContext.getInstance().getBusiness().getTimeZone();
	}
	
	public static String dateTimeToString(final Date date) {
		if (date == null) {
			return "";
		}
		TimeZone tz = getTimeZone();
		if (!tz.equals(dateTimeFormatter.getTimeZone())) {
			dateTimeFormatter.setTimeZone(tz);
		}
		return dateTimeFormatter.format(date);
	}
	
	public static String timeToString(final Date date) {
		if (date == null) {
			return "";
		}
		TimeZone tz = getTimeZone();
		if (!tz.equals(timeFormatter.getTimeZone())) {
			timeFormatter.setTimeZone(tz);
		}
		return timeFormatter.format(date);
	}

	public static Date dateTimeToDate(final String str) throws ParseException {
		TimeZone tz = getTimeZone();
		if (!tz.equals(dateTimeFormatter.getTimeZone())) {
			dateTimeFormatter.setTimeZone(tz);
		}
		return (Date)dateTimeFormatter.parseObject(str);
	}

	public static Date toDate(final String str) throws ParseException {
		return (Date)formatter.parseObject(str);
	}

	public static String toHeaderString(final Date date) {
		if (date == null) {
			return "";
		}
		return headerFormatter.format(date);
	}
	
	
}

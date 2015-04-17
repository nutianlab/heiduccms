

package com.heiduc.utils;


/**
 * @author Alexander Oleynik
 */
public class UrlUtil {

	/**
	 * Extract parent page friendly URL.
	 * For page /my/test/hope parent url is /my/test
	 * @param friendlyUrl - page friendlyURL
	 * @return parent friendlyURL.
	 */
	static public String getParentFriendlyURL(final String friendlyUrl) {
		if (friendlyUrl == null || friendlyUrl.equals("/")) {
			return "";
		}
		int lastSlash = friendlyUrl.lastIndexOf('/');
		if (lastSlash == 0 || lastSlash == -1) {
			return "/";
		}
		return friendlyUrl.substring(0, lastSlash);
	}

	/**
	 * Extract last name in friendlyURL.
	 * For page url /my/test/hope last name will be "hope"
	 * @param friendlyUrl
	 * @return
	 */
	static public String getNameFromFriendlyURL(final String friendlyUrl) {
		if (friendlyUrl == null || friendlyUrl.equals("/")) {
			return "";
		}
		int lastSlash = friendlyUrl.lastIndexOf('/');
		if (lastSlash == -1) {
			return "";
		}
		return friendlyUrl.substring(lastSlash + 1, friendlyUrl.length());
	}
	
	/**
	 * Convert title to url. Replace spaces to _
	 */
	static public String titleToURL(String title) {
		return title.replace(' ', '_').toLowerCase();
	}
	
}

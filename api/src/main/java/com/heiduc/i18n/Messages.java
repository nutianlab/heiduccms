

package com.heiduc.i18n;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.LanguageEntity;

/**
 * Message bundle helper class for creatng localized messages from Java code.
 * 
 * @author Alexander Oleynik
 *
 */
public class Messages {

	private static final Log logger  = LogFactory.getLog(Messages.class);
	
	public static final String I18N_CACHE_KEY = "i18n_";
	
	private static final Locale[] supportedLocales = {
		Locale.ENGLISH,
		Locale.GERMAN,
		new Locale("es"),
		new Locale("sv"),
		new Locale("ru"),
		new Locale("cs"),
		new Locale("tr"),
		new Locale("pt", "BR"),
		Locale.JAPANESE,
		Locale.TRADITIONAL_CHINESE,
		Locale.SIMPLIFIED_CHINESE};
	
	private static Map<Locale, HeiducResourceBundle> bundles = 
			new HashMap<Locale, HeiducResourceBundle>();
	
	private static HeiducResourceBundle getBundle(HttpServletRequest request) {
		Locale sessionLocale = HeiducContext.getInstance().getSession().getLocale();
		Locale locale = sessionLocale != null ? sessionLocale : request.getLocale();
		return getBundle(locale);
	}
	
	private static HeiducResourceBundle getDefaultBundle() {
		return getBundle(Locale.ENGLISH);
	}

	private static HeiducResourceBundle getBundle(Locale locale) {
		if (!bundles.containsKey(locale)) {
			bundles.put(locale, new HeiducResourceBundle(locale));
		}
		return bundles.get(locale);
	}
	
	public static String get(String key, Object...objects) {
		HeiducContext ctx = HeiducContext.getInstance();
		String pattern = "not found";
		if (isLocaleSupported(ctx.getLocale())) {
			pattern = getBundle(ctx.getRequest()).getString(key);
		}
		else {
			pattern = getDefaultBundle().getString(key);
		}
		if (objects != null) {
			MessageFormat formatter = new MessageFormat("");
			formatter.setLocale(ctx.getLocale());
			formatter.applyPattern(pattern);
			pattern = formatter.format(objects);
		}
		return pattern;
	}
	
	private static Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}
	
	/**
	 * Generate JavaScript JSON message bundle for JavaScript messages 
	 * localization.
	 * @return JS file.
	 */
	public static String getJSMessages() {
		HeiducContext ctx = HeiducContext.getInstance();
		String cached = (String)getBusiness().getSystemService().getCache().get(
				I18N_CACHE_KEY + ctx.getLanguage());
		
		logger.debug("checking is i18n_" + ctx.getLanguage() + " in cache: " + (cached != null ? "yes":"no"));
		
		if (cached != null) return cached;	
		Map<String, String> messages = new HashMap<String, String>();
		HeiducResourceBundle defaultBundle = getDefaultBundle();
		for (String key : Collections.list(defaultBundle.getKeys())) {
			try {
				messages.put(key, defaultBundle.getString(key));
			}
			catch (MissingResourceException e) {
			}
		}
		HeiducResourceBundle bundle = getBundle(ctx.getRequest());
		for (String key : Collections.list(bundle.getKeys())) {
			try {
				messages.put(key, bundle.getString(key));
			}
			catch (MissingResourceException e) {
			}
		}
		ConfigEntity config = HeiducContext.getInstance().getConfig();
		StringBuffer result = new StringBuffer();
		result.append("var locale = '").append(ctx.getLocale().toString())
				.append("';\n");
		result.append("var locale_language = '")
			.append(ctx.getLocale().getLanguage()).append("';\n");
		result.append("var default_language = '")
			.append(getBusiness().getDefaultLanguage()).append("';\n");
		result.append(
				"function messages(key) {\n" 
			  + "  if (_messages[key] == 'undefined') {\n"
			  + "    return '__' + key + '__';\n"
			  + "  } else {\n"
			  + "    return _messages[key];\n"
			  + "  }\n"
			  + "}\n");
		result.append("var _messages = {\n");
		int i = 0;
		for (String key : messages.keySet()) {
			if (i++ > 0) {
				result.append(",");
			}
			result.append(" '").append(key).append("' : '")
				.append(filterForJS(messages.get(key)))
				.append("'\n");
		}
		result.append("};");
		
		cached = result.toString();
		getBusiness().getSystemService().getCache().put(
				I18N_CACHE_KEY + ctx.getLanguage(), cached);

		logger.debug("put i18n_" + ctx.getLanguage() + " to cache");
		
		return cached;
	}
	
	public static void resetCache() {
		HeiducContext ctx = HeiducContext.getInstance();
		for (LanguageEntity language : ctx.getBusiness().getDao().getLanguageDao().select()) {
			getBusiness().getSystemService().getCache().remove(
					I18N_CACHE_KEY + language.getCode());
		}
	}
	
	private static String filterForJS(String msg) {
		return msg.replaceAll("\n", "").replaceAll("'", "\\\\'");
	}
	
	public static boolean isLocaleSupported(Locale locale) {
		for (Locale l : supportedLocales) {
			if (l.equals(locale)) {
				return true;
			}
		}
		return false;
	}
	
}

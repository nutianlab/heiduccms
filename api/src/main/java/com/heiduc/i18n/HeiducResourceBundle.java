package com.heiduc.i18n;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.heiduc.business.Business;
import com.heiduc.business.plugin.PluginEntryPoint;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PluginEntity;

/**
 * 
 * @author Alexander Oleynik
 * 
 */
public class HeiducResourceBundle extends ResourceBundle {

	private static final String BUNDLE_NAME = "com.heiduc.resources.messages";

	private static final Log logger = LogFactory.getLog(
			HeiducResourceBundle.class);
	
	private Locale locale;

	public HeiducResourceBundle(Locale aLocale) {
		locale = aLocale;
	}

	@Override
	public Enumeration<String> getKeys() {
		List<String> result = new ArrayList<String>();
		for (ResourceBundle bundle : getResourceBundles()) {
			result.addAll(Collections.list(bundle.getKeys()));
		}
		return Collections.enumeration(result);
	}

	private Business getBusiness() {
		return HeiducContext.getInstance().getBusiness();
	}

	@Override
	protected Object handleGetObject(String key) {
		Object result = null;
		for (ResourceBundle bundle : getResourceBundles()) {
			try {
				result = bundle.getObject(key);
			}
			catch (MissingResourceException e) {
			}
			if (result != null) {
				return result;
			}
		}
		return "_" + key + "_";
	}

	private List<ResourceBundle> getResourceBundles() {
		List<ResourceBundle> result = new ArrayList<ResourceBundle>();
		for (PluginEntity plugin : getBusiness().getDao().getPluginDao()
				.selectEnabled()) {
			PluginEntryPoint entryPoint = getBusiness().getPluginBusiness()
					.getEntryPoint(plugin);
			if (entryPoint != null && entryPoint.getBundleName() != null) {
				result.add(ResourceBundle.getBundle(
						entryPoint.getBundleName(),
						locale, 
						getBusiness().getPluginBusiness().getClassLoader(plugin)));
			}
		}
		result.add(ResourceBundle.getBundle(BUNDLE_NAME, locale));
		return result;
	}
	
}

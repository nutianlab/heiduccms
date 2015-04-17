

package com.heiduc.business.impl.pagefilter.fragments;

import org.apache.commons.lang.StringUtils;

import com.heiduc.business.Business;
import com.heiduc.business.impl.pagefilter.ContentFragment;
import com.heiduc.entity.ConfigEntity;
import com.heiduc.entity.PageEntity;

public class GoogleAnalyticsFragment implements ContentFragment {

	@Override
	public String get(Business business, PageEntity page) {
		ConfigEntity config = business.getConfigBusiness().getConfig();
		if (!StringUtils.isEmpty(config.getGoogleAnalyticsId())) {
			String id = config.getGoogleAnalyticsId();
			return getGoogleAnalyticsCode(id);
		}
		return "";
	}
	
	private static String getGoogleAnalyticsCode(final String id) { 
		return  
		"\n<!-- Google Analytics -->\n"
		+ "<script type=\"text/javascript\">\n"
		+ "var _gaq = _gaq || [];\n"
		+ "_gaq.push(['_setAccount', '" + id + "']);\n"
		+ "_gaq.push(['_trackPageview']);\n"
		+ "_gaq.push(['_trackPageLoadTime']);\n"
		+ "(function() {\n"
		+ "var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;\n"
		+ "ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';\n"
		+ "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);\n"
		+ "})();\n"
	    + "</script>\n";
	}

}

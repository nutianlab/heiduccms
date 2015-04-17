

package com.heiduc.business;

import java.util.Map;

/**
 * @author Alex Oleynik
 */
public interface RewriteUrlBusiness {

	String rewrite(String url);
	
	void addRule(String from, String to);
	
	void addRules(Map<String,String> rules);
	
	void removeRules(Map<String,String> rules);
}

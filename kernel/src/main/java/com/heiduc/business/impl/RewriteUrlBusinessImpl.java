

package com.heiduc.business.impl;

import java.util.HashMap;
import java.util.Map;

import com.heiduc.business.RewriteUrlBusiness;

public class RewriteUrlBusinessImpl extends AbstractBusinessImpl 
	implements RewriteUrlBusiness {

	private Map<String,String> rules = new HashMap<String, String>();
	
	@Override
	public void addRule(String from, String to) {
		rules.put(from, to);
	}

	@Override
	public String rewrite(String url) {
		String newUrl = null;
		for (String from : rules.keySet()) {
			if (url.matches(from)) {
				newUrl = url.replaceAll(from, rules.get(from));
			}
		}
		return newUrl != null ? newUrl : url;
	}

	@Override
	public void addRules(Map<String, String> rules) {
		this.rules.putAll(rules);
	}

	@Override
	public void removeRules(Map<String, String> rules) {
		for (String key : rules.keySet()) {
			this.rules.remove(key);
		}
	}
	
}

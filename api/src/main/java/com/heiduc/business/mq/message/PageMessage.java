

package com.heiduc.business.mq.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class PageMessage extends SimpleMessage {

	private Map<String, Set<Long>> pages;
	
	public PageMessage(Topic topic) {
		this(topic, (String)null);
	}
	
	public PageMessage(Topic topic, QueueSpeed speed) {
		this(topic);
		setSpeed(speed);
	}

	public PageMessage(Topic topic, String message) {
		super(topic, message);
		pages = new HashMap<String, Set<Long>>();
	}

	public PageMessage(Topic topic, String pageURL, 
			Long pageId) {
		this(topic);
		addPage(pageURL, pageId);
	}

	public void addPage(String url, Long id) {
		if (!pages.containsKey(url)) {
			pages.put(url, new HashSet<Long>());
		}
		pages.get(url).add(id);
	}
	
	public Map<String, Set<Long>> getPages() {
		return pages;
	}
}

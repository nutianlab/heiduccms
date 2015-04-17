package com.heiduc.business.page.impl;

import java.util.List;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.entity.PageEntity;

public class PageSetAttributeTask extends AbstractSubscriber {

	@Override
	public void onMessage(Message message) {
		PageSetAttributeMessage msg = (PageSetAttributeMessage)message;
		List<PageEntity> versions = getDao().getPageDao().selectByUrl(
				msg.getUrl());
		for (PageEntity version : versions) {
			version.setAttribute(msg.getName(), msg.getLanguage(), 
					msg.getValue());
			getDao().getPageDao().save(version);
		}
		if (!versions.isEmpty()) {
			List<PageEntity> children = getDao().getPageDao().getByParent(
					versions.get(0).getFriendlyURL());
			for (PageEntity child : children) {
				getBusiness().getMessageQueue().publish(
						new PageSetAttributeMessage(child.getFriendlyURL(), 
								msg.getName(), msg.getLanguage(), 
								msg.getValue()));
			}
		}
	}

}



package com.heiduc.business.impl.mq.subscriber;

import java.util.Set;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.PageMessage;
import com.heiduc.common.HeiducContext;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.helper.UserHelper;

/**
 * Reindex changed pages.
 * 
 * @author Alexander Oleynik
 *
 */
public class IndexChangedPages extends AbstractSubscriber {

	public void onMessage(Message message) {
		PageMessage msg = (PageMessage)message;
		try {
			HeiducContext.getInstance().setUser(UserHelper.ADMIN);
			for (Set<Long> pages : msg.getPages().values()) {
				for (Long pageId : pages) {
					PageEntity page = getDao().getPageDao().getById(pageId);
					if (page != null) {
						getBusiness().getSearchEngine().updateIndex(
								page.getId());
					}
				}
			}
			getBusiness().getSearchEngine().saveIndex();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
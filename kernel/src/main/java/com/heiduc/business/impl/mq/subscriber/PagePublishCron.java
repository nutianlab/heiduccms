

package com.heiduc.business.impl.mq.subscriber;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.PageMessage;
import com.heiduc.entity.PageEntity;
import com.heiduc.enums.PageState;

/**
 * Reset cache for fresh scheduled published and unpublished pages.
 * 
 * @author Alexander Oleynik
 *
 */
public class PagePublishCron extends AbstractSubscriber {

	public void onMessage(Message message) {
		logger.info("Page publish cron...");
		for (PageEntity page : getDao().getPageDao()
				.getCurrentHourPublishedPages()) {
			if (page.getState().equals(PageState.APPROVED)) {
				logger.info("Found published " + page.getFriendlyURL());
				getBusiness().getSystemService().getPageCache().remove(
					page.getFriendlyURL());
				getBusiness().getMessageQueue().publish(new PageMessage(
					Topic.PAGE_CACHE_CLEAR, page.getFriendlyURL(), page.getId()));
			}
		}
		for (PageEntity page : getDao().getPageDao()
				.getCurrentHourUnpublishedPages()) {
			if (page.getState().equals(PageState.APPROVED)) {
				logger.info("Found unpublished " + page.getFriendlyURL());
				getBusiness().getSystemService().getPageCache().remove(
					page.getFriendlyURL());
				getBusiness().getMessageQueue().publish(new PageMessage(
					Topic.PAGE_CACHE_CLEAR, page.getFriendlyURL(), page.getId()));
			}
		}
	}
}
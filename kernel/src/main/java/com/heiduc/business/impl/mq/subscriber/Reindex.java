

package com.heiduc.business.impl.mq.subscriber;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.message.IndexMessage;
import com.heiduc.common.HeiducContext;
import com.heiduc.common.RequestTimeoutException;
import com.heiduc.entity.PageEntity;
import com.heiduc.entity.helper.UserHelper;

/**
 * Reindex pages.
 * 
 * @author Alexander Oleynik
 *
 */
public class Reindex extends AbstractSubscriber {

	private int currentNumber = 0;
	private int count = 0;
	private IndexMessage msg;
	
	public void onMessage(Message message) {
		try {
			HeiducContext.getInstance().setUser(UserHelper.ADMIN);
			msg = (IndexMessage)message;
			reindexPage(getDao().getPageDao().getByUrl("/"));
			getBusiness().getSearchEngine().saveIndex();
			logger.info("Reindex finished. Reindexed " + count + " pages.");
		}
		catch (RequestTimeoutException e) {
			getBusiness().getSearchEngine().saveIndex();
			getBusiness().getMessageQueue().publish(new IndexMessage(
					currentNumber));
			logger.info("Reindexed " + count + " pages");
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void reindexPage(PageEntity page) throws RequestTimeoutException {
		currentNumber++;
		if (getBusiness().getSystemService().getRequestCPUTimeSeconds() > 20) {
			throw new RequestTimeoutException();
		}
		if (msg.getNumber() <= currentNumber) {
			getBusiness().getSearchEngine().updateIndex(page.getId());
			count++;
		}
		for (PageEntity child : getDao().getPageDao().getByParent(
				page.getFriendlyURL())) {
			reindexPage(child);
		}
	}

}
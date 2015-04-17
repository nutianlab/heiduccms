

package com.heiduc.business.impl.mq.subscriber;


import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.PageMessage;
import com.heiduc.entity.PageDependencyEntity;
import com.heiduc.utils.FolderUtil;

/**
 * Clear cache for all dependent pages.
 * 
 * @author Alexander Oleynik
 *
 */
public class PageCacheClear extends AbstractSubscriber {

	public void onMessage(Message message) {
		PageMessage msg = (PageMessage)message;
		try {
			for (String url : msg.getPages().keySet()) {
				clearDependency(url);			
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void clearDependency(String url) {
		String dependency = "";
		String[] parts = FolderUtil.getPathChain(url);
		PageMessage message = new PageMessage(Topic.PAGE_CACHE_CLEAR, 
				QueueSpeed.LOW);
		
		for (String part : parts) {
			dependency += "/" + part;
			for (PageDependencyEntity entity : getDao().getPageDependencyDao()
					.selectByDependency(dependency)) {

				if (getBusiness().getSystemService().getPageCache()
						.contains(entity.getPage())) {
				
					getBusiness().getSystemService().getPageCache().remove(
							entity.getPage());
					
					message.addPage(entity.getPage(), -1L);
				}
			}
		}
		getBusiness().getMessageQueue().publish(message);
	}
	
}
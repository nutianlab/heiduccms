

package com.heiduc.business.impl.mq.subscriber;


import org.heiduc.api.datastore.DatastoreService;
import org.heiduc.api.datastore.Entity;
import org.heiduc.api.datastore.PreparedQuery;
import org.heiduc.api.datastore.Query;

import com.heiduc.business.impl.mq.AbstractSubscriber;
import com.heiduc.business.mq.Message;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;
import com.heiduc.business.mq.message.SimpleMessage;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class EntityRemove extends AbstractSubscriber {

	public void onMessage(Message message) {
		SimpleMessage msg = (SimpleMessage) message;
		String kind =  msg.getMessage();
		DatastoreService datastore = getBusiness().getSystemService()
	        		.getDatastore(); 
        Query query = new Query(kind);
        PreparedQuery results = datastore.prepare(query);
        int i = 0;
        boolean end = true;
        for (Entity entity : results.asIterable()) {
        	datastore.delete(entity.getKey());
        	i++;
    		if (getBusiness().getSystemService()
    				.getRequestCPUTimeSeconds() > 25) {
    			addEntityRemoveTask(kind);
    			end = false;
    			break;
    		}
        }
		logger.info("Deleted " + i + " entities " + kind);
        if (end) {
        	logger.info("Finished entity removing.");
        }
	}
	
	private void addEntityRemoveTask(String kind) {
		getMessageQueue().publish(new SimpleMessage(Topic.ENTITY_REMOVE, 
				kind, QueueSpeed.LOW));
		logger.info("Added new entity remove task " + kind);
	}
	
}

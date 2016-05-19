package org.heiduc.api.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class QueueFactory {

	private static final Map<String, Queue> queueMap = new HashMap<String, Queue>();
	
	public static Queue getDefaultQueue() {
		return getQueue("default");
	}

	public static Queue getQueue(String queueName) {
		Queue queue = (Queue)queueMap.get(queueName);
	    if (queue == null) {
	      queue = new QueueImpl(queueName);
	      queueMap.put(queueName, queue);
	    }
	    return queue;
	}

}

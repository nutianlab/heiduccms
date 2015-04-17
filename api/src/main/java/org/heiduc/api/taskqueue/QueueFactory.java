package org.heiduc.api.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class QueueFactory {

	private static final Map<String, Queue> queueMap = new HashMap();
	
	public static Queue getDefaultQueue() {
		return getQueue("default");
	}

	public static Queue getQueue(String queueName) {
//		System.out.println("=========QueueFactory getQueue=========");
		Queue queue = (Queue)queueMap.get(queueName);
	    if (queue == null) {
//	      System.out.println("=========queue is null=========");
	      queue = new QueueImpl(queueName);
	      queueMap.put(queueName, queue);
	    }
	    return queue;
	}

}

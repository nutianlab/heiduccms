

package com.heiduc.business.mq;


/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface MessageQueue {
	
	void publish(Message message);

	void subscribe(Topic topic, Class subscriber);

	void subscribe(String topic, Class subscriber);

	void unsubscribe(String topic, Class subscriber);
	
	void execute(Message message);
	
}

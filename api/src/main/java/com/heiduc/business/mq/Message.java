

package com.heiduc.business.mq;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public interface Message {

	String getTopic();

	QueueSpeed getSpeed();
	
	String getCommandClassName();
	
}

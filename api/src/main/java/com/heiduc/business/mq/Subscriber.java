

package com.heiduc.business.mq;

/**
 * Subscriber is thread safe. It's created then onMessage called and 
 * garbage collected.
 * 
 * @author Alexander Oleynik
 *
 */
public interface Subscriber {

	void onMessage(Message message);
}

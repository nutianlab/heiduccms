

package com.heiduc.business.mq.message;

import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class IndexMessage extends SimpleMessage {

	private int number;

	public IndexMessage(int aNumber) {
		super(Topic.REINDEX);
		setSpeed(QueueSpeed.LOW);
		number = aNumber;
	}

	public IndexMessage() {
		this(0);
	}
	
	public int getNumber() {
		return number;
	}
	
}

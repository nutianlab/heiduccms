

package com.heiduc.business.mq.message;

import com.heiduc.business.mq.AbstractMessage;
import com.heiduc.business.mq.QueueSpeed;
import com.heiduc.business.mq.Topic;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public class SimpleMessage extends AbstractMessage {

	private String message;
	
	public SimpleMessage(String topic, String msg) {
		super();
		setTopic(topic);
		message = msg;
	}

	public SimpleMessage(Topic topic, String msg) {
		this(topic.name(), msg);
	}

	public SimpleMessage(Class clazz) {
		this("simple.command", "");
		setCommandClassName(clazz.getName());
	}

	public SimpleMessage(Topic topic, String msg, QueueSpeed speed) {
		this(topic.name(), msg);
		setSpeed(speed);
	}

	public SimpleMessage(Topic topic) {
		this(topic.name(), null);
	}

	public String getMessage() {
		return message;
	}
}

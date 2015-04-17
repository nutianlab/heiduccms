

package com.heiduc.business.mq;

import java.io.Serializable;

/**
 * 
 * @author Alexander Oleynik
 *
 */
public abstract class AbstractMessage implements Message, Serializable {

	private String topic;
	private QueueSpeed speed;
	private String commandClassName;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public QueueSpeed getSpeed() {
		return speed;
	}

	public void setSpeed(QueueSpeed speed) {
		this.speed = speed;
	}

	public String getCommandClassName() {
		return commandClassName;
	}

	public void setCommandClassName(String commandClassName) {
		this.commandClassName = commandClassName;
	}
	
}

package com.heiduc.command;

import java.util.Map;

import com.heiduc.business.mq.AbstractMessage;
import com.heiduc.business.mq.QueueSpeed;

public class CommandMessage extends AbstractMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clientId;
	private Map<String, String> params;
	
	public CommandMessage(String clientId, String commandClass, 
			Map<String, String> params) {
		super();
		setTopic(commandClass);
		setSpeed(QueueSpeed.MEDIUM);
		setCommandClassName(commandClass);
		this.clientId = clientId;		
		this.params = params;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public String getClientId() {
		return clientId;
	}
	
}
